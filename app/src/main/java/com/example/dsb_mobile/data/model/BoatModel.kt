package com.example.dsb_mobile.data.model

/**
 * Class to represent a model of a boat.
 *
 * @property id an unique identifier for a Boat object.
 * @property imageUrl a URL string pointing to an image associated with the Boat object.
 * @property heading a string representing a title for the Boat object.
 */
data class BoatModel(var id : String, var image:String, var title : String, var color: String)
