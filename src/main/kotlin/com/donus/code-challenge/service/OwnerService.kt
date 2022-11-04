package com.donus.`code-challenge`.service

import com.donus.`code-challenge`.exception.BankAccountException
import com.donus.`code-challenge`.model.Owner
import com.donus.`code-challenge`.repository.AccountRepository
import com.donus.`code-challenge`.utils.DocumentValidate
import org.springframework.stereotype.Service

@Service
class OwnerService(
    val accountRepository: AccountRepository,
) {
    fun findOwnerByDocument(owner: Owner): Owner? {
        DocumentValidate.documentValidate(owner.document)
        val owner = accountRepository.findByOwnerDocument(owner.document)

        return if (owner != null) {
            throw BankAccountException("It's not possible to create a new account for a owner of the account.")
        } else {
            owner
        }
    }
}