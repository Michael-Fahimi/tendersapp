package com.example.tendersapp.presentation.tenders


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tendersapp.domain.model.TenderDomain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TendersScreen(
    viewModel: TendersViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tenders") },
                actions = {
                    IconButton(onClick = { viewModel.refreshTenders() }) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val currentState = state) {
                is TendersState.Idle, TendersState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is TendersState.Success -> {
                    if (currentState.tenders.isEmpty()) {
                        Text(
                            text = "No tenders found",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        TendersList(
                            tenders = currentState.tenders,
                            onLoadMore = { viewModel.loadNextPage() }
                        )
                    }
                }
                is TendersState.Error -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = currentState.message)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.refreshTenders() }) {
                            Text("Try Again")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TendersList(
    tenders: List<TenderDomain>,
    onLoadMore: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tenders) { tender ->
            TenderItem(tender = tender)
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = onLoadMore) {
                    Text("Load More")
                }
            }
        }
    }
}

@Composable
fun TenderItem(tender: TenderDomain) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = tender.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Organization: ${tender.organization}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Published: ${tender.publicationDate.split("T")[0]}",
                    style = MaterialTheme.typography.bodySmall
                )

                tender.value?.let {
                    Text(
                        text = "${tender.value} ${tender.currency ?: ""}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            tender.deadline?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Deadline: ${it.split("T")[0]}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
