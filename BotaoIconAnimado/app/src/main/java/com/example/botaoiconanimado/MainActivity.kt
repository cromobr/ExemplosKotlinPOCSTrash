package com.example.botaoiconanimado


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }




    fun trocaIcon(view: View) {
        Log.i("btnImproimi","Funcao Imprimir")

    }
}