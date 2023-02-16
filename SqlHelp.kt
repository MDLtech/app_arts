package com.example.ulsuart.ui.home

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class SqlHelp(context: Context):SQLiteOpenHelper(context,"/data/user/0/com.example.ulsuart/databases/"+DATABASE_NAME,null,DATABASE_VERSION) {


//    companion object{
//        private const val DATABASE_NAME="arts_db.db"
//        private const val DATABASE_VERSION=1
//
//    }
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "arts_db.db"
        private const val DATABASE_PATH = "/data/user/0/com.example.ulsuart/databases/"
    }

    private val myContext: Context = context

    init {
        // Создаем папку для базы данных, если она не существует
        val file = File(DATABASE_PATH)
        if (!file.exists()) {
            file.mkdirs()
        }

        // Копируем базу данных из assets во внутреннее хранилище
        try {
            println("TRY THIS")
            val dbInputStream = myContext.assets.open(DATABASE_NAME)
            val dbOutputStream = FileOutputStream(DATABASE_PATH + DATABASE_NAME)
            val buffer = ByteArray(1024)
            var length: Int = dbInputStream.read(buffer)
            println("TRY THIS2")
            while (length > 0) {
                dbOutputStream.write(buffer, 0, length)
                length = dbInputStream.read(buffer)
            }
            dbOutputStream.flush()
            println("TRY THIS3")
            dbOutputStream.close()
            dbInputStream.close()
            println("TRY THIS4")
        } catch (e: IOException) {
            println("Report THIS")
            e.printStackTrace()
        }
    }





    override fun onCreate(p0: SQLiteDatabase?) {
        //
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        //
    }

    fun getArts(step: Int): Array<out String>{
        val string2DArray= Array(60) { "" }
        println("CREATEEEEEEEEEEEEEEEEEEEEEEEEE")
        val sqlQuer="""SELECT * FROM arts LIMIT 20 OFFSET ${step*20}"""
        //val dbHelper = DatabaseHelper(this)
        val db= this.readableDatabase
        println(db)
        val curs : Cursor?
        try {
            curs=db.rawQuery(sqlQuer,null)
        }
        catch (e: java.lang.Exception){
            return string2DArray
        }
        val id_has:String
        val auth:String
        val desc:String
        var st=0
        if (curs.moveToFirst()){
            do{
                string2DArray[st*3]=curs.getString(curs.getColumnIndexOrThrow("id_has"))
                string2DArray[st*3+1]=curs.getString(curs.getColumnIndexOrThrow("auth"))
                string2DArray[st*3+2]=curs.getString(curs.getColumnIndexOrThrow("desc"))
                st += 1
            }while (curs.moveToNext())

        }



        return string2DArray
    }

}