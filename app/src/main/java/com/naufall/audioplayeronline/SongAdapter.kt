package com.naufall.audioplayeronline

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naufall.audioplayeronline.databinding.ItemSongBinding
import com.naufall.audioplayeronline.model.Song

class SongAdapter: RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    val list = ArrayList<Song>()

    fun setList(list: List<Song>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(private val binding: ItemSongBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            with(binding) {
                tvTittle.text = song.title
                seekBar.isEnabled = false
                btnPlay.setOnClickListener {
                    (binding.root.context as MainActivity).playSong(song.audioUrl, seekBar, btnPlay)
                }
            }
        }
    }
}