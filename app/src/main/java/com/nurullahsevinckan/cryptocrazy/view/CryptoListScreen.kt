package com.nurullahsevinckan.cryptocrazy.view
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nurullahsevinckan.cryptocrazy.model.CryptoList
import com.nurullahsevinckan.cryptocrazy.ui.theme.*
import com.nurullahsevinckan.cryptocrazy.viewmodel.CryptoListViewModel

@Composable
fun CryptoListScreen(
    navController: NavController,
    viewModel : CryptoListViewModel = hiltViewModel()
){
    Surface(
        color = LightColorScheme.secondary,
        modifier = Modifier.fillMaxSize()
    ){
        Column {
            Text("Crypto Crazy", modifier = Modifier.fillMaxWidth()
                .padding(20.dp),
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = LightColorScheme.primary
            )
            Spacer(modifier = Modifier.height(10.dp))
            //Search Bar
            SearchBar(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                hint = "Searching",
                onSearch = {
                  viewModel.searchCryptoList(it)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            CryptoList(navController)

        }
    }
}
@Composable
fun SearchBar(
    modifier: Modifier,
    hint : String,
    onSearch: (String) -> Unit = {}
){

    var text by remember{
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(true)
    }

    Box(modifier = modifier){
        //xml -> EditText, Compose -> TextFiled
        BasicTextField(value = text, onValueChange = {
            text = it
            onSearch(it)
        }, maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier.fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = it.isFocused != true && text.isEmpty()
                })

        if(isHintDisplayed){
            Text(text = hint,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp))
        }
    }
}




// Each single crypto element UI
@Composable
fun CryptoRow(navController: NavController,
              crypto : CryptoList){
    Column(modifier = Modifier.fillMaxWidth()
        .background(color = LightColorScheme.secondary)
        .clickable {
            navController.navigate(
                "crypto_detail_screen/${crypto.currency}/${crypto.price}")}){

        Text(text = crypto.currency,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(3.dp),
            color = LightColorScheme.primary)

        Text(text = crypto.price,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(3.dp),
            color = LightColorScheme.tertiary)
    }

}


@Composable
fun CryptoListView(navController: NavController,
                   cryptos : List<CryptoList>){

    LazyColumn(contentPadding = PaddingValues(2.dp)){
        items(cryptos){crypto->
            CryptoRow(navController = navController,
                crypto = crypto)
        }
    }
}

@Composable
fun CryptoList(navController: NavController,
               viewModel: CryptoListViewModel = hiltViewModel()
){
    val cryptoList by remember {viewModel.cryptoList}
    val errorMessage by remember {viewModel.errorMessage}
    val isLoading by remember { viewModel.isLoading}

    CryptoListView(cryptos = cryptoList,
        navController = navController)

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize())
    {
        if(isLoading){
            CircularProgressIndicator(color = LightColorScheme.primary)
        }
        if(errorMessage.isNotEmpty()){
            RetryView(errorMessage) {
                viewModel.loadCryptos()
            }
        }

    }
}


//If we get an error, this view will be come out
@Composable
fun RetryView (
    error : String,
    onRetry : () -> Unit
){
    Column(){
        Text(text = error,color = Color.Red, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            onRetry
        }, modifier = Modifier.align((Alignment.CenterHorizontally))) {
            Text(text = "Retry")
        }
    }
}










