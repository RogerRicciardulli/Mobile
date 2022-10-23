package com.example.mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import retrofit2.Call
import retrofit2.Response

class PruebaActivity : AppCompatActivity() {

    lateinit var tvServiciosRest : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prueba)
        val api = RetroFitClient.retrofit.create(MyAPI::class.java)
        val callGetPost = api.getCharacters()
        callGetPost.enqueue(object : retrofit2.Callback<List<CharacterResponse>>{
            override fun onResponse(call: Call<List<CharacterResponse>>, response: Response<List<CharacterResponse>>) {
                val posts = response.body()
                if(posts != null){
                    tvServiciosRest = findViewById(R.id.tvServiciosRest)
                    tvServiciosRest.text = posts.toString()
                }else{
                    Log.d("REST", "No hay respuesta...")
                }
            }

            override fun onFailure(call: Call<List<CharacterResponse>>, t: Throwable) {
                Log.e("REST", t.message ?:"")
            }
        })
    }
}