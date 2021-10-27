package com.example.trippleatt.ui.bSU5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trippleatt.R
import com.example.trippleatt.data.ShopListData
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.squareup.picasso.Picasso

class ShopListAdapter(
    private val shopList: List<ShopListData>,
    private val listener: OnItemClick
) : RecyclerView.Adapter<ShopListAdapter.DetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shop_list, parent, false)
        return DetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val currentItem = shopList[position]
        holder.textView1.text = currentItem.shopName
        holder.textView2.text = currentItem.category
        Picasso.get().load(currentItem.frontImageLink).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    inner class DetailViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val imageView: ImageView = itemView.findViewById(R.id.frontImageView)
        val textView1: TextView = itemView.findViewById(R.id.tvShopName)
        val textView2: TextView = itemView.findViewById(R.id.tvCategoryName)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION)
                listener.onItemClick(position)
        }
    }

    interface OnItemClick {
        fun onItemClick(position: Int)
    }
}