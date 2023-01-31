package com.example.dsb_mobile.ui

import android.os.Bundle
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dsb_mobile.R
import com.example.dsb_mobile.data.model.BoatModel
import com.example.dsb_mobile.utils.NetworkUtils
import com.example.dsb_mobile.view.MyAdapter
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET


data class Boats(
    @SerializedName("id")
    var id: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("image")
    var image: String,
    @SerializedName("color")
    var color: String
)

interface Endpoint {

    @GET("boats.json")
    fun getPosts(): Call<List<Boats>>
}

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

        getData()
    }


    private fun getData() {
        val retrofitClient = NetworkUtils
            .getRetrofitInstance("https://andreocunha.github.io/upload_files_test/")

        val endpoint = retrofitClient.create(Endpoint::class.java)
        val callback = endpoint.getPosts()

        callback.enqueue(object : Callback<List<Boats>> {
            override fun onFailure(call: Call<List<Boats>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Boats>>, response: Response<List<Boats>>) {
                response.body()?.forEach {
//                    println(it.title)
                    boatArray.add(
                        BoatModel(
                            it.id,
                            "https://dsbrastreio.com.br/logos/" + it.image,
                            it.title,
                            it.color
                        )
                    )
                }
                println(boatArray)
                // Set an adapter for the RecyclerView with the provided boatArray data
                newRecylerview.adapter = MyAdapter(boatArray)
            }
        })
    }


}
