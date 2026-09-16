package com.smartlink2sd.backend

import com.smartlink2sd.settings.AdvancedSettings

/**
 * Final gate before an engine is allowed to request a privileged operation.
 */
object OperationCapabilityGate {

    fun check(
        settings: AdvancedSettings,
        snapshot: CapabilitySnapshot,
        needMount: Boolean = false,
        needFreeze: Boolean = false,
        needPackageOps: Boolean = false
    ): CapabilityResult {
        val (backend, reason) = BackendSelector.select(
            settings.backendSelection,
            snapshot.capabilities,
            needMount,
            needFreeze,
            needPackageOps
        )

        val cap = snapshot.forBackend(backend)
            ?: return CapabilityResult.Blocked("No capability record exists for $backend.")

        if (!cap.available || !cap.authorized) {
            return CapabilityResult.Blocked(
                "Backend $backend is not currently available/authorized."
            )
        }

        return CapabilityResult.Allowed(backend, reason)
    }
}
