package com.metaplex.lib.solana

import com.solana.api.Api
import com.solana.api.getAccountInfo
import com.solana.api.getMultipleAccounts
import com.solana.api.getProgramAccounts
import com.solana.core.PublicKey
import com.solana.models.ProgramAccount
import com.solana.models.buffer.BufferInfo
import com.solana.networking.NetworkingRouter
import com.solana.networking.RPCEndpoint
import com.solana.vendor.borshj.BorshCodable

interface Connection {
    fun <T: BorshCodable> getProgramAccounts(account: PublicKey,
                                                 decodeTo: Class<T>,
                                                 onComplete: (Result<List<ProgramAccount<T>>>) -> Unit
    )
    fun <T: BorshCodable> getAccountInfo(account: PublicKey,
                              decodeTo: Class<T>,
                              onComplete: ((Result<BufferInfo<T>>) -> Unit))

    fun <T: BorshCodable> getMultipleAccountsInfo(
        accounts: List<PublicKey>,
        decodeTo: Class<T>,
        onComplete: ((Result<List<BufferInfo<T>>>) -> Unit)
    )
}

class SolanaConnectionDriver(endpoint: RPCEndpoint): Connection {
    val solanaRPC: Api = Api(NetworkingRouter(endpoint))

    override fun <T: BorshCodable> getProgramAccounts(account: PublicKey,
                                                      decodeTo: Class<T>,
                                                      onComplete: (Result<List<ProgramAccount<T>>>) -> Unit
    ){
        solanaRPC.getProgramAccounts(account, decodeTo, onComplete)
    }

    override fun <T: BorshCodable> getAccountInfo(account: PublicKey,
                                    decodeTo: Class<T>,
                                    onComplete: ((Result<BufferInfo<T>>) -> Unit)){
        solanaRPC.getAccountInfo(account, decodeTo, onComplete)
    }

    override fun <T: BorshCodable> getMultipleAccountsInfo(
        accounts: List<PublicKey>,
        decodeTo: Class<T>,
        onComplete: ((Result<List<BufferInfo<T>>>) -> Unit)
    ){
        solanaRPC.getMultipleAccounts(accounts, decodeTo, onComplete)
    }
}