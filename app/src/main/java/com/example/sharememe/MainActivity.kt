package com.example.sharememe

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var crurl: String?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       shareMeme()
       Switch()
    }

     fun shareMeme(){
         progressBar.visibility = View.VISIBLE
         //nextbutton.isEnabled = false
         sharebutton.isEnabled = false
        //val queue = Volley.newRequestQueue(this)
        val URL = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, URL, null,
            { response ->
                val crurl = response.getString("url")
               // val url = "https://i.redd.it/1mqmxyvjz9q51.png"
                Glide.with(this).load(crurl).listener(
                    object : RequestListener<Drawable>{
                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                           // nextbutton.isEnabled = true
                            sharebutton.isEnabled = true
                            return false
                        }

                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }
                    }
                ).into(imageView)

            },
            Response.ErrorListener{
                Toast.makeText(  this, "something wrong", Toast.LENGTH_SHORT).show()
            })

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun share(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey! i am found a new Meme $crurl ")
        startActivity(Intent.createChooser(intent, "Share this meme with!"))
    }

    fun next(view: View) {
        shareMeme()
    }

    fun Switch() {
       switch1.setOnCheckedChangeListener { buttonView, isChecked ->
           if (isChecked) {
               Toast.makeText(this, "ON! This this working", Toast.LENGTH_SHORT).show()
               switch1.text = "CARDVIEW"
           }
           else
           {
           Toast.makeText(this, "OFF Yeh!", Toast.LENGTH_SHORT).show()
           switch1.text = "MEMEVIEW"
           }
         }
    }
}