package com.donus.`code-challenge`.service

import com.donus.`code-challenge`.model.Account
import com.donus.`code-challenge`.model.Owner
import com.donus.`code-challenge`.repository.AccountRepository
import org.hamcrest.CoreMatchers.any
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import org.mockito.BDDMockito
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.math.BigDecimal
import java.util.*

@SpringBootTest
class AccountServiceTest {
    @MockBean
    private val accountRepository: AccountRepository? = null

    @MockBean
    private val ownerService: OwnerService? = accountRepository?.let { OwnerService(it) }

    @MockBean
    private val financialTransactionsService: FinancialTransactionsService? =
        accountRepository?.let { FinancialTransactionsService(it) }

    @Autowired
    private val accountService: AccountService? =
        ownerService?.let { AccountService(accountRepository!!, it, financialTransactionsService!!) }


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
    private val accountFake2 = Account(
        id = 12,
        Owner(
            id = 112,
            name = "Samwise Gamgee",
            document = "888.888.888-88"
        ),
        agencyNumber = "745bb",
        accountNumber = "74bb",
        amount = BigDecimal("1000.00"),
        createdAt = Date()
    )

    @BeforeEach
    fun setUp() {
        BDDMockito.given(accountRepository?.findById(accountIdFake)).willReturn(accountFake1)
        BDDMockito.given(
            accountRepository!!
                .save(accountFake1)
        ).willReturn(accountFake1)
    }

    @Test
    fun createAccount() {
        `when`(accountService?.hasAccountExist(ownerFake)).thenReturn(null)

        val accountCreated = accountService?.createAccount(ownerFake)
        assertThat(accountCreated, `is`(accountFake1))


    }


}