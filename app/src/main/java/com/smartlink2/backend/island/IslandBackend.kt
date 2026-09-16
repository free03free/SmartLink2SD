package com.smartlink2.backend.island

import android.content.Context

class IslandBackend(private val context: Context) {
    companion object {
        private const val ISLAND_PACKAGE = "com.oasisfeng.island"
    }

    fun isInstalled(): Boolean =
        runCatching {
            context.packageManager.getPackageInfo(ISLAND_PACKAGE, 0)
            true
        }.getOrDefault(false)

    // Island integration is intentionally isolated here.
    // Actual API calls should only be added after verifying the installed
    // Island API version and authorization state.
}
