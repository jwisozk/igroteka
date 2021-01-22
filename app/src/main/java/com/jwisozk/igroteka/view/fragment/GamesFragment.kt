package com.jwisozk.igroteka.view.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.res.Configuration
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jwisozk.igroteka.R
import com.jwisozk.igroteka.databinding.FragmentGamesBinding
import com.jwisozk.igroteka.util.GridSpacingItemDecoration
import com.jwisozk.igroteka.util.hideKeyboard
import com.jwisozk.igroteka.view.GamesAdapter
import com.jwisozk.igroteka.viewmodel.GamesViewModel

class GamesFragment : Fragment(R.layout.fragment_games) {

    private var binding: FragmentGamesBinding? = null
    private lateinit var viewModel: GamesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        initListeners()
    }

    private fun init(view: View) {
        binding = FragmentGamesBinding.bind(view)
        Log.d("debug", "gamesAdapter: 1")
        binding?.gamesList?.apply {
            val spanCount =
                // Set span count depending on layout
                when (resources.configuration.orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> 4
                    else -> 2
                }
            layoutManager = GridLayoutManager(activity, spanCount)
            Log.d("debug", "gamesAdapter: 2")
            val gamesAdapter = GamesAdapter {
                Log.d("debug", "MoviesAdapter: ${it.name}")
                viewModel.onGameAction(it)
            }
            adapter = gamesAdapter
            addItemDecoration(GridSpacingItemDecoration(spanCount, resources.getDimension(R.dimen.itemsDist).toInt(), true))
//            addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                    super.onScrollStateChanged(recyclerView, newState)
//                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
//                        recyclerView.hideKeyboard()
//                    }
//                }
//            })
        }
    }

    private fun initListeners() {
        binding?.let {
            it.searchInput.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus)
                    v.hideKeyboard()
            }
        } ?: throw IllegalStateException("Binding is null in InputFragment")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GamesViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance() = GamesFragment()
    }
}