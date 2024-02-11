package com.example.mtgflavortext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textbox: TextView = findViewById(R.id.tvFlavorText)
        val ft = GetFlavorText()
        textbox.text = ft

        val btnFlavorText: Button = findViewById(R.id.btnFlavorText)
        btnFlavorText.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val newft = doMtgFlavorTextAPICall()

                withContext(Dispatchers.Main) {
                    textbox.text = String.format("%s \uD83C\uDF0C", newft)
                }
            }
        }
    }

    suspend fun doMtgFlavorTextAPICall(): String {
        var new_flavor_text = ""
        while (true) {
            val url = URL("https://api.scryfall.com/cards/random")
            val resptext = url.readText()
            new_flavor_text = GetFlavorText(resptext)

            if (!new_flavor_text.isNullOrEmpty()) {
                break
            } else {
                Log.d("doMtgFlavorTextAPICall", "null or empty")
            }
        }
        return new_flavor_text
    }
}
