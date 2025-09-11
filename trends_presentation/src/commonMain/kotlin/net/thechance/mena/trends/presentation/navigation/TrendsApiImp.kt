package net.thechance.mena.trends.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import net.thechance.mena.trends.api.TrendsApi

class TrendsApiImp () : TrendsApi {

    @Composable
    override fun TrendsContainer() {
        val navController =  rememberNavController()

        TrendsNavGraph(
            navController = navController
        )
    }
}