package com.example.uangkas.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.uangkas.data.Constant
import com.example.uangkas.data.model.KasModel

class DatabaseHelper :SQLiteOpenHelper {

    companion object{

        val DBName = "uangkasKotlin"
        val DBVersion = 1
        val tableName = "transaksi"
        val transaksiId = "transaksi_id"
        val status = "status"
        val jumlah = "jumlah"
        val keterangan = "keterangan"
        val tanggal = "tanggal"

    }

    private val createTable = "CREATE TABLE $tableName ($transaksiId INTEGER PRIMARY KEY AUTOINCREMENT " +
            ", $status TEXT, $jumlah TEXT, $keterangan TEXT, $tanggal DATETIME DEFAULT CURRENT_DATE)"

    var context: Context? = null
    var db: SQLiteDatabase


    constructor(context: Context) : super (context,
        DBName, null,
        DBVersion
    ){
        this.context = context
        db = this.writableDatabase
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $tableName")
    }

    fun insertData(stts : String, jml: String, ktr: String) : Long{
        val values = ContentValues()
        values.put(status, stts)
        values.put(jumlah, jml)
        values.put(keterangan, ktr)

        //        db.close()

        return db.insert(tableName, null, values)
    }

    fun getData() : List<KasModel>{

        val kas = ArrayList<KasModel>()

        val strSql = "SELECT * FROM $tableName ORDER BY $transaksiId DESC"


        val cursor : Cursor = db.rawQuery(strSql, null)


            cursor.moveToFirst()


        Log.e("data", "-> ${cursor.count}")
            for (i in 0 until cursor.count){
                cursor.moveToPosition(i)
                kas.add(
                    KasModel(
                        cursor.getInt(cursor.getColumnIndex(transaksiId)),
                        cursor.getString(cursor.getColumnIndex(status)),
                        cursor.getLong(cursor.getColumnIndex(jumlah)),
                        cursor.getString(cursor.getColumnIndex(keterangan)),
                        cursor.getString(cursor.getColumnIndex(tanggal))
                    )
                )

        }
        return kas
    }

    fun getTotal(){
        val strSql =
            "SELECT SUM($jumlah) AS total, " +
            "(SELECT SUM($jumlah) FROM $tableName WHERE $status = 'Masuk') AS masuk, " +
            "(SELECT SUM($jumlah) FROM $tableName WHERE $status = 'Keluar') AS keluar " +
            "FROM $tableName"

        val cursor: Cursor = db.rawQuery(strSql, null)
        cursor.moveToFirst()

        Log.e("_pemasukan",cursor.getLong(cursor.getColumnIndex("masuk") ).toString())
        Log.e("_pengeluaran", cursor.getLong(cursor.getColumnIndex("keluar") ).toString())

        Constant.masuk = cursor.getLong(cursor.getColumnIndex("masuk"))
        Constant.keluar = cursor.getLong(cursor.getColumnIndex("keluar"))
    }

    fun updateData(id: Int, sttr: String, jml: Long, ktr: String, tgl: String){

        val values = ContentValues()
        values.put(status, sttr)
        values.put(jumlah, jml)
        values.put(keterangan, ktr)
        values.put(tanggal, tgl)

        db.update(tableName,values,"$transaksiId='$id'", null)

    }

    fun deleteData(id: Int){
        db.delete(tableName, "$transaksiId='$id'", null)
    }
}