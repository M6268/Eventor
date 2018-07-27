package com.example.shanoop.database.Event

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.shanoop.database.Event.Fragments.AddMemberFragment
import com.example.shanoop.database.Event.Fragments.DashBoardFragment
import com.example.shanoop.database.Event.Fragments.ListUsersFragment
import com.example.shanoop.database.Event.Fragments.ScanQRFragment
import com.example.shanoop.database.Event.Modal.Event
import com.example.shanoop.database.R
import kotlinx.android.synthetic.main.activity_event_details.*

class EventDetailsActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                replaceFragment(DashBoardFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_add_user -> {
                replaceFragment(AddMemberFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_camera -> {
                replaceFragment(ScanQRFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_users -> {
                replaceFragment(ListUsersFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    lateinit var event: Event
    lateinit var args:Bundle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        val event = intent.getSerializableExtra("event") as Event
        this.event=event
        args=Bundle()
        args.putSerializable("event",event)
        replaceFragment(DashBoardFragment())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun replaceFragment(frag: Fragment) {
        frag.arguments=args
        if(supportFragmentManager.findFragmentById(R.id.container)!=null)
            supportFragmentManager.beginTransaction().replace(R.id.container,frag).commit()
        else
            supportFragmentManager.beginTransaction().add(R.id.container,frag).commit()
    }
}
