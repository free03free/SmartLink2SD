package com.smartlink2.engine

import android.content.Context
import com.smartlink2.backend.island.IslandBackend
import com.smartlink2.backend.root.RootBackend
import com.smartlink2.backend.shizuku.ShizukuBackend
import com.smartlink2.model.BackendType

class CapabilityEngine(context: Context) {
    private val shizuku = ShizukuBackend(context)
    private val island = IslandBackend(context)
    private val root = RootBackend()

    fun availableBackends(): List<BackendType> = buildList {
        add(BackendType.ANDROID)
        if (shizuku.isInstalled() && shizuku.isRunning() && shizuku.hasPermission()) {
            add(BackendType.SHIZUKU)
        }
        if (island.isInstalled()) add(BackendType.ISLAND)
        if (root.isAvailable()) add(BackendType.ROOT)
    }
}
