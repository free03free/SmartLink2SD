package com.smartlink2sd.settings

data class AdvancedSettings(
    var autoLink: Boolean = false, var linkApk: Boolean = true, var linkDex: Boolean = false,
    var linkLib: Boolean = false, var linkData: Boolean = false, var linkObb: Boolean = false,
    var forceLink: Boolean = false, var linkMethod: String = "Automatic",
    var bindMount: Boolean = true, var symlink: Boolean = false, var mountOptions: String = "",
    var mountOrder: Int = 0, var bootMount: Boolean = false, var mountVerification: Boolean = true,
    var sdDetection: Boolean = true, var partitionDetection: Boolean = true,
    var filesystemDetection: Boolean = true, var ext2: Boolean = true, var ext3: Boolean = true,
    var ext4: Boolean = true, var f2fs: Boolean = true,
    var preventTouch: Boolean = false, var preventLinks: Boolean = false,
    var protectedApps: Boolean = false, var excludedApps: Boolean = false,
    var excludedPaths: String = "", var confirmation: Boolean = true,
    var autoMount: Boolean = false, var autoFreeze: Boolean = false,
    var autoUnfreeze: Boolean = false, var autoCacheClear: Boolean = false,
    var verifyLinks: Boolean = true, var repairLinks: Boolean = true,
    var backupMetadata: Boolean = true, var restore: Boolean = true, var rollback: Boolean = true,
    var shizukuEnabled: Boolean = true, var islandEnabled: Boolean = true,
    var rootEnabled: Boolean = true, var backendSelection: String = "Automatic"
)
