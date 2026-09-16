package com.smartlink2sd.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smartlink2sd.R
import com.smartlink2sd.model.AppInfo

class AppListAdapter(
    private var apps: List<AppInfo>,
    private val onClick: (AppInfo) -> Unit,
    private val onLongClick: (AppInfo) -> Boolean
) : RecyclerView.Adapter<AppListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.appIcon)
        val name: TextView = view.findViewById(R.id.appName)
        val packageName: TextView = view.findViewById(R.id.packageName)
        val size: TextView = view.findViewById(R.id.appSize)
        val status: TextView = view.findViewById(R.id.appStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val app = apps[position]
        holder.name.text = app.name
        holder.packageName.text = app.packageName
        holder.size.text = formatSize(app.totalSize)
        holder.status.text = when {
            app.frozen -> "Frozen"
            app.linked -> "Linked"
            app.isSystem -> "System"
            else -> "Internal"
        }

        holder.itemView.setOnClickListener { onClick(app) }
        holder.itemView.setOnLongClickListener { onLongClick(app) }
    }

    override fun getItemCount(): Int = apps.size

    fun update(newApps: List<AppInfo>) {
        apps = newApps
        notifyDataSetChanged()
    }

    private fun formatSize(size: Long): String {
        if (size <= 0) return "0 MB"
        val mb = size / (1024.0 * 1024.0)
        return if (mb >= 1024) "%.2f GB".format(mb / 1024) else "%.0f MB".format(mb)
    }
}
