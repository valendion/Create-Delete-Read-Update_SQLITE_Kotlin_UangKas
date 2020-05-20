package com.example.uangkas.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.uangkas.App
import com.example.uangkas.R
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {

    var status: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        supportActionBar!!.title = "Tambah data"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        rg_masuk_keluar.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId){
                R.id.radioMasuk -> status = "Masuk"
                R.id.radioKeluar -> status = "Keluar"
            }
        }

        btn_simpan.setOnClickListener {
            if (status.isNullOrBlank() || etJumlah.text.isNullOrBlank() || et_keterangan.text.isNullOrBlank()){
                Toast.makeText(applicationContext, "Isi data dengan benar", Toast.LENGTH_SHORT).show()
            }else{
                var id = App.db!!.insertData(status, etJumlah.text.toString(), et_keterangan.text.toString())

                Log.e("_logId", "-> $id")

                if (id > 0){
                    Toast.makeText(applicationContext, "Transaksi berhasil di simpan",
                        Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
