package tela

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import viewmodel.TipViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun tela(){
    val tipViewModel: TipViewModel = viewModel()
    val state = tipViewModel.uiState.collectAsState()
    var amountText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("Tip Calculator") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text("Amount")
                OutlinedTextField(
                    modifier = Modifier.padding(15.dp),
                    value = amountText,
                    onValueChange = {
                        if (it.isEmpty() || it.toDoubleOrNull() != null) {
                            amountText = it
                            val valor = it.toDoubleOrNull() ?: 0.0
                            tipViewModel.updateAmount(valor)
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text("Custom %")
                Slider(
                    modifier = Modifier.padding(15.dp).weight(1f),
                    value = state.value.custom.toFloat(),
                    onValueChange = { tipViewModel.updateCustom(it.toInt()) },
                    valueRange = 0f..30f,
                    thumb = {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(color = MaterialTheme.colorScheme.primaryContainer, shape = CircleShape)
                )})
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Text("15%", modifier = Modifier.padding(horizontal = 40.dp))
                Text(text = "${state.value.custom}%", modifier = Modifier.padding(horizontal = 40.dp))
            }


            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Tip")

                Box(modifier = Modifier.padding(horizontal = 15.dp)
                    .background(color = MaterialTheme.colorScheme.primaryContainer)) {
                    Text(modifier = Modifier.padding(horizontal = 15.dp), text = "$${"%.2f".format(state.value.tip15)}")
                }

                Box(modifier = Modifier.padding(horizontal = 15.dp)
                    .background(color = MaterialTheme.colorScheme.primaryContainer)) {
                    Text(modifier = Modifier.padding(horizontal = 15.dp), text = "$${"%.2f".format(state.value.tipCustom)}")
                }
            }


            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Total")

                Box(modifier = Modifier.padding(horizontal = 15.dp)
                    .background(color = MaterialTheme.colorScheme.primaryContainer)) {
                    Text(modifier = Modifier.padding(horizontal = 15.dp), text = "$${"%.2f".format(state.value.total15)}")
                }

                Box(modifier = Modifier.padding(horizontal = 15.dp)
                    .background(color = MaterialTheme.colorScheme.primaryContainer)) {
                    Text(modifier = Modifier.padding(horizontal = 15.dp), text = "$${"%.2f".format(state.value.totalCustom)}")
                }
            }
        }
    }
}