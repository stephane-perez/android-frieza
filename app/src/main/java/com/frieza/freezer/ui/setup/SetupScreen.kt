package com.frieza.freezer.ui.setup

import com.frieza.freezer.R
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.frieza.freezer.data.FriezaRepository
import kotlinx.coroutines.launch

/** Creates a freezer (first launch, or "Add a freezer"). */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupScreen(
    repository: FriezaRepository,
    isFirstRun: Boolean,
    onSaved: () -> Unit,
    onBack: () -> Unit
) {
    val defaultName = stringResource(R.string.default_freezer_name)
    var name by rememberSaveable { mutableStateOf(if (isFirstRun) defaultName else "") }
    var drawerCount by rememberSaveable { mutableIntStateOf(3) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            if (!isFirstRun) {
                TopAppBar(
                    title = { Text(stringResource(R.string.new_freezer)) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                        }
                    }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.AcUnit,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (isFirstRun) {
                Text(
                    text = stringResource(R.string.welcome),
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                singleLine = true,
                label = { Text(stringResource(R.string.freezer_name)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )

            Text(
                text = stringResource(R.string.how_many_drawers),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                IconButton(onClick = { if (drawerCount > 1) drawerCount-- }) {
                    Icon(Icons.Filled.Remove, contentDescription = stringResource(R.string.fewer_drawers))
                }
                Text(text = drawerCount.toString(), style = MaterialTheme.typography.displayMedium)
                IconButton(onClick = { if (drawerCount < 20) drawerCount++ }) {
                    Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.more_drawers))
                }
            }

            Text(
                text = stringResource(R.string.setup_hint),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 16.dp)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                enabled = name.isNotBlank(),
                onClick = {
                    scope.launch {
                        repository.createFreezer(name, drawerCount)
                        onSaved()
                    }
                }
            ) {
                Text(stringResource(if (isFirstRun) R.string.create_my_freezer else R.string.create))
            }
        }
    }
}
