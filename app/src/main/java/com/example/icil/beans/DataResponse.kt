package com.example.icil.beans

import com.google.gson.annotations.SerializedName

class DataResponse(
    @SerializedName("status") var status: Int,
    @SerializedName("message") var message: String
    )
