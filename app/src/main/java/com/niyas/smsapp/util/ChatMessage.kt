package com.niyas.smsapp.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatMessage(val text: String, val isSent: Boolean) :Parcelable