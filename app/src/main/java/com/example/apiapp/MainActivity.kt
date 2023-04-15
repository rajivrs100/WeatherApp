package com.example.apiapp

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    val CITY: String = "delhi,IN"
    val API: String = "ffbb577ecce2c4e6e75f55c1b16d8efe"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherTask().execute()
    }
    @SuppressLint("StaticFieldLeak")
    inner class weatherTask: AsyncTask<String, Void, String>()
    {
        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            super.onPreExecute()
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            findViewById<TextView>(R.id.errortext).visibility = View.GONE

        }

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg p0: String?): String? {
            var response :String?
            try {
                response =URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY & units=metrics&appid=$API" )
                    .readText(Charsets.UTF_8)

            }
            catch (e:java.lang.Exception)
            {
                response = null
        }
            return response

                }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val updatedAt:Long =jsonObj.getLong("dt")
                val updatedAtText = "Updated at: "+SimpleDateFormat("dd/mm/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000))
                val temp = main.getString("temp")+"°C"
                val tempMin = "Min Temp: "+main.getString("temp_min  ")+"°C"
                val tempMax = "Max Temp: "+main.getString("temp_Max  ")+"°C"
                val address = jsonObj.getString("name")+", "+sys.getString("country")

                findViewById<TextView>(R.id.address).text= address
               // findViewById<TextView>(R.id.updated_at).text= updatedAtText
               // findViewById<TextView>(R.id.status).text= weatherDescription.capitalize()
                findViewById<TextView>(R.id.temp).text= temp
                findViewById<TextView>(R.id.temp_min).text= tempMin
                findViewById<TextView>(R.id.temp_max).text= tempMax

                findViewById<ProgressBar>(R.id.loader).visibility= View.GONE
                findViewById<RelativeLayout>(R.id.mainContainer).visibility=View.VISIBLE



            }
            catch (e:java.lang.Exception)
            {
                findViewById<ProgressBar>(R.id.loader).visibility=View.GONE
                findViewById<TextView>(R.id.errortext).visibility=View.VISIBLE

            }
        }
    }

}