package com.haohz.search.search

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haohz.search.R
import com.haohz.search.dto.SearchDto

class SearchTipsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvTitle: TextView
    private val tvDescription: TextView
    private val tvCompany: TextView
    fun bindData(context: Context, dto: SearchDto?) {
        dto?.apply {
            tvTitle.text = title
            tvDescription.text = description
            company?.apply {
                tvCompany.text = "公司数量：${dto.company?.size}"
            }
        }

    }

    init {
        tvTitle = itemView.findViewById(R.id.tv_title)
        tvDescription = itemView.findViewById(R.id.tv_description)
        tvCompany = itemView.findViewById(R.id.tv_company)
    }
}