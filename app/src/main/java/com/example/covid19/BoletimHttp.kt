package com.example.covid19

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.io.IOException
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

object BoletimHttp {
    val Json_URL="https://raw.githubusercontent.com/ramonsl/ws-covid/master/db.json"

    fun hasConnection(ctx: Context): Boolean{
        val cm = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info !=null && info.isConnected
    }

    fun loadBoletim(): List<Boletim>?{
        val client =OkHttpClient.Builder()
            .readTimeout(5,TimeUnit.SECONDS)
            .connectTimeout(10,TimeUnit.SECONDS)
            .build()
        val request = Request.Builder()
            .url(Json_URL)
            .build()
        val response = client.newCall(request).execute()
        val jsonString = response.body?.string()
        val json = JSONArray(jsonString)

        return readJson(json)
    }


    fun readJson(json: JSONArray): List<Boletim>{
        var boletins = mutableListOf<Boletim>()
        try {
            for (i in 0 .. json.length()-1){
                var js = json.getJSONObject(i)
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

                boletins.add(boletim)

            }
        }
        catch (e : IOException){
            Log.e("Erro", "Impossivel ler JSON")
        }
        return boletins
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
}