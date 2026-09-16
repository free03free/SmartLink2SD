package com.smartlink2sd.backend

data class CapabilitySnapshot(
    val capabilities: List<BackendCapability>,
    val selectedBackend: BackendType,
    val selectionReason: String
) {
    fun forBackend(type: BackendType): BackendCapability? =
        capabilities.firstOrNull { it.backend == type }
}
