package com.smartlink2sd

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smartlink2sd.model.AppInfo
import com.smartlink2sd.scanner.AppScanner
import com.smartlink2sd.ui.AppDetailsActivity
import com.smartlink2sd.ui.AppListAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: AppListAdapter
    private var allApps: List<AppInfo> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler = findViewById<RecyclerView>(R.id.appList)
        val search = findViewById<EditText>(R.id.searchBox)

        adapter = AppListAdapter(
            emptyList(),
            onClick = { app ->
                startActivity(Intent(this, AppDetailsActivity::class.java).apply {
                    putExtra(AppDetailsActivity.EXTRA_PACKAGE, app.packageName)
                })
            },
            onLongClick = {
                // Batch operations will be added here without removing
                // any advanced settings from the project.
                true
            }
        )

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        allApps = AppScanner(this).scan()
        adapter.update(allApps)

        search.setOnEditorActionListener { _, _, _ ->
            filterApps(search.text.toString())
            false
        }
        search.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterApps(s?.toString().orEmpty())
            }
            override fun afterTextChanged(s: android.text.Editable?) = Unit
        })
    }

    private fun filterApps(query: String) {
        if (query.isBlank()) {
            adapter.update(allApps)
            return
        }
        val q = query.trim().lowercase()
        adapter.update(allApps.filter {
            it.name.lowercase().contains(q) || it.packageName.lowercase().contains(q)
        })
    }
}
