package com.smartlink2sd.ui

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.smartlink2sd.R
import com.smartlink2sd.scanner.AppScanner

class AppDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PACKAGE = "package_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_details)

        val packageName = intent.getStringExtra(EXTRA_PACKAGE) ?: run {
            finish()
            return
        }

        val app = AppScanner(this).scan().firstOrNull { it.packageName == packageName }
            ?: run {
                finish()
                return
            }

        val ai = packageManager.getApplicationInfo(packageName, 0)

        findViewById<ImageView>(R.id.appIcon).setImageDrawable(ai.loadIcon(packageManager))
        findViewById<TextView>(R.id.appName).text = app.name
        findViewById<TextView>(R.id.packageName).text = app.packageName

        findViewById<TextView>(R.id.details).text = buildString {
            appendLine("Version: ${app.versionName ?: "-"} (${app.versionCode})")
            appendLine("UID: ${app.uid}")
            appendLine("APK: ${formatSize(app.apkSize)}")
            appendLine("Data: ${formatSize(app.dataSize)}")
            appendLine("Lib: ${formatSize(app.libSize)}")
            appendLine("OBB: ${formatSize(app.obbSize)}")
            appendLine("Cache: ${formatSize(app.cacheSize)}")
            appendLine("Total: ${formatSize(app.totalSize)}")
            appendLine("Type: ${if (app.isSystem) "System" else "User"}")
            appendLine("Enabled: ${app.enabled}")
            appendLine("Link status: ${if (app.linked) "Linked" else "Not linked"}")
            append("Freeze status: ${if (app.frozen) "Frozen" else "Active"}")
        }

        findViewById<Button>(R.id.linkButton).setOnClickListener {
            // Safe placeholder: real transactional LinkEngine will be added next.
        }
        findViewById<Button>(R.id.moveButton).setOnClickListener {
            // Safe placeholder.
        }
        findViewById<Button>(R.id.freezeButton).setOnClickListener {
            // Backend-specific freeze implementation will be added separately.
        }
    }

    private fun formatSize(bytes: Long): String {
        if (bytes <= 0) return "0 MB"
        val mb = bytes / 1024.0 / 1024.0
        return if (mb >= 1024) "%.2f GB".format(mb / 1024) else "%.1f MB".format(mb)
    }
}
