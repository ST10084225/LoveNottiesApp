package com.lovenottiesmobile.lovenotties_v1

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SuccessStoryAdapter(
    private val context: Context,
    private val ssItemList: MutableList<SuccessStoryModel>,
    private val mListener: ISSDetails
) : RecyclerView.Adapter<SuccessStoryAdapter.SSViewHolder>() {

    interface ISSDetails {
        fun GotoSSDetails(SSItem: SuccessStoryModel)
    }

    inner class SSViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val imgView: ImageView = itemView.findViewById(R.id.imgView)
        val tvDesc: TextView = itemView.findViewById(R.id.tvDesc)
        var blogItem: SuccessStoryModel? = null

        init {
            tvDesc.setOnClickListener {
                Log.d("demo", "Onclick item clicked $adapterPosition Category: ${blogItem?.SSTitle}")
                //mListener.GotoSSDetails(blogItem!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SSViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.ss_item_aes, parent, false)
        return SSViewHolder(v)
    }


    override fun onBindViewHolder(holder: SSViewHolder, position: Int) {
        val ssItem = ssItemList[position]

        //add blog item details
        if (ssItem != null) {
            holder.tvTitle.text = ssItem.SSTitle
            if (ssItem.SSDescription.length >= 50){
                holder.tvDesc.text = ssItem.SSDescription.substring(0, 50) + "\n... Show More"
            }
            else
            {
                holder.tvDesc.text = ssItem.SSDescription
            }
            holder.blogItem = ssItem

            holder.tvDesc.setOnClickListener {
                if (holder.tvDesc.text == (ssItem.SSDescription.substring(
                        0,
                        50
                    ) + "\n... Show More")
                ) {
                    holder.tvDesc.text = ssItem.SSDescription + "\n... Show Less"
                } else {
                    holder.tvDesc.text = ssItem.SSDescription.substring(0, 50) + "\n... Show More"
                }
            }

            // load image
            val ref: String = "https://lovenottiesblob.blob.core.windows.net/successimages/${ssItem.SSImageID}"
            Glide.with(context).load(ref).into(holder.imgView)
        }
        else { // if no blog items, display prompt
            holder.tvTitle.text = "No Success Stories Added"
            Glide.with(context).load(R.drawable.logo).into(holder.imgView)
        }
    }

    override fun getItemCount(): Int {
        return ssItemList.size
    }
}