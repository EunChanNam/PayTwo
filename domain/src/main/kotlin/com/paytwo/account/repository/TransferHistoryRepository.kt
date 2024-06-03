package com.paytwo.account.repository

import com.paytwo.account.model.TransferHistory

interface TransferHistoryRepository {

    fun save(transferHistory: TransferHistory): TransferHistory
}
