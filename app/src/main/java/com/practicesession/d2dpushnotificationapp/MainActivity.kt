package com.practicesession.d2dpushnotificationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var mProfileLabel: TextView
    private lateinit var mUserLabel: TextView
    private lateinit var mNotificationsLabel: TextView
    private lateinit var mViewPager: ViewPager

    private lateinit var mPagerViewAdapter: PagerViewAdapter
    private lateinit var mFirebaseAuth: FirebaseAuth

    override fun onStart() {
        super.onStart()

        val currentUser = mFirebaseAuth.currentUser
        if(currentUser == null){
            sendToLogin()
        }
    }

    private fun sendToLogin() {
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFirebaseAuth = FirebaseAuth.getInstance()

        mProfileLabel = findViewById(R.id.profileLabel)
        mUserLabel = findViewById(R.id.usersLabel)
        mNotificationsLabel = findViewById(R.id.notificationsLabel)

        mViewPager = findViewById(R.id.mainPager)
        mViewPager.offscreenPageLimit = 2

        mPagerViewAdapter = PagerViewAdapter(supportFragmentManager)

        mViewPager.adapter = mPagerViewAdapter

        mProfileLabel.setOnClickListener {
            mViewPager.currentItem = 0
        }

        mUserLabel.setOnClickListener {
            mViewPager.currentItem = 1
        }

        mNotificationsLabel.setOnClickListener {
            mViewPager.currentItem = 2
        }

        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                changeTabs(position)
            }


        })

    }

    private fun changeTabs(position: Int) {
        when (position) {
            0 -> {
                mProfileLabel.setTextColor(ContextCompat.getColor(this, R.color.textTabBright))
                mProfileLabel.textSize = 22.0f

                mUserLabel.setTextColor(ContextCompat.getColor(this, R.color.textTabLight))
                mUserLabel.textSize = 16.0f

                mNotificationsLabel.setTextColor(ContextCompat.getColor(this, R.color.textTabLight))
                mNotificationsLabel.textSize = 16.0f

            }

            1 -> {
                mUserLabel.setTextColor(ContextCompat.getColor(this, R.color.textTabBright))
                mUserLabel.textSize = 22.0f

                mProfileLabel.setTextColor(ContextCompat.getColor(this, R.color.textTabLight))
                mProfileLabel.textSize = 16.0f

                mNotificationsLabel.setTextColor(ContextCompat.getColor(this, R.color.textTabLight))
                mNotificationsLabel.textSize = 16.0f
            }

            2 -> {
                mNotificationsLabel.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.textTabBright
                    )
                )
                mNotificationsLabel.textSize = 22.0f

                mProfileLabel.setTextColor(ContextCompat.getColor(this, R.color.textTabLight))
                mProfileLabel.textSize = 16.0f

                mUserLabel.setTextColor(ContextCompat.getColor(this, R.color.textTabLight))
                mUserLabel.textSize = 16.0f
            }
        }

    }
}
