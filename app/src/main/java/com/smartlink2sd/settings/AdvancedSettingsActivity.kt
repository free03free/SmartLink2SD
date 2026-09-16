package com.smartlink2sd.settings

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AdvancedSettingsActivity : AppCompatActivity() {
    private lateinit var store: SettingsStore
    private lateinit var s: AdvancedSettings

    override fun onCreate(b: Bundle?) {
        super.onCreate(b); store=SettingsStore(this); s=store.load()
        val box=LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setPadding(20,20,20,20) }
        box.addView(TextView(this).apply { text="Advanced Settings"; textSize=24f })
        section(box,"Linking"); sw(box,"Auto Link",s.autoLink){s.autoLink=it}; sw(box,"Link APK",s.linkApk){s.linkApk=it}
        sw(box,"Link Dex",s.linkDex){s.linkDex=it}; sw(box,"Link Lib",s.linkLib){s.linkLib=it}
        sw(box,"Link Data",s.linkData){s.linkData=it}; sw(box,"Link OBB",s.linkObb){s.linkObb=it}
        sw(box,"Force Link",s.forceLink){s.forceLink=it}
        section(box,"Mount"); sw(box,"Bind Mount",s.bindMount){s.bindMount=it}; sw(box,"Symlink",s.symlink){s.symlink=it}
        sw(box,"Boot Mount",s.bootMount){s.bootMount=it}; sw(box,"Mount Verification",s.mountVerification){s.mountVerification=it}
        section(box,"Storage"); sw(box,"SD Detection",s.sdDetection){s.sdDetection=it}; sw(box,"Partition Detection",s.partitionDetection){s.partitionDetection=it}
        sw(box,"Filesystem Detection",s.filesystemDetection){s.filesystemDetection=it}; sw(box,"EXT2",s.ext2){s.ext2=it}
        sw(box,"EXT3",s.ext3){s.ext3=it}; sw(box,"EXT4",s.ext4){s.ext4=it}; sw(box,"F2FS",s.f2fs){s.f2fs=it}
        section(box,"Protection"); sw(box,"Prevent Touch",s.preventTouch){s.preventTouch=it}; sw(box,"Prevent Links",s.preventLinks){s.preventLinks=it}
        sw(box,"Protected Apps",s.protectedApps){s.protectedApps=it}; sw(box,"Excluded Apps",s.excludedApps){s.excludedApps=it}; sw(box,"Confirmation",s.confirmation){s.confirmation=it}
        section(box,"Automation"); sw(box,"Auto Mount",s.autoMount){s.autoMount=it}; sw(box,"Auto Freeze",s.autoFreeze){s.autoFreeze=it}
        sw(box,"Auto Unfreeze",s.autoUnfreeze){s.autoUnfreeze=it}; sw(box,"Auto Cache Clear",s.autoCacheClear){s.autoCacheClear=it}
        section(box,"Recovery"); sw(box,"Verify Links",s.verifyLinks){s.verifyLinks=it}; sw(box,"Repair Links",s.repairLinks){s.repairLinks=it}
        sw(box,"Backup Metadata",s.backupMetadata){s.backupMetadata=it}; sw(box,"Restore",s.restore){s.restore=it}; sw(box,"Rollback",s.rollback){s.rollback=it}
        section(box,"Permissions"); sw(box,"Shizuku",s.shizukuEnabled){s.shizukuEnabled=it}; sw(box,"Island",s.islandEnabled){s.islandEnabled=it}; sw(box,"Root",s.rootEnabled){s.rootEnabled=it}
        box.addView(Button(this).apply { text="SAVE"; setOnClickListener { store.save(s); Toast.makeText(context,"Settings saved",Toast.LENGTH_SHORT).show() } })
        setContentView(ScrollView(this).apply{addView(box)})
    }
    private fun section(v:LinearLayout,t:String){v.addView(TextView(this).apply{text=t;textSize=18f;setPadding(0,18,0,4)})}
    private fun sw(v:LinearLayout,t:String,c:Boolean=false,f:(Boolean)->Unit){v.addView(Switch(this).apply{text=t;isChecked=c;setOnCheckedChangeListener{_,x->f(x)}})}
}
