package com.donus.`code-challenge`.model.financialTransaction

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

class BankTransfer(
    @JsonProperty("account_id_from")
    val accountIdFrom: Long,
    @JsonProperty("account_id_to")
    val accountIdTo: Long,
    val amount: BigDecimal
)