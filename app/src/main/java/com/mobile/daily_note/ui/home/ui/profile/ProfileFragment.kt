package com.mobile.daily_note.ui.home.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.mobile.daily_note.R
import com.mobile.daily_note.data.local.UserPreference
import com.mobile.daily_note.data.local.datastore
import com.mobile.daily_note.databinding.ActivityProfileBinding
import com.mobile.daily_note.databinding.FragmentArchiveBinding
import com.mobile.daily_note.databinding.FragmentProfileBinding
import com.mobile.daily_note.helper.ViewModelFactory
import com.mobile.daily_note.ui.home.ui.archive.ArchiveViewModel
import com.mobile.daily_note.ui.login.LoginActivity
import com.mobile.daily_note.ui.welcomePage.WelcomeActivity
import java.util.jar.Manifest
import androidx.core.net.toUri

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }
    private lateinit var binding: ActivityProfileBinding

    private lateinit var  viewModel: ProfileViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pref = UserPreference.getInstance(requireContext().datastore)
        viewModel = ViewModelProvider(this, ViewModelFactory (pref))[ProfileViewModel::class.java]

        viewModel.getTheme().observe(viewLifecycleOwner){
            binding.switchTheme.isChecked= it.isDark
            val mode = if (it.isDark) AppCompatDelegate.MODE_NIGHT_YES  else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(mode)
            it.imgUri.let {
                binding.imgProfile.setImageURI(it.toUri())
            }
        }

        binding.switchTheme.setOnCheckedChangeListener{ _: CompoundButton?, isChecked: Boolean ->
            viewModel.setThemeSetting(isChecked)
        }
        binding.logoutButton.setOnClickListener{
            viewModel.logout()
            val intent = Intent (context, WelcomeActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.cardView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

        viewModel.result.observe(viewLifecycleOwner){
            binding.profileName.text = it.name
            binding.profileEmail.text = it.email
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = result.data?.data
            if (selectedImageUri != null) {
                binding.imgProfile.setImageURI(selectedImageUri)
                viewModel.setUriImage(selectedImageUri.toString())
            }
        }
    }
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openGallery()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }
}