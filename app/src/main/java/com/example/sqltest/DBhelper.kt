package com.example.sqltest

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "myApp3.db", null, 1) {
    val TABLE_NAME = "TODOLIST"
    val COL_ID = "ID"
    val COL_TITLE = "TITLE"
    val COL_BODY = "BODY"
    val COL_ISDONE = "ISDONE"
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                    "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COL_TITLE TEXT NOT NULL," +
                    "$COL_BODY TEXT NOT NULL,"+
                    "$COL_ISDONE INTEGER NOT NULL)"
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(p0)
    }

    fun insertTask(task: Tasks) {
        val db = this.writableDatabase
        var contentValue = ContentValues().apply {
            put(COL_TITLE, task.title)
            put(COL_BODY, task.body)
            put(COL_ISDONE, task.isDone)
        }
        db.insert(TABLE_NAME, null, contentValue)
        db.close()
    }

    fun updateTask(task: Tasks) {
        val db = this.writableDatabase
        var contentValue = ContentValues().apply {
            put(COL_TITLE, task.title)
            put(COL_BODY, task.body)
            put(COL_ISDONE, task.isDone)
        }
        db.update(TABLE_NAME, contentValue, "$COL_ID = ?", arrayOf(task.id.toString()))
        db.close()
    }

    fun deleteTask(task: Tasks) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COL_ID =?", arrayOf(task.id.toString()))
        db.close()
    }

    fun readTask(): ArrayList<Tasks> {
        val db = this.readableDatabase
        var cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        var Tasks = ArrayList<Tasks>()
        if (cursor.moveToFirst()) {
            do {
                var ID = cursor.getInt(0)
                var TITLE = cursor.getString(1)
                var BODY = cursor.getString(2)
                var ISDONE = cursor.getInt(3)
                var task = Tasks(ID, TITLE, BODY,ISDONE)
                Tasks.add(task)
            } while (cursor.moveToNext())
        }
        db.close()
        return Tasks
    }


}