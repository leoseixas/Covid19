package com.example.covid19


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.linha_de_boletim.view.*

class BoletimAdapter(private val boletins: List<Boletim>): RecyclerView.Adapter<BoletimAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.linha_de_boletim, parent, false)
        val vh = VH(v)

        vh.itemView.setOnClickListener  {
            val boletim = boletins[vh.adapterPosition]
            val intent = Intent(v.context, DetelhesBoletim::class.java)
            intent.putExtra("data", boletim.data)
            intent.putExtra("hora", boletim.hora)
            intent.putExtra("confirmados", boletim.confirmados.toString())
            intent.putExtra("curados", boletim.curados.toString())
            intent.putExtra("suspeitos", boletim.suspeitos.toString())
            intent.putExtra("descartados", boletim.descartados.toString())
            intent.putExtra("monitorados", boletim.monitoramento.toString())
            intent.putExtra("sDomiciliar", boletim.sDomiciliar.toString())
            intent.putExtra("sHospitalar", boletim.sHospitalar.toString())
            intent.putExtra("cHospitalar", boletim.cHospitalar.toString())
            intent.putExtra("mortes", boletim.mortes.toString())
            v.context.startActivity(intent)
        }
        return vh
    }


    override fun getItemCount(): Int {
        return  boletins.size
    }

    override fun onBindViewHolder(holder: BoletimAdapter.VH, position: Int) {
        var boletim =  boletins[position]
        holder.txtMortes.text=boletim.mortes.toString()
        holder.txtNconfirmados.text=boletim.confirmados.toString()
        holder.txtData.text= boletim.data.toString()
        holder.txtHora.text=boletim.hora.toString()


    }

    class VH(itenView: View):RecyclerView.ViewHolder(itenView) {
        var txtMortes: TextView = itenView.txtNumMortes
        var txtNconfirmados: TextView = itenView.txtNumConfirmados
        var txtData: TextView = itenView.txtData
        var txtHora: TextView = itenView.txtHora
    }

}
