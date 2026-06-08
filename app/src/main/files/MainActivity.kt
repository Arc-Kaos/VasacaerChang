package com.tuapp.p2pdivisas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tuapp.p2pdivisas.ui.screens.*
import com.tuapp.p2pdivisas.ui.theme.P2PDivisasTheme

object Routes {
    const val LOGIN        = "login"
    const val REGISTER     = "register"
    const val DASHBOARD    = "dashboard"
    const val DEPOSIT      = "deposit"
    const val MOVEMENTS    = "movements"
    const val PUBLISH      = "publish_offer"
    const val COINCIDENCES = "coincidences"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            P2PDivisasTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Routes.LOGIN) {
                        composable(Routes.LOGIN) {
                            LoginScreen(
                                onNavigateToRegister = { navController.navigate(Routes.REGISTER) },
                                onLoginSuccess = {
                                    navController.navigate(Routes.DASHBOARD) {
                                        popUpTo(Routes.LOGIN) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Routes.REGISTER) {
                            RegisterScreen(
                                onNavigateToLogin = { navController.popBackStack() },
                                onRegisterSuccess = {
                                    navController.navigate(Routes.DASHBOARD) {
                                        popUpTo(Routes.LOGIN) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Routes.DASHBOARD) {
                            DashboardScreen(
                                onNavigateToDeposit   = { navController.navigate(Routes.DEPOSIT) },
                                onNavigateToMovements = { navController.navigate(Routes.MOVEMENTS) },
                                onNavigateToPublish   = { navController.navigate(Routes.PUBLISH) }
                            )
                        }
                        composable(Routes.DEPOSIT) {
                            DepositScreen(onBack = { navController.popBackStack() })
                        }
                        composable(Routes.MOVEMENTS) {
                            MovementsScreen(onBack = { navController.popBackStack() })
                        }
                        composable(Routes.PUBLISH) {
                            PublishOfferScreen(
                                onBack      = { navController.popBackStack() },
                                onPublished = { navController.navigate(Routes.COINCIDENCES) }
                            )
                        }
                        composable(Routes.COINCIDENCES) {
                            CoincidencesScreen(onBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}
