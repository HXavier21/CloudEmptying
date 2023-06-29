package com.example.wintercamp.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wintercamp.ActivityCollector
import com.example.wintercamp.questionnaire.component.CustomText
import com.example.wintercamp.ui.component.MyLabelTextField
import com.example.wintercamp.ui.theme.WinterCampTheme

@Preview
@Composable
fun RegisterScreen(
    onNavigateToEmptying: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var nickName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }
    DisposableEffect(Unit) {
        onDispose {}
    }
    BackHandler {
        ActivityCollector.finishAll()
    }
    WinterCampTheme {
        Surface(
            Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Column {
                Spacer(modifier = Modifier.height(100.dp))
                CustomText(
                    text = "Sign up",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier.padding(start = 20.dp, bottom = 20.dp)
                )
                var isTextFieldSelected by remember { mutableStateOf(false) }
                MyLabelTextField(
                    horizontalPadding = 24.dp,
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    label = {
                        CustomText(text = "E-mail")
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))
                Box(contentAlignment = Alignment.BottomEnd) {
                    MyLabelTextField(
                        horizontalPadding = 24.dp,
                        value = code,
                        onValueChange = {
                            code = it
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        label = {
                            CustomText(text = "SMS code")
                        }
                    )
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .padding(horizontal = 24.dp, vertical = 0.5.dp),
                        shape = MaterialTheme.shapes.small,
                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                            if (email == "") Color(209, 209, 209)
                            else Color(172, 204, 248)
                        ),
                        contentPadding = PaddingValues(10.dp),
                        enabled = if (email == "") false else true
                    ) {
                        CustomText(
                            text = "send code",
                            style = MaterialTheme.typography.bodyLarge,
                            color =
                            if (email == "") Color.White
                            else Color.Black,
                            modifier = Modifier.padding(
                                vertical = 5.dp,
                                horizontal = 10.dp
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                MyLabelTextField(
                    horizontalPadding = 24.dp,
                    value = nickName,
                    onValueChange = {
                        nickName = it
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    label = {
                        CustomText(text = "Nickname")
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))
                MyLabelTextField(
                    horizontalPadding = 24.dp,
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    label = {
                        CustomText(text = "password")
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))
                MyLabelTextField(
                    horizontalPadding = 24.dp,
                    value = password2,
                    onValueChange = {
                        password2 = it
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    label = {
                        CustomText(text = "verify password")
                    }
                )
                Spacer(modifier = Modifier.height(40.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(172, 204, 248)
                    ),
                    onClick = {
                        onNavigateToEmptying()
                    }
                ) {
                    CustomText(
                        text = "Create an account",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CustomText(
                        text = "Already have an account? ",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                    CustomText(text = "Sign in",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.clickable {

                        }
                    )
                }
            }
        }
    }
}
