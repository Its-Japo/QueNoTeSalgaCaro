package com.example.quenotesalgacaro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quenotesalgacaro.navigation.Navigation
import com.example.quenotesalgacaro.ui.theme.QueNoTeSalgaCaroTheme
import com.example.quenotesalgacaro.ui.view.vms.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QueNoTeSalgaCaroTheme {
                val authViewModel: AuthViewModel = viewModel()


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(authViewModel = authViewModel)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QueNoTeSalgaCaroTheme {
        Navigation(authViewModel = AuthViewModel())
    }
}