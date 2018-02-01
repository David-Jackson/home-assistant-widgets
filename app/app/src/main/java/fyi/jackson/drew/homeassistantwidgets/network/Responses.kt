package fyi.jackson.drew.homeassistantwidgets.network

import com.google.gson.annotations.SerializedName

// Responses for Home Assistant API

data class Status(var message: String)

data class UnitSystem(
        var length: String,
        var mass: String,
        var temperature: String,
        val volume: String
)

data class Config(
        var components: List<String>,
        var elevation: Float,
        var latitude: Float,
        @SerializedName("location_name") var locationName: String,
        var longitude: Float,
        @SerializedName("time_zone") var timeZone: String,
        @SerializedName("unit_system") var unitSystem: UnitSystem,
        var version: String
)

data class DiscoveryInfo(
        @SerializedName("base_url") var baseUrl: String,
        @SerializedName("location_name") var locationName: String,
        @SerializedName("requires_api_password") var passwordRequired: Boolean,
        var version: String
)

data class Event(
        var event: String,
        @SerializedName("listener_count") var listenerCount: Int
)

data class State (
        var attributes: Attributes,
        @SerializedName("entity_id") var entityId: String,
        @SerializedName("last_changed") var lastChanged: String,
        @SerializedName("last_updated") var lastUpdated: String,
        var state: String
)

data class Attributes (
        @SerializedName("friendly_name") var friendlyName: String,
        @SerializedName("assumed_state") var assumedState: Boolean,
        @SerializedName("entity_id") var entityIds: List<String>,
        var order: Int,
        @SerializedName("unit_of_measurement") var unitsOfMeasurement: String,

        // For cameras
        @SerializedName("entity_picture") var entityPicture: String,
        @SerializedName("access_token") var accessToken: String

        // there are many more... maybe just be JSONObject instead?
)


// Responses for Material Design Icons API

data class Package (
        var id: String,
        var name: String,
        var description: String,
        var size: Int,
        var iconCount: Int
)

data class InitData (
        var name: String,
        var description: String,
        var intro: String,
        var packages: List<Package>
)

data class IconUser (
        var id: String,
        var name: String
)

data class Icon (
        var id: String,
        var name: String,
        var aliases: List<String>,
        var data: String,
        var user: IconUser,
        var commentCount: Int
)

data class IconsList (
        var icons: List<Icon>
)
