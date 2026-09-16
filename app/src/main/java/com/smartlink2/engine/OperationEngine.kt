package com.smartlink2.engine

import com.smartlink2.model.AppInfo
import com.smartlink2.model.BackendType

class OperationEngine(private val capabilityEngine: CapabilityEngine) {

    fun selectBackend(requiresFilesystem: Boolean): BackendType {
        val available = capabilityEngine.availableBackends()

        return if (requiresFilesystem && BackendType.ROOT in available) {
            BackendType.ROOT
        } else when {
            BackendType.SHIZUKU in available -> BackendType.SHIZUKU
            BackendType.ISLAND in available -> BackendType.ISLAND
            else -> BackendType.ANDROID
        }
    }

    fun describeBackend(backend: BackendType): String = when (backend) {
        BackendType.ANDROID -> "Android native APIs"
        BackendType.SHIZUKU -> "Shizuku"
        BackendType.ISLAND -> "Island"
        BackendType.ROOT -> "Root"
    }

    fun link(app: AppInfo): Result<Unit> =
        Result.failure(UnsupportedOperationException(
            "Link engine is not enabled yet. This scaffold does not perform destructive filesystem operations."
        ))

    fun move(app: AppInfo): Result<Unit> =
        Result.failure(UnsupportedOperationException(
            "Move engine is not enabled yet."
        ))
}
