package com.example.uangkas.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Converter {

    companion object{
        fun dateFormat(date : String) : String{

            var format = SimpleDateFormat("yyyy-MM-dd")
            var newDate: Date? = null

            try {
                newDate = format.parse(date)
            }catch (e: ParseException){
                e.message
            }

            format = SimpleDateFormat("dd/MM/yyyy")

            return format.format(newDate)

        }

        fun rupiahFOrmat(number: String): String{
            var rupiahFormat = NumberFormat.getInstance(Locale.GERMANY)
            return rupiahFormat.format(number.toLong())
        }

        fun decimalFormat (number: Int): String{
            val numberFormat = DecimalFormat("00")
            return numberFormat.format(number.toLong())
        }
    }

}