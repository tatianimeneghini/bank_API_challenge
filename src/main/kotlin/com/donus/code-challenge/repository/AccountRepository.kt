package com.donus.`code-challenge`.repository

import com.donus.`code-challenge`.model.Account
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : MongoRepository<com.donus.`code-challenge`.model.Account, String> {
    fun findById(id: Long): com.donus.`code-challenge`.model.Account?
    fun findByOwnerDocument(document: String): com.donus.`code-challenge`.model.Account?
}