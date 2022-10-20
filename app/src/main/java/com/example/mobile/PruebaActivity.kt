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
        val callGetPost = api.getFilms()
        callGetPost.enqueue(object : retrofit2.Callback<List<MovieResponse>>{
            override fun onResponse(call: Call<List<MovieResponse>>, response: Response<List<MovieResponse>>) {
                val posts = response.body()
                if(posts != null){
                    tvServiciosRest = findViewById(R.id.tvServiciosRest)
                    tvServiciosRest.text = posts.toString()
                    Log.d("REST", posts.toString())
                }
            }

            override fun onFailure(call: Call<List<MovieResponse>>, t: Throwable) {
                Log.e("REST", t.message ?:"")
            }
        })
    }
}