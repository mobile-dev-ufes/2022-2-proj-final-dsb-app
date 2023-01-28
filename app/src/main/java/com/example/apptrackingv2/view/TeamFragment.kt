package com.example.apptrackingv2.view

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apptrackingv2.data.model.TeamModel
import com.example.apptrackingv2.view.adapter.TeamAdapter
import com.example.dsb_app.R
import com.example.dsb_app.databinding.FragmentTeamBinding
import com.example.dsb_app.databinding.SosFragmentBinding

class TeamFragment : Fragment() {
    private lateinit var binding: FragmentTeamBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    //val adapter = TeamAdapter(teams)

    val teams = listOf(
        TeamModel("Team 1"),
        TeamModel("Team 2"),
        TeamModel("Team 3"),
        TeamModel("Team 4"),
        TeamModel("Team 5"),
        TeamModel("Team 6"),
        TeamModel("Team 7"),
        TeamModel("Team 8"),
        TeamModel("Team 9"),
        TeamModel("Team 10"),
        TeamModel("Team 11"),
        TeamModel("Team 12"),
        TeamModel("Team 13"),
        TeamModel("Team 14")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_team, container, false)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = TeamAdapter(teams)

        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_team).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
            //binding = FragmentTeamBinding.inflate(layoutInflater)
            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.teamsTitle.setOnClickListener {
            findNavController().navigate(R.id.action_teamFragment_to_configFragment)
        }
    }
}