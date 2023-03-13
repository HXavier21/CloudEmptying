package com.example.wintercamp.ui.navigate

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wintercamp.data.SelfEmptyingViewModel
import com.example.wintercamp.data.WoodenFishViewModel
import com.example.wintercamp.questionnaire.QuizViewModel
import com.example.wintercamp.questionnaire.screen.FinishScreen
import com.example.wintercamp.questionnaire.screen.QuizScreen
import com.example.wintercamp.ui.screen.DeepBreathScreen
import com.example.wintercamp.ui.screen.SelfEmptyingScreen
import com.example.wintercamp.ui.screen.SelfNameScreen
import com.example.wintercamp.ui.screen.SpecialScreen
import com.example.wintercamp.ui.screen.WoodenFishScreen
import com.tencent.mmkv.MMKV

private const val TAG = "MyNavigation"

@Composable
fun MyNavigation(
    navController: NavHostController = rememberNavController(),
    quizViewModel: QuizViewModel = viewModel(),
    startDestination: String = RouteName.Beginning_Screen
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(RouteName.Beginning_Screen) {
            SpecialScreen(
                screen = RouteName.Beginning_Screen,
                onNavigateToSelfName = {
                    navController.navigate(RouteName.Self_Name_Screen) {
                        popUpTo(RouteName.Self_Name_Screen) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(RouteName.Self_Name_Screen) {
            SelfNameScreen(
                onNavigateToSelfEmptyingScreen = {
                    navController.navigate(RouteName.Self_Emptying_Screen)
                }
            )
        }

        composable(RouteName.Self_Emptying_Screen) {
            SelfEmptyingScreen(
                onNavigateToWoodenFishScreen = {
                    navController.navigate(RouteName.Wooden_Fish_Screen)
                },
                onNavigateToQuestionnaire = {
                    quizViewModel.questionInit()
                    navController.navigate(RouteName.Quiz_Screen)
                },
                onNavigateToHiddenScreen = {
                    navController.navigate(RouteName.Hidden_Screen)
                }
            )
        }

        composable(RouteName.Hidden_Screen) {
            SpecialScreen(
                screen = RouteName.Hidden_Screen,
                onNavigateToEmptying = {
                    navController.navigate(RouteName.Self_Emptying_Screen) {
                        popUpTo(RouteName.Self_Emptying_Screen) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(RouteName.Wooden_Fish_Screen) {
            WoodenFishScreen(
                onNavigateToBreathScreen = {
                    navController.navigate(RouteName.Breathing_Screen)
                },
                onNavigateToSelfEmptying = {
                    navController.navigate(RouteName.Self_Emptying_Screen) {
                        popUpTo(RouteName.Self_Emptying_Screen) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(RouteName.Breathing_Screen) {
            DeepBreathScreen(
                onNavigateToWoodenFish = {
                    navController.navigate(RouteName.Wooden_Fish_Screen) {
                        popUpTo(RouteName.Wooden_Fish_Screen) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(RouteName.Quiz_Screen) {
            QuizScreen(
                onNavigateToEmptying = {
                    navController.navigate(RouteName.Self_Emptying_Screen) {
                        popUpTo(RouteName.Self_Emptying_Screen) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToFinish = {
                    navController.navigate(RouteName.Finish_Screen)
                }
            )
        }

        composable(RouteName.Finish_Screen) {
            FinishScreen(
                onNavigateToEmptying = {
                    navController.navigate(RouteName.Self_Emptying_Screen) {
                        popUpTo(RouteName.Self_Emptying_Screen) {
                            inclusive = true
                        }
                    }
                }
            )
        }

    }
}