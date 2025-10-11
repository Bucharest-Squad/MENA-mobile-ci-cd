package net.thechance.mena.faith.presentation.qibla

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import platform.CoreLocation.CLHeading
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.darwin.NSObject

class IOSAzimuthProvider : NSObject(), AzimuthProvider, CLLocationManagerDelegateProtocol {
    private val locationManager = CLLocationManager()
    private val _azimuth = MutableStateFlow(0f)
    override val azimuthFlow = _azimuth.asStateFlow()

    override fun startListening() {
        locationManager.delegate = this
        locationManager.requestWhenInUseAuthorization()
        locationManager.startUpdatingHeading()
    }

    override fun stopListening() {
        locationManager.stopUpdatingHeading()
    }

    override fun locationManager(manager: CLLocationManager, didUpdateHeading: CLHeading) {
        _azimuth.value = didUpdateHeading.magneticHeading.toFloat()
    }
}