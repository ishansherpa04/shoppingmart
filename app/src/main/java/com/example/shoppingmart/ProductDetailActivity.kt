package com.example.shoppingmart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name = intent.getStringExtra("name") ?: "Product"
        val price = intent.getStringExtra("price") ?: "$0"
        val emoji = intent.getStringExtra("emoji") ?: "🛍️"
        val description = intent.getStringExtra("description") ?: ""

        setContent {
            ShoppingMartTheme {
                ProductDetailScreen(
                    name = name,
                    price = price,
                    emoji = emoji,
                    description = description,
                    onBackClick = { finish() }
                )
            }
        }
    }
}

@Composable
fun ProductDetailScreen(
    name: String,
    price: String,
    emoji: String,
    description: String,
    onBackClick: () -> Unit
) {
    val pink = Color(0xFFE91E63)
    var quantity by remember { mutableIntStateOf(1) }
    var selectedSize by remember { mutableStateOf("M") }
    var addedToCart by remember { mutableStateOf(false) }
    val sizes = listOf("XS", "S", "M", "L", "XL")

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5))) {

        // Top Bar
        Box(modifier = Modifier.fillMaxWidth().background(pink).padding(16.dp)) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.align(Alignment.CenterStart).clickable { onBackClick() })
            Text("Product Detail", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                color = Color.White, modifier = Modifier.align(Alignment.Center))
        }

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

            // Product Image
            Box(
                modifier = Modifier.fillMaxWidth().height(220.dp)
                    .clip(RoundedCornerShape(16.dp)).background(Color(0xFFEEEEEE)),
                contentAlignment = Alignment.Center
            ) {
                Text(emoji, fontSize = 80.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Name & Price
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(price, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = pink)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(description, fontSize = 14.sp, color = Color.Gray, lineHeight = 20.sp)

            Spacer(modifier = Modifier.height(16.dp))

            // Size selector
            Text("Size", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                sizes.forEach { size ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (selectedSize == size) pink else Color.White)
                            .clickable { selectedSize = size },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(size, fontSize = 12.sp,
                            color = if (selectedSize == size) Color.White else Color.Black,
                            fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quantity selector
            Text("Quantity", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = { if (quantity > 1) quantity-- },
                    colors = ButtonDefaults.buttonColors(containerColor = pink),
                    contentPadding = PaddingValues(horizontal = 16.dp)) {
                    Text("-", fontSize = 18.sp)
                }
                Text("$quantity", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Button(onClick = { quantity++ },
                    colors = ButtonDefaults.buttonColors(containerColor = pink),
                    contentPadding = PaddingValues(horizontal = 16.dp)) {
                    Text("+", fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Add to Cart Button
            Button(
                onClick = { addedToCart = true },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (addedToCart) Color(0xFF4CAF50) else pink),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    if (addedToCart) "✓ Added to Cart!" else "Add to Cart",
                    fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
            }
        }
    }
}