package com.smartlink2sd.settings

/**
 * Central read-only bridge for engines to consume persisted advanced settings.
 * Keeps UI/storage concerns out of operation engines.
 */
class SettingsBridge(private val store: SettingsStore) {
    fun current(): AdvancedSettings = store.load()

    fun isAutoLinkEnabled() = current().autoLink
    fun isAutoMountEnabled() = current().autoMount
    fun isAutoFreezeEnabled() = current().autoFreeze
    fun isPreventTouchEnabled() = current().preventTouch
    fun isPreventLinksEnabled() = current().preventLinks
    fun selectedBackend(): String = current().backendSelection
    fun linkMethod(): String = current().linkMethod
    fun mountOptions(): String = current().mountOptions
    fun mountOrder(): Int = current().mountOrder
    fun excludedPaths(): String = current().excludedPaths
}
