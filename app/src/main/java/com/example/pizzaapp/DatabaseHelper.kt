package com.example.pizzaapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.provider.ContactsContract
import android.widget.Toast
import com.example.pizzaapp.model.MenuModel
import java.io.ByteArrayOutputStream

class DatabaseHelper(private var context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DB_VERSION) {
    companion object{
        //mendeklarasikan nama database dan version
        private val DATABASE_NAME = "pizza"
        private val DB_VERSION = 1
        //deklarasi nama table
        private val TABLE_ACCOUNT = "account"
        //mendeklarsikan field
        private val COLUMN_EMAIL = "email"
        private val COLUMN_NAME = "name"
        private val COLUMN_LEVEL = "level"
        private val COLUMN_PASSWORD = "password"

        //TABLE MENU
        private val TABLE_MENU = "menu"
        //column menu table
        private val COLUMN_ID_MENU = "idMenu"
        private val COLUMN_NAMA_MENU = "menuName"
        private val COLUMN_PRICE_MENU = "price"
        private val COLUMN_IMAGE = "photo"
    }

    //CREATE TABLE ACCOUNT
    private val CREATE_ACCOUNT_TABLE = ("CREATE TABLE " + TABLE_ACCOUNT + "("
            + COLUMN_EMAIL+" TEXT PRIMARY KEY, "
            + COLUMN_NAME+"  TEXT,"
            + COLUMN_LEVEL+" TEXT, "
            + COLUMN_PASSWORD+" TEXT)")

    //CREATE DROP ACCOUNT
    private val DROP_ACCOUNT_TABLE = "DROP TABLE IF EXISTS $TABLE_ACCOUNT"

    //CREATE TABLE MENU SQL QUERY
    private val CREATE_MENU_TABLE = ("CREATE TABLE " + TABLE_MENU + "("
            + COLUMN_ID_MENU + " INT PRIMARY KEY, "
            + COLUMN_NAMA_MENU + " TEXT,  "
            + COLUMN_PRICE_MENU + " INT, "
            + COLUMN_IMAGE + " BLOB)")

    //CREATE DRO TABLE MENU SQL QUERY
    private val DROP_MENU_TABLE = "DROP TABLE IF EXISTS $TABLE_MENU"

    override fun onCreate(p0: SQLiteDatabase?) {
        //CREATE TABLE ACCOUNT
        p0?.execSQL(CREATE_ACCOUNT_TABLE)
        p0?.execSQL(CREATE_MENU_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(DROP_ACCOUNT_TABLE)
        p0?.execSQL(DROP_MENU_TABLE)
        onCreate(p0)
    }
    fun checkLogin(email: String, password:String):Boolean {
        val columns = arrayOf(COLUMN_NAME)
        val db = this.readableDatabase
        //SELECTION CRITERIA
        val selection = "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ? "
        //SELECTION ARGUMENTS
        val selectionArgs = arrayOf(email,password)

        //execute
        val cursor = db.query(TABLE_ACCOUNT, //table to query
        columns, //colums to return
            selection, // column for WHERE clause
            selectionArgs,//the values for the WHERE clause
            null, //group the rows
            null,//filter by row groups
            null//the sort order
        )

        //count result data
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount>0){
            return true
        }else{
            return false
        }
    }
    fun addAccount(email: String, name:String, level:String, password: String){
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_EMAIL, email)
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_LEVEL, level)
        values.put(COLUMN_PASSWORD, password)

        val result = db.insert(TABLE_ACCOUNT, null, values)
        //show message
        if(result == (0).toLong()){
            Toast.makeText(context, "Register Failed", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Register Succes, "+ "Please Login Using Your New Account" , Toast.LENGTH_SHORT).show()
        }

        db.close()
    }
    @SuppressLint("Range")
    fun checkData(email: String):String {
        val columns = arrayOf(COLUMN_NAME)
        val db = this.readableDatabase
        //SELECTION CRITERIA
        val selection = "$COLUMN_EMAIL = ?"
        //SELECTION ARGUMENTS
        val selectionArgs = arrayOf(email)
        var name:String = ""

        //execute
        val cursor = db.query(TABLE_ACCOUNT, //table to query
            columns, //colums to return
            selection, // column for WHERE clause
            selectionArgs,//the values for the WHERE clause
            null, //group the rows
            null,//filter by row groups
            null//the sort order
        )
        if (cursor.moveToFirst()){
            name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
        }
        cursor.close()
        db.close()
        return name
    }
    //add new menu
    fun addMenu(menu: MenuModel){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID_MENU, menu.id)
        values.put(COLUMN_NAMA_MENU, menu.name)
        values.put(COLUMN_PRICE_MENU, menu.price)
        //prepare image
        val byteOutputStream = ByteArrayOutputStream()
        val imageInByte:ByteArray
        menu.image.compress(Bitmap.CompressFormat.JPEG, 100, byteOutputStream)
        imageInByte = byteOutputStream.toByteArray()
        values.put(COLUMN_IMAGE, imageInByte)

        val result = db.insert(TABLE_MENU, null, values)
        //show message
        if (result ==(0).toLong()){
            Toast.makeText(context, "Add menu Failed", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Add menu Success", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

}
