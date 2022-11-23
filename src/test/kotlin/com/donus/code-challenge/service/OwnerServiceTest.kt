package com.donus.`code-challenge`.service


import com.donus.`code-challenge`.exception.DocumentOwnerException
import com.donus.`code-challenge`.model.Account
import com.donus.`code-challenge`.model.Owner
import com.donus.`code-challenge`.repository.AccountRepository
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.math.BigDecimal
import java.util.*

@SpringBootTest
class OwnerServiceTest(
    @MockBean
    val accountRepository: AccountRepository

) {
    @Autowired
    lateinit var ownerService: OwnerService

    private val accountIdFake: Long = 11
    private val ownerFake = Owner(
        id = 111,
        name = "Frodo Bolseiro",
        document = "999.999.999-99"
    )
    private val accountFake1 = Account(
        id = accountIdFake,
        ownerFake,
        agencyNumber = "631aa",
        accountNumber = "63aaa",
        amount = BigDecimal("100.00"),
        createdAt = Date()
    )

    @Test
    fun `When incorrect document, must return error`() {
        val exception =
            Assertions.assertThrows(DocumentOwnerException::class.java) {
                Mockito.`when`(
                    ownerService.findOwnerByDocument(
                        Owner(
                            id = 111,
                            name = "Frodo Bolseiro",
                            document = "999.999.999"
                        )
                    )
                ).thenThrow(DocumentOwnerException::class.java)
            }

        Assertions.assertEquals("${accountFake1.owner.document} must have at least 11 numbers.", exception.message)
    }
}