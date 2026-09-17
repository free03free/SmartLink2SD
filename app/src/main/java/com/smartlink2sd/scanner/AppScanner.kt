package com.smartlink2sd.scanner

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.smartlink2sd.model.AppInfo
import java.io.File

class AppScanner(private val context: Context) {

    private val packageManager: PackageManager = context.packageManager

    fun scan(): List<AppInfo> {
        return packageManager.getInstalledPackages(
            PackageManager.GET_META_DATA
        ).mapNotNull { packageInfo ->
            val appInfo = packageInfo.applicationInfo ?: return@mapNotNull null

            val apkPath = appInfo.sourceDir
            val apkSize = runCatching {
                File(apkPath).length()
            }.getOrDefault(0L)

            AppInfo(
                packageName = packageInfo.packageName,
                name = appInfo.loadLabel(packageManager).toString(),
                versionName = packageInfo.versionName,
                versionCode = packageInfo.longVersionCode,
                uid = appInfo.uid,
                isSystem = (appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0,
                isUpdatedSystem = (appInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0,
                enabled = appInfo.enabled,
                apkPath = apkPath,
                apkSize = apkSize
            )
        }.sortedBy { it.name.lowercase() }
    }
}
