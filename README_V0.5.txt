SmartLink2SD V0.5

Purpose:
- Add a conservative backend capability layer.
- Detect Root, Shizuku package presence, Island package presence, and Android fallback.
- Select a backend only when it is actually marked available/authorized for the requested capability.
- Prevent false claims of Shizuku/Island authorization.

Important:
- Shizuku and Island are detected by package presence only in this incremental V0.5 layer.
- V0.5 does NOT claim Shizuku/Island authorization or execute privileged operations.
- Root detection is conservative and does not perform root commands.
- No existing advanced setting is removed.
- Real Shizuku API integration should be the next implementation step after verifying the current official API dependency.
