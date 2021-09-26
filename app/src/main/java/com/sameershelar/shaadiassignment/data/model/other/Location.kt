package com.sameershelar.shaadiassignment.data.model.other

data class Location(
    val city: String,
    val state: String,
    val country: String,
    val postcode: String,
    val street: Street,
    val coordinates: Coordinates,
    val timeZone: TimeZone,
)
