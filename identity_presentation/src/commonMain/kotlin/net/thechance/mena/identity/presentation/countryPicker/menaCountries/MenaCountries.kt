package net.thechance.mena.identity.presentation.countryPicker.menaCountries;

import mena.identity_presentation.generated.resources.Res
import mena.identity_presentation.generated.resources.ae_flag
import mena.identity_presentation.generated.resources.bh_flag
import mena.identity_presentation.generated.resources.dz_flag
import mena.identity_presentation.generated.resources.eg_flag
import mena.identity_presentation.generated.resources.iq_flag
import mena.identity_presentation.generated.resources.ir_flag
import mena.identity_presentation.generated.resources.jo_flag
import mena.identity_presentation.generated.resources.kw_flag
import mena.identity_presentation.generated.resources.lb_flag
import mena.identity_presentation.generated.resources.ly_flag
import mena.identity_presentation.generated.resources.ma_flag
import mena.identity_presentation.generated.resources.om_flag
import mena.identity_presentation.generated.resources.ps_flag
import mena.identity_presentation.generated.resources.qa_flag
import mena.identity_presentation.generated.resources.sa_flag
import mena.identity_presentation.generated.resources.sy_flag
import mena.identity_presentation.generated.resources.tn_flag
import mena.identity_presentation.generated.resources.ye_flag
import org.jetbrains.compose.resources.DrawableResource

enum class MenaCountries(
    val countryName: String,
    val callingCode: String,
    val flagImage: DrawableResource
) {
    ALGERIA("Algeria", "+213", Res.drawable.dz_flag),
    BAHRAIN("Bahrain", "+973", Res.drawable.bh_flag),
    EGYPT("Egypt", "+20", Res.drawable.eg_flag),
    IRAN("Iran", "+98", Res.drawable.ir_flag),
    IRAQ("Iraq", "+964", Res.drawable.iq_flag),
    JORDAN("Jordan", "+962", Res.drawable.jo_flag),
    KUWAIT("Kuwait", "+965", Res.drawable.kw_flag),
    LEBANON("Lebanon", "+961", Res.drawable.lb_flag),
    LIBYA("Libya", "+218", Res.drawable.ly_flag),
    MOROCCO("Morocco", "+212", Res.drawable.ma_flag),
    OMAN("Oman", "+968", Res.drawable.om_flag),
    PALESTINE("Palestine", "+970", Res.drawable.ps_flag),
    QATAR("Qatar", "+974", Res.drawable.qa_flag),
    SAUDI_ARABIA("Saudi Arabia", "+966", Res.drawable.sa_flag),
    SYRIA("Syria", "+963", Res.drawable.sy_flag),
    TUNISIA("Tunisia", "+216", Res.drawable.tn_flag),
    UAE("United Arab Emirates", "+971", Res.drawable.ae_flag),
    YEMEN("Yemen", "+967", Res.drawable.ye_flag)
}
