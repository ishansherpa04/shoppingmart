package com.example.shoppingmart.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

    Column(modifier = Modifier.Companion.fillMaxSize().background(Color(0xFFF5F5F5))) {

        // Top Bar
        Box(modifier = Modifier.Companion.fillMaxWidth().background(pink).padding(16.dp)) {
            Icon(
                Icons.Default.ArrowBack, contentDescription = "Back",
                tint = Color.Companion.White,
                modifier = Modifier.Companion.align(Alignment.Companion.CenterStart)
                    .clickable { onBackClick() })
            Text(
                "Product Detail",
                fontSize = 20.sp,
                fontWeight = FontWeight.Companion.Bold,
                color = Color.Companion.White,
                modifier = Modifier.Companion.align(Alignment.Companion.Center)
            )
        }

        Column(modifier = Modifier.Companion.fillMaxSize().padding(16.dp)) {

            // Product Image
            Box(
                modifier = Modifier.Companion.fillMaxWidth().height(220.dp)
                    .clip(RoundedCornerShape(16.dp)).background(Color(0xFFEEEEEE)),
                contentAlignment = Alignment.Companion.Center
            ) {
                Text(emoji, fontSize = 80.sp)
            }

            Spacer(modifier = Modifier.Companion.height(16.dp))

            // Name & Price
            Row(
                modifier = Modifier.Companion.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Text(name, fontSize = 24.sp, fontWeight = FontWeight.Companion.Bold)
                Text(price, fontSize = 24.sp, fontWeight = FontWeight.Companion.Bold, color = pink)
            }

            Spacer(modifier = Modifier.Companion.height(8.dp))

            // Description
            Text(description, fontSize = 14.sp, color = Color.Companion.Gray, lineHeight = 20.sp)

            Spacer(modifier = Modifier.Companion.height(16.dp))

            // Size selector
            Text("Size", fontSize = 16.sp, fontWeight = FontWeight.Companion.Bold)
            Spacer(modifier = Modifier.Companion.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                sizes.forEach { size ->
                    Box(
                        modifier = Modifier.Companion
                            .size(40.dp)
                            .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                            .background(if (selectedSize == size) pink else Color.Companion.White)
                            .clickable { selectedSize = size },
                        contentAlignment = Alignment.Companion.Center
                    ) {
                        Text(
                            size, fontSize = 12.sp,
                            color = if (selectedSize == size) Color.Companion.White else Color.Companion.Black,
                            fontWeight = FontWeight.Companion.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.Companion.height(16.dp))

            // Quantity selector
            Text("Quantity", fontSize = 16.sp, fontWeight = FontWeight.Companion.Bold)
            Spacer(modifier = Modifier.Companion.height(8.dp))
            Row(
                verticalAlignment = Alignment.Companion.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { if (quantity > 1) quantity-- },
                    colors = ButtonDefaults.buttonColors(containerColor = pink),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    Text("-", fontSize = 18.sp)
                }
                Text("$quantity", fontSize = 18.sp, fontWeight = FontWeight.Companion.Bold)
                Button(
                    onClick = { quantity++ },
                    colors = ButtonDefaults.buttonColors(containerColor = pink),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    Text("+", fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.Companion.weight(1f))

            // Add to Cart Button
            Button(
                onClick = { addedToCart = true },
                modifier = Modifier.Companion.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (addedToCart) Color(0xFF4CAF50) else pink
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
            ) {
                Text(
                    if (addedToCart) "✓ Added to Cart!" else "Add to Cart",
                    fontSize = 16.sp, fontWeight = FontWeight.Companion.Bold
                )
            }
        }
    }
}