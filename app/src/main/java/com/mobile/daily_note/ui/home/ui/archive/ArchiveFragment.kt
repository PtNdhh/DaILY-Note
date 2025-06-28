package com.mobile.daily_note.ui.home.ui.archive

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.daily_note.R
import com.mobile.daily_note.data.local.UserPreference
import com.mobile.daily_note.data.local.datastore
import com.mobile.daily_note.databinding.FragmentArchiveBinding
import com.mobile.daily_note.databinding.FragmentNotesBinding
import com.mobile.daily_note.helper.ViewModelFactory
import com.mobile.daily_note.ui.home.ui.notes.ListNotesAdapter
import com.mobile.daily_note.ui.home.ui.notes.NotesViewModel
import com.mobile.daily_note.ui.tambah_note.TambahNoteActivity

class ArchiveFragment : Fragment() {

    companion object {
        fun newInstance() = ArchiveFragment()
    }

    private lateinit var viewModel: ArchiveViewModel
    private lateinit var binding: FragmentArchiveBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pref = UserPreference.getInstance(requireContext().datastore)
        viewModel = ViewModelProvider(this,ViewModelFactory (pref))[ArchiveViewModel::class.java]

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager

        viewModel.listNote.observe(viewLifecycleOwner){
            val adapter = ListNotesAdapter()
            adapter.submitList(it)
            binding.recyclerView.adapter = adapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArchiveBinding.inflate(inflater, container, false)
        return binding.root
    }
}