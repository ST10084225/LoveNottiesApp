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
import java.sql.Connection
import java.sql.DriverManager

class OurPeople : AppCompatActivity(), OurPeopleAdapter.IOPDetails {
    private lateinit var recyclerView: RecyclerView
    private lateinit var  opItemList: MutableList<OurPeopleModel>
    private lateinit var  opAdapter: OurPeopleAdapter
    private lateinit var  loadingText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_our_people)

        opItemList = ArrayList()
        opAdapter = OurPeopleAdapter(this, opItemList, this)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = opAdapter
        loadingText = findViewById(R.id.loadingtxt)



        //Nav Bar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.selectedItemId = R.id.bottom_stories

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
                    startActivity(Intent(applicationContext, LoadingActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    true
                }

                R.id.bottom_stories -> { finish()
                    true}

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
    }//end on create

    private fun eventChangeListener() {
        //Db Connection Setup
        val connectionURL = "jdbc:jtds:sqlserver://lovenottiessql.database.windows.net:1433;databaseName=lovenottiesdb;user=LoveNotties@lovenottiessql;password=MissySue01#;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;ssl=request"

        val a : StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(a)
        val conn : Connection = DriverManager.getConnection(connectionURL)


        // the query is only prepared not executed
        val stmt = conn.createStatement()
        var result = stmt.resultSet
        try {
            result = stmt.executeQuery("SELECT * FROM OurPeoples")
        }
        catch (e : Exception)
        {
            result = null
        }


        // the query is executed and results are fetched
        if (result != null) {
            while (result.next()) {

                // getting the value of the id column
                val id = result.getString("OurPersonID")

                // getting the value of the name column
                val fname = result.getString("OurPersonFirstName")
                val lname = result.getString("OurPersonLastName")
                val title = result.getString("OurPersonTitle")
                val OPImageID = result.getString("OurPersonImageID")
                val OPDescription = result.getString("OurPersonDescription")
                /*
                constructing a User object and
                putting data into the list
                 */
                opItemList.add(OurPeopleModel(id, fname, lname, title, OPImageID, OPDescription))
            }
        }
        else
        {
            val id = "1"

            // getting the value of the name column
            val fname = ""
            val lname = ""
            val title = "No Posts Found"
            val OPImageID = "1"
            val OPDescription = "No posts have being added to this page yet"

            /*
                constructing a User object and
                putting data into the list
                 */
            opItemList.add(OurPeopleModel(id, fname, lname, title, OPImageID, OPDescription))
        }
        opAdapter.notifyDataSetChanged()
        loadingText.visibility = View.GONE
    }

    override fun GotoOPDetails(opItem: OurPeopleModel) {
        val intent = Intent(this, OurPeople::class.java)
        intent.putExtra("object", opItem.OPID)
        startActivity(intent)
    }
}