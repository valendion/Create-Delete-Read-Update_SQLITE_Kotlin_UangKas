package com.example.uangkas.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.uangkas.App
import com.example.uangkas.R
import com.example.uangkas.R.string.*
import com.example.uangkas.utils.Converter
import kotlinx.android.synthetic.main.activity_edit.*
import java.util.*

class EditActivity : AppCompatActivity() {

    var tgl: String? = null
    var status: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        var kiriman = intent

        status = kiriman.getStringExtra(getString(intent_status))

        Log.e("_status", "-> ${intent.getStringExtra(getString(intent_id)).toInt()}")
        when (status) {
            "Masuk" -> radioMasuk.isChecked = true
            "Keluar" -> radioKeluar.isChecked = true
        }

        rg_masuk_keluar.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioMasuk -> status = "Masuk"
                R.id.radioKeluar -> status = "Keluar"
            }
        }

        etJumlah.setText(kiriman.getStringExtra(getString(intent_jumlah)))
        et_keterangan.setText(kiriman.getStringExtra(getString(intent_keterangan)))

        tgl = kiriman.getStringExtra(getString(intent_tanggal))

        et_tanggal.setText(Converter.dateFormat(tgl.toString()))

        btn_simpan.setOnClickListener {
            if (status.isNullOrBlank() || etJumlah.text.isNullOrBlank() || et_keterangan.text.isNullOrBlank() || tgl.isNullOrBlank()) {
                Toast.makeText(applicationContext, "Isi data dengan benar", Toast.LENGTH_SHORT)
                    .show()
            } else {
                App.db!!.updateData(
                    kiriman.getStringExtra(getString(intent_id)).toInt(),
                    status.toString(),
                    etJumlah.text.toString().toLong(),
                    et_keterangan.text.toString(),
                    tgl.toString()
                )

                Toast.makeText(applicationContext, "Perubahan berhasil", Toast.LENGTH_SHORT).show()
                finish()
            }
        }


        var calendar = Calendar.getInstance()

        var y = calendar.get(Calendar.YEAR)
        var d = calendar.get(Calendar.DAY_OF_MONTH)
        var m = calendar.get(Calendar.MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                tgl = "$year-${Converter.decimalFormat(month + 1)}-${Converter.decimalFormat(
                    dayOfMonth
                )}"
                Log.e("_tgl", tgl)
                et_tanggal.setText(Converter.dateFormat(tgl.toString()))
            },
            y,
            m,
            d
        )

        et_tanggal.setOnClickListener {
            datePickerDialog.show()
        }

        Log.e("hasilIntent", kiriman.getStringExtra(getString(intent_keterangan)))

        supportActionBar!!.title = "Edit"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
