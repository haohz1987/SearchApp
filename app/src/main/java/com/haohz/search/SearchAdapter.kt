package com.haohz.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.haohz.search.dto.SearchDto
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.haohz.search.search.SearchTipsHolder

class SearchAdapter(private val mContext: Context, mList: List<SearchDto>?) :
    RecyclerView.Adapter<SearchTipsHolder>() {
    private val mDataList: List<SearchDto>?
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTipsHolder {
        return SearchTipsHolder(
            LayoutInflater.from(mContext).inflate(R.layout.holder_search_tips, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchTipsHolder, position: Int) {
        holder.bindData(mContext, mDataList!![position])
        holder.itemView.setOnClickListener { v: View? ->
            val intent = Intent(mContext,SearchResultActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("data",mDataList[position])
            intent.putExtras(bundle)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mDataList?.size ?: 0
    }


    init {
        mDataList = mList
    }
}