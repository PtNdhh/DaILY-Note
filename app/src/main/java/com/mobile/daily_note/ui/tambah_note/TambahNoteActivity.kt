package com.mobile.daily_note.ui.tambah_note

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.mobile.daily_note.R
import com.mobile.daily_note.databinding.ActivityTambahNoteBinding

class TambahNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBarTitle: String = "Tambah Note"

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
//                showAlertDialog(ALERT_DIALOG_CLOSE)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_add_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {}
            R.id.action_delete -> {}
        }

        return super.onOptionsItemSelected(item)
    }
}