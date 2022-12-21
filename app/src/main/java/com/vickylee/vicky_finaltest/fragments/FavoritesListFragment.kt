package com.vickylee.vicky_finaltest.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vickylee.vicky_finaltest.R
import com.vickylee.vicky_finaltest.adapters.CountryListAdapter
import com.vickylee.vicky_finaltest.adapters.OnItemClickListener
import com.vickylee.vicky_finaltest.databinding.FragmentFavoritesListBinding
import com.vickylee.vicky_finaltest.db.CountryRepository
import com.vickylee.vicky_finaltest.models.Country
import kotlinx.coroutines.launch

class FavoritesListFragment : Fragment(R.layout.fragment_favorites_list), OnItemClickListener {

    //region Properties
    val TAG = this.toString()
    private var _binding: FragmentFavoritesListBinding? = null
    private val binding get() = _binding!!
    lateinit var favCountryArrayList: ArrayList<Country>
    var countryAdapter: CountryListAdapter? = null

    // Room
    private lateinit var countryRepository: CountryRepository
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        countryRepository = CountryRepository(requireActivity().application)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favCountryArrayList = arrayListOf()

        // recycler View
        countryAdapter = CountryListAdapter(this.requireContext(), favCountryArrayList, this)
        binding.rvFavCountryList.layoutManager = LinearLayoutManager(this.requireContext())
        binding.rvFavCountryList.adapter = countryAdapter
    }

    override fun onResume() {
        super.onResume()

        countryRepository.allFavCountries.observe(this) { favCountryList ->
            if (favCountryList.isNotEmpty()) {
                favCountryArrayList.clear()

                // Run in background
                lifecycleScope.launch {
                    favCountryList.forEach { country ->

                        val countryName = country.countryName
                        val capital = listOf<String>(country.capital)
                        val population = country.population
                        val capitalInfo = listOf<String>()
                        val laglng = listOf<String>()
                        val flagImg = country.flagImg
                        val flagEmoji = country.flagEmoji

                        val country: Country =
                            Country(countryName, capital, population, capitalInfo, laglng, flagImg, flagEmoji)

                        if (country != null) {
                            favCountryArrayList.add(country as Country)
                            countryAdapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
        }

    }

    override fun onItemClicked(country: Country, position: Int) {
        lifecycleScope.launch {

            // Remove from db
            countryRepository.removeFavCountry(
                countryRepository.allFavCountries.value!!.get(
                    position
                )
            )

            if (position == 0 && favCountryArrayList.size == 1) {
                // clear the list
                favCountryArrayList.clear()
            }

            countryAdapter?.notifyDataSetChanged()
        }
    }
}