package com.hatman.financelot.models

data class FinalResult(val exSingle: List<SingleExpense> = listOf(),
                       val pSingle: List<SingleGain> = listOf(),
                       val state: FinancialState= FinancialState.EVEN,
                       val value: Double=0.0,
                        val showDialog: Boolean=false
)
