package com.smartlink2sd.backend

data class BackendCapability(
    val backend: BackendType,
    val installed: Boolean,
    val available: Boolean,
    val authorized: Boolean,
    val canFreeze: Boolean,
    val canDisable: Boolean,
    val canPackageOps: Boolean,
    val canMount: Boolean,
    val detail: String = ""
)
