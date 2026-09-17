package com.smartlink2sd.storage

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.smartlink2sd.R
import com.smartlink2sd.settings.SettingsBridge
import com.smartlink2sd.settings.SettingsStore

class StorageManagerActivity : AppCompatActivity() {

    private lateinit var storageManager: StorageManager
    private lateinit var storageList: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage_manager)

        storageList = findViewById(R.id.storageList)

        storageManager = StorageManager(
            this,
            SettingsBridge(SettingsStore(this))
        )

        findViewById<Button>(R.id.refreshStorage).setOnClickListener {
            refreshStorage()
        }

        refreshStorage()
    }

    private fun refreshStorage() {
        val storages = storageManager.scan()

        storageList.text = if (storages.isEmpty()) {
            "No storage detected."
        } else {
            storages.joinToString("\n\n") { storage ->
                buildString {
                    append("Path: ${storage.path}\n")
                    append("Total: ${formatSize(storage.totalBytes)}\n")
                    append("Free: ${formatSize(storage.freeBytes)}\n")
                    append("Available: ${formatSize(storage.availableBytes)}\n")
                    append("Filesystem: ${storage.filesystem ?: "Unknown"}\n")
                    append("Removable: ${storage.isRemovable}\n")
                    append("SD Card: ${storage.isSdCard}\n")
                    append("Primary: ${storage.isPrimary}")
                }
            }
        }
    }

    private fun formatSize(bytes: Long): String {
        if (bytes <= 0L) return "0 MB"

        val mb = bytes / 1024.0 / 1024.0
        return if (mb >= 1024.0) {
            "%.2f GB".format(mb / 1024.0)
        } else {
            "%.1f MB".format(mb)
        }
    }
}
