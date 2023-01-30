package com.example.dsb_mobile.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dsb_mobile.R
import com.example.dsb_mobile.data.model.BoatModel
import com.example.dsb_mobile.view.MyAdapter


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
    lateinit var boatArray: ArrayList<BoatModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boats)

        boatArray = arrayListOf(
            BoatModel(
                1,
                "https://i0.wp.com/desafiosolar.com.br/wp-content/uploads/2020/11/solares.png?resize=150%2C150&ssl=1",
                "SOLARES"
            ),
            BoatModel(
                2,
                "https://i0.wp.com/desafiosolar.com.br/wp-content/uploads/2020/11/team-brazil.png?resize=150%2C150&ssl=1",
                "TEAM BRAZIL"
            ), BoatModel(
                3,
                "https://i0.wp.com/desafiosolar.com.br/wp-content/uploads/2020/11/solares.png?resize=150%2C150&ssl=1",
                "SOLARES"
            ),
            BoatModel(
                4,
                "https://i0.wp.com/desafiosolar.com.br/wp-content/uploads/2020/11/team-brazil.png?resize=150%2C150&ssl=1",
                "TEAM BRAZIL"
            ), BoatModel(
                5,
                "https://i0.wp.com/desafiosolar.com.br/wp-content/uploads/2020/11/solares.png?resize=150%2C150&ssl=1",
                "SOLARES"
            ),
            BoatModel(
                6,
                "https://i0.wp.com/desafiosolar.com.br/wp-content/uploads/2020/11/team-brazil.png?resize=150%2C150&ssl=1",
                "TEAM BRAZIL"
            ), BoatModel(
                7,
                "https://i0.wp.com/desafiosolar.com.br/wp-content/uploads/2020/11/solares.png?resize=150%2C150&ssl=1",
                "SOLARES"
            ),
            BoatModel(
                8,
                "https://i0.wp.com/desafiosolar.com.br/wp-content/uploads/2020/11/team-brazil.png?resize=150%2C150&ssl=1",
                "TEAM BRAZIL"
            ), BoatModel(
                9,
                "https://i0.wp.com/desafiosolar.com.br/wp-content/uploads/2020/11/solares.png?resize=150%2C150&ssl=1",
                "SOLARES"
            ),
            BoatModel(
                10,
                "https://i0.wp.com/desafiosolar.com.br/wp-content/uploads/2020/11/team-brazil.png?resize=150%2C150&ssl=1",
                "TEAM BRAZIL"
            )
        )

        // Initialize the RecyclerView and set its properties
        newRecylerview = findViewById(R.id.recyclerView)
        newRecylerview.layoutManager = LinearLayoutManager(this)
        newRecylerview.setHasFixedSize(true) // set fixed size for optimization purposes


        // Set an adapter for the RecyclerView with the provided boatArray data
        newRecylerview.adapter = MyAdapter(boatArray)

    //TODO receber do firebase os times e montar a lista de equipes
    //getUserdata()

    }

    // Function to retrieve user data and update the RecyclerView's adapter
    private fun getUserdata() {

        for (i in heading.indices) {
            // Create a BoatModel object for each iteration and add it to the boatArray list
            val boat = BoatModel(imageId[i], imageUrl[i], heading[i])
            boatArray.add(boat)

        }
        // Set the RecyclerView's adapter with the updated boatArray data
        newRecylerview.adapter = MyAdapter(boatArray)
    }
}
