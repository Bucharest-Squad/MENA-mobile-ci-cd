package net.thechance.mena.trends.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
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

         composable<Route.Trends> {
            // TODO: Just a placeholder for navigation until its user story
            Text(
               text = "Trends Screen",
               style = Theme.typography.headline.large,
               color = Theme.colorScheme.primary.primary,
               textAlign = TextAlign.Center,
               modifier = Modifier
                  .fillMaxSize()
                  .padding(top = 100.dp)
            )
         }
      }
   }
}

private val LocalNavController = compositionLocalOf<NavController> {
   error("NavController not provided")
}