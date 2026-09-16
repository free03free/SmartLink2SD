package com.smartlink2sd.settings

import android.content.Context

class SettingsStore(context: Context) {
    private val p = context.getSharedPreferences("advanced_settings", Context.MODE_PRIVATE)

    fun load() = AdvancedSettings(
        autoLink=p.getBoolean("autoLink",false), linkApk=p.getBoolean("linkApk",true),
        linkDex=p.getBoolean("linkDex",false), linkLib=p.getBoolean("linkLib",false),
        linkData=p.getBoolean("linkData",false), linkObb=p.getBoolean("linkObb",false),
        forceLink=p.getBoolean("forceLink",false), linkMethod=p.getString("linkMethod","Automatic")!!,
        bindMount=p.getBoolean("bindMount",true), symlink=p.getBoolean("symlink",false),
        mountOptions=p.getString("mountOptions","")!!, mountOrder=p.getInt("mountOrder",0),
        bootMount=p.getBoolean("bootMount",false), mountVerification=p.getBoolean("mountVerification",true),
        sdDetection=p.getBoolean("sdDetection",true), partitionDetection=p.getBoolean("partitionDetection",true),
        filesystemDetection=p.getBoolean("filesystemDetection",true), ext2=p.getBoolean("ext2",true),
        ext3=p.getBoolean("ext3",true), ext4=p.getBoolean("ext4",true), f2fs=p.getBoolean("f2fs",true),
        preventTouch=p.getBoolean("preventTouch",false), preventLinks=p.getBoolean("preventLinks",false),
        protectedApps=p.getBoolean("protectedApps",false), excludedApps=p.getBoolean("excludedApps",false),
        excludedPaths=p.getString("excludedPaths","")!!, confirmation=p.getBoolean("confirmation",true),
        autoMount=p.getBoolean("autoMount",false), autoFreeze=p.getBoolean("autoFreeze",false),
        autoUnfreeze=p.getBoolean("autoUnfreeze",false), autoCacheClear=p.getBoolean("autoCacheClear",false),
        verifyLinks=p.getBoolean("verifyLinks",true), repairLinks=p.getBoolean("repairLinks",true),
        backupMetadata=p.getBoolean("backupMetadata",true), restore=p.getBoolean("restore",true),
        rollback=p.getBoolean("rollback",true), shizukuEnabled=p.getBoolean("shizukuEnabled",true),
        islandEnabled=p.getBoolean("islandEnabled",true), rootEnabled=p.getBoolean("rootEnabled",true),
        backendSelection=p.getString("backendSelection","Automatic")!!
    )

    fun save(s: AdvancedSettings) {
        p.edit().apply {
            putBoolean("autoLink",s.autoLink); putBoolean("linkApk",s.linkApk)
            putBoolean("linkDex",s.linkDex); putBoolean("linkLib",s.linkLib)
            putBoolean("linkData",s.linkData); putBoolean("linkObb",s.linkObb)
            putBoolean("forceLink",s.forceLink); putString("linkMethod",s.linkMethod)
            putBoolean("bindMount",s.bindMount); putBoolean("symlink",s.symlink)
            putString("mountOptions",s.mountOptions); putInt("mountOrder",s.mountOrder)
            putBoolean("bootMount",s.bootMount); putBoolean("mountVerification",s.mountVerification)
            putBoolean("sdDetection",s.sdDetection); putBoolean("partitionDetection",s.partitionDetection)
            putBoolean("filesystemDetection",s.filesystemDetection); putBoolean("ext2",s.ext2)
            putBoolean("ext3",s.ext3); putBoolean("ext4",s.ext4); putBoolean("f2fs",s.f2fs)
            putBoolean("preventTouch",s.preventTouch); putBoolean("preventLinks",s.preventLinks)
            putBoolean("protectedApps",s.protectedApps); putBoolean("excludedApps",s.excludedApps)
            putString("excludedPaths",s.excludedPaths); putBoolean("confirmation",s.confirmation)
            putBoolean("autoMount",s.autoMount); putBoolean("autoFreeze",s.autoFreeze)
            putBoolean("autoUnfreeze",s.autoUnfreeze); putBoolean("autoCacheClear",s.autoCacheClear)
            putBoolean("verifyLinks",s.verifyLinks); putBoolean("repairLinks",s.repairLinks)
            putBoolean("backupMetadata",s.backupMetadata); putBoolean("restore",s.restore)
            putBoolean("rollback",s.rollback); putBoolean("shizukuEnabled",s.shizukuEnabled)
            putBoolean("islandEnabled",s.islandEnabled); putBoolean("rootEnabled",s.rootEnabled)
            putString("backendSelection",s.backendSelection); apply()
        }
    }
}
