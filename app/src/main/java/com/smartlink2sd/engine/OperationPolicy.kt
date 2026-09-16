package com.smartlink2sd.engine

import com.smartlink2sd.settings.AdvancedSettings

/**
 * Safety policy applied before an operation is executed.
 * This does not perform the operation; it only decides whether it may proceed.
 */
object OperationPolicy {

    data class Decision(
        val allowed: Boolean,
        val reason: String = ""
    )

    fun canLink(
        settings: AdvancedSettings,
        packageName: String,
        requestedComponent: String
    ): Decision {
        if (settings.preventLinks) {
            return Decision(false, "Link operations are disabled by Prevent Links.")
        }

        if (settings.excludedApps.split(",")
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .contains(packageName)
        ) {
            return Decision(false, "Application is excluded.")
        }

        if (!componentAllowed(settings, requestedComponent)) {
            return Decision(false, "$requestedComponent linking is disabled.")
        }

        return Decision(true)
    }

    fun canTouch(settings: AdvancedSettings): Decision {
        return if (settings.preventTouch) {
            Decision(false, "Touch operations are disabled by Prevent Touch.")
        } else {
            Decision(true)
        }
    }

    fun canFreeze(settings: AdvancedSettings): Decision {
        return if (settings.autoFreeze || !settings.preventTouch) {
            Decision(true)
        } else {
            Decision(false, "Freeze operation blocked by current protection policy.")
        }
    }

    private fun componentAllowed(settings: AdvancedSettings, component: String): Boolean {
        return when (component.lowercase()) {
            "apk" -> settings.linkApk
            "dex" -> settings.linkDex
            "lib" -> settings.linkLib
            "data" -> settings.linkData
            "obb" -> settings.linkObb
            "external_data" -> settings.linkExternalData
            else -> false
        }
    }
}
