package com.jwisozk.igroteka.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.jwisozk.igroteka.R
import com.jwisozk.igroteka.databinding.FragmentGameDetailBinding
import com.jwisozk.igroteka.databinding.FragmentGamesBinding
import com.jwisozk.igroteka.model.Game

class GameDetailFragment : Fragment(R.layout.fragment_game_detail) {

    private var binding: FragmentGameDetailBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View) {
        binding = FragmentGameDetailBinding.bind(view)
        val game = arguments?.getParcelable<Game>(GAME_ARG)
        game?.let {
            binding?.gameName?.text = it.name
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        val GAME_ARG = "game arg"
    }
}