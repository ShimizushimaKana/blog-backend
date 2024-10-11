package com.soyo.blog.http

data class RestResult<T>(
    val code: String,
    val message: String,
    val data: T?
) {
    companion object {
        @JvmStatic
        fun <T> success(data: T): RestResult<T> {
            return RestResult("200", "success", data)
        }

        @JvmStatic
        fun <T> error(code: String, message: String = "error"): RestResult<T> {
            return RestResult(code, message, null)
        }
    }
}