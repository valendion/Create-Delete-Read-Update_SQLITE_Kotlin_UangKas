package com.example.uangkas.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.uangkas.R
import com.example.uangkas.data.model.KasModel
import com.example.uangkas.utils.Converter

class MainAdapter (val activity: Activity, kasModel: List<KasModel>): BaseAdapter() {

    private var kasModel = ArrayList<KasModel>()

    init {
        this.kasModel = kasModel as ArrayList<KasModel>
    }
    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.adapter_main, null)

        val status: TextView = view.findViewById(R.id.tv_status)
        val jumlah: TextView = view.findViewById(R.id.tv_Jumlah)
        val keterangan: TextView = view.findViewById(R.id.tv_keterangan)
        val tanggal: TextView = view.findViewById(R.id.tv_tanggal)

        status.text = kasModel[position].status
        jumlah.text = kasModel[position].jumlah.toString()
        keterangan.text = kasModel[position].keterangan
        tanggal.text = Converter.dateFormat(kasModel[position].tanggal)

        when(kasModel[position].status){
            "Masuk" -> status.setTextColor(ContextCompat.getColor(activity, R.color.colorAccent))
            "Keluar" -> status.setTextColor(ContextCompat.getColor(activity, R.color.colorRed))
        }


        return view
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return kasModel.size
    }

}