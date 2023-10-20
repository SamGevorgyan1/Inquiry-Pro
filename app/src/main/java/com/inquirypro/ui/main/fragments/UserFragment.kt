package com.inquirypro.ui.main.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.inquirypro.R
import com.inquirypro.base.BaseFragment
import com.inquirypro.databinding.FragmentUserBinding
import com.inquirypro.model.login.LoginResponse
import com.inquirypro.repository.Result
import com.inquirypro.ui.auth.RegisterActivity
import com.inquirypro.ui.viewmodel.QuestionResultViewModel
import com.inquirypro.ui.viewmodel.HistoryViewModel
import com.inquirypro.ui.viewmodel.LogoutViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class UserFragment : BaseFragment(R.layout.fragment_user) {

    private lateinit var binding: FragmentUserBinding
    private val questionResultViewModel by viewModel<QuestionResultViewModel>()
    private val historyViewModel by viewModel<HistoryViewModel>()
    private val logoutViewModel by viewModel<LogoutViewModel>()

    private val PICK_IMAGE_REQUEST = 2
    private val IMAGE_FILENAME = "user_image.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyViewModel.getHistoriesByUserId(1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserBinding.bind(view)

        val user = LoginResponse.retrieverUser()

        lifecycleScope.launch {
            user?.id?.let {
                val userCorrectAnswers = questionResultViewModel.getUserCorrectAnswerAmount(it)
                val userIncorrectAnswers = questionResultViewModel.getUserIncorrectAnswerAmount(it)

                binding.tvCorrectAnswersNumber.text = userCorrectAnswers.toString()
                binding.tvIncorrectAnswersNumber.text = userIncorrectAnswers.toString()



                if (userCorrectAnswers != null && userIncorrectAnswers != null && userCorrectAnswers > 0) {
                    val progress: Double

                    if (userCorrectAnswers >= userIncorrectAnswers) {
                        progress =
                            ((userCorrectAnswers - userIncorrectAnswers).toDouble() / userCorrectAnswers) * 100.0
                    } else {
                        progress = 0.0
                    }

                    binding.Prog.progress = progress.toInt()
                    binding.tvProgressPercent.text = String.format("%.2f%%", progress)
                }
            }


        }

        val userFullName = user?.firstName + " " + user?.lastName

        binding.tvUserFullName.text = userFullName
        binding.tvUserEmail.text = user?.email

        setupListener()
    }

    private fun setupListener() {
        binding.tvCorrectAnswersNumber.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_correctAnswerFragment)
        }

        binding.tvIncorrectAnswersNumber.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_inCorrectAnswerFragment)
        }

        binding.btnLogout.setOnClickListener {
            logoutViewModel.logout()
            logoutViewModel.logoutResponse.observe(viewLifecycleOwner) { result ->

                when (result) {
                    is Result.Success -> {
                        val intent = Intent(requireContext(), RegisterActivity::class.java)
                        activity?.startActivity(intent)
                        Toast.makeText(requireContext(), "logout success", Toast.LENGTH_SHORT)
                            .show()

                    }
                    is Result.Error -> {
                        Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

        binding.ivUser.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data

            selectedImageUri?.let { uri ->
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            requireContext().contentResolver,
                            uri
                        )

                        val resizedBitmap = resizeBitmap(bitmap, 200, 200)

                        saveBitmapToFile(resizedBitmap, IMAGE_FILENAME)


                        withContext(Dispatchers.Main) {
                            binding.ivUser.setImageBitmap(resizedBitmap)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }


    private fun resizeBitmap(bitmap: Bitmap, width: Int, height: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }

    private fun saveBitmapToFile(bitmap: Bitmap, filename: String) {
        val file = File(requireContext().filesDir, filename)
        val fos = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.close()
    }
}
