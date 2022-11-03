package com.donus.`code-challenge`.utils

import com.donus.`code-challenge`.exception.DocumentOwnerException

class DocumentValidate {
    companion object {
        fun documentValidate(document: String) {
            val documentReplaced = document.replace(".", "").replace("-", "")

            if (documentReplaced.length < 11)
                throw com.donus.`code-challenge`.exception.DocumentOwnerException("$document must have at least 11 numbers.")
        }
    }
}