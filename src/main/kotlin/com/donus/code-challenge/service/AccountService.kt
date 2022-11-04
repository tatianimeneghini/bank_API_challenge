package com.donus.`code-challenge`.service

import com.donus.`code-challenge`.exception.BankAccountException
import com.donus.`code-challenge`.exception.DepositException
import com.donus.`code-challenge`.model.Account
import com.donus.`code-challenge`.model.Owner
import com.donus.`code-challenge`.model.financialTransaction.BankDeposit
import com.donus.`code-challenge`.model.financialTransaction.BankTransfer
import com.donus.`code-challenge`.repository.AccountRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*
import java.util.concurrent.ThreadLocalRandom

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val ownerService: OwnerService,
    private val financialTransactionsService: FinancialTransactionsService
) {
    fun getAccountAll(): MutableIterable<Account> =
        accountRepository.findAll()

    fun getAccountById(id: Long): Account? {
        return accountRepository.findById(id)
    }

    fun hasAccountExist(owner: Owner) {
        val owner = ownerService.findOwnerByDocument(owner)
        if (owner != null) {
            if (owner.name.isNotEmpty() && owner.document.isNotEmpty())
                throw BankAccountException("Account already exists.")
        }
    }

    fun createAccount(owner: Owner): Account {
        hasAccountExist(owner)
        if (owner.name == "")
            throw Exception("The name field is required.")
        val account = Account(
            id = ThreadLocalRandom.current().nextLong(100000),
            owner = Owner(
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

    fun allowedDeposit(bankDeposit: BankDeposit): Boolean {
        if (bankDeposit.amount < BigDecimal.ZERO)
            throw DepositException("Account balance is negative. Contact your agency.")
        else if (bankDeposit.amount > BigDecimal("2000.00"))
            throw DepositException("Deposit must never exceed R$2000. Contact your agency.")
        else
            return true
    }

    fun cashDeposit(bankDeposit: BankDeposit): ResponseEntity<Account> {
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

    fun cashTransfer(bankTransfer: BankTransfer): Map<String, Account?> {
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