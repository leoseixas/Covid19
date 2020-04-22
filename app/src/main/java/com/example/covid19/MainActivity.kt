package com.example.covid19

import android.content.Context

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {

  var lista = arrayListOf<Boletim>()
  val adapter = BoletimAdapter(lista)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    readJson(this)
    initRecycleView()

  }


  fun readJson(context: Context){
    var json: String?=null
    try {
      val inputStream: InputStream= context.assets.open("data.json")
      json = inputStream.bufferedReader().use { it.readText() }
      var jsonArray =JSONArray(json)
      for (i in 0 .. jsonArray.length()-1){
        var js = jsonArray.getJSONObject(i)
        val suspeitos = js.optInt("Suspeitos")
        val confirmados = js.optInt("Confirmados")
        val descartados = js.optInt("Descartados")
        val monitoramento = js.optInt("Monitoramento")
        val curados = js.optInt("Curados")
        val sDomiciliar = js.optInt("Sdomiciliar")
        val sHospitalar = js.optInt("Shopitalar")
        val cHospitalar = js.optInt("Chopitalar")
        val mortes = js.optInt("mortes")
        val data = formatarData(js.optString("boletim").substring(0,10))
        val hora = formatarHora(js.optString("boletim").substring(11,23))

        val boletim =Boletim(suspeitos, confirmados, descartados, monitoramento,
          curados, sDomiciliar, sHospitalar, mortes, cHospitalar, data, hora)

        lista.add(boletim)

      }
    }
    catch (e : IOException){
    Log.e("Erro", "Impossivel ler JSON")
    }

  }
  fun formatarData(data: String): String {
    val diaString =data
    var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    var date = LocalDate.parse(diaString)
    var formattedDate = date.format(formatter)
    return formattedDate
  }

  fun formatarHora(data: String): String{
    val horaString = data
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")
    val date = LocalTime.parse(horaString)
    val formattedHora = date.format(formatter)
    return formattedHora
  }

  fun initRecycleView(){
    recycler.adapter=adapter
    val layout = LinearLayoutManager(this)
    recycler.layoutManager=layout
  }

}
