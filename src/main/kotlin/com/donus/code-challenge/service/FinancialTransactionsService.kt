package com.donus.`code-challenge`.service

import com.donus.`code-challenge`.model.Account
import com.donus.`code-challenge`.model.financialTransaction.BankDeposit
import com.donus.`code-challenge`.model.financialTransaction.BankTransfer
import com.donus.`code-challenge`.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class FinancialTransactionsService(
    val accountRepository: AccountRepository
) {
    fun cashDeposit(
        account: Account,
        bankDeposit: BankDeposit
    ) {
        account.amount = account.amount?.plus(bankDeposit.amount)
        accountRepository.save(account)
    }

    fun setDebit(
        accountFrom: Account,
        bankTransfer: BankTransfer
    ) {
        accountFrom.amount = accountFrom.amount!! - bankTransfer.amount
        accountRepository.save(accountFrom)
    }

    fun setAmount(
        accountTo: Account,
        bankTransfer: BankTransfer
    ) {
        accountTo.amount = accountTo.amount?.plus(bankTransfer.amount)
        accountRepository.save(accountTo)

    }

}