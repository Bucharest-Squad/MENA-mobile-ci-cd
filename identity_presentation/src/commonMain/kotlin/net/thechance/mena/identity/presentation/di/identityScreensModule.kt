package net.thechance.mena.identity.presentation.di

import net.thechance.mena.identity.presentation.core.util.ImageDecoder
import net.thechance.mena.identity.presentation.core.util.ImageDecoderImpl
import net.thechance.mena.identity.presentation.core.util.factoryOfOrNull
import net.thechance.mena.identity.presentation.core.util.permissionHandler.PermissionHandler
import net.thechance.mena.identity.presentation.feature.authentication.login.LoginScreenViewModel
import net.thechance.mena.identity.presentation.feature.authentication.register.accountCreated.AccountCreatedViewModel
import net.thechance.mena.identity.presentation.feature.authentication.register.createPassword.CreatePasswordViewModel
import net.thechance.mena.identity.presentation.feature.authentication.register.datePicker.DatePickerScreenViewModel
import net.thechance.mena.identity.presentation.feature.authentication.register.enterName.EnterNameViewModel
import net.thechance.mena.identity.presentation.feature.authentication.register.otp.RegisterOtpViewModel
import net.thechance.mena.identity.presentation.feature.authentication.register.phoneEntry.RegisterPhoneEntryViewModel
import net.thechance.mena.identity.presentation.feature.authentication.register.selectGender.SelectGenderScreenViewModel
import net.thechance.mena.identity.presentation.feature.authentication.register.uploadProfileImage.UploadProfileImageViewModel
import net.thechance.mena.identity.presentation.feature.authentication.resetPassword.otp.ResetPasswordOtpScreenViewModel
import net.thechance.mena.identity.presentation.feature.authentication.resetPassword.phoneEntry.ResetPasswordPhoneEntryScreenViewModel
import net.thechance.mena.identity.presentation.feature.authentication.resetPassword.setNewPassword.SetNewPasswordScreenViewModel
import net.thechance.mena.identity.presentation.feature.location.enableLocationScreen.EnableLocationScreenViewModel
import net.thechance.mena.identity.presentation.feature.location.locationManagement.AddressOperationStrategyFactory
import net.thechance.mena.identity.presentation.feature.location.locationManagement.CreateAddressStrategy
import net.thechance.mena.identity.presentation.feature.location.locationManagement.LocationManagementViewModel
import net.thechance.mena.identity.presentation.feature.location.locationManagement.UpdateAddressStrategy
import net.thechance.mena.identity.presentation.feature.location.myAddresses.MyAddressesScreenViewModel
import net.thechance.mena.identity.presentation.feature.profile.changePassword.ChangePasswordScreenViewModel
import net.thechance.mena.identity.presentation.feature.profile.contactUs.ContactUsViewModel
import net.thechance.mena.identity.presentation.feature.profile.editProfile.EditUserProfileViewModel
import net.thechance.mena.identity.presentation.feature.profile.imageCropper.ImageCropperUiState
import net.thechance.mena.identity.presentation.feature.profile.imageCropper.ImageCropperViewModel
import net.thechance.mena.identity.presentation.feature.profile.imageCropper.components.imageCropperComponent.ImageCropperComponentViewModel
import net.thechance.mena.identity.presentation.feature.profile.profileMainScreen.ProfileScreenViewModel
import net.thechance.mena.identity.presentation.feature.profile.profileMainScreen.components.share.ShareDialogViewModel
import net.thechance.mena.identity.presentation.feature.profile.privacyAndPolicy.PrivacyAndPolicyScreenViewModel
import net.thechance.mena.identity.presentation.screen.addresses.pickLocation.PickLocationScreenViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

const val APP_VERSION = "appVersion"

val identityScreensModule = module {

    includes(platformModule())
    factoryOf(::PermissionHandler)
    factory { ProfileScreenViewModel(get(), get(), get(named(APP_VERSION)), get()) }
    factoryOf(::ImageCropperViewModel)
    factoryOf(::LoginScreenViewModel)
    factoryOf(::CreatePasswordViewModel)
    factoryOf(::AccountCreatedViewModel)
    factoryOf(::ResetPasswordPhoneEntryScreenViewModel)
    factoryOf(::ResetPasswordOtpScreenViewModel)
    factoryOf(::EditUserProfileViewModel)
    factoryOf(::SetNewPasswordScreenViewModel)
    factoryOf(::MyAddressesScreenViewModel)
    factoryOf(::EnableLocationScreenViewModel)
    factoryOf(::ShareDialogViewModel)
    factoryOf(::RegisterPhoneEntryViewModel)
    factoryOf(::RegisterOtpViewModel)
    factoryOf(::EnterNameViewModel)
    factoryOf(::UploadProfileImageViewModel)
    factoryOf(::DatePickerScreenViewModel)
    factoryOf(::SelectGenderScreenViewModel)
    factoryOf(::ChangePasswordScreenViewModel)
    factoryOf(::PrivacyAndPolicyScreenViewModel)
    factoryOf(::ContactUsViewModel)
    factoryOf(::AddressOperationStrategyFactory)
    factoryOf(::UpdateAddressStrategy)
    factoryOf(::CreateAddressStrategy)
    factoryOf(::ImageDecoderImpl) bind ImageDecoder::class
    viewModel { (minScale: Float, maxScale: Float, initialState: ImageCropperUiState) ->
        ImageCropperComponentViewModel(minScale, maxScale, initialState)
    }
    factoryOfOrNull(::LocationManagementViewModel)
    factoryOfOrNull(::PickLocationScreenViewModel)
    factoryOf(::EnableLocationScreenViewModel)
    factoryOf(::ShareDialogViewModel)
}