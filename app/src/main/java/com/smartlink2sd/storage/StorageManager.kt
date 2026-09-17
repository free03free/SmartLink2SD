package com.smartlink2sd.storage

import android.content.Context
import android.os.Environment
import android.os.StatFs
import java.io.File

class StorageManager(
    private val context: Context,
    private val settingsBridge: com.smartlink2sd.settings.SettingsBridge
) {

    fun scan(): List<StorageInfo> {
        val roots = linkedSetOf<File>()
        val settings = settingsBridge.current()

        roots += Environment.getDataDirectory()
        roots += Environment.getExternalStorageDirectory()

        if (settings.partitionDetection) {
            context.getExternalFilesDirs(null).forEach { file ->
                file?.let { roots += findStorageRoot(it) }
            }
        }

        val candidateRoots = if (settings.sdDetection) {
            roots
        } else {
            roots.filterNot { root ->
                runCatching { Environment.isExternalStorageRemovable(root) }.getOrDefault(false)
            }.toSet()
        }

        return candidateRoots
            .mapNotNull { createStorageInfo(it) }
            .distinctBy { it.path }
    }

    private fun createStorageInfo(root: File): StorageInfo? {
        val stat = runCatching {
            StatFs(root.absolutePath)
        }.getOrNull() ?: return null

        val blockSize = stat.blockSizeLong
        val totalBytes = stat.blockCountLong * blockSize
        val freeBytes = stat.freeBlocksLong * blockSize
        val availableBytes = stat.availableBlocksLong * blockSize

        if (totalBytes <= 0L) return null

        val path = root.absolutePath
        val primaryPath = Environment.getExternalStorageDirectory().absolutePath
        val isPrimary = path == primaryPath

        val isRemovable = runCatching {
            Environment.isExternalStorageRemovable(root)
        }.getOrDefault(false)

        val isSdCard = isRemovable ||
            path.contains("/sdcard", ignoreCase = true)

        val filesystem = if (settingsBridge.isFilesystemDetectionEnabled()) detectFilesystem(root) else null
        if (filesystem != null && !isFilesystemAllowed(filesystem)) return null

        return StorageInfo(
            path = path,
            totalBytes = totalBytes,
            freeBytes = freeBytes,
            availableBytes = availableBytes,
            filesystem = filesystem,
            isRemovable = isRemovable,
            isSdCard = isSdCard,
            isPrimary = isPrimary
        )
    }

    private fun isFilesystemAllowed(filesystem: String): Boolean {
        return when (filesystem.lowercase()) {
            "ext2" -> settingsBridge.isExt2Enabled()
            "ext3" -> settingsBridge.isExt3Enabled()
            "ext4" -> settingsBridge.isExt4Enabled()
            "f2fs" -> settingsBridge.isF2fsEnabled()
            else -> true
        }
    }

    private fun findStorageRoot(file: File): File {
        var current = file
        while (current.parentFile != null) {
            val parent = current.parentFile!!
            if (parent.absolutePath == "/storage" || parent.absolutePath == "/storage/emulated") {
                return current
            }
            current = parent
        }
        return current
    }

    private fun detectFilesystem(root: File): String? {
        return runCatching {
            val mounts = File("/proc/mounts")
            if (!mounts.canRead()) return@runCatching null

            mounts.useLines { lines ->
                lines
                    .mapNotNull { line ->
                        val parts = line.split(" ")
                        if (parts.size >= 3) {
                            parts[1] to parts[2]
                        } else {
                            null
                        }
                    }
                    .firstOrNull { (mountPoint, _) ->
                        root.absolutePath == mountPoint
                    }
                    ?.second
            }
        }.getOrNull()
    }
}
