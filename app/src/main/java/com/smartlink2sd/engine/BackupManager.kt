package com.smartlink2sd.engine

import android.content.Context
import java.io.File

class BackupManager(
    private val context: Context
) {
    fun createBackup(packageName: String, source: File): File? {
        if (!source.exists()) return null

        val safePackageName = packageName.replace(Regex("[^A-Za-z0-9._-]"), "_")
        val root = File(context.filesDir, "operation_backups/$safePackageName")

        if (!root.exists() && !root.mkdirs()) return null

        val backup = File(root, "${source.name}.backup")

        return runCatching {
            if (backup.exists() && !backup.deleteRecursively()) {
                error("Unable to clear existing backup.")
            }

            copyRecursively(source, backup)
            backup
        }.getOrNull()
    }

    fun removeBackup(backup: File): Boolean {
        return !backup.exists() || backup.deleteRecursively()
    }

    fun restoreBackup(backup: File, destination: File): Boolean {
        if (!backup.exists()) return false

        return runCatching {
            if (destination.exists() && !destination.deleteRecursively()) {
                error("Unable to clear restore destination.")
            }

            copyRecursively(backup, destination)
            true
        }.getOrDefault(false)
    }

    private fun copyRecursively(source: File, target: File) {
        if (source.isDirectory) {
            if (!target.exists() && !target.mkdirs()) {
                error("Unable to create backup directory.")
            }

            source.listFiles()?.forEach { child ->
                copyRecursively(child, File(target, child.name))
            }
        } else {
            target.parentFile?.mkdirs()
            source.copyTo(target, overwrite = true)
        }
    }
}
