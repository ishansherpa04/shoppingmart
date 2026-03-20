package com.example.shoppingmart.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingmart.view.ProductDetailActivity
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

    Column(modifier = Modifier.Companion.fillMaxSize().background(Color(0xFFF5F5F5))) {

        // Top Bar
        Box(modifier = Modifier.Companion.fillMaxWidth().background(pink).padding(16.dp)) {
            Icon(
                Icons.Default.ArrowBack, contentDescription = "Back",
                tint = Color.Companion.White,
                modifier = Modifier.Companion.align(Alignment.Companion.CenterStart)
                    .clickable { onBackClick() })
            Text(
                "Products",
                fontSize = 20.sp,
                fontWeight = FontWeight.Companion.Bold,
                color = Color.Companion.White,
                modifier = Modifier.Companion.align(Alignment.Companion.Center)
            )
        }

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search products...") },
            modifier = Modifier.Companion.fillMaxWidth().padding(16.dp),
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
                    modifier = Modifier.Companion.fillMaxWidth()
                        .clickable { onProductClick(product) },
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.Companion.padding(12.dp),
                        horizontalAlignment = Alignment.Companion.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.Companion.fillMaxWidth().height(90.dp)
                                .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                                .background(Color(0xFFEEEEEE)),
                            contentAlignment = Alignment.Companion.Center
                        ) {
                            Text(product.emoji, fontSize = 40.sp)
                        }
                        Spacer(modifier = Modifier.Companion.height(8.dp))
                        Text(product.name, fontWeight = FontWeight.Companion.Bold, fontSize = 14.sp)
                        Text(product.price, color = pink, fontSize = 14.sp)
                        Text(product.category, fontSize = 11.sp, color = Color.Companion.Gray)
                    }
                }
            }
        }
    }
}