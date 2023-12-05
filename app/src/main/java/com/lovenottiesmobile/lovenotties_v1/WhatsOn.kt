package com.lovenottiesmobile.lovenotties_v1

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.w3c.dom.Text
import java.sql.Connection
import java.sql.DriverManager

class WhatsOn : AppCompatActivity(), WhatsOnAdapter.IBlogDetails  {
    private lateinit var recyclerView: RecyclerView
    private lateinit var  blogItemList: MutableList<WhatsOnModel>
    private lateinit var  blogAdapter: WhatsOnAdapter
    private lateinit var  loadingText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whats_on)

        blogItemList = ArrayList()
        blogAdapter = WhatsOnAdapter(this, blogItemList, this)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = blogAdapter
        loadingText = findViewById(R.id.loadingtxt)

        //Nav Bar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.selectedItemId = R.id.bottom_home

        val layout = findViewById<LinearLayout>(R.id.mainLin)
        val layoutHeader = findViewById<LinearLayout>(R.id.HeaderLayout)



        //setup background gradient animations
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        val animDrawable = layout.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(750)
        animDrawable.setExitFadeDuration(1500)
        animDrawable.start()

        val animDrawable2 = layoutHeader.background as AnimationDrawable
        animDrawable2.setEnterFadeDuration(750)
        animDrawable2.setExitFadeDuration(1500)
        animDrawable2.start()

        val animDrawable3 = bottomNavigationView.background as AnimationDrawable
        animDrawable3.setEnterFadeDuration(750)
        animDrawable3.setExitFadeDuration(1500)
        animDrawable3.start()

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    finish()
                    true
                }

                R.id.bottom_stories -> {startActivity(Intent(applicationContext, BlogPagesIBT::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    true
                }

                R.id.bottom_info -> {
                    startActivity(Intent(applicationContext, Info::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    true
                }

                R.id.bottom_donate -> {
                    startActivity(Intent(applicationContext, Donate::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    true
                }
                else -> false
            }
        }//Nav bar nav ends

        eventChangeListener()
    }//on create ends

    private fun eventChangeListener() {
        //Db Connection Setup
        val connectionURL = "jdbc:jtds:sqlserver://lovenottiessql.database.windows.net:1433;databaseName=lovenottiesdb;user=LoveNotties@lovenottiessql;password=MissySue01#;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;sslProtocol=TLSv1.2;ssl=require"

        val a : StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(a)
        val conn : Connection = DriverManager.getConnection(connectionURL)


        // the query is only prepared not executed
        val stmt = conn.createStatement()
        var result = stmt.resultSet
        try {
            result = stmt.executeQuery("SELECT * FROM WhatsOn")
        }
        catch (e : Exception)
        {
            result = null
        }


        // the query is executed and results are fetched
if (result != null) {
    while (result.next()) {

        // getting the value of the id column
        val id = result.getString("PostID")

        // getting the value of the name column
        val title = result.getString("PostTitle")
        val BlogImageID = result.getString("PostImageID")
        val BlogDescription = result.getString("PostDescription")
        val BlogDate = result.getString("PostDate")

        /*
            constructing a User object and
            putting data into the list
             */
        blogItemList.add(WhatsOnModel(id, title, BlogImageID, BlogDescription, BlogDate))
    }
}
        else
{
    val id = "1"

    // getting the value of the name column
    val title = " No posts found"
    val BlogImageID = "1"
    val BlogDescription = "No posts have being added to this page yet"
    val BlogDate = ""

    /*
        constructing a User object and
        putting data into the list
         */
    blogItemList.add(WhatsOnModel(id, title, BlogImageID, BlogDescription, BlogDate))
}
        blogAdapter.notifyDataSetChanged()
        loadingText.visibility = View.GONE
    }

    override fun GotoBlogDetails(blogItem: WhatsOnModel) {
        val intent = Intent(this, OurPeople::class.java)
        intent.putExtra("object", blogItem.PostID)
        startActivity(intent)
    }
}