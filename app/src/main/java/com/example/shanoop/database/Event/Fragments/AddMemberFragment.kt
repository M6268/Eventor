package com.example.shanoop.database.Event.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shanoop.database.Event.Modal.Event

import com.example.shanoop.database.R


class AddMemberFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_member, container, false)
    }

    lateinit var event:Event
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        event= arguments?.getSerializable("event") as Event
        event.let {

        }
    }

}
