package com.donus.`code-challenge`.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/account/")
class AccountController(
    private val accountService: com.donus.`code-challenge`.service.AccountService,
) {
    @GetMapping
    fun getAll() =
        accountService.getAccountAll()

    @GetMapping("{id}")
    fun getById(@PathVariable id: Long) = accountService.getAccountById(id)

    @PostMapping
    fun createAccount(@RequestBody owner: com.donus.`code-challenge`.model.Owner): ResponseEntity<com.donus.`code-challenge`.model.Account> =
        ResponseEntity.ok(accountService.createAccount(owner))

    @PatchMapping("deposit")
    fun cashDeposit(@RequestBody bankDeposit: com.donus.`code-challenge`.model.financialTransaction.BankDeposit) =
        accountService.cashDeposit(bankDeposit)

    @PatchMapping("transfer")
    fun cashTransfer(@RequestBody bankTransfer: com.donus.`code-challenge`.model.financialTransaction.BankTransfer) =
        accountService.cashTransfer(bankTransfer)
}