package com.example.uzbexchangerate.utils

sealed class NetworkResource<T>(val data: T? = null, val uiText: UiText = UiText.StaticString(), val message: String? = null) {
    class Success<T>(data: T?) : NetworkResource<T>(data)
    class Error<T>(uiText: UiText, data: T? = null, message: String? = null) : NetworkResource<T>(data, uiText)
}