package com.smartlink2sd.engine

data class OperationLogEntry(
    val stage: OperationStage,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)

class OperationLog {
    private val entries = mutableListOf<OperationLogEntry>()

    fun add(stage: OperationStage, message: String) {
        entries += OperationLogEntry(stage, message)
    }

    fun entries(): List<OperationLogEntry> = entries.toList()

    fun clear() {
        entries.clear()
    }
}
