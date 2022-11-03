package com.donus.`code-challenge`.model.financialTransaction

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

open class FinancialTransaction(
    @JsonProperty("account_id")
    open val accountId: Long,
    open val amount: BigDecimal
)