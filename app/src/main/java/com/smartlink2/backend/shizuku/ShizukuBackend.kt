package com.smartlink2.backend.shizuku

import android.content.Context

class ShizukuBackend(private val context: Context) {
    companion object {
        private const val SHIZUKU_PACKAGE = "moe.shizuku.privileged.api"
    }

    fun isInstalled(): Boolean =
        runCatching {
            context.packageManager.getPackageInfo(SHIZUKU_PACKAGE, 0)
            true
        }.getOrDefault(false)

    // Shizuku API dependency is intentionally not hard-coded here until
    // the exact dependency version is selected.
    fun isRunning(): Boolean = false
    fun hasPermission(): Boolean = false
}
