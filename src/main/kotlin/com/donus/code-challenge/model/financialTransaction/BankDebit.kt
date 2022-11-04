package com.donus.`code-challenge`.model.financialTransaction

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

class BankDebit(
    @JsonProperty("account_id")
    override val accountId: Long,
    override val amount: BigDecimal
) : FinancialTransaction(accountId, amount)