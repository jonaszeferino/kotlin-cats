@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mycatsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mycatsapp.data.CatApiResponse
import com.example.mycatsapp.data.DogApiResponse
import com.example.mycatsapp.repository.CatRepository
import com.example.mycatsapp.ui.theme.MyCatsAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyCatsAppTheme {
                MainContent()
            }
        }
    }
}

@Composable
fun MainContent() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("My Cats App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        HomeScreen(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val selectedCategory = remember { mutableStateOf<String?>(null) }
    val catData = remember { mutableStateOf<CatApiResponse?>(null) }
    val dogData = remember { mutableStateOf<DogApiResponse?>(null) }
    val isLoading = remember { mutableStateOf(false) }
    val dogIsLoading = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val dogErrorMessage = remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val repository = remember { CatRepository() }
    
    // Função para buscar dados da API (imagem aleatória)
    fun fetchCatData() {
        isLoading.value = true
        errorMessage.value = null
        catData.value = null
        dogData.value = null
        dogErrorMessage.value = null
        
        scope.launch {
            try {
                val responseList = repository.searchCatImages(limit = 1)
                // Pega o primeiro item da lista (já que limit=1)
                if (responseList.isNotEmpty()) {
                    catData.value = responseList[0]
                } else {
                    errorMessage.value = "Nenhuma imagem encontrada"
                }
            } catch (e: Exception) {
                // Log do erro completo para debug
                Log.e("CatAPI", "Erro ao buscar dados", e)
                errorMessage.value = "Erro: ${e.javaClass.simpleName} - ${e.message ?: "Erro desconhecido"}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun fetchDogData() {
        dogIsLoading.value = true
        dogErrorMessage.value = null
        dogData.value = null
        catData.value = null
        errorMessage.value = null

        scope.launch {
            try {
                val response = repository.getRandomDogImage()
                dogData.value = response
            } catch (e: Exception) {
                Log.e("DogAPI", "Erro ao buscar dados", e)
                dogErrorMessage.value = "Erro: ${e.javaClass.simpleName} - ${e.message ?: "Erro desconhecido"}"
            } finally {
                dogIsLoading.value = false
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.QuestionAnswer,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Bem-vindo!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Dog & Cat API",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            }
        }

        Button(
            onClick = { fetchCatData() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading.value
        ) {
            if (isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Carregando...")
            } else {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Novo Gato")
            }
        }

        Button(
            onClick = { fetchDogData() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !dogIsLoading.value
        ) {
            if (dogIsLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Carregando cachorro...")
            } else {
                Icon(Icons.Default.Pets, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Novo Dog")
            }
        }

        errorMessage.value?.let { error ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = error,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }

        dogErrorMessage.value?.let { error ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = error,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }

        catData.value?.let { CatInfoCard(cat = it) }

        dogData.value?.let { DogInfoCard(dog = it) }
    }
}

@Composable
fun CatInfoCard(cat: CatApiResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Text(
                text = "Novo gato",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            // Imagem do gato
            AsyncImage(
                model = cat.url,
                contentDescription = "Imagem do gato",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            
            // Informações básicas
            Text(
                text = "ID: ${cat.id}",
                style = MaterialTheme.typography.bodyMedium
            )
            if (cat.width != null && cat.height != null) {
                Text(
                    text = "Dimensões: ${cat.width} x ${cat.height}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            // Informações da raça (se disponível)
            if (!cat.breeds.isNullOrEmpty()) {
                val breed = cat.breeds[0]
                
                Divider()
                
                breed.name?.let {
                    Text(
                        text = "Raça: $it",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                breed.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Características
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        breed.origin?.let { Text("Origem: $it", style = MaterialTheme.typography.bodySmall) }
                        breed.lifeSpan?.let { Text("Expectativa de vida: $it", style = MaterialTheme.typography.bodySmall) }
                        breed.weight?.metric?.let { Text("Peso: $it kg", style = MaterialTheme.typography.bodySmall) }
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Temperamento
                breed.temperament?.let {
                    Text(
                        text = "Temperamento: $it",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                
                // Níveis (adaptabilidade, energia, etc.)
                if (breed.adaptability != null || breed.energyLevel != null || breed.affectionLevel != null || breed.intelligence != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Características:",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                    breed.adaptability?.let { Text("Adaptabilidade: $it/5", style = MaterialTheme.typography.bodySmall) }
                    breed.energyLevel?.let { Text("Nível de energia: $it/5", style = MaterialTheme.typography.bodySmall) }
                    breed.affectionLevel?.let { Text("Nível de afeto: $it/5", style = MaterialTheme.typography.bodySmall) }
                    breed.intelligence?.let { Text("Inteligência: $it/5", style = MaterialTheme.typography.bodySmall) }
                }
            }
        }
    }
}

@Composable
fun DogInfoCard(dog: DogApiResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Novo Dog",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            AsyncImage(
                model = dog.message,
                contentDescription = "Imagem de cachorro",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Status: ${dog.status}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MyCatsAppTheme {
        HomeScreen()
    }
}
