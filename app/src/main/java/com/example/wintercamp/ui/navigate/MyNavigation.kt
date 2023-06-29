package com.example.wintercamp.ui.navigate

import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.content.contentValuesOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wintercamp.App
import com.example.wintercamp.data.KvKey
import com.example.wintercamp.questionnaire.Decode
import com.example.wintercamp.questionnaire.Encode
import com.example.wintercamp.questionnaire.QuizViewModel
import com.example.wintercamp.questionnaire.obj
import com.example.wintercamp.questionnaire.screen.FinishScreen
import com.example.wintercamp.questionnaire.screen.QuizScreen
import com.example.wintercamp.ui.screen.DeepBreathScreen
import com.example.wintercamp.ui.screen.LogInScreen
import com.example.wintercamp.ui.screen.RegisterScreen
import com.example.wintercamp.ui.screen.SelfEmptyingScreen
import com.example.wintercamp.ui.screen.SelfNameScreen
import com.example.wintercamp.ui.screen.SpecialScreen
import com.example.wintercamp.ui.screen.WoodenFishScreen
import com.tencent.mmkv.MMKV
import java.lang.Exception

private const val TAG = "MyNavigation"

@Composable
fun MyNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = RouteName.Beginning_Screen
) {
    val kv = MMKV.defaultMMKV()
    val quizViewModel: QuizViewModel = viewModel()
    val viewState by quizViewModel.stateFlow.collectAsState()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(RouteName.Beginning_Screen) {
            SpecialScreen(
                screen = RouteName.Beginning_Screen,
                onNavigateToRegister = {
                    navController.navigate(RouteName.Login_Screen) {
                        popUpTo(RouteName.Login_Screen) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(RouteName.Login_Screen){
            LogInScreen(
                onNavigateToEmptying = {
                    navController.navigate(RouteName.Self_Emptying_Screen)
                },
                onNavigateToRegister = {
                    navController.navigate(RouteName.Register_Screen)
                }
            )
        }

        composable(RouteName.Register_Screen){
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(RouteName.Login_Screen)
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
                    try {
                        quizViewModel.questionChange(
                            questions = Decode(kv.decodeString("json") ?: Encode(obj))
                        )
                        navController.navigate(RouteName.Quiz_Screen)
                    } catch (e: Exception) {
                        Toast.makeText(App.context, "Error in .json", Toast.LENGTH_SHORT).show()
                    }
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
                quizViewModel = quizViewModel,
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
                    try {
                        val uri =
                            Uri.parse("content://com.example.questionnairebackstage.provider/json")
                        val values = contentValuesOf("json" to Encode(viewState.questions))
                        App.context.contentResolver.insert(uri, values)
                        val nameUri =
                            Uri.parse("content://com.example.questionnairebackstage.provider/name")
                        val name = contentValuesOf("name" to kv.decodeString(KvKey.NAME))
                        App.context.contentResolver.insert(nameUri, name)
                    } catch (e: Exception) {
                        Toast.makeText(App.context, "Backstage offline", Toast.LENGTH_SHORT).show()
                    }
                    quizViewModel.resumeIndex()
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