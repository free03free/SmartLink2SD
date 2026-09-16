package com.smartlink2.backend.root

class RootBackend {
    fun isAvailable(): Boolean =
        runCatching {
            Runtime.getRuntime().exec(arrayOf("su", "-c", "id")).inputStream
                .bufferedReader().readLine()?.contains("uid=0") == true
        }.getOrDefault(false)
}
