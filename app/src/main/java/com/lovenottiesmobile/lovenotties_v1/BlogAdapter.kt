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

class BlogAdapter(
    private val context: Context,
    private val blogItemList: MutableList<BlogItemModel>,
    private val mListener: IBlogDetails
) : RecyclerView.Adapter<BlogAdapter.CategoryViewHolder>() {

    interface IBlogDetails {
        fun GotoBlogDetails(blogItem: BlogItemModel)
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val imgView: ImageView = itemView.findViewById(R.id.imgView)
        val tvDesc: TextView = itemView.findViewById(R.id.tvDesc)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        var blogItem: BlogItemModel? = null

        init {
            tvDesc.setOnClickListener {
                Log.d("demo", "Onclick item clicked $adapterPosition Category: ${blogItem?.BlogTitle}")
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
            holder.tvTitle.text = blogItem.BlogTitle
            holder.tvDate.text = blogItem.BlogDate
            if (blogItem.BlogDescription.length >= 50){
                holder.tvDesc.text = blogItem.BlogDescription.substring(0, 50) + "\n... Show More"
            }
            else
            {
                holder.tvDesc.text = blogItem.BlogDescription
            }

            holder.tvDesc.setOnClickListener {
                if (holder.tvDesc.text == (blogItem.BlogDescription.substring(0, 50) + "\n... Show More"))
                {
                    holder.tvDesc.text = blogItem.BlogDescription + "\n... Show Less"
                }
                else
                {
                    holder.tvDesc.text = blogItem.BlogDescription.substring(0, 50) + "\n... Show More"
                }
            }

            holder.blogItem = blogItem

            // load image
            val ref = "https://lovenottiesblob.blob.core.windows.net/blogimages/${blogItem.BlogImageID}"
            Glide.with(context).load(ref).into(holder.imgView)
        }
        else { // if no blog items, display prompt
            holder.tvTitle.text = "No Blog Posts Added"
            Glide.with(context).load(R.drawable.lovenottieslogo).into(holder.imgView)
        }
    }

    override fun getItemCount(): Int {
        return blogItemList.size
    }
}