package com.haohz.search.search

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haohz.search.R
import com.haohz.search.dto.CompanyDto
import com.haohz.search.dto.SearchDto

class CompanyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val ivCompany: ImageView

    private val tvCompany: TextView
    fun bindData(context: Context, dto: CompanyDto?) {
        dto?.apply {
            tvCompany.text = name
            Glide.with(context)
                .load(logoUrl)
                .into(ivCompany)
        }

    }

    init {
        tvCompany = itemView.findViewById(R.id.tv_company)
        ivCompany = itemView.findViewById(R.id.iv_company)
    }
}