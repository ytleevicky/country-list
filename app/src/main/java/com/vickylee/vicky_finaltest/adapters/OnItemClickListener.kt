package com.vickylee.vicky_finaltest.adapters

import com.vickylee.vicky_finaltest.models.Country

interface OnItemClickListener {
    fun onItemClicked(country: Country, position: Int)
}