package com.donus.`code-challenge`.repository

import com.donus.`code-challenge`.model.Account
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : MongoRepository<Account, String> {
    fun findById(id: Long): Account?
    fun findByOwnerDocument(document: String): Account?
}