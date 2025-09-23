package net.thechance.mena

import platform.UIKit.UIDevice
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

fun initNapier() {
    Napier.base(DebugAntilog())
}