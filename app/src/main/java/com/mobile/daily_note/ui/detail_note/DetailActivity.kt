package com.mobile.daily_note.ui.detail_note

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.mobile.daily_note.R
import com.mobile.daily_note.data.local.UserPreference
import com.mobile.daily_note.data.local.datastore
import com.mobile.daily_note.databinding.AddNoteAlternateBinding
import com.mobile.daily_note.helper.ViewModelFactory
import com.mobile.daily_note.ui.home.HomeActivity
import com.mobile.daily_note.ui.home.ui.profile.ProfileViewModel
import com.mobile.daily_note.ui.tambah_note.TambahNoteViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: AddNoteAlternateBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var viewModelNote: TambahNoteViewModel
    private var isArchive: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddNoteAlternateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noteTitle = intent.getStringExtra("title_note")
        val noteBody = intent.getStringExtra("body_note")
        binding.etTitle.setText(noteTitle)
        binding.etBody.setText(noteBody)
        isArchive = intent.getBooleanExtra("is_archive", false)

        supportActionBar?.title = noteTitle

        val pref = UserPreference.getInstance(application.datastore)
        viewModel = ViewModelProvider(this, ViewModelFactory (pref))[DetailViewModel::class.java]
        viewModelNote = ViewModelProvider(this, ViewModelFactory (pref))[TambahNoteViewModel::class.java]

        viewModel.getTheme().observe(this){
            binding.swDark2 .isChecked = it.isDark
            val mode = if (it.isDark) AppCompatDelegate.MODE_NIGHT_YES  else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(mode)
        }
        binding.swDark2 .setOnCheckedChangeListener{ _: CompoundButton?, isChecked: Boolean ->
            viewModel.setThemeSetting(isChecked)
        }


//        experiment
//        viewModelProfile = ViewModelProvider(this, ViewModelFactory(pref))[ProfileViewModel::class.java]
//        viewModelProfile.getTheme().observe(this){
//            binding.swDark2.isChecked = it
//            val mode = if (it) AppCompatDelegate.MODE_NIGHT_YES  else AppCompatDelegate.MODE_NIGHT_NO
//            AppCompatDelegate.setDefaultNightMode(mode)
//        }
//        binding.swDark2.setOnCheckedChangeListener{ _: CompoundButton?, isChecked: Boolean ->
//            viewModelProfile.setThemeSetting(isChecked)
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_detail_menu, menu)

        val archiveItem = menu.findItem(R.id.action_archive)
        if (isArchive) {
            archiveItem.setIcon(R.drawable.outline_unarchive_24)
        }else{
            archiveItem.setIcon(R.drawable.outline_archive_24)
        }

        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = intent.getStringExtra("id_note")

        when (item.itemId) {
            R.id.action_save -> {
                val title = binding.etTitle.text.toString()
                val body = binding.etBody.text.toString()
                if (id != null) {
                    viewModelNote.uploadNote(title, body)
                    viewModel.deleteNote(id)
                    moveToFragment()
                }
            }
            R.id.action_archive -> {
                if (id != null) {
                    if (isArchive) {
                        viewModel.unArchiveNote(id)
                    } else {
                        viewModel.archiveNote(id)
                    }
                    moveToFragment()
                }
            }
            R.id.action_delete -> {
                if (id != null) {
                    viewModel.deleteNote(id)
                    moveToFragment()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun moveToFragment(){
        val intent = Intent(this, HomeActivity::class.java)
        if (isArchive){
            intent.putExtra("navigate_to", R.id.navigation_archive) // Kirim ID menu untuk fragment Archive
        }else{
            intent.putExtra("navigate_to", R.id.navigation_notes) // Kirim ID menu untuk fragment Archive
        }
        startActivity(intent)
        finish()
    }
}