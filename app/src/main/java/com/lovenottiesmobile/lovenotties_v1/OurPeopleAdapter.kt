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

class OurPeopleAdapter(
    private val context: Context,
    private val opItemList: MutableList<OurPeopleModel>,
    private val mListener: IOPDetails
) : RecyclerView.Adapter<OurPeopleAdapter.OPViewHolder>() {

    interface IOPDetails {
        fun GotoOPDetails(SSItem: OurPeopleModel)
    }

    inner class OPViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val imgView: ImageView = itemView.findViewById(R.id.imgView)
        val tvDesc: TextView = itemView.findViewById(R.id.tvDesc)
        var opItem: OurPeopleModel? = null

        init {
            tvDesc.setOnClickListener {
                Log.d("demo", "Onclick item clicked $adapterPosition Category: ${opItem?.OPTitle}")
               //mListener.GotoOPDetails(opItem!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OPViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.op_item_aes, parent, false)
        return OPViewHolder(v)
    }


    override fun onBindViewHolder(holder: OPViewHolder, position: Int) {
        val opItem = opItemList[position]

        //add blog item details
        if (opItem != null) {
            holder.tvTitle.text = opItem.OPTitle + " " + opItem.OPFName + " " + opItem.OPLName
            if (opItem.OPDescription.length >= 50){
                holder.tvDesc.text = opItem.OPDescription.substring(0, 50) + "\n... Show More"
            }
            else
            {
                holder.tvDesc.text = opItem.OPDescription
            }
            holder.opItem = opItem


            holder.tvDesc.setOnClickListener {
                if (holder.tvDesc.text == (opItem.OPDescription.substring(0, 50) + "\n... Show More"))
                {
                holder.tvDesc.text = opItem.OPDescription + "\n... Show Less"
                }
                else
                {
                    holder.tvDesc.text = opItem.OPDescription.substring(0, 50) + "\n... Show More"
                }
        }
            // load image
            val ref: String = "https://lovenottiesblob.blob.core.windows.net/ourpeopleimages/${opItem.OPImageID}"
            Glide.with(context).load(ref).into(holder.imgView)
        }
        else { // if no blog items, display prompt
            holder.tvTitle.text = "No People Added"
            Glide.with(context).load(R.drawable.logo).into(holder.imgView)
        }
    }

    override fun getItemCount(): Int {
        return opItemList.size
    }
}