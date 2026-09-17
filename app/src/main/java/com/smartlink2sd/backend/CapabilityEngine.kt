package com.smartlink2sd.backend

import android.content.Context
import com.smartlink2sd.settings.SettingsBridge

class CapabilityEngine(
    private val context: Context,
    private val settingsBridge: SettingsBridge
) {
    private val shizuku = ShizukuBackend(context)

    fun snapshot(): CapabilitySnapshot {
        val caps = listOf(
            detectRoot(),
            detectShizuku(),
            detectIsland(),
            detectAndroid()
        )

        val settings = settingsBridge.current()
        val (selected, reason) = BackendSelector.select(
            preferred = settings.backendSelection,
            snapshot = caps
        )
        return CapabilitySnapshot(caps, selected, reason)
    }

    fun shizukuStatus(): ShizukuStatus = shizuku.status()

    fun requestShizukuPermission() = shizuku.requestPermission()

    private fun detectRoot(): BackendCapability {
        val su = listOf("/system/xbin/su", "/system/bin/su")
            .firstOrNull { java.io.File(it).canExecute() }
        return BackendCapability(
            backend = BackendType.ROOT,
            installed = su != null,
            available = su != null,
            authorized = su != null,
            canFreeze = su != null,
            canDisable = su != null,
            canPackageOps = su != null,
            canMount = su != null,
            detail = if (su != null) "su executable detected." else "No usable su executable detected."
        )
    }

    private fun detectShizuku(): BackendCapability {
        val s = shizuku.status()
        return BackendCapability(
            backend = BackendType.SHIZUKU,
            installed = s.installed,
            available = s.running,
            authorized = s.permissionGranted,
            canFreeze = s.usable,
            canDisable = s.usable,
            canPackageOps = s.usable,
            canMount = false,
            detail = s.detail + (s.uid?.let { " UID=$it." } ?: "")
        )
    }

    private fun detectIsland(): BackendCapability {
        val installed = runCatching {
            context.packageManager.getPackageInfo("com.oasisfeng.island", 0)
            true
        }.getOrDefault(false)
        return BackendCapability(
            backend = BackendType.ISLAND,
            installed = installed,
            available = false,
            authorized = false,
            canFreeze = false,
            canDisable = false,
            canPackageOps = false,
            canMount = false,
            detail = if (installed) "Island detected; API connection check is pending."
            else "Island not detected."
        )
    }

    private fun detectAndroid(): BackendCapability =
        BackendCapability(
            backend = BackendType.ANDROID,
            installed = true,
            available = true,
            authorized = true,
            canFreeze = false,
            canDisable = false,
            canPackageOps = true,
            canMount = false,
            detail = "Android framework operations only."
        )
}
