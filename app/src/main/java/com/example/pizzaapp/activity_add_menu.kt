package com.example.pizzaapp

import android.app.Notification
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class activity_add_menu : AppCompatActivity() {
    //object global
    lateinit var image : ImageView
    //declare code to make sure that the photos from gallery can be put on the apps
    companion object{
        val IMAGE_REQUEST_CODE = 100
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)

        //instance button upload image ->object local
        val btnUpload : Button = findViewById(R.id.buttonAddImage)
        image = findViewById(R.id.imageMenu)
        //event
        btnUpload.setOnClickListener {
            //pick image from gallery
            pickImageGalery()
        }
    }

    private fun pickImageGalery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            image.setImageURI(data?.data)
        }
    }
}