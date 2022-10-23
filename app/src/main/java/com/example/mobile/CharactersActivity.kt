package com.example.mobile

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile.helper.MenuMain
import com.example.mobile.helper.SP
import com.example.mobile.helper.SharedPreferencesHelper
import com.example.mobile.list.character.Character
import com.example.mobile.list.character.CharacterAdapter
import com.example.mobile.list.character.CharacterDetail
import com.example.mobile.list.movie.MovieDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Response

class CharactersActivity : AppCompatActivity() {
    private var rvCharactersList: RecyclerView? = null
    private var adapter: CharacterAdapter? = null
    private var count = 30

    private var apiResponse = listOf<CharacterDetail>()
    private var sharedPreferencesHelper: SharedPreferencesHelper? = null

    override fun onBackPressed() {
        val intent = Intent(this@CharactersActivity, MainWikiActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)
        MenuMain.Init(this, R.id.toolbarCharacters)

        sharedPreferencesHelper = SharedPreferencesHelper(getPreferences(android.content.Context.MODE_PRIVATE))
        // Llamada a la API
        lifecycleScope.launch {
            runBlocking(Dispatchers.IO) {
                apiResponse = getCharacters()
                setupAdapter()
            }
        }
        mensajeCorto("Cargando informacion...")
        Thread.sleep(3500)

        val dropdown = findViewById<Spinner>(R.id.spinnerCharacters)
        val items = arrayOf("20", "40", "60")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        var characters: List<CharacterDetail>

        dropdown.adapter = adapter
        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                // La primera vez que se llama a la API se guardan los datos en SharedPreferences para utilizarlos sin consultarlos de nuevo
                uploadDataInSharedPreferences()
                val item = adapterView.getItemAtPosition(i)
                count = item.toString().toInt()
                characters = convertToCharacter(count)
                this@CharactersActivity.adapter!!.set(characters)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    private fun setupAdapter() {
        rvCharactersList = findViewById(R.id.rvCharacterList)
        val numberOfColumns = getNumberOfColumnsByScreenSize(screenWidth)
        if (rvCharactersList != null) {
            val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, numberOfColumns)
            rvCharactersList!!.layoutManager = layoutManager
            adapter = CharacterAdapter(apiResponse) {
                val intent = Intent(this@CharactersActivity, CharacterDetailActivity::class.java)
                intent.putExtra("name",it.name)
                intent.putExtra("gender",it.gender)
                intent.putExtra("age",it.age)
                intent.putExtra("eyeColor",it.eyeColor)
                intent.putExtra("hairColor",it.hairColor)
                startActivity(intent)
                finish()
            }
            rvCharactersList!!.adapter = adapter
        } else {
            Log.d("LOG", "RV CHARACTER LIST IS NULL")
        }
    }

    private fun getNumberOfColumnsByScreenSize(screenWidth: Int): Int {
        Log.d("LOG", screenWidth.toString())
        return if (screenWidth < 500) {
            1
        } else {
            2
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun getCharacters(): List<CharacterDetail> {
        var apiResponse = ArrayList<CharacterDetail>()
        val api = RetroFitClient.retrofit.create(MyAPI::class.java)
        val callGetPost = api.getCharacters()
        callGetPost.enqueue(object : retrofit2.Callback<List<CharacterResponse>>{
            override fun onResponse(call: Call<List<CharacterResponse>>, response: Response<List<CharacterResponse>>) {
                val apiRest = response.body()
                apiRest?.forEach {
                    val character = CharacterDetail(it.name, it.gender, it.age, it.eye_color, it.hair_color)
                    apiResponse.add(character)
                }
                Log.d("REST", "Cantidad de personajes: " + apiRest?.size.toString())
            }
            override fun onFailure(call: Call<List<CharacterResponse>>, t: Throwable) {
                Log.e("REST", t.message ?:"")
            }
        })
        return apiResponse
    }

    private fun convertToCharacter(contador: Int): List<CharacterDetail> {
        val allCharacters = sharedPreferencesHelper!!.getString(SP.CHARACTERS_WITH_DETAIL)
        val Name = StringBuilder()
        val Gender = StringBuilder()
        val Age = StringBuilder()
        val EyeColor = StringBuilder()
        val HairColor = StringBuilder()

        val elements = arrayOf("Name", "Gender", "Age", "EyeColor", "HairColor")
        var cont = 0
        var actualElement: String
        var beginning = 0
        var beginningAddition = 2

        var characters = ArrayList<CharacterDetail>()
        var character = CharacterDetail()
        for(counter in allCharacters.indices){
            if(characters.count() <= contador-1) {
                if(allCharacters[counter] == '|'){
                    actualElement = elements[cont]
                    when(actualElement){
                        "Name" -> {
                            if(characters.count() == 1)
                                beginning += 2
                            Name.append(allCharacters.substring(beginning, counter - 1))
                            beginning = counter + 2
                            character.name = Name.toString()
                        }
                        "Gender" -> {
                            Gender.append(allCharacters.substring(beginning, counter - 1))
                            beginning = counter + 2
                            character.gender = Gender.toString()
                        }
                        "Age" -> {
                            Age.append(allCharacters.substring(beginning, counter - 1))
                            beginning = counter + 2
                            character.age = Age.toString()
                        }
                        "EyeColor" -> {
                            EyeColor.append(allCharacters.substring(beginning, counter - 1))
                            beginning = counter + 2
                            character.eyeColor = EyeColor.toString()
                        }
                        "HairColor" -> {
                            HairColor.append(allCharacters.substring(beginning, counter - 1))
                            beginning = counter + beginningAddition
                            character.hairColor = HairColor.toString()
                        }
                    }
                    cont+=1
                }
                if (allCharacters[counter] == '*') {
                    Log.d("LOG", character.name + character.gender + character.age + character.eyeColor +
                            character.hairColor)
                    characters.add(
                        CharacterDetail(character.name, character.gender, character.age, character.eyeColor,
                                character.hairColor)
                    )
                    Name.setLength(0)
                    Gender.setLength(0)
                    Age.setLength(0)
                    EyeColor.setLength(0)
                    HairColor.setLength(0)
                    cont = 0
                    beginningAddition = 4
                }
            }
        }
        return characters;
    }

    private fun uploadDataInSharedPreferences(){
        val builder = StringBuilder()
        if(apiResponse.isNotEmpty()) {
            for(counter in apiResponse.indices){
                builder.append(apiResponse[counter].name)
                builder.append(" | ")
                builder.append(apiResponse[counter].gender)
                builder.append(" | ")
                builder.append(apiResponse[counter].age)
                builder.append(" | ")
                builder.append(apiResponse[counter].eyeColor)
                builder.append(" | ")
                builder.append(apiResponse[counter].hairColor)
                builder.append(" | ")
                if(counter != apiResponse.size-1){
                    builder.append("* ")
                }
            }
            sharedPreferencesHelper!!.setString(SP.CHARACTERS_WITH_DETAIL, builder.toString())
            Log.d("REST", sharedPreferencesHelper!!.getString(SP.CHARACTERS_WITH_DETAIL))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_inicio) {
            val intent = Intent(this@CharactersActivity, MainWikiActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        val screenWidth: Int
            get() = Resources.getSystem().displayMetrics.widthPixels
    }
}