package com.smartlink2sd.engine

import android.content.Context
import com.smartlink2sd.settings.SettingsStore
import java.io.File

class OperationEngine(
    private val context: Context
) {
    private val settingsStore = SettingsStore(context)
    private val backupManager = BackupManager(context)
    private val fileOperations = FileOperationManager()
    private val verifier = VerificationManager()

    fun prepareLink(
        packageName: String,
        component: String
    ): OperationResult {
        val settings = settingsStore.load()
        val contextSnapshot = OperationContext(
            packageName = packageName,
            operation = "LINK",
            settings = settings
        )

        val log = OperationLog()
        log.add(OperationStage.IDLE, "Preparing link operation.")

        val decision = OperationPolicy.canLink(
            settings = contextSnapshot.settings,
            packageName = contextSnapshot.packageName,
            requestedComponent = component
        )

        if (!decision.allowed) {
            log.add(OperationStage.FAILED, decision.reason)
            return OperationResult(
                success = false,
                stage = OperationStage.FAILED,
                message = decision.reason
            )
        }

        log.add(OperationStage.PREPARE, "Link operation is allowed.")

        return OperationResult(
            success = true,
            stage = OperationStage.PREPARE,
            message = "Operation prepared successfully."
        )
    }

    fun transfer(
        packageName: String,
        source: File,
        target: File,
        move: Boolean
    ): OperationResult {
        val settings = settingsStore.load()
        val contextSnapshot = OperationContext(
            packageName = packageName,
            operation = if (move) "MOVE" else "COPY",
            settings = settings
        )

        val log = OperationLog()

        val touchDecision = OperationPolicy.canTouch(contextSnapshot.settings)
        if (!touchDecision.allowed) {
            return OperationResult(
                success = false,
                stage = OperationStage.FAILED,
                message = touchDecision.reason
            )
        }

        if (!source.exists()) {
            return OperationResult(
                success = false,
                stage = OperationStage.FAILED,
                message = "Source does not exist."
            )
        }

        if (target.exists()) {
            return OperationResult(
                success = false,
                stage = OperationStage.PREPARE,
                message = "Target already exists."
            )
        }

        log.add(OperationStage.BACKUP, "Creating backup.")

        val backup = backupManager.createBackup(packageName, source)
            ?: return OperationResult(
                success = false,
                stage = OperationStage.BACKUP,
                message = "Backup creation failed."
            )

        log.add(
            OperationStage.COPY,
            if (move) "Moving data." else "Copying data."
        )

        val copied = if (move) {
            fileOperations.move(source, target)
        } else {
            fileOperations.copy(source, target)
        }

        if (!copied) {
            backupManager.restoreBackup(backup, source)

            return OperationResult(
                success = false,
                stage = OperationStage.COPY,
                message = "File operation failed. Backup restored."
            )
        }

        log.add(OperationStage.VERIFY, "Verifying operation.")

        if (!target.exists()) {
            backupManager.restoreBackup(backup, source)
            target.deleteRecursively()

            return OperationResult(
                success = false,
                stage = OperationStage.ROLLBACK,
                message = "Operation target is missing. Operation rolled back.",
                rollbackRequired = true
            )
        }

        val verificationPassed = if (move) {
            verifier.verify(backup, target)
        } else {
            verifier.verify(source, target)
        }

        if (!verificationPassed) {
            backupManager.restoreBackup(backup, source)
            target.deleteRecursively()

            return OperationResult(
                success = false,
                stage = OperationStage.ROLLBACK,
                message = "Verification failed. Operation rolled back.",
                rollbackRequired = true
            )
        }

        log.add(OperationStage.COMMIT, "Operation committed.")
        backupManager.removeBackup(backup)

        return OperationResult(
            success = true,
            stage = OperationStage.COMPLETED,
            message = if (move) {
                "Move completed successfully."
            } else {
                "Copy completed successfully."
            }
        )
    }
}
