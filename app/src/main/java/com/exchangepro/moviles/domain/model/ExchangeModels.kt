package com.exchangepro.moviles.domain.model

enum class UserRole { USER, ADMIN }
enum class OperationType { COMPRA, VENTA }
enum class OfferStatus { ACTIVA, PAUSADA, COMPLETADA, CANCELADA }
enum class TransactionStatus { PENDIENTE_PAGO, PAGADO, COMPLETADO, CANCELADO, EN_DISPUTA }
enum class CurrencyCode { PEN, USD }

data class AppUser(
    val id: String,
    val role: UserRole,
    val fullName: String,
    val email: String,
    val phone: String,
    val documentNumber: String,
    val reputation: Double,
    val totalRatings: Int,
    val photoUrl: String? = null
)

data class WalletBalance(
    val currency: CurrencyCode,
    val available: Double,
    val retained: Double
)

data class Wallet(
    val userId: String,
    val balances: List<WalletBalance>
)

data class Offer(
    val id: String,
    val userId: String,
    val userName: String,
    val operationType: OperationType,
    val fromCurrency: CurrencyCode,
    val toCurrency: CurrencyCode,
    val exchangeRate: Double,
    val offeredAmount: Double,
    val minimumAmount: Double,
    val paymentMethods: List<String>,
    val status: OfferStatus
)

data class Transaction(
    val id: String,
    val code: String,
    val offerId: String,
    val buyerId: String,
    val buyerName: String,
    val sellerId: String,
    val sellerName: String,
    val paymentMethod: String,
    val operationAmount: Double,
    val totalToPay: Double,
    val currency: CurrencyCode,
    val status: TransactionStatus,
    val voucherUrl: String? = null
)

data class PaymentData(
    val id: String,
    val userId: String,
    val methodName: String,
    val bankName: String?,
    val accountNumber: String?,
    val alias: String
)

data class Dispute(
    val id: String,
    val transactionCode: String,
    val reason: String,
    val status: String
)

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val read: Boolean
)
