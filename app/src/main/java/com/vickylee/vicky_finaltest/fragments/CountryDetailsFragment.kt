package com.vickylee.vicky_finaltest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.vickylee.vicky_finaltest.R
import com.vickylee.vicky_finaltest.databinding.FragmentCountryDetailsBinding

class CountryDetailsFragment : Fragment(R.layout.fragment_country_details) {

    //region Properties
    val TAG = this.toString()
    private var _binding: FragmentCountryDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: CountryDetailsFragmentArgs by navArgs()
    //endregion

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvName.setText(args.country.name)

        if (args.country.capital == null || (args.country.capital)!!.isEmpty()) {
            binding.tvCapital.setText("Capital: N/A")
        } else {
            var text = "Capital: "
            for(item in args.country.capital!!) {
                text += item
            }
            binding.tvCapital.setText("$text")
        }

        binding.tvPopulation.setText("Population: ${args.country.population}")

    }

}