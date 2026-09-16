package com.smartlink2sd.backend

/**
 * Chooses only from backends that are actually available.
 * User preference is honored when possible; otherwise the fallback order is explicit.
 */
object BackendSelector {

    fun select(
        preferred: String,
        snapshot: List<BackendCapability>,
        needMount: Boolean = false,
        needFreeze: Boolean = false,
        needPackageOps: Boolean = false
    ): Pair<BackendType, String> {

        fun usable(c: BackendCapability): Boolean {
            if (!c.available || !c.authorized) return false
            if (needMount && !c.canMount) return false
            if (needFreeze && !c.canFreeze) return false
            if (needPackageOps && !c.canPackageOps) return false
            return true
        }

        val preferredType = runCatching {
            BackendType.valueOf(preferred.trim().uppercase())
        }.getOrNull()

        if (preferredType != null) {
            val preferredCap = snapshot.firstOrNull { it.backend == preferredType }
            if (preferredCap != null && usable(preferredCap)) {
                return preferredType to "Selected configured backend."
            }
        }

        val fallbackOrder = listOf(
            BackendType.ROOT,
            BackendType.SHIZUKU,
            BackendType.ISLAND,
            BackendType.ANDROID
        )

        fallbackOrder.firstOrNull { type ->
            snapshot.firstOrNull { it.backend == type }?.let(::usable) == true
        }?.let {
            return it to "Configured backend unavailable; selected first compatible backend."
        }

        return BackendType.ANDROID to "No requested backend is currently authorized for this operation."
    }
}
