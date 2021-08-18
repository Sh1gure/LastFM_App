package com.shigure.core_common.format

fun formatBigNumber(number: Int): String =
    String.format("%,d", number).replace(',', ' ')