package com.donus.`code-challenge`.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.util.*

@SpringBootTest
@AutoConfigureWebMvc
class AccountControllerTest {
    @Autowired
    private val mockMvc: MockMvc? = null

    private val baseUrl: String = "localhost:8080/account"
    private val accountIdFake: Long = 11
    private val accountFake1 = com.donus.`code-challenge`.model.Account(
        id = accountIdFake,
        com.donus.`code-challenge`.model.Owner(
            id = 111,
            name = "Frodo Bolseiro",
            document = "999.999.999-99"
        ),
        agencyNumber = "631aa",
        accountNumber = "63aaa",
        amount = BigDecimal("100.00"),
        createdAt = Date()
    )

    @Test
    fun `when listing all accounts, it should return the complete list`() {
        mockMvc?.perform(
            MockMvcRequestBuilders.get(baseUrl)
        )
            ?.andExpect(status().isOk)
            ?.andExpect(MockMvcResultMatchers.jsonPath("\$").isArray)
            ?.andExpect(MockMvcResultMatchers.jsonPath("\$[0].id").isNotEmpty)
            ?.andExpect(MockMvcResultMatchers.jsonPath("\$[0].owner.name").isString)
            ?.andExpect(MockMvcResultMatchers.jsonPath("\$[0].owner.document").isString)
            ?.andExpect(MockMvcResultMatchers.jsonPath("\$[1].id").isNotEmpty)
            ?.andExpect(MockMvcResultMatchers.jsonPath("\$[1].owner.name").isString)
            ?.andExpect(MockMvcResultMatchers.jsonPath("\$[1].owner.document").isString)
            ?.andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `when listing an account owner, it should return successfully`() {
        mockMvc?.perform(
            MockMvcRequestBuilders.get("$baseUrl/$accountIdFake")
        )
            ?.andExpect(status().isOk)
            ?.andExpect(MockMvcResultMatchers.jsonPath("\$").isNotEmpty)
            ?.andExpect(MockMvcResultMatchers.jsonPath("\$[0].id").isNotEmpty)
            ?.andExpect(MockMvcResultMatchers.jsonPath("\$[0].owner.name").isString)
            ?.andExpect(MockMvcResultMatchers.jsonPath("\$[0].owner.document").isString)
            ?.andExpect(MockMvcResultMatchers.jsonPath("\$[0].owner.amount").isNotEmpty)
            ?.andDo(MockMvcResultHandlers.print())
    }

    @Test
    @Throws(Exception::class)
    fun `when listing a non-existent owner it should return error`() {
        mockMvc?.perform(
            MockMvcRequestBuilders.get("$baseUrl/123")
        )
            ?.andExpect(status().isBadRequest)
            ?.andExpect(MockMvcResultMatchers.jsonPath("\$").isEmpty)
            ?.andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun createAccount() {
        mockMvc?.perform(
            MockMvcRequestBuilders.post(baseUrl)
                .content(jacksonObjectMapper().writeValueAsString(accountFake1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            ?.andExpect(status().isOk)
            ?.andExpect(MockMvcResultMatchers.jsonPath("\$").isNotEmpty)
            ?.andExpect(MockMvcResultMatchers.jsonPath("\$[0].id").isNotEmpty)
            ?.andExpect(MockMvcResultMatchers.jsonPath("\$[0].owner.name").isString)
            ?.andExpect(MockMvcResultMatchers.jsonPath("\$[0].owner.document").isString)
            ?.andExpect(MockMvcResultMatchers.jsonPath("\$[0].owner.amount").isNotEmpty)
            ?.andDo(MockMvcResultHandlers.print())
    }
}