package com.jwisozk.igroteka.view

import android.content.res.Configuration
import android.os.Bundle
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
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class GamesFragment : Fragment(R.layout.fragment_games) {

    private var binding: FragmentGamesBinding? = null

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    private lateinit var viewModel: GamesViewModel
    private lateinit var gamesAdapter: GamesAdapter

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGamesBinding.bind(view)
        init()
        initListeners()
        launchCoroutines()
    }

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    private fun init() {
        binding?.let { _binding ->
            viewModel = (requireActivity().application as App).appContainer.getGamesViewModel(this)
            lifecycleScope.launch {
                if (viewModel.hintSearch == null) {
                    viewModel.sendSearchGamesQuery("")
                }
            }
            viewModel.hintSearch?.let { hintSearch ->
                binding?.searchInput?.hint = hintSearch
            }
            _binding.gamesList.apply {
                // Set span count depending on layout
                val spanCount = when (resources.configuration.orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> resources.getInteger(R.integer.four_columns)
                    else -> resources.getInteger(R.integer.two_columns)
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
        } ?: throw IllegalStateException("Binding is null in GamesFragment")
    }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    private fun initListeners() {
        binding?.let { _binding ->
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

    @InternalCoroutinesApi
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

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    private fun handleGamesUiState(gamesUiState: GamesUiState) {
        when (gamesUiState) {
            is GamesUiState.Success -> {
                if (viewModel.hintSearch == null) {
                    viewModel.hintSearch = gamesUiState.searchGameResponse.getHintCountGames(
                        gamesUiState.searchGameResponse.count,
                        getString(R.string.hint_search_query_search),
                        getString(R.string.hint_search_query_games)
                    )
                    binding?.searchInput?.hint = viewModel.hintSearch
                }
                if (gamesUiState.searchGameResponse.games.isNotEmpty()) {
                    binding?.gamesPlaceholder?.visibility = View.GONE
                    binding?.gamesList?.visibility = View.VISIBLE
                    gamesAdapter.submitList(gamesUiState.searchGameResponse.games)
                } else {
                    binding?.gamesList?.visibility = View.GONE
                    binding?.gamesPlaceholder?.visibility = View.VISIBLE
                }
                handleLoadingVisible(false)
            }
            is GamesUiState.Error -> {
                binding?.gamesList?.visibility = View.GONE
                binding?.gamesPlaceholder?.visibility = View.VISIBLE
                binding?.gamesPlaceholder?.setText(R.string.search_error)
                handleLoadingVisible(false)
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