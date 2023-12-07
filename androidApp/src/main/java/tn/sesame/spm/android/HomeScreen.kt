package tn.sesame.spm.android

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import tn.sesame.designsystem.components.NavigationBarScreenTemplate
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBar
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBarDefaults

@Composable
fun NavGraphBuilder.HomeScreen(
    homeDestinations : SesameBottomNavigationBarDefaults,
    onHomeExit : (route : String)->Unit
) {
    val homeNavController = rememberNavController()
    val initialRoute = remember {
        NavigationRoutingData.Home.Calendar
    }
    val selectedHomeDestinationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }
    val navOpts = remember {
        NavOptions.Builder().setLaunchSingleTop(true).build()
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            SesameBottomNavigationBar(
                modifier = Modifier
                    .heightIn(min = 24.dp, max = 56.dp)
                    .fillMaxWidth(),
                selectedItemIndex = selectedHomeDestinationIndex.intValue,
                properties = homeDestinations,
                onItemSelected = {index->
                    selectedHomeDestinationIndex.intValue = index
                    homeNavController.navigate(
                        route = NavigationRoutingData.Home.mapIndexToRoute(index),
                        navOptions = navOpts
                    )
                },
            )
        },
        content = { paddingValues ->
            NavHost(
                navController= homeNavController,
                route = NavigationRoutingData.Home.ROOT ,
                startDestination = initialRoute
            ){
                composable(NavigationRoutingData.Home.Calendar){
                    NavigationBarScreenTemplate(
                        modifier = Modifier
                            .padding(paddingValues),
                        onExitNavigation = onHomeExit,
                    ){modifier ->
                        Box(
                            modifier = modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            Text(text = "Calendar Template")
                        }
                    }
                }
                composable(NavigationRoutingData.Home.Projects){
                    NavigationBarScreenTemplate(
                        modifier = Modifier
                            .padding(paddingValues),
                        onExitNavigation = onHomeExit,
                    ){modifier ->
                        Box(
                            modifier = modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            Text(text = "Projects Template")
                        }
                    }
                }
                composable(NavigationRoutingData.Home.Notifications){
                    NavigationBarScreenTemplate(
                        modifier = Modifier
                            .padding(paddingValues),
                        onExitNavigation = onHomeExit,
                    ){modifier ->
                        Box(
                            modifier = modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            Text(text = "Notifications")
                        }
                    }
                }
                composable(NavigationRoutingData.Home.Profile){
                    NavigationBarScreenTemplate(
                        modifier = Modifier
                            .padding(paddingValues),
                        onExitNavigation = onHomeExit,
                    ){modifier ->
                        Box(
                            modifier = modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            Text(text = "Profile")
                        }
                    }
                }
            }
        }
    )
}