package com.example.shoppingmart

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingmart.ui.theme.ShoppingMartTheme

data class Product(
    val id: Int,
    val name: String,
    val price: String,
    val emoji: String,
    val category: String,
    val description: String
)

val sampleProducts = listOf(
    Product(1, "T-Shirt", "$15", "👕", "Clothes", "Comfortable cotton t-shirt, available in multiple colors."),
    Product(2, "Jeans", "$40", "👖", "Clothes", "Classic denim jeans with a modern slim fit."),
    Product(3, "Pizza", "$8", "🍕", "Food", "Freshly baked pizza with your choice of toppings."),
    Product(4, "Burger", "$6", "🍔", "Food", "Juicy beef burger with lettuce, tomato and cheese."),
    Product(5, "Headphones", "$49", "🎧", "Electronics", "Wireless headphones with noise cancellation."),
    Product(6, "Phone", "$299", "📱", "Electronics", "Latest smartphone with high resolution camera."),
    Product(7, "Novel", "$12", "📚", "Books", "Bestselling fiction novel, great for all ages."),
    Product(8, "Sneakers", "$60", "👟", "Clothes", "Stylish sneakers perfect for everyday wear."),
    Product(9, "Sushi", "$14", "🍣", "Food", "Fresh sushi rolls made with premium ingredients."),
    Product(10, "Laptop", "$799", "💻", "Electronics", "Powerful laptop for work and gaming.")
)

class ProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingMartTheme {
                ProductScreen(
                    onProductClick = { product ->
                        val intent = Intent(this, ProductDetailActivity::class.java).apply {
                            putExtra("name", product.name)
                            putExtra("price", product.price)
                            putExtra("emoji", product.emoji)
                            putExtra("description", product.description)
                        }
                        startActivity(intent)
                    },
                    onBackClick = { finish() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(onProductClick: (Product) -> Unit, onBackClick: () -> Unit) {
    val pink = Color(0xFFE91E63)
    var searchQuery by remember { mutableStateOf("") }

    val filtered = sampleProducts.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.category.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5))) {

        // Top Bar
        Box(modifier = Modifier.fillMaxWidth().background(pink).padding(16.dp)) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.align(Alignment.CenterStart).clickable { onBackClick() })
            Text("Products", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                color = Color.White, modifier = Modifier.align(Alignment.Center))
        }

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search products...") },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(12.dp)
        )

        // Product Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filtered) { product ->
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { onProductClick(product) },
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(90.dp)
                                .clip(RoundedCornerShape(8.dp)).background(Color(0xFFEEEEEE)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(product.emoji, fontSize = 40.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(product.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(product.price, color = pink, fontSize = 14.sp)
                        Text(product.category, fontSize = 11.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}