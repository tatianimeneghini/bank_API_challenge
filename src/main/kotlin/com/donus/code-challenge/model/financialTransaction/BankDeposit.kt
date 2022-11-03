package com.donus.`code-challenge`.model.financialTransaction

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

class BankDeposit(
    @JsonProperty("account_id")
    override val accountId: Long,
    override val amount: BigDecimal
) : com.donus.`code-challenge`.model.financialTransaction.FinancialTransaction(accountId, amount)