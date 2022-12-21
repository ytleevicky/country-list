package com.vickylee.vicky_finaltest.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vickylee.vicky_finaltest.databinding.CountryBinding
import com.vickylee.vicky_finaltest.fragments.FavoritesListFragment
import com.vickylee.vicky_finaltest.models.Country

class CountryListAdapter(
    private val context: Context,
    private val countryList: ArrayList<Country>,
    private val clickListener: OnItemClickListener
) : RecyclerView.Adapter<CountryListAdapter.ItemViewHolder>() {

    // bind the data with the view
    class ItemViewHolder(var binding: CountryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: Country, clickListener: OnItemClickListener) {

            binding.tvName.setText(currentItem.name)

            if (currentItem.capital == null || currentItem.capital.isEmpty()) {
                binding.tvCapital.setText("N/A")
            } else {
                var text = ""
                for (item in currentItem.capital) {
                    text += item
                }
                binding.tvCapital.setText("$text")
            }

            binding.tvPopulation.setText("Population: ${currentItem.population}")

            val imageView = binding.ivFlag

            Picasso.get().load(currentItem.flagImg).into(imageView)

            itemView.setOnClickListener {
                clickListener.onItemClicked(currentItem, this.adapterPosition)
            }
        }

    }

    // creates the appearance of view
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryListAdapter.ItemViewHolder {
        return CountryListAdapter.ItemViewHolder(
            CountryBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    // binds the data with the view
    override fun onBindViewHolder(holder: CountryListAdapter.ItemViewHolder, position: Int) {
        holder.bind(
            countryList.get(position),
            clickListener
        )
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

}