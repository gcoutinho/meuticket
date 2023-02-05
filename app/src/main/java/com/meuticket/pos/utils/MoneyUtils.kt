package com.meuticket.pos.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale
import kotlin.math.pow

private const val DECIMAL_EMPTY_BASE_VALUE = 0
private const val REPLACE_CURRENCY_VALUE = "[%s,.\\s]"
private const val DEFAULT_CURRENCY = "BRL"
private const val DEFAULT_DIGITS = 2
private val DEFAULT_LOCALE = Locale("pt", "BR")

interface MoneyValueListener {
    fun hasValue(hasValue: Boolean)
}

class MoneyWatcher(
    private val valueEditText: EditText,
    private val currencyCode: String = DEFAULT_CURRENCY,
    private val parentListener: MoneyValueListener? = null
) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(s: Editable) {
        valueEditText.removeTextChangedListener(this)

        val parsed = s.toString().parseToBigDecimal(currencyCode)
        val formatted = parsed?.toFormattedCurrency(currencyCode, locale = null)

        valueEditText.setText(formatted)
        formatted?.length?.let { valueEditText.setSelection(it) }
        valueEditText.addTextChangedListener(this)

        parentListener?.hasValue(s.isNotEmpty() && s.toString().parseToBigDecimal(currencyCode) != BigDecimal.ZERO)
    }
}

fun getCurrencySymbol(currencyCode: String = DEFAULT_CURRENCY, locale: Locale?): String? = getCurrency(currencyCode, locale = locale).currency.symbol

private fun setBigDecimalConfig(bigDecimal: BigDecimal, digits: Int = DEFAULT_DIGITS): BigDecimal {
    return bigDecimal.setScale(
        digits,
        BigDecimal.ROUND_FLOOR
    ).divide(BigDecimal(10.0.pow(digits.toDouble())), BigDecimal.ROUND_FLOOR)
}

fun String.parseToBigDecimal(currencyCode: String = DEFAULT_CURRENCY, digits: Int = DEFAULT_DIGITS): BigDecimal? {
    val replaceable = String.format(REPLACE_CURRENCY_VALUE, getCurrencySymbol(currencyCode, null))
    val cleanString = replace(replaceable.toRegex(), "")
    return try {
        setBigDecimalConfig(BigDecimal(cleanString), digits)
    } catch (e: NumberFormatException) {
        BigDecimal(DECIMAL_EMPTY_BASE_VALUE)
    }
}

fun BigDecimal.toFormattedCurrency(currencyCode: String = DEFAULT_CURRENCY, digits: Int = DEFAULT_DIGITS, locale: Locale?): String {
    return getCurrency(currencyCode, digits, locale).format(this)
}

fun getCurrency(currencyCode: String = DEFAULT_CURRENCY, digits: Int = DEFAULT_DIGITS, locale: Locale?) =
    (if (locale != null) NumberFormat.getCurrencyInstance(locale) else NumberFormat.getCurrencyInstance()).apply {
        currency = Currency.getInstance(currencyCode)
        maximumFractionDigits = digits
    }
