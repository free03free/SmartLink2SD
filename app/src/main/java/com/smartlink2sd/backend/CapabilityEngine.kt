package com.smartlink2sd.backend

import android.content.Context
import com.smartlink2sd.settings.SettingsBridge

/**
 * V0.5 capability layer.
 *
 * It intentionally performs conservative detection. A backend is not reported as
 * operational merely because an app/package exists.
 */
class CapabilityEngine(
    private val context: Context,
    private val settingsBridge: SettingsBridge
) {

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
        val installed = runCatching {
            context.packageManager.getPackageInfo("moe.shizuku.privileged.api", 0)
            true
        }.getOrDefault(false)

        // V0.5 deliberately avoids claiming authorization without the actual Shizuku API.
        return BackendCapability(
            backend = BackendType.SHIZUKU,
            installed = installed,
            available = false,
            authorized = false,
            canFreeze = false,
            canDisable = false,
            canPackageOps = false,
            canMount = false,
            detail = if (installed)
                "Shizuku package detected; API authorization check is pending."
            else
                "Shizuku package not detected."
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
            detail = if (installed)
                "Island detected; API connection check is pending."
            else
                "Island not detected."
        )
    }

    private fun detectAndroid(): BackendCapability {
        return BackendCapability(
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
}
