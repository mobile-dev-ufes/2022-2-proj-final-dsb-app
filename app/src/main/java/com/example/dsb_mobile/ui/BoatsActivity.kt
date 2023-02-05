package com.example.dsb_mobile.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dsb_mobile.R
import com.example.dsb_mobile.data.model.BoatModel
import com.example.dsb_mobile.view.MyAdapter
import com.google.android.gms.security.ProviderInstaller
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLEngine
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.withContext

/**
 * Class that implements an activity to display a list of boats using RecycleView.
 *
 * @property newRecylerview RecyclerView instance to display the boats
 * @property imageId Array of ids of the images of the boats
 * @property heading Array of names of the boats
 * @property imageUrl Array of URLs of the images of the boats
 * @property boatArray ArrayList containing instances of [BoatModel] to be displayed in [newRecylerview]
 *
 * @author Example Developer
 */
class BoatsActivity : AppCompatActivity() {
    private lateinit var newRecylerview: RecyclerView
    lateinit var imageId: Array<Int>
    lateinit var heading: Array<String>
    lateinit var imageUrl: Array<String>
    var boatArray: ArrayList<BoatModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boats)

        // Initialize the RecyclerView and set its properties
        newRecylerview = findViewById(R.id.recyclerView)
        newRecylerview.layoutManager = LinearLayoutManager(this)
        newRecylerview.setHasFixedSize(true) // set fixed size for optimization purposes

        // To fix SSL problem in Android < 4.4
        ProviderInstaller.installIfNeeded(applicationContext)
        val sslContext: SSLContext = SSLContext.getInstance("TLSv1.2")
        sslContext.init(null, null, null)
        val engine: SSLEngine = sslContext.createSSLEngine()


        // Perform network call on a background thread
        GlobalScope.launch(Dispatchers.IO) {
            val response =
                getDataFromApi("https://andreocunha.github.io/upload_files_test/boats.json")

            // parse response to a list of BoatModel objects
            val boats = parseResponse(response)

            // add the boats to the boatArray
            boatArray.addAll(boats)
            println(boatArray)
            withContext(Dispatchers.Main) {
                newRecylerview.adapter = MyAdapter(boatArray)
            }
        }
    }

    private suspend fun getDataFromApi(url: String): String {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        val inputStream = connection.inputStream
        val response = inputStream.bufferedReader().readText()
        inputStream.close()
        connection.disconnect()
        return response
    }

    private fun parseResponse(response: String): ArrayList<BoatModel> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<BoatModel>>() {}.type
        return gson.fromJson(response, type)
    }

}
