package com.mobile.daily_note.ui.home.ui.notes

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobile.daily_note.data.network.retrofit.response.DataNote
import com.mobile.daily_note.databinding.ItemNoteBinding
import com.mobile.daily_note.ui.detail_note.DetailActivity
import java.text.SimpleDateFormat
import java.util.Locale

class ListNotesAdapter : ListAdapter<DataNote, ListNotesAdapter.MyViewHolder>(DIFF_CALLBACK){
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataNote>() {
            override fun areItemsTheSame(oldItem: DataNote, newItem: DataNote): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataNote, newItem: DataNote): Boolean {
                return oldItem == newItem
            }
        }
    }

    class MyViewHolder (private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root){
        fun showFormattedDate(date: String): String {
            val inputDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)
            val outputFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale("id", "ID"))
            return outputFormat.format(inputDate)
        }
        fun bind(note : DataNote) {
            binding.tvNoteTitle.text = note.title
            binding.tvNoteBody.text = note.body
            binding.tvNoteDate.text = showFormattedDate(note.createdAt)
            binding.cardItem.setOnClickListener {
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra("id_note", note.id)
                intent.putExtra("title_note", note.title)
                intent.putExtra("body_note", note.body)
                intent.putExtra("is_archive", note.archived)
                it.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNotesAdapter.MyViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note)
    }

}