package com.example.plantme.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.plantme.data.Databse
import com.example.plantme.data.entities.CareActivity
import com.example.plantme.data.entities.Flower
import com.example.plantme.databinding.FragmentAddNewFlowerBinding
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddNewFlower() : Fragment() {

    private val IMAGE_REQUEST_CODE = 8242008
    private var imagePath: Uri? = null
    var imageToStoreInArray :ByteArray? = null


    private var _binding: FragmentAddNewFlowerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddNewFlowerBinding.inflate(inflater, container, false)
        val dao = context?.let { Databse.getInstance(it).createDao() }

        binding.pictureFlower.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }

        binding.btnAdd.setOnClickListener {
            lifecycleScope.launch {
                if(dao?.getSpecificFlower(binding.etName.text.toString())  == null)
                {
                    dao?.insertNewFlower(Flower(binding.etName.text.toString(),binding.etType.text.toString(), imageToStoreInArray, binding.etWateringFrequency.text.toString().toInt(),binding.etFertzilizingFrequency.text.toString().toInt(),binding.etRepotingFrequency.text.toString().toInt()*30,binding.etCleaningFrequency.text.toString().toInt()))
                    dao?.insertNewActivity(CareActivity(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), binding.etName.text.toString(),1))
                    dao?.insertNewActivity(CareActivity(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), binding.etName.text.toString(),2))
                    dao?.insertNewActivity(CareActivity(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), binding.etName.text.toString(),3))
                    dao?.insertNewActivity(CareActivity(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), binding.etName.text.toString(),4))

                    val parameter: String = binding.etName.text.toString()
                    val action = AddNewFlowerDirections.actionAddNewFlowerToSecondFragment(parameter)
                    it.findNavController().navigate(action)
                } else {
                    Toast.makeText(context, "Kvietok so zadaným menom už existuje", Toast.LENGTH_LONG).show()
                }
            }
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            binding.pictureFlower.setImageURI(data?.data)

            imagePath = data?.data
            val imageToStore = MediaStore.Images.Media.getBitmap(context?.contentResolver, imagePath)
            val baos = ByteArrayOutputStream()

            imageToStore.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            imageToStoreInArray = baos.toByteArray()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}