package com.example.shoppingmart

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import kotlinx.coroutines.delay

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingMartTheme {
                HomeScreen(
                    onProductClick = {
                        startActivity(Intent(this, ProductActivity::class.java))
                    }
                )
            }
        }
    }
}

val bannerColors = listOf(
    Color(0xFFE91E63),
    Color(0xFF9C27B0),
    Color(0xFF2196F3),
    Color(0xFF4CAF50)
)

val bannerTexts = listOf(
    "🛍️ Summer Sale — Up to 50% Off!",
    "👗 New Arrivals Just Dropped!",
    "🚀 Free Shipping on Orders $30+",
    "🍕 Fresh Food Deals Every Day!"
)

@Composable
fun HomeScreen(onProductClick: () -> Unit) {
    val pink = Color(0xFFE91E63)
    var currentBanner by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            currentBanner = (currentBanner + 1) % bannerColors.size
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5))) {

        // Top Bar
        Box(
            modifier = Modifier.fillMaxWidth().background(pink).padding(16.dp)
        ) {
            Text("ShoppingMart", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        // Sliding Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(bannerColors[currentBanner]),
            contentAlignment = Alignment.Center
        ) {
            Text(bannerTexts[currentBanner], color = Color.White,
                fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        // Banner dots
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            bannerColors.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (index == currentBanner) 12.dp else 8.dp)
                        .clip(CircleShape)
                        .background(if (index == currentBanner) pink else Color.Gray)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Categories
        Text("  Categories", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
            val categories = listOf("All", "Clothes", "Food", "Electronics", "Shoes")
            items(categories.size) { i ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (i == 0) pink else Color.White)
                        .clickable { }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(categories[i], color = if (i == 0) Color.White else Color.Black)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Products grid
        Text("  Featured Products", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        val products = listOf("👕 T-Shirt", "👟 Sneakers", "🍕 Pizza", "👗 Dress", "📱 Phone", "🎧 Headphones")
        val prices = listOf("$19.99", "$59.99", "$12.99", "$39.99", "$299.99", "$49.99")

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            for (row in 0 until 3) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    for (col in 0..1) {
                        val index = row * 2 + col
                        Card(
                            modifier = Modifier.weight(1f).clickable { onProductClick() },
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(products[index], fontSize = 32.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(products[index].drop(3), fontWeight = FontWeight.Medium)
                                Text(prices[index], color = pink, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}