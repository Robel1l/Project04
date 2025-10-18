package com.example.finalproj04


import androidx.annotation.Keep
import kotlinx.serialization.SerialName


@Keep
@kotlinx.serialization.Serializable
data class ActorSearchResponse(
    // JSON key for the results array
    @SerialName("results")
    val results: List<Actor>?
)

@Keep
@kotlinx.serialization.Serializable
data class Actor(
    // Actors name and headshot
    @SerialName("name")
    val actor_name: String?,
    // Actors known for
    @SerialName("known_for")
    var famous_fr: List<KnownFor>?,
    // profile path for actors headshot
    @SerialName("profile_path")
    var hshotURL: String?
) : java.io.Serializable


@Keep
@kotlinx.serialization.Serializable
data class KnownForWrapper(
    @SerialName("known_For")
    val famous_for: List<KnownFor>?
) : java.io.Serializable

//  movie or tv show actors known for
@Keep
@kotlinx.serialization.Serializable
data class KnownFor(
    // name of tv show title
    @SerialName("name")
    var famus_frName: String? = null,
    // name of title
    @SerialName("title")
    var famous_frTitle: String? = null,
    // poster path
    @SerialName("poster_path")
    var famour_frPosterU: String?,
    // overview of movie or tv show
    @SerialName("overview")
    var desc: String?
) : java.io.Serializable