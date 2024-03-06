package com.example.submissionakhir

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Strawhat(
    val name: String,
    val position: String,
    val description: String,
    val moreDescription: String,
    val bounty: String,
    val photo: String,
) : Parcelable
