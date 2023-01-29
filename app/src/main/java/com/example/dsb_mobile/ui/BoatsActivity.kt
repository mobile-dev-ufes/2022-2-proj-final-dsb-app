package com.example.dsb_mobile.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dsb_mobile.R
import com.example.dsb_mobile.data.model.BoatModel
import com.example.dsb_mobile.view.MyAdapter

class BoatsActivity : AppCompatActivity() {
    private lateinit var newRecylerview: RecyclerView
    private lateinit var newArrayList: ArrayList<BoatModel>
    lateinit var imageId: Array<Int>
    lateinit var heading: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boats)
        imageId = arrayOf(
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e,
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e
        )

        heading = arrayOf(
            "SOLARES",
            "SOLARES",
            "SOLARES",
            "SOLARES",
            "SOLARES",
            "SOLARES",
            "SOLARES",
            "SOLARES",
            "SOLARES",
            "SOLARES",

            )

        newRecylerview = findViewById(R.id.recyclerView)
        newRecylerview.layoutManager = LinearLayoutManager(this)
        newRecylerview.setHasFixedSize(true)


        newArrayList = arrayListOf<BoatModel>()
        getUserdata()

    }


    private fun getUserdata() {

        for (i in imageId.indices) {

            val news = BoatModel(imageId[i], heading[i])
            newArrayList.add(news)

        }

        newRecylerview.adapter = MyAdapter(newArrayList)
    }
}
