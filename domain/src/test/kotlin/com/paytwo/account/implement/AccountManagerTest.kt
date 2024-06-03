package com.paytwo.account.implement

import com.paytwo.account.model.Account
import com.paytwo.account.repository.AccountRepository
import com.paytwo.support.error.DomainException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.given

@ExtendWith(MockitoExtension::class)
@DisplayName("[AccountManager 테스트]")
class AccountManagerTest {

    @InjectMocks
    private lateinit var accountManager: AccountManager

    @Mock
    private lateinit var accountRepository: AccountRepository

    @Test
    @DisplayName("계좌를 생성한다")
    fun createAccount() {
        // given
        val account = Account(1L, "bankName", 1232123213, 0, 1L)
        given { accountRepository.findByAccountNumber(any()) }.willReturn(null)
        given { accountRepository.save(any()) }.willReturn(account)

        // when
        val result = accountManager.createAccount(account.bankName, account.accountNumber, account.userId)

        // then
        assertThat(result).isNotNull
    }

    @Test
    @DisplayName("이미 존재하는 계좌번호로 실패한다")
    fun createAccount2() {
        // given
        val account = Account(1L, "bankName", 1232123213, 0, 1L)
        given { accountRepository.findByAccountNumber(any()) }.willReturn(account)

        // when, then
        assertThatThrownBy { accountManager.createAccount(account.bankName, account.accountNumber, account.userId) }
            .isInstanceOf(DomainException::class.java)
    }
}
