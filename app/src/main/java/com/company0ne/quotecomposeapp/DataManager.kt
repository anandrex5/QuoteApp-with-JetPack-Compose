package com.company0ne.quotecomposeapp

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.company0ne.quotecomposeapp.models.Quote
import com.google.gson.Gson

object DataManager {


    var data = emptyArray<Quote>()
    var currentQuote:Quote? = null

    var currentPage = mutableStateOf(Pages.LISTING)
    var isDataLoaded= mutableStateOf(false)

    //load/read the data from the quote.json file
    fun loadAssetsFromFile(context: Context) {
        val inputStream = context.assets.open("quotes.json")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        data = gson.fromJson(json, Array<Quote>::class.java)

        isDataLoaded.value = true
    }
    fun switchPages(quote: Quote?){
        if(currentPage.value == Pages.LISTING){
            currentQuote = quote
            currentPage.value = Pages.DETAIL
        }else{
            currentPage.value = Pages.LISTING
        }
    }
}