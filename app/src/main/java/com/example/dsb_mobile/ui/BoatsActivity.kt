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


        newRecylerview = findViewById(R.id.recyclerView)
        newRecylerview.layoutManager = LinearLayoutManager(this)
        newRecylerview.setHasFixedSize(true)



        newRecylerview.adapter = MyAdapter(boatArray)

    //TODO receber do firebase os times e montar a lista de equipes
    //getUserdata()

    }


    private fun getUserdata() {

        for (i in heading.indices) {

            val boat = BoatModel(imageId[i], imageUrl[i], heading[i])
            boatArray.add(boat)

        }

        newRecylerview.adapter = MyAdapter(boatArray)
    }
}
