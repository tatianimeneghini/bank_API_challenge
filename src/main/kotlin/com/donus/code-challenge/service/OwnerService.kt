package com.donus.`code-challenge`.service

import com.`donus.code-challenge`.utils.DocumentValidate
import org.springframework.stereotype.Service

@Service
class OwnerService(
    val accountRepository: com.donus.`code-challenge`.repository.AccountRepository,
) {
    fun findOwnerByDocument(owner: com.donus.`code-challenge`.model.Owner): com.donus.`code-challenge`.model.Owner? {
        DocumentValidate.documentValidate(owner.document)
        val owner = accountRepository.findByOwnerDocument(owner.document)

        return if (owner != null) {
            throw com.donus.`code-challenge`.exception.BankAccountException("It's not possible to create a new account for a owner of the account.")
        } else {
            owner
        }
    }
}