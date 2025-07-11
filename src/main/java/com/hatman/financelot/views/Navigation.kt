package com.hatman.financelot.views
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import com.hatman.financelot.ui.theme.Purple80
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hatman.financelot.models.FinalResult
import com.hatman.financelot.viewmodel.FinancelotViewModel


enum class Tabs(val route: String, val title: String){
    IncomeTotal("incomeTotal", "Income"), ExpenseTotal("expenseTotal", "Expenses"), FinalRes("finalRes", "Final Calculation")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationTabs(model: FinalResult, contentPadding: PaddingValues, modifier: Modifier= Modifier){
    val navController= rememberNavController()
    val startTab= Tabs.IncomeTotal
    var selectedTab by rememberSaveable { mutableIntStateOf(startTab.ordinal) }
    Column {
        PrimaryTabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier.fillMaxWidth().padding(contentPadding)
        ) {
            Tabs.entries.forEachIndexed { idx, des ->
                Tab(selected = selectedTab == idx,
                    onClick = {
                        navController.navigate(route = des.route)
                        selectedTab = idx
                    },
                    text = {
                        Text(
                            text = des.title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
            }
        }
        AppNavHost(navController, startTab, Modifier, model)
    }
}
@Composable
fun PlaceholderView(name: String, modifier: Modifier = Modifier) {
    Column (modifier.wrapContentSize().fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Box(modifier.wrapContentSize().border(4.dp, color = Purple80, shape = RoundedCornerShape(4.dp)).fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            Text(
                text = "Hello $name!",
                modifier = modifier.wrapContentSize(),
                textAlign = TextAlign.Center,
            )
        }
    }
}
@Composable
fun AppNavHost(navCont: NavHostController, tab: Tabs, modifier: Modifier= Modifier, model: FinalResult) {
    NavHost(navCont, startDestination = tab.route, modifier = modifier){
        Tabs.entries.forEach{ des ->
            composable(des.route){
                when(des){
                    Tabs.IncomeTotal -> IncomeTab(model.pSingle)
                    Tabs.ExpenseTotal -> ExpenseTab(model.exSingle)
                    Tabs.FinalRes -> FinalTab(model)
                }
            }
        }
    }
}
