package com.donus.`code-challenge`.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.math.BigDecimal
import java.util.*
import javax.annotation.Generated
import java.math.MathContext.DECIMAL128 as DECIMAL1281

@Document
data class Account(
    @Id
    val id: Long? = null,
    val owner: com.donus.`code-challenge`.model.Owner,
    @JsonProperty("agency_number")
    val agencyNumber: String?,
    @JsonProperty("account_number")
    val accountNumber: String?,
    var amount: BigDecimal? = BigDecimal.ZERO,
    @JsonProperty("created_at")
    val createdAt: Date,
    @JsonProperty("closed_at")
    val closedAt: Date? = null,
)