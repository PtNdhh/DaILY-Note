package com.mobile.daily_note.ui.home.ui.notes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.daily_note.data.local.UserPreference
import com.mobile.daily_note.data.local.datastore
import com.mobile.daily_note.databinding.FragmentNotesBinding
import com.mobile.daily_note.helper.ViewModelFactory
import com.mobile.daily_note.ui.tambah_note.TambahNoteActivity

class NotesFragment : Fragment() {

    companion object {
        fun newInstance() = NotesFragment()
    }

    private lateinit var viewModel: NotesViewModel
    private lateinit var binding: FragmentNotesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pref = UserPreference.getInstance(requireContext().datastore)
        viewModel = ViewModelProvider(this,ViewModelFactory (pref))[NotesViewModel::class.java]


        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager

        viewModel.listNote.observe(viewLifecycleOwner){
            val adapter = ListNotesAdapter()
            adapter.submitList(it)
            binding.recyclerView.adapter = adapter
        }

        binding.floatingActionButton2.setOnClickListener {
            val intent = Intent(context, TambahNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }


}