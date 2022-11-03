package com.donus.`code-challenge`.service

import org.springframework.stereotype.Service

@Service
class FinancialTransactionsService(
    val accountRepository: com.donus.`code-challenge`.repository.AccountRepository
) {
    fun cashDeposit(
        account: com.donus.`code-challenge`.model.Account,
        bankDeposit: com.donus.`code-challenge`.model.financialTransaction.BankDeposit
    ) {
        account.amount = account.amount?.plus(bankDeposit.amount)
        accountRepository.save(account)
    }

    fun setDebit(
        accountFrom: com.donus.`code-challenge`.model.Account,
        bankTransfer: com.donus.`code-challenge`.model.financialTransaction.BankTransfer
    ) {
        accountFrom.amount = accountFrom.amount!! - bankTransfer.amount
        accountRepository.save(accountFrom)
    }

    fun setAmount(
        accountTo: com.donus.`code-challenge`.model.Account,
        bankTransfer: com.donus.`code-challenge`.model.financialTransaction.BankTransfer
    ) {
        accountTo.amount = accountTo.amount?.plus(bankTransfer.amount)
        accountRepository.save(accountTo)

    }

}