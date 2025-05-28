package myapp.catscatalogexpanded.ui.loginScreen

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
fun LoginScreen(
    viewModel: LoginScreenViewModel,
    onNavigateToHome: () -> Unit,
    onRegisterTextClick: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is LoginScreenContract.Effect.NavigateToHome ->
                    onNavigateToHome()

                is LoginScreenContract.Effect.ShowNotification ->
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
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
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Login",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                val isValidEmail = remember(state.value.email) {
                    android.util.Patterns.EMAIL_ADDRESS
                        .matcher(state.value.email)
                        .matches()
                }

                OutlinedTextField(
                    value = state.value.email,
                    onValueChange = {
                        viewModel.onEvent(LoginScreenContract.UIEvent.EmailChanged(it))
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
                        viewModel.onEvent(LoginScreenContract.UIEvent.PasswordChanged(it))
                    },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.onEvent(LoginScreenContract.UIEvent.SubmitLogin)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Login")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "Don't have an account? Register here!",
                    modifier = Modifier.clickable { onRegisterTextClick() }
                )
            }
        }
    }
}
