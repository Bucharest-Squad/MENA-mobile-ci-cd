package net.thechance.mena.admin_panel.data.utils

import com.russhwolf.settings.Settings


internal var Settings.accessToken: String
    get() = getString(ACCESS_TOKEN, "")
    set(value) = putString(ACCESS_TOKEN, value)

internal var Settings.refreshToken: String
    get() = getString(REFRESH_TOKEN, "")
    set(value) = putString(REFRESH_TOKEN, value)

const val ACCESS_TOKEN = "access_token"
const val REFRESH_TOKEN = "refresh_token"