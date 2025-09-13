package net.thechance.mena.trends.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.thechance.mena.trends.presentation.screen.TestScreen

@Composable
fun TrendsNavGraph(
   navController : NavHostController,
){

   NavHost(
      navController = navController,
      startDestination = Route.Test
   ){
      composable<Route.Test> {
         TestScreen()
      }
   }
}