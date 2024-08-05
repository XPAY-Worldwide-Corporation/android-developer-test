package com.exam.myapp.ui.pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exam.myapp.data.local.room.PokemonEnity
import com.exam.myapp.databinding.ItemPokemonBinding

class PokemonListAdapter : RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>(){

    private lateinit var pokemonList: List<PokemonEnity>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position])
    }

    override fun getItemCount(): Int {
        return if (::pokemonList.isInitialized) pokemonList.size else 0
    }

    fun updatePokemonList(newList: List<PokemonEnity>) {
        this.pokemonList = newList
        notifyDataSetChanged()
    }

    class PokemonViewHolder(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: PokemonEnity) {
            binding.pokemon = pokemon
            itemView.setOnClickListener {  }

            binding.executePendingBindings()
        }
    }

}
