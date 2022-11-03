package com.donus.`code-challenge`.service

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*
import java.util.concurrent.ThreadLocalRandom

@Service
class AccountService(
    private val accountRepository: com.donus.`code-challenge`.repository.AccountRepository,
    private val ownerService: com.donus.`code-challenge`.service.OwnerService,
    private val financialTransactionsService: com.donus.`code-challenge`.service.FinancialTransactionsService
) {
    fun getAccountAll(): MutableIterable<com.donus.`code-challenge`.model.Account> =
        accountRepository.findAll()

    fun getAccountById(id: Long): com.donus.`code-challenge`.model.Account? {
        return accountRepository.findById(id)
    }

    fun hasAccountExist(owner: com.donus.`code-challenge`.model.Owner) {
        val owner = ownerService.findOwnerByDocument(owner)
        if (owner != null) {
            if (owner.name.isNotEmpty() && owner.document.isNotEmpty())
                throw com.donus.`code-challenge`.exception.BankAccountException("Account already exists.")
        }
    }

    fun createAccount(owner: com.donus.`code-challenge`.model.Owner): com.donus.`code-challenge`.model.Account {
        hasAccountExist(owner)
        if (owner.name == "")
            throw Exception("The name field is required.")
        val account = com.donus.`code-challenge`.model.Account(
            id = ThreadLocalRandom.current().nextLong(100000),
            owner = com.donus.`code-challenge`.model.Owner(
                id = ThreadLocalRandom.current().nextLong(100000),
                document = owner.document,
                name = owner.name
            ),
            agencyNumber = UUID.randomUUID().toString(),
            accountNumber = UUID.randomUUID().toString(),
            createdAt = Date()
        )
        return accountRepository.save(account)
    }

    fun allowedDeposit(bankDeposit: com.donus.`code-challenge`.model.financialTransaction.BankDeposit): Boolean {
        if (bankDeposit.amount < BigDecimal.ZERO)
            throw com.donus.`code-challenge`.exception.DepositException("Account balance is negative. Contact your agency.")
        else if (bankDeposit.amount > BigDecimal("2000.00"))
            throw com.donus.`code-challenge`.exception.DepositException("Deposit must never exceed R$2000. Contact your agency.")
        else
            return true
    }

    fun cashDeposit(bankDeposit: com.donus.`code-challenge`.model.financialTransaction.BankDeposit): ResponseEntity<com.donus.`code-challenge`.model.Account> {
        val account = getAccountById(bankDeposit.accountId)
        val allowedDeposit = allowedDeposit(bankDeposit)
        try {
            if (account != null && allowedDeposit) {
                financialTransactionsService.cashDeposit(account, bankDeposit)
            }
        } catch (e: Error) {
            e.message
        }
        return ResponseEntity.ok(account)
    }

    fun cashTransfer(bankTransfer: com.donus.`code-challenge`.model.financialTransaction.BankTransfer): Map<String, com.donus.`code-challenge`.model.Account?> {
        val accountTo = getAccountById(bankTransfer.accountIdTo)
        val accountFrom = getAccountById(bankTransfer.accountIdFrom)

        try {
            if (accountFrom != null && accountTo != null) {
                financialTransactionsService.setDebit(accountFrom, bankTransfer)
                financialTransactionsService.setAmount(accountTo, bankTransfer)
            }
        } catch (e: Error) {
            e.message
        }
        return mutableMapOf(
            "Account From" to accountFrom,
            "Account To" to accountTo
        )
    }
}