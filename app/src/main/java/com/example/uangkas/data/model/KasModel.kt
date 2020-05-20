package com.example.uangkas.data.model

data class KasModel (
    val transaksi_id : Int,
    val status : String,
    val jumlah : Long,
    val keterangan : String,
    val tanggal : String
)