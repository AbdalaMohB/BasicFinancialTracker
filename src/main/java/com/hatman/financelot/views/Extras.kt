package com.hatman.financelot.views
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hatman.financelot.viewmodel.FinancelotViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import com.hatman.financelot.models.SingleExpense
import com.hatman.financelot.models.SingleGain

@Composable
fun AddItemButton(onClick: () -> Unit){
        FloatingActionButton(modifier = Modifier, onClick = onClick) {
            Icon(Icons.Filled.AddCircle, "Add Item")
        }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun InputDialog(financelotViewModel: FinancelotViewModel, onDismissRequest: () -> Unit = {}){
    var amount by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var choice by remember { mutableStateOf("") }

    val mod=Modifier.fillMaxWidth()
        .padding(16.dp)
    Dialog(onDismissRequest = onDismissRequest) {
        Card(modifier = mod.wrapContentSize(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(Modifier.fillMaxWidth()) {
                OutlinedTextField(modifier = mod ,onValueChange = {
                    title=it
                }, value = title,
                    label={ Text("Name") })
                OutlinedTextField(modifier = mod, onValueChange = {
                    amount=it
                }, value = amount,
                    label={ Text("Amount")}, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                ExposedDropdownMenuBox(modifier = mod, onExpandedChange = {expanded=!expanded}, expanded = expanded) {
                    OutlinedTextField(modifier = Modifier.menuAnchor(), readOnly = true, value = choice, onValueChange = {}, label = {Text("Income or Expense")})
                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = {expanded=false}) {
                        DropdownMenuItem(text = { Text("Income") }, onClick = { choice = "Income"; expanded=false })
                        DropdownMenuItem(
                            text = { Text("Expense") },
                            onClick = { choice = "Expense"; expanded=false })
                    }
                }
                Row(mod, horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(onClick=onDismissRequest){ Text("Cancel") }
                    Button(onClick={
                        if (choice=="Income") financelotViewModel.addGain(SingleGain(title, amount))
                        if (choice=="Expense") financelotViewModel.addCost(SingleExpense(title, amount))
                        if (choice!="") onDismissRequest()
                    }){ Text("Add") }
                }
            }
        }
    }
}