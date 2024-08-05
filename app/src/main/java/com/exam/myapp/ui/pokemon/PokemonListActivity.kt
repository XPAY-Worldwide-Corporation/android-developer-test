package com.exam.myapp.ui.pokemon

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.exam.myapp.databinding.ActivityMainBinding
import com.exam.myapp.util.DialogHelper
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class PokemonListActivity: DaggerAppCompatActivity()  {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: PokemonViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[PokemonViewModel::class.java]
        viewModel.start()

        val adapter = PokemonListAdapter()
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        binding.recyclerView.adapter = adapter

        viewModel.allPokemonList.observe(this) { pokemon ->
            viewModel.pokemonList.value = pokemon
        }

        viewModel.pokemonList.observe(this) { pokemon ->
            adapter.updatePokemonList(pokemon)
            binding.progressBar.visibility = View.GONE
            binding.swiperefresh.isRefreshing = false
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.loadingVisibility.observe(this) {
            if(it) {
                DialogHelper.showLoading(this, null, "Loading...")
            } else {
                DialogHelper.dismissProgressDialog()
            }
        }

        binding.swiperefresh.setOnRefreshListener {
            binding.swiperefresh.isRefreshing = true
//            viewModel.offset = 0
            viewModel.getPokemonList()
        }

//        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
//                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == viewModel.pokemonList.value!!.size - 1
//                    && viewModel.hasNextItems && recyclerView.canScrollVertically(-1)) {
//                    binding.progressBar.visibility = View.VISIBLE
//                    viewModel.getPokemonList()
//                }
//            }
//        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.searchPokemons(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    viewModel.searchPokemons(newText)
                } else {
                    viewModel.pokemonList.postValue(viewModel.allPokemonList.value)
                }
                return true
            }
        })

    }

    public override fun onDestroy() {
        super.onDestroy()
        DialogHelper.dismissProgressDialog()
    }

}