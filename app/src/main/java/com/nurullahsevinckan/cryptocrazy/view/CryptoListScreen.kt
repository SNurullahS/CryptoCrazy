package com.nurullahsevinckan.cryptocrazy.view
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CryptoListScreen(
    navController: NavController){

    Text("Deneme yazısı", modifier = Modifier.padding(30.dp), fontSize = 20.sp )

}