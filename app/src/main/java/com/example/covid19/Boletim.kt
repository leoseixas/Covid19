package com.example.covid19

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Boletim(
  var suspeitos: Int=0,
  var confirmados: Int=0,
  var descartados: Int=0,
  var monitoramento: Int=0,
  var curados: Int=0,
  var sDomiciliar: Int=0,
  var sHospitalar: Int=0,
  var cHospitalar: Int=0,
  var mortes: Int=0,
  var data: String,
  var hora: String
) {
  override fun toString(): String {
    return data
  }

}
