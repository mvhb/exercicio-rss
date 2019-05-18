package br.ufpe.cin.if710.rss

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.itemlista.view.*


class RssListAdapter (var rss : List<ItemRSS>, val context : Context) : Adapter<RssListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.itemlista, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
            return rss.size
    }

    override fun onBindViewHolder(holder: RssListAdapter.ViewHolder, position: Int) {
        val rssItem = rss[position]
        holder.title.text = rssItem.title
        holder.data.text = rssItem.pubDate

        holder.title.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(rssItem.link))
            context.startActivity(intent)
        }
    }

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val title = itemView.item_titulo
        val data = itemView.item_data
    }
}

