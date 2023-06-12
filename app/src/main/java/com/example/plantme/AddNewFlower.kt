package com.example.plantme

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.plantme.data.Databse
import com.example.plantme.data.entities.Flower
import com.example.plantme.databinding.FragmentAddNewFlowerBinding
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.sql.Blob


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddNewFlower() : Fragment() {

    private val WATER = 1
    private val FERTILIZE = 2
    private val REPOT = 3
    private val CLEAN = 4

    private val IMAGE_REQUEST_CODE = 8242008
    private var imagePath: Uri? = null
    var imageToStoreInArray :ByteArray? = null


    private var _binding: FragmentAddNewFlowerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentAddNewFlowerBinding.inflate(inflater, container, false)
        val dao = context?.let { Databse.getInstance(it).createDao() }

        binding.pictureFlower.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }

        binding.btnAdd.setOnClickListener {
            lifecycleScope.launch {
                dao?.insertNewFlower(Flower(binding.etName.text.toString(),binding.etType.text.toString(), imageToStoreInArray, binding.etWateringFrequency.text.toString().toInt(),binding.etFertzilizingFrequency.text.toString().toInt(),binding.etRepotingFrequency.text.toString().toInt(),binding.etCleaningFrequency.text.toString().toInt()))
            }
            val parameter: String = binding.etName.text.toString()

            val action = AddNewFlowerDirections.actionAddNewFlowerToSecondFragment(parameter)

            it.findNavController().navigate(action)
        }


        return binding.root


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            binding.pictureFlower.setImageURI(data?.data)

            imagePath = data?.data
            var imageToStore = MediaStore.Images.Media.getBitmap(context?.contentResolver, imagePath)
            val baos = ByteArrayOutputStream()

            imageToStore.compress(Bitmap.CompressFormat.JPEG, 100, baos)

            imageToStoreInArray = baos.toByteArray()


        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}