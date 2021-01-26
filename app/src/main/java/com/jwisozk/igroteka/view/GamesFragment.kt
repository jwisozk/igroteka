package com.jwisozk.igroteka.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.jwisozk.igroteka.App
import com.jwisozk.igroteka.R
import com.jwisozk.igroteka.databinding.FragmentGamesBinding
import com.jwisozk.igroteka.util.GridSpacingItemDecoration
import com.jwisozk.igroteka.util.hideKeyboard
import com.jwisozk.igroteka.viewmodel.*
import kotlinx.coroutines.launch

class GamesFragment : Fragment(R.layout.fragment_games) {

    private var binding: FragmentGamesBinding? = null
    private lateinit var viewModel: GamesViewModel
    private lateinit var gamesAdapter: GamesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        initListeners()
    }

    private fun init(view: View) {
        binding = FragmentGamesBinding.bind(view)
        binding?.gamesList?.apply {
            // Set span count depending on layout
            val spanCount = when (resources.configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> 4
                else -> 2
            }
            layoutManager = GridLayoutManager(activity, spanCount)
            gamesAdapter = GamesAdapter {
                // show DetailFragment
                Toast.makeText(this@GamesFragment.context, it.name, Toast.LENGTH_SHORT).show()
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
                        viewModel.sendSearchQuery(v.text.toString())
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
            viewModel.searchResult.observe(viewLifecycleOwner, {
                handleGamesList(it)
            })
            viewModel.searchState.observe(viewLifecycleOwner, {
                handleLoadingState(it)
            })
            viewModel.countGames.observe(viewLifecycleOwner, {
                setCountGames(it)
            })

        } ?: throw IllegalStateException("Binding is null in InputFragment")
    }

    private fun handleLoadingState(it: SearchState) {
        when (it) {
            Loading -> {
                binding?.searchProgress?.visibility = View.VISIBLE
            }
            Ready -> {
                binding?.searchProgress?.visibility = View.GONE
            }
        }
    }

    private fun handleGamesList(it: GamesResult) {
        when (it) {
            is ValidResult -> {
                binding?.gamesPlaceholder?.visibility = View.GONE
                binding?.gamesList?.visibility = View.VISIBLE
                gamesAdapter.submitList(it.result)
            }
            is ErrorResult -> {
                gamesAdapter.submitList(emptyList())
                binding?.gamesPlaceholder?.visibility = View.VISIBLE
                binding?.gamesList?.visibility = View.GONE
                binding?.gamesPlaceholder?.setText(R.string.search_error)
            }
            is EmptyResult -> {
                gamesAdapter.submitList(emptyList())
                binding?.gamesPlaceholder?.visibility = View.VISIBLE
                binding?.gamesList?.visibility = View.GONE
                binding?.gamesPlaceholder?.setText(R.string.empty_result)
            }
            is EmptyQuery -> {
                gamesAdapter.submitList(emptyList())
                binding?.gamesPlaceholder?.visibility = View.VISIBLE
                binding?.gamesList?.visibility = View.GONE
                binding?.gamesPlaceholder?.setText(R.string.games_placeholder)
            }
        }
    }

    private fun setCountGames(count: Int) {
        binding?.searchInput?.hint = "${getString(R.string.hint_search_query_search)} " +
                String.format("%,d", count) +
                " ${getString(R.string.hint_search_query_games)}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}