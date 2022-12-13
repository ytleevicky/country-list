package com.vickylee.vicky_finaltest.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vickylee.vicky_finaltest.R
import com.vickylee.vicky_finaltest.adapters.CountryListAdapter
import com.vickylee.vicky_finaltest.adapters.OnItemClickListener
import com.vickylee.vicky_finaltest.api.IAPIResponse
import com.vickylee.vicky_finaltest.api.RetrofitInstance
import com.vickylee.vicky_finaltest.databinding.FragmentCountryListBinding
import com.vickylee.vicky_finaltest.models.Country
import com.vickylee.vicky_finaltest.models.CountryJSONResponse
import kotlinx.coroutines.launch

class CountryListFragment : Fragment(R.layout.fragment_country_list), OnItemClickListener {

    //region Properties
    val TAG = this.toString()
    private var _binding: FragmentCountryListBinding? = null
    private val binding get() = _binding!!
    lateinit var countryArrayList: ArrayList<Country>
    var countryAdapter: CountryListAdapter? = null

    var countryJSONResponseData : MutableLiveData<List<CountryJSONResponse>> = MutableLiveData<List<CountryJSONResponse>>()
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() is executing")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countryArrayList = arrayListOf()

        // API
        var api: IAPIResponse = RetrofitInstance.retrofitService

        viewLifecycleOwner.lifecycleScope.launch {
            val countryListFromAPI: List<CountryJSONResponse> = api.getAllCountries()
            countryJSONResponseData.postValue(countryListFromAPI)
        }

        // Recycler View
        countryAdapter = CountryListAdapter(this.requireContext(), countryArrayList, this)
        binding.rvCountryList.layoutManager = LinearLayoutManager(this.requireContext())
        binding.rvCountryList.adapter = countryAdapter
    }

    override fun onResume() {
        super.onResume()

        countryJSONResponseData.observe(this, Observer { list ->

            val sortedList = list.sortedWith(compareBy({it.name.common}))

            if(sortedList != null) {
                countryArrayList.clear()
                for(item in sortedList){
                    val countryName = item.name.common
                    val capital = item.capital
                    val population = item.population
                    val capital_latlng = item.capitalInfo.latlng
                    val country_latlng = item.latlng

                    val country = Country(countryName, capital, population, capital_latlng, country_latlng)
                    countryArrayList.add(country)
                    countryAdapter?.notifyDataSetChanged()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(country: Country, position: Int) {
        val action = CountryListFragmentDirections.actionCountryListFragmentToCountryDetailsFragment(country)
        findNavController().navigate(action)
    }

}