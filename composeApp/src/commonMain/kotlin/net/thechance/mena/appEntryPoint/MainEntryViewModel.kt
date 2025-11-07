package net.thechance.mena.appEntryPoint

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainEntryViewModel : ViewModel(), MainEntryInteractionListener {
    private val _activeFeature = MutableStateFlow(Feature.CHAT)
    val activeFeature = _activeFeature.asStateFlow()

    private var _currentDeepLink = MutableStateFlow<DeepLink?>(null)
    val currentDeepLink = _currentDeepLink.asStateFlow()

    override fun onDeepLinkChange(deepLink: DeepLink) {
        _currentDeepLink.update { deepLink }
    }

    override fun clearDeepLink() {
        _currentDeepLink.update { null }
    }

    override fun setActiveFeature(feature: Feature) {
        _activeFeature.value = feature
    }
}