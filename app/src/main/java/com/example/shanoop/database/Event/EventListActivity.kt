package com.example.shanoop.database.Event

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.shanoop.database.Event.Modal.Event
import com.example.shanoop.database.Event.Adapter.EventAdapter
import com.example.shanoop.database.Helper.BASE_URL
import com.example.shanoop.database.Helper.SESSION_STORAGE
import com.example.shanoop.database.Login.LoginActivity
import com.example.shanoop.database.R
import kotlinx.android.synthetic.main.activity_event_list.*
import kotlinx.android.synthetic.main.content_event_list.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.uiThread
import org.json.JSONObject


class EventListActivity : AppCompatActivity() {

    var events = ArrayList<Event>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)
        setSupportActionBar(toolbar)
        eventlistRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        getEvents()
        refreshLayout.setOnRefreshListener {
            getEvents()
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    fun getEvents() {
        doAsync {

            val list = Request.Builder()
                    .url(BASE_URL + "api/list_events")
                    .post(FormBody.Builder().build())
                    .header("Authorization", "Bearer " + getSharedPreferences(SESSION_STORAGE, 0).getString(SESSION_STORAGE, ""))
                    .build()

            val client = OkHttpClient()

            val resp = client.newCall(list).execute()
            if (resp.body() != null) {
                uiThread {
                    refreshLayout.isRefreshing=false
                    val rawResponse = resp.body()!!.string()
                    Log.w("LISTS", "$rawResponse")
                    val json = JSONObject(rawResponse).optJSONArray("events")
                    when (resp.code()) {
                        200 -> {
                            events=ArrayList<Event>()
                            for (i in 0..json.length() - 1) {
                                val tmp = json.optJSONObject(i)
                                events.add(Event(tmp.optString("_id"), tmp.optString("event_name"),
                                        tmp.optInt("days"),
                                        tmp.optInt("no_of_participants"),
                                        tmp.optString("date"),
                                        tmp.optString("venue")))
                            }
                            val adapter = EventAdapter(events, this@EventListActivity)
                            eventlistRecyclerView.adapter = adapter

                        }
                        400 -> {
                            Snackbar.make(root, "Something Went Wrong!", Snackbar.LENGTH_INDEFINITE).setAction("OK", object : View.OnClickListener {
                                override fun onClick(p0: View?) {
                                }
                            })
                        }
                        else->{
                            Snackbar.make(root, "Something Went Wrong!", Snackbar.LENGTH_INDEFINITE).setAction("OK", object : View.OnClickListener {
                                override fun onClick(p0: View?) {
                                }
                            })
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_signout -> {
                getSharedPreferences(SESSION_STORAGE, 0).edit().remove(SESSION_STORAGE).apply()
                startActivity(intentFor<LoginActivity>())
                finish()
            }
        }
        return true
    }
}
