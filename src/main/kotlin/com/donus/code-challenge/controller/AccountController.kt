package com.donus.`code-challenge`.controller

import com.donus.`code-challenge`.model.Account
import com.donus.`code-challenge`.model.Owner
import com.donus.`code-challenge`.model.financialTransaction.BankDeposit
import com.donus.`code-challenge`.model.financialTransaction.BankTransfer
import com.donus.`code-challenge`.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/account/")
class AccountController(
    private val accountService: AccountService,
) {
    @GetMapping
    fun getAll() =
        accountService.getAccountAll()

    @GetMapping("{id}")
    fun getById(@PathVariable id: Long) = accountService.getAccountById(id)

    @PostMapping
    fun createAccount(@RequestBody owner: Owner): ResponseEntity<Account> =
        ResponseEntity.ok(accountService.createAccount(owner))

    @PatchMapping("deposit")
    fun cashDeposit(@RequestBody bankDeposit: BankDeposit) =
        accountService.cashDeposit(bankDeposit)

    @PatchMapping("transfer")
    fun cashTransfer(@RequestBody bankTransfer: BankTransfer) =
        accountService.cashTransfer(bankTransfer)
}