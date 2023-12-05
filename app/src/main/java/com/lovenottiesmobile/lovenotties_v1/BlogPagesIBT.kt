package com.lovenottiesmobile.lovenotties_v1

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView

class BlogPagesIBT : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog_pages_ibt)

        //Nav Bar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.selectedItemId = R.id.bottom_stories

        //CardViews
        val whatsOn = findViewById<TextView>(R.id.textViewBlog)
        val environment = findViewById<TextView>(R.id.textViewSS)
        val humanity = findViewById<TextView>(R.id.textViewOP)

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
                R.id.bottom_stories ->  {
                    startActivity(Intent(applicationContext, BlogPagesIBT::class.java))
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

        // For the 3 Buttons
        whatsOn.setOnClickListener {
            val intent = Intent(this, Blog::class.java)
            startActivity(intent)
        }

        environment.setOnClickListener {
            val intent = Intent(this, SuccessStories::class.java)
            startActivity(intent)
        }

        humanity.setOnClickListener {
            val intent = Intent(this, OurPeople::class.java)
            startActivity(intent)
        }
    }
}