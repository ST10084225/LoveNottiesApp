package com.lovenottiesmobile.lovenotties_v1

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class WhatsOnAdapter(
    private val context: Context,
    private val blogItemList: MutableList<WhatsOnModel>,
    private val mListener: IBlogDetails
) : RecyclerView.Adapter<WhatsOnAdapter.CategoryViewHolder>() {

    interface IBlogDetails {
        fun GotoBlogDetails(blogItem: WhatsOnModel)
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val imgView: ImageView = itemView.findViewById(R.id.imgView)
        val tvDesc: TextView = itemView.findViewById(R.id.tvDesc)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        var blogItem: WhatsOnModel? = null

        init {
            tvDesc.setOnClickListener {
                Log.d("demo", "Onclick item clicked $adapterPosition Category: ${blogItem?.PostTitle}")
                //mListener.GotoBlogDetails(blogItem!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.blog_item_aes, parent, false)
        return CategoryViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val blogItem = blogItemList[position]

        //add blog item details
        if (blogItem != null) {
            holder.tvTitle.text = blogItem.PostTitle
            holder.tvDate.text = blogItem.PostDate
            if (blogItem.PostDescription.length >= 50){
                holder.tvDesc.text = blogItem.PostDescription.substring(0, 50) + "\n... Show More"
            }
            else
            {
                holder.tvDesc.text = blogItem.PostDescription
            }

            holder.tvDesc.setOnClickListener {
                if (holder.tvDesc.text == (blogItem.PostDescription.substring(0, 50) + "\n... Show More"))
                {
                    holder.tvDesc.text = blogItem.PostDescription + "\n... Show Less"
                }
                else
                {
                    holder.tvDesc.text = blogItem.PostDescription.substring(0, 50) + "\n... Show More"
                }
            }

            holder.blogItem = blogItem

            // load image
            val ref = "https://lovenottiesblob.blob.core.windows.net/blogimages/${blogItem.PostImageID}"
            Glide.with(context).load(ref).into(holder.imgView)
        }
        else { // if no blog items, display prompt
            holder.tvTitle.text = "No Whats On Posts Added"
            Glide.with(context).load(R.drawable.lovenottieslogo).into(holder.imgView)
        }
    }

    override fun getItemCount(): Int {
        return blogItemList.size
    }
}