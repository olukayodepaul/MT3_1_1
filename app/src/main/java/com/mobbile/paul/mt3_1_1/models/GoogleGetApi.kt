package com.mobbile.paul.mt3_1_1.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GoogleGetApi(

    @SerializedName("geocoded_waypoints")
    @Expose
    val geocoded_waypoints: List<GeocodedWaypoint>,

    @SerializedName("routes")
    @Expose
    val routes: List<Route>,

    @SerializedName("status")
    @Expose
    val status: String

)

data class Bounds(

    @SerializedName("northeast")
    @Expose
    val northeast: Northeast,

    @SerializedName("southwest")
    @Expose
    val southwest: Southwest
)

data class Distance(

    @SerializedName("text")
    @Expose
    val text: String,

    @SerializedName("value")
    @Expose
    val value: Int
)

data class Duration(

    @SerializedName("text")
    @Expose
    val text: String,

    @SerializedName("value")
    @Expose
    val value: Int
)

data class EndLocation(

    @SerializedName("lat")
    @Expose
    val lat: Double,

    @SerializedName("lng")
    @Expose
    val lng: Double
)

data class GeocodedWaypoint(

    @SerializedName("geocoder_status")
    @Expose
    val geocoder_status: String,

    @SerializedName("place_id")
    @Expose
    val place_id: String,

    @SerializedName("types")
    @Expose
    val types: List<String>
)

data class Leg(

    @SerializedName("distance")
    @Expose
    val distance: Distance,

    @SerializedName("duration")
    @Expose
    val duration: Duration,

    @SerializedName("end_address")
    @Expose
    val end_address: String,

    @SerializedName("end_location")
    @Expose
    val end_location: EndLocation,

    @SerializedName("start_address")
    @Expose
    val start_address: String,

    @SerializedName("start_location")
    @Expose
    val start_location: StartLocation,

    @SerializedName("steps")
    @Expose
    val steps: List<Step>,

    @SerializedName("traffic_speed_entry")
    @Expose
    val traffic_speed_entry: List<Any>,

    @SerializedName("via_waypoint")
    @Expose
    val via_waypoint: List<Any>
)

data class Northeast(

    @SerializedName("lat")
    @Expose
    val lat: Double,

    @SerializedName("lng")
    @Expose
    val lng: Double
)

data class OverviewPolyline(

    @SerializedName("points")
    @Expose
    val points: String
)

data class Polyline(

    @SerializedName("points")
    @Expose
    val points: String
)

data class Route(

    @SerializedName("bounds")
    @Expose
    val bounds: Bounds,

    @SerializedName("copyrights")
    @Expose
    val copyrights: String,

    @SerializedName("legs")
    @Expose
    val legs: List<Leg>,

    @SerializedName("overview_polyline")
    @Expose
    val overview_polyline: OverviewPolyline,

    @SerializedName("summary")
    @Expose
    val summary: String,

    @SerializedName("warnings")
    @Expose
    val warnings: List<Any>,

    @SerializedName("waypoint_order")
    @Expose
    val waypoint_order: List<Any>
)

data class Southwest(

    @SerializedName("lat")
    @Expose
    val lat: Double,

    @SerializedName("lng")
    @Expose
    val lng: Double
)

data class StartLocation(

    @SerializedName("lat")
    @Expose
    val lat: Double,

    @SerializedName("lng")
    @Expose
    val lng: Double
)

data class Step(

    @SerializedName("distance")
    @Expose
    val distance: Distance,

    @SerializedName("duration")
    @Expose
    val duration: Duration,

    @SerializedName("end_location")
    @Expose
    val end_location: EndLocation,

    @SerializedName("html_instructions")
    @Expose
    val html_instructions: String,

    @SerializedName("polyline")
    @Expose
    val polyline: Polyline,

    @SerializedName("start_location")
    @Expose
    val start_location: StartLocation,

    @SerializedName("travel_mode")
    @Expose
    val travel_mode: String

)