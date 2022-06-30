package com.example.pizzaapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pizzaapp.model.MenuModel

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
        //hide title bar
        getSupportActionBar()?.hide()

        //instance button upload image ->object local
        val btnUpload : Button = findViewById(R.id.buttonAddImage)
        image = findViewById(R.id.imageMenu)
        //instance
        val textId: EditText = findViewById(R.id.menuId)
        val textName: EditText = findViewById(R.id.menuName)
        val textPrice: EditText = findViewById(R.id.menuPrice)
        val btnAddImage: Button = findViewById(R.id.buttonAddImage)
        val btnSaveMenu: Button = findViewById(R.id.buttonSaveMenu)
        //event
        btnUpload.setOnClickListener {
            //pick image from gallery
            pickImageGalery()
        }
        //event saat button save di klik
        btnSaveMenu.setOnClickListener {
            //object class databaseHelper
            val databaseHelper = DatabaseHelper(this)

            val id: Int = textId.text.toString().toInt()
            val name: String = textName.text.toString().trim()
            val price: Int = textPrice.text.toString().toInt()
            val bitmapDrawable: BitmapDrawable = image.drawable as BitmapDrawable
            val bitmap: Bitmap = bitmapDrawable.bitmap

            val menuModel = MenuModel(id, name, price, bitmap)
            databaseHelper.addMenu(menuModel)
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