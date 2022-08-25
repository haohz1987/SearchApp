package com.haohz.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haohz.search.dto.CompanyDto
import com.haohz.search.search.CompanyHolder

class CompanyAdapter(private val mContext: Context, mList: List<CompanyDto>?) :
    RecyclerView.Adapter<CompanyHolder>() {
    private val mDataList: List<CompanyDto>?
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyHolder {
        return CompanyHolder(
            LayoutInflater.from(mContext).inflate(R.layout.holder_company, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CompanyHolder, position: Int) {
        holder.bindData(mContext, mDataList!![position])
        holder.itemView.setOnClickListener { v: View? ->

        }
    }

    override fun getItemCount(): Int {
        return mDataList?.size ?: 0
    }


    init {
        mDataList = mList
    }
}