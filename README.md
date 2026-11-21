# Bypass Charging QS

[![Android](https://img.shields.io/badge/Android-10+-3DDC84?logo=android&logoColor=white)](https://android.com)
[![Root](https://img.shields.io/badge/Root-Required-red.svg)](#requirements)
[![Size](https://img.shields.io/badge/Size-~13KB-brightgreen.svg)](#)

A simple Quick Settings (QS) tile to toggle **Bypass Charging** directly from your notification panel on the **Lenovo Legion Y70** and **Motorola Edge 70 Max** — without needing Game Assistant. Does not run in the background. No App Interface. Just Pure Notification Tile.

Power runs straight from the charger to the device, keeping temperatures low and protecting battery health while gaming or charging under load.

---

## Requirements

- **Root access**: Magisk, KernelSU, or APatch.
- **Supported devices**: Lenovo Legion Y70, Motorola Edge 70 Max, or devices using `/sys/class/power_supply/battery/device/direct_power_supply`.

---

## Quick Setup

1. **Download & Install**: Grab `app-release.apk` from the [Releases](https://github.com/Martuesday/LLY702026BypassChargingQS/releases) page.
2. **Grant Root**: Open your root manager (Magisk/KSU/APatch) and allow superuser access.
3. **Add QS Tile**: Pull down the notification shade, tap the **Edit (pencil)** icon, and drag **Bypass Charging** into your tiles.
4. **Quick Reboot**: Restart your phone if the toggle is grayed out so root permissions take effect, If not, directly use it and enjoy.
5. **Use**: Tap the tile anytime to toggle bypass charging on or off!

---

## Tile States

- **Active (Enabled)**: Power bypass active (`direct_power_supply = 1`). Battery does not charge.
- **Inactive (Disabled)**: Normal charging resumed (`direct_power_supply = 0`).
- **Unavailable**: Root access not detected or unsupported kernel.

---

## Build from Source

```bash
./gradlew assembleRelease
