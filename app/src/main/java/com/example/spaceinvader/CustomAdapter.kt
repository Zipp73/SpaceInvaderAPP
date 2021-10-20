package com.example.spaceinvader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val mList: List<Player>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val playerTV: TextView = itemView.findViewById(R.id.player_tv)
        val scoreTV: TextView = itemView.findViewById(R.id.score_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        val player = mList[pos]
        holder.playerTV.text = player.nickname
        holder.scoreTV.text = player.score.toString()
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}

