package com.smartlink2sd.backend

data class ShizukuStatus(
    val installed: Boolean,
    val running: Boolean,
    val permissionGranted: Boolean,
    val uid: Int?,
    val usable: Boolean,
    val detail: String
)
