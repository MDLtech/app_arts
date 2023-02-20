package com.example.newulsuart

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Url
import kotlin.random.Random


class ArtsObj constructor(tit: String,artistTit:String,imageI:String){
    val title = tit

    val artistTitle = artistTit
    val imageId = imageI
}

interface ArticApiService {
    @GET
    fun getArtworks(@Url url:String): Call<JsonObject>
}




class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter

    var count=1









    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        //adapter = CustomAdapter(ArrayList<ArtsObj>())
        //recyclerView.adapter = adapter
        val myB = findViewById<Button>(R.id.my_button)
        myB.setOnClickListener{
            recyclerView.removeAllViews()
            val adapter12 : CustomAdapter = CustomAdapter(ArrayList<ArtsObj>(),this@MainActivity)
            recyclerView.adapter = adapter12
            //adapter.set
            adapter12.notifyDataSetChanged()
            fetchData()
        }

        fetchData()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val itemCount = layoutManager.itemCount
                if (lastVisibleItemPosition == itemCount - 3) {
                    println("Hello!!!")
                    //fetchData()
                }
            }
        })


    }

    private fun fetchData() {
        var randomval= Random.nextInt(1,1000)
        println("random $randomval")
        val api_url = "https://api.artic.edu/api/v1/"
        val retrofit = Retrofit.Builder()
            .baseUrl(api_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ArticApiService::class.java)

        service.getArtworks("artworks?fields=title,id,api_link,artist_title,image_id&page=$randomval&limit=100").enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    count++
                    val data: JsonArray = response.body()?.getAsJsonArray("data") ?: JsonArray()
                    println(data)
                    println(data.size())
                    val itemsList = ArrayList<ArtsObj>()
                    var st_=itemsList.size
                    var steper=0
                    for (i in 0 .. data.size()-1) {
                        //println(i)
                        try {
                            val artwork: JsonObject = data[i].asJsonObject

                            val title: String = artwork.get("title").asString
                            val artistTitle: String = artwork.get("artist_title").asString
                            val imageId: String = artwork.get("image_id").asString

                            val item = ArtsObj(title, artistTitle, imageId)
                            itemsList.add(item)
                            steper = steper + 1
                        }catch (e : Exception){

                        }
                    }
                    println(itemsList)
                    //adapter = CustomAdapter(itemsList)
                    //adapter=CustomAdapter(ArrayList<ArtsObj>())
                    val adapter1 : CustomAdapter = CustomAdapter(itemsList,this@MainActivity)
                    recyclerView.adapter = adapter1
                    //adapter.set
                    adapter1.notifyItemRangeChanged(st_,steper)

                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                // Handle failure
            }
        })
    }


    class CustomAdapter(private val items: ArrayList<ArtsObj>, val cont: Context) :
        RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lay, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val currentItem = items[position]
            holder.titleTextView.text = currentItem.title
            holder.artistTextView.text = currentItem.artistTitle
            Picasso.with(cont).load("https://www.artic.edu/iiif/2/${currentItem.imageId}/full/843,/0/default.jpg").into(holder.imagineView)
            holder.imagineView.setOnClickListener{
                //Toast.makeText(requireContext(),"Why u push me?",Toast.LENGTH_SHORT).show()
                val dialog = Dialog(cont, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
                val imageView = ImageView(cont)
                Picasso.with(cont).load("https://www.artic.edu/iiif/2/${currentItem.imageId}/full/843,/0/default.jpg").into(imageView)

                // Добавляем кнопку закрытия
                val closeButton = Button(cont)
                closeButton.text = "X"
                closeButton.width=10
                closeButton.setOnClickListener { dialog.dismiss() }

                // Создаем layout для ImageView и кнопки закрытия
                val layout = FrameLayout(cont)
                layout.addView(imageView, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))
                layout.addView(closeButton, FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.END or Gravity.TOP))

                dialog.setContentView(layout)
                dialog.show()






            }



            //holder.imageView.setImageResource(currentItem.imageId)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val titleTextView: TextView = itemView.findViewById(R.id.titleView)
            val artistTextView: TextView = itemView.findViewById(R.id.artView)
            val imagineView: ImageView = itemView.findViewById(R.id.imageView)
        }
    }



}