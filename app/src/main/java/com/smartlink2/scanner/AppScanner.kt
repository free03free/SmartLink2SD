package com.smartlink2.scanner

import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import com.smartlink2.model.AppInfo
import java.io.File

class AppScanner(private val context: Context) {
    private val pm = context.packageManager

    fun scan(): List<AppInfo> {
        return pm.getInstalledPackages(0).mapNotNull { packageInfo ->
            val ai = packageInfo.applicationInfo ?: return@mapNotNull null
            val label = runCatching { ai.loadLabel(pm).toString() }
                .getOrDefault(packageInfo.packageName)

            val apkPaths = buildList {
                ai.sourceDir?.let(::add)
                if (Build.VERSION.SDK_INT >= 21) {
                    ai.splitSourceDirs?.forEach { add(it) }
                }
            }.distinct()

            val apkSize = apkPaths.sumOf { File(it).length() }
            val flags = ai.flags

            AppInfo(
                packageName = packageInfo.packageName,
                name = label,
                versionName = packageInfo.versionName,
                versionCode = if (Build.VERSION.SDK_INT >= 28)
                    packageInfo.longVersionCode
                else
                    @Suppress("DEPRECATION") packageInfo.versionCode.toLong(),
                uid = ai.uid,
                isSystem = flags and ApplicationInfo.FLAG_SYSTEM != 0,
                isUpdatedSystem = flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP != 0,
                enabled = ai.enabled,
                apkPath = ai.sourceDir,
                apkSize = apkSize
            )
        }.sortedBy { it.name.lowercase() }
    }
}
