SmartLink2SD V0.4

Purpose:
- Connect persisted Advanced Settings to the operation layer.
- Preserve all existing V0.2/V0.3 settings.
- Add a central safety policy without performing filesystem or package mutations yet.

New:
- SettingsBridge: single access point for engines to read settings.
- OperationPolicy: checks Prevent Links, exclusions, and component toggles before linking.
- OperationContext: immutable settings snapshot for each operation.

Important:
- V0.4 does NOT claim that Link/Mount/Freeze operations are fully functional.
- No existing advanced setting is removed.
- Prevent Touch and Prevent Links remain explicit controls.
- Real Shizuku/Root/Island execution will be added only after capability checks.
