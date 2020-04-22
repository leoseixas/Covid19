package com.example.covid19

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_details_boletim.*

class DetelhesBoletim : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_boletim)
        txtData.text = intent.getStringExtra("data")
        txtHora.text = intent.getStringExtra("hora")
        txtNumConfirmados.text = intent.getStringExtra("confirmados")
        txtCHospitalar.text = intent.getStringExtra("cHospitalar")
        txtCurados.text = intent.getStringExtra("curados")
        txtSuspeitos.text = intent.getStringExtra("suspeitos")
        txtSDomiciliar.text = intent.getStringExtra("sDomiciliar")
        txtSHospitalar.text = intent.getStringExtra("sHospitalar")
        txtDescartados.text = intent.getStringExtra("descartados")
        txtMonitorados.text = intent.getStringExtra("monitorados")
        txtMortes.text = intent.getStringExtra("mortes")
    }
}