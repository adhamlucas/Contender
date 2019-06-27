package com.example.contender

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.contender.Model.SmartPhoneItem

const val DATABASE_NAME = "MyDB"
const val DATABASE_VERSION = 1
const val TABLE_NAME = "TABLE_SMARTPHONES"
const val COLUMN_ID = "COL_ID"
const val COLUMN_ID_CEL = "COL_ID_CEL"
const val COLUMN_TITLE = "COL_TITLE"
const val SQL_CREATE_TABLE =
        "CREATE TABLE $TABLE_NAME (" +
            "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COLUMN_ID_CEL TEXT," +
            "$COLUMN_TITLE TEXT)"

const val SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS $TABLE_NAME"

class DataBaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun addSmartPhone(smartPhoneItem: SmartPhoneItem){
        val db = this.writableDatabase
        Log.d("SQLITE DEBUGGER", "INSERI")

        val values = ContentValues().apply {
            put(COLUMN_ID_CEL, smartPhoneItem.id)
            put(COLUMN_TITLE, smartPhoneItem.title)
        }
        val newRowId = db?.insert(TABLE_NAME, null, values)

        db.close()
    }

    fun getAllSmartPhone(): MutableList<SmartPhoneItem> {
        var list = mutableListOf<SmartPhoneItem>()
        val db = this.readableDatabase
        var cursor = db.rawQuery("SELECT $COLUMN_ID, $COLUMN_TITLE FROM $TABLE_NAME", null)
        val moveToFirst = cursor!!.moveToFirst()

        var id: String = cursor.getString(cursor.getColumnIndex(COLUMN_ID_CEL))
        var name: String = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
        list.add(SmartPhoneItem(id, name))

        while(cursor.moveToNext()){
            id = cursor.getString(cursor.getColumnIndex(COLUMN_ID_CEL))
            name = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
            list.add(SmartPhoneItem(id, name))
        }

        return list
    }

}