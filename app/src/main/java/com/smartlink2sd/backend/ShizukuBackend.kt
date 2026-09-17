package com.smartlink2sd.backend

import android.content.Context
import android.content.pm.PackageManager
import rikka.shizuku.Shizuku

class ShizukuBackend(private val context: Context) {

    companion object {
        const val REQUEST_CODE = 7001
    }

    fun status(): ShizukuStatus {
        val installed = runCatching {
            context.packageManager.getPackageInfo("moe.shizuku.privileged.api", 0)
            true
        }.getOrDefault(false)

        if (!installed) {
            return ShizukuStatus(
                installed = false,
                running = false,
                permissionGranted = false,
                uid = null,
                usable = false,
                detail = "Shizuku is not installed."
            )
        }

        val running = runCatching { Shizuku.pingBinder() }.getOrDefault(false)
        if (!running) {
            return ShizukuStatus(
                installed = true,
                running = false,
                permissionGranted = false,
                uid = null,
                usable = false,
                detail = "Shizuku is installed but its service is not running."
            )
        }

        val permission = runCatching {
            Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
        }.getOrDefault(false)

        val uid = runCatching { Shizuku.getUid() }.getOrNull()

        return ShizukuStatus(
            installed = true,
            running = true,
            permissionGranted = permission,
            uid = uid,
            usable = permission,
            detail = when {
                permission -> "Shizuku is running and permission is granted."
                else -> "Shizuku is running but permission is not granted."
            }
        )
    }

    fun requestPermission() {
        if (runCatching { Shizuku.pingBinder() }.getOrDefault(false) &&
            runCatching { Shizuku.checkSelfPermission() != PackageManager.PERMISSION_GRANTED }
                .getOrDefault(false)
        ) {
            Shizuku.requestPermission(REQUEST_CODE)
        }
    }
}
