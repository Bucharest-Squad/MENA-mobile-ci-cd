package net.thechance.mena.identity.presentation.screen.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import net.thechance.mena.designsystem.presentation.component.button.PrimaryButton
import net.thechance.mena.designsystem.presentation.component.scaffold.Scaffold
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.identity.presentation.base.BaseScreen
import net.thechance.mena.identity.presentation.countryPicker.CountryPicker

class LoginScreen : BaseScreen<
    LoginScreenModel,
    LoginScreenUIState,
    LoginScreenUIEffect,
    LoginScreenInteractionListener>() {
    @Composable
    override fun Content() {
        InitScreen(getScreenModel())
    }

    @Composable
    override fun OnRender(
        state: LoginScreenUIState,
        listener: LoginScreenInteractionListener,
    ) {
        Scaffold(
            overlays = {
                bottomSheet(state.showBottomSheet) {
                    CountryPicker(
                        isEnabled = state.countryPickerUIState.isEnabled,
                        countries = state.countryPickerUIState.countries,
                        onSelectCountryItem = listener::onSelectCountryItem,
                        onDismiss = listener::onDismissBottomSheet,
                        onClickConfirm = listener::onClickConfirmButton
                    )
                }
            }
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.colorScheme.background.surface).fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    AnimatedVisibility(
                        visible = !state.showBottomSheet &&
                                  state.countryPickerUIState.selectedCountry != null,
                    ) {
                        Text(
                            text = "${state.countryPickerUIState.selectedCountry?.name} ${state.countryPickerUIState.selectedCountry?.callingCode}",
                            style = Theme.typography.title.large
                        )
                    }
                    PrimaryButton(
                        text = "open countries",
                        onClick = listener::onClickCountryPicker,
                    )
                }
            }
        }
    }

    override fun onEffect(
        effect: LoginScreenUIEffect,
        navigator: Navigator
    ) {

    }
}