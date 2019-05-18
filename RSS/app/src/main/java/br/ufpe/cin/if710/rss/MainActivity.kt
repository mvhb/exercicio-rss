package br.ufpe.cin.if710.rss

import android.app.Activity
import android.app.Application
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.*
import br.ufpe.cin.if710.rss.ParserRSS.parse
import br.ufpe.cin.if710.rss.ParserRSS.parserSimples
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    //ao fazer envio da resolucao, use este link no seu codigo!
    private val RSS_FEED = "http://leopoldomt.com/if1001/g1brasil.xml"

    //OUTROS LINKS PARA TESTAR...
    //http://rss.cnn.com/rss/edition.rss
    //http://pox.globo.com/rss/g1/brasil/
    //http://pox.globo.com/rss/g1/ciencia-e-saude/
    //http://pox.globo.com/rss/g1/tecnologia/

    //use ListView ao invés de TextView - deixe o atributo com o mesmo nome
    var recyclerView : RecyclerView? = null
    var adapter : RssListAdapter  = RssListAdapter(emptyList(),this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.conteudoRSS)
        recyclerView?.layoutManager = LinearLayoutManager(applicationContext)
        button.setOnClickListener{
           GetRss().execute()
        }
    }

    internal inner class GetRss : AsyncTask<String, Void, String>(){

        override fun onPreExecute() {
            super.onPreExecute()
            Toast.makeText(this@MainActivity,"Baixando RSS",Toast.LENGTH_SHORT).show()
        }

        override fun doInBackground(vararg p0: String): String {
            val rssFeed = GetRssFeed(RSS_FEED)
            return rssFeed
        }

        override fun onPostExecute(rssFeed: String?){
            super.onPostExecute(rssFeed)
            if (rssFeed != ""){
                val parser = ParserRSS.parse(rssFeed!!)
                adapter.rss = parser
                recyclerView?.adapter = adapter
            }
        }
    }
    fun GetRssFeed(url: String): String{
        var input: InputStream? = null
        var rssFeed = ""
        try {
            val url = URL(RSS_FEED)
            val conn = url.openConnection() as HttpURLConnection
            input = conn.inputStream
            val response = input.bufferedReader().readText()
            rssFeed = response
        }
        finally {
            if (input != null) {
                input!!.close()
            }
        }
        return rssFeed
    }
}