package com.smartlink2sd.engine

enum class OperationStage {
    IDLE,
    BACKUP,
    PREPARE,
    COPY,
    VERIFY,
    LINK,
    TEST,
    COMMIT,
    ROLLBACK,
    COMPLETED,
    FAILED
}

data class OperationResult(
    val success: Boolean,
    val stage: OperationStage,
    val message: String,
    val rollbackRequired: Boolean = false
)
