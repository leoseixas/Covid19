package com.example.covid19


import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

  var boletinsList = arrayListOf<Boletim>()
  val adapter = BoletimAdapter(boletinsList)
  private var asyncTask : BoletimTask? =null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(findViewById(R.id.my_tool))
    carregarDados()
    initRecycleView()

  }


  fun initRecycleView(){
    recycler.adapter=adapter
    val layout = LinearLayoutManager(this)
    recycler.layoutManager=layout
  }

  fun showProgress(show: Boolean){
    if(show){
      txtMsg.text="Carregando"
    }else{
      txtMsg.visibility = if(show) View.VISIBLE else View.GONE
      progressBar.visibility = if(show) View.VISIBLE else View.GONE
    }
  }

  fun carregarDados(){
    boletinsList.clear()
    if (boletinsList.isNotEmpty()){
      showProgress(false)
    }else{
      if (asyncTask==null){
        if (BoletimHttp.hasConnection(this)){
//          startDownload()
          if  (asyncTask?.status!=AsyncTask.Status.RUNNING){
            asyncTask = BoletimTask()
            asyncTask?.execute()
          }
        }else{
          progressBar.visibility= View.GONE
          txtMsg.text = "Erro"
        }
      }else if (asyncTask?.status==AsyncTask.Status.RUNNING){
        showProgress(true)
      }
    }
  }



  private fun updateBoletins(result: List<Boletim>){
    if(result!=null){
      boletinsList.clear()
      boletinsList.addAll(result.reversed())
    }else{
      txtMsg.text = "Erro ao Carregar"
    }
    adapter.notifyDataSetChanged()
    asyncTask=null
  }

  inner class BoletimTask: AsyncTask<Void,Void,List<Boletim>?>(){
    override fun onPreExecute() {
      super.onPreExecute()
      showProgress(true)
    }

    override fun doInBackground(vararg params: Void?): List<Boletim>? {
      return BoletimHttp.loadBoletim()
    }

    override fun onPostExecute(result: List<Boletim>?) {
      super.onPostExecute(result)
      showProgress(false)
      if (result != null) {
        updateBoletins(result)
      }
    }

  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu,menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem)= when (item.itemId) {
    R.id.menu_refresh ->{
      carregarDados()
      Log.e("Erro","carregando dados")
      true
    }
    else->{
      super.onOptionsItemSelected(item)
    }
  }

}
