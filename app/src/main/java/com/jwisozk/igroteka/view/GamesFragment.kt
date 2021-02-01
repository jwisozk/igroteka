package com.jwisozk.igroteka.view

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.jwisozk.igroteka.App
import com.jwisozk.igroteka.MainActivity
import com.jwisozk.igroteka.R
import com.jwisozk.igroteka.databinding.FragmentGamesBinding
import com.jwisozk.igroteka.util.GridSpacingItemDecoration
import com.jwisozk.igroteka.util.hideKeyboard
import com.jwisozk.igroteka.viewmodel.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class GamesFragment : Fragment(R.layout.fragment_games) {

    private var binding: FragmentGamesBinding? = null

    @ExperimentalCoroutinesApi
    private lateinit var viewModel: GamesViewModel
    private lateinit var gamesAdapter: GamesAdapter

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGamesBinding.bind(view)
        init()
        initListeners()
        launchCoroutines()
    }

    private fun init() {
        binding?.gamesList?.apply {
            // Set span count depending on layout
            val spanCount = when (resources.configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> 4
                else -> 2
            }
            layoutManager = GridLayoutManager(activity, spanCount)
            gamesAdapter = GamesAdapter { game ->
                // show DetailFragment
                (requireActivity() as MainActivity).transitionToGameDetailFragment(game)
            }
            adapter = gamesAdapter
            addItemDecoration(
                GridSpacingItemDecoration(
                    spanCount,
                    resources.getDimension(R.dimen.itemsDist).toInt(),
                    true
                )
            )
        }
    }

    @ExperimentalCoroutinesApi
    private fun initListeners() {
        binding?.let { _binding ->
            viewModel = (requireActivity().application as App).appContainer.getGamesViewModel(this)
            _binding.searchInput.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus)
                    v.hideKeyboard()
            }
            _binding.searchInput.setOnEditorActionListener { v, actionId, keyEvent ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER &&
                            keyEvent.action == KeyEvent.ACTION_DOWN)
                ) {
                    v.hideKeyboard()
                    lifecycleScope.launch {
                        viewModel.sendSearchGamesQuery(v.text.toString())
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        } ?: throw IllegalStateException("Binding is null in GamesFragment")
    }

    @ExperimentalCoroutinesApi
    private fun launchCoroutines() {
        lifecycleScope.launchWhenStarted {
            viewModel.gamesUiState.collect { gamesUiState ->
                if (gamesUiState == null)
                    return@collect
                handleGamesUiState(gamesUiState)
            }
        }
    }

    @ExperimentalCoroutinesApi
    private fun handleGamesUiState(gamesUiState: GamesUiState) {
        when (gamesUiState) {
            is GamesUiState.Success -> {
                if (!viewModel.isCountGamesReceive) {
                    binding?.searchInput?.hint = gamesUiState.searchGameResponse.getHintCountGames(
                        gamesUiState.searchGameResponse.count,
                        getString(R.string.hint_search_query_search),
                        getString(R.string.hint_search_query_games)
                    )
                    viewModel.isCountGamesReceive = true
                }
                if (gamesUiState.searchGameResponse.games.isNotEmpty()) {
                    binding?.gamesPlaceholder?.visibility = View.GONE
                    binding?.gamesList?.visibility = View.VISIBLE
                    gamesAdapter.submitList(gamesUiState.searchGameResponse.games)
                } else {
                    binding?.gamesList?.visibility = View.GONE
                    binding?.gamesPlaceholder?.visibility = View.VISIBLE
                }
            }
            is GamesUiState.Error -> {
                binding?.gamesPlaceholder?.visibility = View.VISIBLE
                binding?.gamesPlaceholder?.setText(R.string.search_error)
            }
            is GamesUiState.Loading -> {
                handleLoadingVisible(!gamesUiState.isLoading)
            }
        }
    }

    private fun handleLoadingVisible(isLoading: Boolean) {
        when (isLoading) {
            true -> {
                binding?.searchProgress?.visibility = View.VISIBLE
            }
            false -> {
                binding?.searchProgress?.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}