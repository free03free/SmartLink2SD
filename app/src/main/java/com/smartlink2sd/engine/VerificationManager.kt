package com.smartlink2sd.engine

import java.io.File
import java.security.MessageDigest

class VerificationManager {

    fun verify(source: File, target: File): Boolean {
        if (!source.exists() || !target.exists()) return false
        if (source.isDirectory != target.isDirectory) return false

        if (source.isDirectory) {
            val sourceFiles = source.walkTopDown()
                .filter { it.isFile }
                .map { it.relativeTo(source).path }
                .toSet()

            val targetFiles = target.walkTopDown()
                .filter { it.isFile }
                .map { it.relativeTo(target).path }
                .toSet()

            if (sourceFiles != targetFiles) return false

            return sourceFiles.all { relative ->
                verify(
                    File(source, relative),
                    File(target, relative)
                )
            }
        }

        if (source.length() != target.length()) return false

        return sha256(source) == sha256(target)
    }

    private fun sha256(file: File): String {
        val digest = MessageDigest.getInstance("SHA-256")

        file.inputStream().use { input ->
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)

            while (true) {
                val count = input.read(buffer)
                if (count <= 0) break
                digest.update(buffer, 0, count)
            }
        }

        return digest.digest().joinToString("") { "%02x".format(it) }
    }
}
