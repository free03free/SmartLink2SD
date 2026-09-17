package com.smartlink2sd.settings

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.smartlink2sd.R
import com.smartlink2sd.backend.CapabilityEngine

class PermissionCenterActivity : AppCompatActivity() {

    private lateinit var capabilityEngine: CapabilityEngine
    private lateinit var statusView: TextView
    private lateinit var requestButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_center)

        statusView = findViewById(R.id.shizukuStatus)
        requestButton = findViewById(R.id.requestShizukuPermission)
        val refreshButton = findViewById<Button>(R.id.refreshShizukuStatus)

        capabilityEngine = CapabilityEngine(
            this,
            SettingsBridge(SettingsStore(this))
        )

        requestButton.setOnClickListener {
            capabilityEngine.requestShizukuPermission()
            refreshStatus()
        }

        refreshButton.setOnClickListener {
            refreshStatus()
        }

        refreshStatus()
    }

    override fun onResume() {
        super.onResume()
        if (::capabilityEngine.isInitialized) {
            refreshStatus()
        }
    }

    private fun refreshStatus() {
        val settings = SettingsStore(this).load()
        val status = capabilityEngine.shizukuStatus()

        if (!settings.shizukuEnabled) {
            statusView.text = "Shizuku is disabled in Advanced Settings."
            requestButton.isEnabled = false
            return
        }

        statusView.text = buildString {
            append("Shizuku\n\n")
            append("Installed: ${status.installed}\n")
            append("Running: ${status.running}\n")
            append("Permission: ${status.permissionGranted}\n")
            append("Usable: ${status.usable}\n")
            status.uid?.let { append("UID: $it\n") }
            append("\n${status.detail}")
        }

        requestButton.isEnabled = status.running && !status.permissionGranted
    }
}
