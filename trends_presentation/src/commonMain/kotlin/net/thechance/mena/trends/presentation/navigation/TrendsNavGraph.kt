package net.thechance.mena.trends.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun TrendsNavGraph(
   navController : NavHostController,
){
   val startDestination = "" //TODO start destination will determined by pick interests story

   NavHost(
      navController = navController,
      startDestination = startDestination
   ){
      //TODO add screens routes

   }
}