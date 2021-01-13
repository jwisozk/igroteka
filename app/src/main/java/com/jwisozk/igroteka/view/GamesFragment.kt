package com.jwisozk.igroteka.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jwisozk.igroteka.R
import com.jwisozk.igroteka.viewmodel.GamesViewModel

class GamesFragment : Fragment() {

    private lateinit var viewModel: GamesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.games_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GamesViewModel::class.java)
        // TODO: Use the ViewModel
    }

    companion object {
        fun newInstance() = GamesFragment()
    }
}