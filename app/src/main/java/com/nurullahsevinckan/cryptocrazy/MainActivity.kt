package com.nurullahsevinckan.cryptocrazy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nurullahsevinckan.cryptocrazy.ui.theme.CryptoCrazyTheme
import com.nurullahsevinckan.cryptocrazy.view.CryptoDetailScreen
import com.nurullahsevinckan.cryptocrazy.view.CryptoListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoCrazyTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "crypto_list_screen"){

                    composable("crypto_list_screen"){
                        //CryptoListScreen
                        CryptoListScreen(navController)
                    }
                    composable("crypto_detail_screen/{crypto_id}/{crypto_price}", arguments = listOf(
                        navArgument("crypto_id"){type = NavType.StringType},
                        navArgument("crypto_price"){type = NavType.StringType}
                    )){
                        //CryptoDetailScreen

                        //Get arguments
                        val cryptoId = remember{it.arguments?.getString("crypto_id")}
                        val cryptoPrice = remember{it.arguments?.getString("crypto_price")}

                        CryptoDetailScreen(
                            cryptoId ?: "",
                            cryptoPrice ?: "",
                            navController)

                    }

                }
            }
        }
    }
}
