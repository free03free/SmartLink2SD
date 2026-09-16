# SmartLink2SD

Open-source Android project scaffold for a Link2SD-style application manager.

## Current state

This package contains the first compilable project foundation:

- Application scanner
- Search by app name/package
- Main app list
- App details screen
- Backend abstraction: Android / Shizuku / Island / Root
- Capability engine
- Safe operation-engine placeholders
- Split APK size detection
- Architecture prepared for transactional Link/Move/Freeze operations

## Important

The actual filesystem Link/Move engine is intentionally NOT enabled yet.
No destructive filesystem operation is performed by this scaffold.

Shizuku dependency is left as a placeholder so the project does not silently
use an outdated dependency version. Add the current version from the official
Shizuku repositories before implementing the Shizuku backend.

## Advanced settings preservation

The architecture is designed so advanced settings are additive. Planned
settings include:

Linking:
- Auto Link
- Link APK / Dex / Lib / Data / OBB
- Force Link
- Link Method

Mount:
- Bind Mount
- Symlink
- Mount Options
- Mount Order
- Boot Mount
- Mount Verification

Storage:
- SD Detection
- Partition Detection
- Filesystem Detection
- EXT2 / EXT3 / EXT4 / F2FS

Protection:
- Prevent Touch
- Prevent Links
- Protected Apps
- Excluded Apps
- Excluded Paths
- Confirmation

Automation:
- Auto Link / Mount / Freeze / Unfreeze / Cache Clear

Recovery:
- Verify Links
- Repair Links
- Backup Metadata
- Restore
- Rollback

Permissions:
- Shizuku
- Island
- Root
- Automatic Backend Selection

These settings should not be removed when new modules are introduced.
