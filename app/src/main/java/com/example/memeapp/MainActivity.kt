package com.example.memeapp

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    val url = "https://meme-api.herokuapp.com/gimme"
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView=findViewById(R.id.memeView)

        loadImageFromApi();

        val Button=findViewById<Button>(R.id.button)
        Button.setOnClickListener {
            button()
        }
    }

    private fun loadImageFromApi() {
        //Volley and Picaso API
        // create request quque
        val queue=Volley.newRequestQueue(this)
       //request queue (found in old documentation)
        val request = JsonObjectRequest(Request.Method.GET,this.url,null, { response->
           Log.d("Result",response.toString())
            Picasso.get().load(response.get("url").toString()).placeholder(R.drawable.loading).into(imageView);
        },
        Response.ErrorListener {
            Log.e("error",it.toString())
            Toast.makeText(applicationContext,"Error in loading meme from server",Toast.LENGTH_LONG).show()
        } )
        queue.add(request);

    }

    fun changeImage(view: View) {
        this.loadImageFromApi()
    }

   private fun button () {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey,Checkout this meme! $url")
        val choser= Intent.createChooser(intent,"share this meme using")
        startActivity(choser)
    }
}