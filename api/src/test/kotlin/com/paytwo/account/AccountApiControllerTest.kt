package com.paytwo.account

import com.paytwo.account.repository.AccountRepository
import com.paytwo.account.request.AccountCreateRequest
import com.paytwo.account.request.TransferRequest
import com.paytwo.support.ApiTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@Sql("/sql/simple-data.sql")
@DisplayName("[Account API 테스트]")
class AccountApiControllerTest : ApiTestSupport() {

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Test
    @DisplayName("계좌 생성 API")
    fun createNewAccount() {
        // given
        val request = AccountCreateRequest("bankName", 123123)

        // when
        val actions = mockMvc?.perform(
            MockMvcRequestBuilders
                .post("/v1/accounts")
                .header(AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request))
        )!!

        // then
        actions.andExpectAll(
            status().isOk,
            jsonPath("$.data").isNumber
        )
    }

    @Test
    @DisplayName("송금 API")
    fun sendMoney() {
        // given
        val accessToken = login("hong", "password123")
        val request = TransferRequest(
            senderAccountNumber = 1234567890,
            amount = 50000,
            dealAt = LocalDateTime.of(2024, 5, 19, 10, 10, 10),
            receiverAccountNumber = 9876543210,
            receiverBankName = "국민은행"
        )

        // when
        val actions = mockMvc?.perform(
            MockMvcRequestBuilders
                .post("/v1/accounts/transfer")
                .header(AUTHORIZATION, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request))
        )!!

        // then
        actions.andExpect(status().isOk)
    }

    @Test
    @DisplayName("송금 API 동시성 테스트")
    fun sendMoneyConcurrency() {
        // given
        val countDownLatch = CountDownLatch(10)
        val executorService = Executors.newFixedThreadPool(10)

        val accessToken1 = login("hong", "password123")
        val request1 = TransferRequest(
            senderAccountNumber = 1234567890,
            amount = 10000,
            dealAt = LocalDateTime.of(2024, 5, 19, 10, 10, 10),
            receiverAccountNumber = 9876543210,
            receiverBankName = "국민은행"
        )

        val accessToken2 = login("kim", "password123")
        val request2 = TransferRequest(
            senderAccountNumber = 9876543210,
            amount = 20000,
            dealAt = LocalDateTime.of(2024, 5, 19, 10, 10, 10),
            receiverAccountNumber = 1234567890,
            receiverBankName = "우리은행"
        )

        // when
        (1..5).forEach { _ ->
            executorService.execute {
                mockMvc?.perform(
                    MockMvcRequestBuilders
                        .post("/v1/accounts/transfer")
                        .header(AUTHORIZATION, accessToken1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request1))
                )
                countDownLatch.countDown()
            }
        }

        (1..5).forEach { _ ->
            executorService.execute {
                mockMvc?.perform(
                    MockMvcRequestBuilders
                        .post("/v1/accounts/decrease")
                        .param("accountNumber", "1234567890")
                        .param("amount", "10000")
                )
                countDownLatch.countDown()
            }
        }
        //
        //        (1..1).forEach { _ ->
        //            executorService.execute {
        //                mockMvc?.perform(
        //                    MockMvcRequestBuilders
        //                        .post("/v1/accounts/transfer")
        //                        .header(AUTHORIZATION, accessToken2)
        //                        .contentType(MediaType.APPLICATION_JSON)
        //                        .content(toJson(request2))
        //                )
        //                countDownLatch.countDown()
        //            }
        //        }

        countDownLatch.await()

        // then
        val balanceList = accountRepository.findAll()
            .map { it.balance }
            .toList()

        assertThat(balanceList.get(0)).isEqualTo(0)
        assertThat(balanceList.get(1)).isEqualTo(550_000)
    }
}
