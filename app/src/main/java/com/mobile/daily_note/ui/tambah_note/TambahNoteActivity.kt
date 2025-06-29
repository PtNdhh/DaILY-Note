package com.mobile.daily_note.ui.tambah_note

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
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

class TambahNoteActivity : AppCompatActivity() {

    private lateinit var binding: AddNoteAlternateBinding
    private lateinit var viewModel: TambahNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddNoteAlternateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val actionBarTitle: String = ""
//
        supportActionBar?.title = actionBarTitle


        val pref = UserPreference.getInstance(application.datastore)
        viewModel = ViewModelProvider(this, ViewModelFactory (pref))[TambahNoteViewModel::class.java]

        viewModel.isSuccess.observe(this){
            if (it == "success"){
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }

        viewModel.isLoading.observe(this){
            binding.pbNote.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.getTheme().observe(this){
            binding.swDark2.isChecked= it.isDark
            val mode = if (it.isDark) AppCompatDelegate.MODE_NIGHT_YES  else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(mode)
        }
        binding.swDark2.setOnCheckedChangeListener{ _: CompoundButton?, isChecked: Boolean ->
            viewModel.setThemeSetting(isChecked)
        }

        binding.etTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                supportActionBar?.title = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_add_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                val title = binding.etTitle.text.toString()
                val body = binding.etBody.text.toString()
                viewModel.uploadNote(title, body)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}