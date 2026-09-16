package com.smartlink2sd.engine

import com.smartlink2sd.settings.AdvancedSettings

/**
 * Immutable snapshot used by an operation.
 * An operation therefore cannot silently change behavior halfway through execution
 * if the user changes settings while it is running.
 */
data class OperationContext(
    val packageName: String,
    val operation: String,
    val settings: AdvancedSettings,
    val timestamp: Long = System.currentTimeMillis()
)
