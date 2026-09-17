package com.smartlink2sd.settings

import android.app.Activity
import android.os.Bundle
import android.widget.*
import android.graphics.Color
import android.text.InputType

class AdvancedSettingsActivity : Activity() {
    private lateinit var store: SettingsStore
    private lateinit var settings: AdvancedSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        store = SettingsStore(this)
        settings = store.load()

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 24, 24, 24)
        }

        val scroll = ScrollView(this)
        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }
        scroll.addView(content)
        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))

        addSection(content, "LINKING")
        addSwitch(content, "Auto Link", settings.autoLink) { settings.autoLink = it }
        addSwitch(content, "Link APK", settings.linkApk) { settings.linkApk = it }
        addSwitch(content, "Link Dex", settings.linkDex) { settings.linkDex = it }
        addSwitch(content, "Link Lib", settings.linkLib) { settings.linkLib = it }
        addSwitch(content, "Link Data", settings.linkData) { settings.linkData = it }
        addSwitch(content, "Link OBB", settings.linkObb) { settings.linkObb = it }
        addSwitch(content, "Force Link", settings.forceLink) { settings.forceLink = it }
        addSpinner(
            content,
            "Link Method",
            arrayOf("Automatic", "Bind Mount", "Symlink", "Move + Link"),
            settings.linkMethod
        ) {
            settings.linkMethod = it
        }

        addSection(content, "MOUNT")
        addSwitch(content, "Bind Mount", settings.bindMount) { settings.bindMount = it }
        addSwitch(content, "Symlink", settings.symlink) { settings.symlink = it }
        addEdit(content, "Mount Options", settings.mountOptions) {
            settings.mountOptions = it
        }
        addNumberEdit(content, "Mount Order", settings.mountOrder) {
            settings.mountOrder = it
        }
        addSwitch(content, "Boot Mount", settings.bootMount) { settings.bootMount = it }
        addSwitch(content, "Mount Verification", settings.mountVerification) {
            settings.mountVerification = it
        }

        addSection(content, "STORAGE")
        addSwitch(content, "SD Detection", settings.sdDetection) {
            settings.sdDetection = it
        }
        addSwitch(content, "Partition Detection", settings.partitionDetection) {
            settings.partitionDetection = it
        }
        addSwitch(content, "Filesystem Detection", settings.filesystemDetection) {
            settings.filesystemDetection = it
        }
        addSwitch(content, "EXT2", settings.ext2) { settings.ext2 = it }
        addSwitch(content, "EXT3", settings.ext3) { settings.ext3 = it }
        addSwitch(content, "EXT4", settings.ext4) { settings.ext4 = it }
        addSwitch(content, "F2FS", settings.f2fs) { settings.f2fs = it }

        addSection(content, "PROTECTION")
        addSwitch(content, "Prevent Touch", settings.preventTouch) {
            settings.preventTouch = it
        }
        addSwitch(content, "Prevent Links", settings.preventLinks) {
            settings.preventLinks = it
        }
        addSwitch(content, "Protected Apps", settings.protectedApps) {
            settings.protectedApps = it
        }
        addSwitch(content, "Excluded Apps", settings.excludedApps) {
            settings.excludedApps = it
        }
        addEdit(content, "Excluded Paths", settings.excludedPaths) {
            settings.excludedPaths = it
        }
        addSwitch(content, "Confirmation", settings.confirmation) {
            settings.confirmation = it
        }

        addSection(content, "AUTOMATION")
        addSwitch(content, "Auto Link", settings.autoLink) {
            settings.autoLink = it
        }
        addSwitch(content, "Auto Mount", settings.autoMount) {
            settings.autoMount = it
        }
        addSwitch(content, "Auto Freeze", settings.autoFreeze) {
            settings.autoFreeze = it
        }
        addSwitch(content, "Auto Unfreeze", settings.autoUnfreeze) {
            settings.autoUnfreeze = it
        }
        addSwitch(content, "Auto Cache Clear", settings.autoCacheClear) {
            settings.autoCacheClear = it
        }

        addSection(content, "RECOVERY")
        addSwitch(content, "Verify Links", settings.verifyLinks) {
            settings.verifyLinks = it
        }
        addSwitch(content, "Repair Links", settings.repairLinks) {
            settings.repairLinks = it
        }
        addSwitch(content, "Backup Metadata", settings.backupMetadata) {
            settings.backupMetadata = it
        }
        addSwitch(content, "Restore", settings.restore) {
            settings.restore = it
        }
        addSwitch(content, "Rollback", settings.rollback) {
            settings.rollback = it
        }

        addSection(content, "PERMISSIONS / BACKEND")
        addSwitch(content, "Shizuku Enabled", settings.shizukuEnabled) {
            settings.shizukuEnabled = it
        }
        addSwitch(content, "Island Enabled", settings.islandEnabled) {
            settings.islandEnabled = it
        }
        addSwitch(content, "Root Enabled", settings.rootEnabled) {
            settings.rootEnabled = it
        }
        addSpinner(
            content,
            "Backend Selection",
            arrayOf("Automatic", "Shizuku", "Island", "Root", "Android Native"),
            settings.backendSelection
        ) {
            settings.backendSelection = it
        }

        val save = Button(this).apply {
            text = "SAVE SETTINGS"
            setOnClickListener {
                store.save(settings)
                Toast.makeText(
                    this@AdvancedSettingsActivity,
                    "Settings saved",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }

        root.addView(save, LinearLayout.LayoutParams(-1, -2))
        setContentView(root)
    }

    private fun addSection(parent: LinearLayout, title: String) {
        val tv = TextView(this).apply {
            text = title
            textSize = 16f
            setTextColor(Color.DKGRAY)
            setPadding(0, 28, 0, 8)
        }
        parent.addView(tv)
    }

    private fun addSwitch(
        parent: LinearLayout,
        title: String,
        checked: Boolean,
        onChange: (Boolean) -> Unit
    ) {
        val sw = Switch(this).apply {
            text = title
            isChecked = checked
            setOnCheckedChangeListener { _, value ->
                onChange(value)
            }
        }
        parent.addView(sw, LinearLayout.LayoutParams(-1, -2))
    }

    private fun addEdit(
        parent: LinearLayout,
        hint: String,
        value: String,
        onChange: (String) -> Unit
    ) {
        val e = EditText(this).apply {
            this.hint = hint
            setText(value)
            setSingleLine(false)
            setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    onChange(text.toString())
                }
            }
        }
        parent.addView(e, LinearLayout.LayoutParams(-1, -2))
    }

    private fun addNumberEdit(
        parent: LinearLayout,
        hint: String,
        value: Int,
        onChange: (Int) -> Unit
    ) {
        val e = EditText(this).apply {
            this.hint = hint
            setText(value.toString())
            inputType = InputType.TYPE_CLASS_NUMBER
            setSingleLine(true)
            setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    onChange(text.toString().toIntOrNull() ?: value)
                }
            }
        }
        parent.addView(e, LinearLayout.LayoutParams(-1, -2))
    }

    private fun addSpinner(
        parent: LinearLayout,
        title: String,
        items: Array<String>,
        selected: String,
        onChange: (String) -> Unit
    ) {
        val label = TextView(this).apply {
            text = title
        }
        parent.addView(label)

        val spinner = Spinner(this)
        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            items
        )

        val index = items.indexOf(selected).coerceAtLeast(0)
        spinner.setSelection(index)

        spinner.onItemSelectedListener =
            object : android.widget.AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p: android.widget.AdapterView<*>?,
                    v: android.view.View?,
                    position: Int,
                    id: Long
                ) {
                    onChange(items[position])
                }

                override fun onNothingSelected(
                    p: android.widget.AdapterView<*>?
                ) {
                }
            }

        parent.addView(spinner)
    }
}
