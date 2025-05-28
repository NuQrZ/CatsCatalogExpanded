package myapp.catscatalogexpanded.ui.registrationScreen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegistrationScreen(
    viewModel: RegistrationScreenViewModel,
    onNavigateToHome: () -> Unit,
    onLoginTextClick: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is RegistrationScreenContract.Effect.NavigateToHome -> onNavigateToHome()
                is RegistrationScreenContract.Effect.ShowNotification -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Register",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                OutlinedTextField(
                    value = state.value.name,
                    onValueChange = {
                        viewModel.onEvent(
                            RegistrationScreenContract.UIEvent.NameChanged(
                                it
                            )
                        )
                    },
                    singleLine = true,
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = state.value.userName,
                    onValueChange = {
                        viewModel.onEvent(
                            RegistrationScreenContract.UIEvent.UsernameChanged(
                                it
                            )
                        )
                    },
                    singleLine = true,
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))

                val isValidEmail = remember(state.value) {
                    android.util.Patterns.EMAIL_ADDRESS
                        .matcher(state.value.email)
                        .matches()
                }

                OutlinedTextField(
                    value = state.value.email,
                    onValueChange = {
                        viewModel.onEvent(RegistrationScreenContract.UIEvent.EmailChanged(it))
                    },
                    label = { Text("Email") },
                    singleLine = true,
                    isError = state.value.email.isNotEmpty() && !isValidEmail,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                if (state.value.email.isNotEmpty() && !isValidEmail) {
                    Text(
                        text = "Please enter a valid email",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = state.value.password,
                    onValueChange = {
                        viewModel.onEvent(
                            RegistrationScreenContract.UIEvent.PasswordChanged(
                                it
                            )
                        )
                    },
                    singleLine = true,
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { viewModel.onEvent(RegistrationScreenContract.UIEvent.SubmitRegistration) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Register")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "Already have an account? Log in here!",
                    modifier = Modifier.clickable { onLoginTextClick() }
                )
            }
        }
    }
}