package net.thechance.mena.identity.domain.useCase.validation.mobileNumber

import net.thechance.mena.identity.domain.exception.InvalidCountryCodeException

class MobileNumberValidatorImpl : MobileNumberValidator {
    override fun isValid(countryCode: String, number: String): Boolean {
        val country = getCountry(countryCode)
        return country.code == countryCode && isMobileNumberValid(number, country.regexPattern)
    }

    private fun getCountry(countryCode: String): Country {
        return Country.entries
            .firstOrNull { it.code == countryCode }
            ?: throw InvalidCountryCodeException(countryCode)
    }

    private fun isMobileNumberValid(
        number: String,
        regexPattern: Regex
    ) = number.matches(regexPattern)

    private enum class Country(val code: String, val regexPattern: Regex) {
        AFGHANISTAN("+93", "^7[0-9]{0,11}$|7[0-9]{0,9}$".toRegex()),
        BAHRAIN("+973", "^([36])\\d{7}$".toRegex()),
        CYPRUS("+357", "^(9([96])\\d{6})$".toRegex()),
        EGYPT("+20", "^(0)?1[0125]\\d{8}\$".toRegex()),
        IRAN("+98", "^(0)?9\\d{9}".toRegex()),
        IRAQ("+964", "^(0)?7[0-9]\\d{8}\$".toRegex()),
        JORDAN("+962", "^(0)?7[789]\\d{7}\$".toRegex()),
        KUWAIT("+965", "^([569]\\d{7}|41\\d{6})\$".toRegex()),
        LEBANON("+961", "^(0)?1[0125]\\d{8}\$".toRegex()),
        OMAN("+968", "^(9[1-9])\\d{6}\$".toRegex()),
        PALESTINE("+970", "^(\\+?970|0)5[6|9]\\d{7}".toRegex()),
        QATAR("+974", "^[3567]\\d{7}\$".toRegex()),
        SAUDI_ARABIA("+966", "^(0)?5\\d{8}\$".toRegex()),
        SYRIA("+963", "^(0)?9\\d{8}\$".toRegex()),
        TURKEY("+90", "^(0)?5\\d{9}\$".toRegex()),
        UNITED_ARAB_EMIRATES("+971", "^(0)?5[024568]\\d{7}\$".toRegex()),
        YEMEN("+967", "^((7|0?7)[0137]\\d{7}|((\\+|00)967|0)[1-7]\\d{6})\$".toRegex())
    }
}