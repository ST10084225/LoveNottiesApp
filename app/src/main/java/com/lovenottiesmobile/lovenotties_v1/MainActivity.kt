package com.lovenottiesmobile.lovenotties_v1

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {
    lateinit var tv1 : TextView
    lateinit var imgLogo : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setup animations
        val layout = findViewById<LinearLayout>(R.id.background)
        tv1 = findViewById(R.id.tv1)
        imgLogo = findViewById<ImageView>(R.id.imgLogo)

        //setup background gradient animations
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        val animDrawable = layout.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(375)
        animDrawable.setExitFadeDuration(750)
        animDrawable.start()
        loadViews()


        Timer().schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(applicationContext, LoadingActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }
        }, 3750)
    }

    private fun loadViews(){
        tv1.visibility = View.VISIBLE
        imgLogo.visibility = View.VISIBLE

        tv1.startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_in))
        imgLogo.startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_slow))
    }
}