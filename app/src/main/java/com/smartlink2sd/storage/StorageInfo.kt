package com.smartlink2sd.storage

data class StorageInfo(
    val path: String,
    val totalBytes: Long,
    val freeBytes: Long,
    val availableBytes: Long,
    val filesystem: String?,
    val isRemovable: Boolean,
    val isSdCard: Boolean,
    val isPrimary: Boolean
)
