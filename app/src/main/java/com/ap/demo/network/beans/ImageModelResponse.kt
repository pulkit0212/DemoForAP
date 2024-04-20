package com.ap.demo.network.beans


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

class ImageModelResponse : ArrayList<ImageModelResponse.ImageModelResponseItem>(){
    @Keep
    data class ImageModelResponseItem(
        @SerializedName("backupDetails")
        val backupDetails: BackupDetails,
        @SerializedName("coverageURL")
        val coverageURL: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("language")
        val language: String,
        @SerializedName("mediaType")
        val mediaType: Int,
        @SerializedName("publishedAt")
        val publishedAt: String,
        @SerializedName("publishedBy")
        val publishedBy: String,
        @SerializedName("thumbnail")
        val thumbnail: Thumbnail,
        @SerializedName("title")
        val title: String
    ) {
        @Keep
        data class BackupDetails(
            @SerializedName("pdfLink")
            val pdfLink: String,
            @SerializedName("screenshotURL")
            val screenshotURL: String
        )
    
        @Keep
        data class Thumbnail(
            @SerializedName("aspectRatio")
            val aspectRatio: Int,
            @SerializedName("basePath")
            val basePath: String,
            @SerializedName("domain")
            val domain: String,
            @SerializedName("id")
            val id: String,
            @SerializedName("key")
            val key: String,
            @SerializedName("qualities")
            val qualities: List<Int>,
            @SerializedName("version")
            val version: Int
        )
    }
}