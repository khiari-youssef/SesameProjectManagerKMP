package tn.sesame.spm.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SesameClassDTO(
 @SerialName("id")   val id : String,
 @SerialName("name")   val name : String,
 @SerialName("level")  val level : String,
 @SerialName("group")    val group : String
)