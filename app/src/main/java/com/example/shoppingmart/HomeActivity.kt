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
    "🔥 Hot Deals Today!",
    "👗 New Arrivals",
    "🍕 Fresh Food Items",
    "⚡ Flash Sale 50% Off"
)

@Composable
fun HomeScreen(onProductClick: () -> Unit) {
    var currentBanner by remember { mutableIntStateOf(0) }
    val pink = Color(0xFFE91E63)

    // Auto-slide every 1 second
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentBanner = (currentBanner + 1) % bannerColors.size
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5))) {

        // Top Bar
        Box(
            modifier = Modifier.fillMaxWidth().background(pink).padding(16.dp)
        ) {
            Text("ShoppingMart", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        // Sliding Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(bannerColors[currentBanner]),
            contentAlignment = Alignment.Center
        ) {
            Text(bannerTexts[currentBanner], fontSize = 22.sp,
                fontWeight = FontWeight.Bold, color = Color.White)
        }

        // Dots indicator
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            bannerColors.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (index == currentBanner) 10.dp else 6.dp)
                        .clip(CircleShape)
                        .background(if (index == currentBanner) pink else Color.LightGray)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Categories
        Text("Categories", fontSize = 18.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
            items(4) { index ->
                val labels = listOf("Clothes", "Food", "Electronics", "Books")
                val colors = listOf(Color(0xFFFF8A80), Color(0xFFFFD180),
                    Color(0xFF80D8FF), Color(0xFFCCFF90))
                Box(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(colors[index])
                        .clickable { onProductClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(labels[index], fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Featured Products
        Text("Featured Products", fontSize = 18.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
            items(5) { index ->
                val names = listOf("T-Shirt", "Pizza", "Headphones", "Novel", "Sneakers")
                val prices = listOf("$15", "$8", "$49", "$12", "$60")
                Card(
                    modifier = Modifier.padding(end = 12.dp).width(130.dp).clickable { onProductClick() },
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(80.dp)
                                .clip(RoundedCornerShape(8.dp)).background(Color(0xFFEEEEEE)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🛍️", fontSize = 32.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(names[index], fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(prices[index], color = pink, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}