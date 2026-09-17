SmartLink2SD V0.6

Shizuku integration:
- Uses the official Shizuku API/provider dependencies at 13.6.0.
- Checks package installation.
- Checks binder/service running state with Shizuku.pingBinder().
- Checks app permission with Shizuku.checkSelfPermission().
- Reads Shizuku UID.
- Provides an explicit permission request method.
- CapabilityEngine now marks Shizuku usable only when running AND permission is granted.

Important:
- No package freeze/disable/link/mount operation is executed yet.
- Backend capability reporting remains conservative.
- Island remains detected but not falsely reported as connected.
- Existing advanced settings are preserved.

Official references:
- Shizuku API documentation: https://github.com/RikkaApps/Shizuku-API
- Shizuku releases: https://github.com/RikkaApps/Shizuku/releases
