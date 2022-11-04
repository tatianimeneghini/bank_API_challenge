package com.donus.`code-challenge`.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Owner(
    @Id
    val id: Long? = null,
    @Indexed(unique = true)
    val document: String,
    val name: String
)