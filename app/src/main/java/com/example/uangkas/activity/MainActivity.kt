package com.example.uangkas.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import com.example.uangkas.App
import com.example.uangkas.R
import com.example.uangkas.adapter.MainAdapter
import com.example.uangkas.data.Constant
import com.example.uangkas.data.model.KasModel
import com.example.uangkas.utils.Converter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_menu.*
import kotlinx.android.synthetic.main.fragment_first.*

class MainActivity : AppCompatActivity() {

    var kasList = ArrayList<KasModel>()

    companion object {
        var intent: Intent? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            //            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
            startActivity(Intent(applicationContext, AddActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        getData();getTotal()
    }

    fun menuDialog() {

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_menu)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        dialog.tv_edit.setOnClickListener {
            //            startActivity(Intent(this, EditActivity::class.java))
            startActivity(intent)
            dialog.dismiss()
        }

        dialog.tv_hapus.setOnClickListener {
            alertDialog()
            dialog.dismiss()
        }

        dialog.show()
    }

    fun alertDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi")
        builder.setMessage("Apakah yakin untuk menghapus transaksi ini ?")
        builder.setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
            App.db!!.deleteData(Constant.transaksi_id!!)
            Toast.makeText(applicationContext, "Transaksi berhasi dihapus", Toast.LENGTH_SHORT)
                .show()
            getData(); getTotal()
            dialog.dismiss()
        })

        builder.setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        builder.show()

    }

    @SuppressLint("SetTextI18n")
    private fun getTotal() {
        App.db!!.getTotal()
        nilaiPemasukan.text = "Rp " + Converter.rupiahFOrmat(Constant.masuk.toString())
        nilaiPengeluaran.text = "Rp " + Converter.rupiahFOrmat(Constant.keluar.toString())
        nilaiSaldo.text =
            "Rp " + Converter.rupiahFOrmat((Constant.masuk - Constant.keluar).toString())

    }

    fun getData() {
        kasList.clear()
        list_data.adapter = null

        kasList.addAll(App.db!!.getData())
        val adapter = MainAdapter(this, kasList)
        list_data.adapter = adapter

        list_data.setOnItemClickListener { parent, view, position, id ->

            Constant.transaksi_id = kasList[position].transaksi_id

            intent = Intent(this, EditActivity::class.java)
            intent.putExtra(getString(R.string.intent_keterangan), kasList[position].keterangan)
            intent.putExtra(
                getString(R.string.intent_id),
                kasList[position].transaksi_id.toString()
            )
            intent.putExtra(getString(R.string.intent_jumlah), kasList[position].jumlah.toString())
            intent.putExtra(getString(R.string.intent_tanggal), kasList[position].tanggal)
            intent.putExtra(getString(R.string.intent_status), kasList[position].status)
            Log.e("jumlah", "-> " + kasList[position].transaksi_id)
            menuDialog()
        }
    }
}
