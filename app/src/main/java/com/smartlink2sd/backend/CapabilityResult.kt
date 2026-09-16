package com.smartlink2sd.backend

sealed class CapabilityResult {
    data class Allowed(val backend: BackendType, val reason: String) : CapabilityResult()
    data class Blocked(val reason: String) : CapabilityResult()
}
