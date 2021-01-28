package com.jwisozk.igroteka.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.jwisozk.igroteka.R
import com.jwisozk.igroteka.databinding.FragmentGameDetailBinding
import com.jwisozk.igroteka.model.Game

class GameDetailFragment : Fragment(R.layout.fragment_game_detail) {

    private var binding: FragmentGameDetailBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View) {
        binding = FragmentGameDetailBinding.bind(view)
        val game = arguments?.getParcelable<Game>(ARG_GAME)
        game?.let {
            binding?.gameName?.text = it.name
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
//        val BUNDLE_KEY = "gameDetailBundleKey"
//        val REQUEST_KEY = "gameDetailRequestKey"
        private const val ARG_GAME = "argGame"

        @JvmStatic
        fun newInstance(game: Game) =
            GameDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_GAME, game)
                }
            }
    }
}