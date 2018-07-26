package com.example.shanoop.database

import com.example.shanoop.database.Event.Modal.Event

interface EventListener{
    fun onEventClick(e:Event)
}