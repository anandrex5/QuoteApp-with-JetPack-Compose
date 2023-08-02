package com.company0ne.quotecomposeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.company0ne.quotecomposeapp.screens.QuoteListScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Our data load in main thread ,so we launch Coroutines to execute process in background thread
        CoroutineScope(Dispatchers.IO).launch {
            //delay(10000)
            DataManager.loadAssetsFromFile(applicationContext)

        }
//execting in main thread
        //DataManager.loadAssetsFromFile(this)

        setContent {
//            QuoteDetail()
            App()
        }
    }
}

@Composable
fun App() {
    //if the data is ready then render the list
    if (DataManager.isDataLoaded.value) {


        if(DataManager.currentPage.value == Pages.LISTING){
            QuoteListScreen(data = DataManager.data) {
                DataManager.switchPages(it)

            }
        }else{
            DataManager.currentQuote?.let { QuoteDetail(quote = it) }
        }

    } else {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize(1f)

        ) {
            Text(
                text = "Loading......",
                style = MaterialTheme.typography.h6
            )
        }
    }
    }
enum class Pages{

    LISTING,
    DETAIL

}