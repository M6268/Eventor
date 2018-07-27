package com.example.shanoop.database.Event.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import com.example.shanoop.database.Event.Modal.Event
import com.example.shanoop.database.R
import kotlinx.android.synthetic.main.fragment_dash_board.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DashBoardFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dash_board, container, false)
    }

    lateinit var event: Event
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        event = arguments!!.getSerializable("event") as Event

        event.let {
            event_name.text=event.name
            event_days.text="${event.days} Days"
            event_venue.text=event.venue
            event_members.text="${event.member_count} Members"
            event_status.visibility=INVISIBLE

            val date=event.date.substring(0,event.date.lastIndexOf("-")+3)
            event_date.text=date

            val format = SimpleDateFormat("yyyy-MM-dd")
            try {
                val start_date = format.parse(date)
                val today=format.format(Date())
                Log.w("DATES","$start_date    $today")
            } catch (e: ParseException) {
                e.printStackTrace()
            }


        }
    }

}
