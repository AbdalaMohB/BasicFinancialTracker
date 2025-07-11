package com.hatman.financelot.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hatman.financelot.models.FinalResult
import com.hatman.financelot.models.SingleExpense
import com.hatman.financelot.models.SingleGain
import com.hatman.financelot.viewmodel.FinancelotViewModel
import com.hatman.financelot.ui.theme.SchemeDark

@Composable
fun MainView(modifier: Modifier= Modifier, financelotViewModel: FinancelotViewModel= viewModel()){
    var showDialog by remember { mutableStateOf(false) }
    Scaffold(Modifier.then(if (showDialog) Modifier.blur(6.dp) else Modifier), floatingActionButton = {AddItemButton{ showDialog = true }}) { contentPadding->
        if (showDialog) {
            InputDialog(financelotViewModel) { showDialog = false }
        }
        val vModel = financelotViewModel.uiState.collectAsState().value
        NavigationTabs(vModel, contentPadding)

    }
}

@Composable
@Preview
fun BaseItem(title: String="TEST TITLE", price: String="9999"){
    val mod=Modifier.fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 0.dp)
    Card(modifier = mod.wrapContentSize().height(65.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = SchemeDark.bg_light,
            contentColor = SchemeDark.text
        )
    ){
        Row (mod.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(title)
            Text(price)
        }
    }
}

@Composable
fun IncomeTab(incomes: List<SingleGain>, modifier: Modifier= Modifier){
    Column {
        for (sg in incomes) {
            BaseItem(sg.name, sg.gain)
        }
    }
}
@Composable
fun ExpenseTab(expenses: List<SingleExpense>, modifier: Modifier= Modifier){
Column {
    for (sg in expenses) {
        BaseItem(sg.name, sg.cost)
    }
}
}
@Composable
fun FinalTab(model: FinalResult, modifier: Modifier= Modifier){
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(fontSize = 20.em, text = model.value.toString(), color = model.state.color)
    }
}