package com.smartlink2sd.model

data class AppInfo(
    val packageName: String,
    val name: String,
    val versionName: String?,
    val versionCode: Long,
    val uid: Int,
    val isSystem: Boolean,
    val isUpdatedSystem: Boolean,
    val enabled: Boolean,
    val apkPath: String?,
    val apkSize: Long,
    var dataSize: Long = 0L,
    var libSize: Long = 0L,
    var obbSize: Long = 0L,
    var cacheSize: Long = 0L,
    var linked: Boolean = false,
    var frozen: Boolean = false
) {
    val totalSize: Long
        get() = apkSize + dataSize + libSize + obbSize + cacheSize
}
