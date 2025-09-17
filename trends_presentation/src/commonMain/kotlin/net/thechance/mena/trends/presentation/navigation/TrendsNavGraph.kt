package net.thechance.mena.trends.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.thechance.mena.trends.presentation.screen.category_pick.CategoryPickScreen

@Composable
fun TrendsNavHost() {

   val navController = rememberNavController()

   CompositionLocalProvider(
      LocalNavController provides navController
   ) {
      NavHost(
         modifier = Modifier.fillMaxSize(),
         navController = navController,
         startDestination = Route.Categories,
      ) {
         composable<Route.Categories> {
            CategoryPickScreen(
               navController = navController
            )
         }
      }
   }
}

private val LocalNavController = compositionLocalOf<NavController> {
   error("NavController not provided")
}