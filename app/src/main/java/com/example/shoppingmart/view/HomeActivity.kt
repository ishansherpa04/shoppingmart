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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingmart.view.ProductActivity
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

    Column(modifier = Modifier.Companion.fillMaxSize().background(Color(0xFFF5F5F5))) {

        // Top Bar
        Box(
            modifier = Modifier.Companion.fillMaxWidth().background(pink).padding(16.dp)
        ) {
            Text(
                "ShoppingMart",
                fontSize = 20.sp,
                fontWeight = FontWeight.Companion.Bold,
                color = Color.Companion.White
            )
        }

        // Sliding Banner
        Box(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .height(180.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(bannerColors[currentBanner]),
            contentAlignment = Alignment.Companion.Center
        ) {
            Text(
                bannerTexts[currentBanner], fontSize = 22.sp,
                fontWeight = FontWeight.Companion.Bold, color = Color.Companion.White
            )
        }

        // Dots indicator
        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            bannerColors.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier.Companion
                        .padding(4.dp)
                        .size(if (index == currentBanner) 10.dp else 6.dp)
                        .clip(CircleShape)
                        .background(if (index == currentBanner) pink else Color.Companion.LightGray)
                )
            }
        }

        Spacer(modifier = Modifier.Companion.height(16.dp))

        // Categories
        Text(
            "Categories", fontSize = 18.sp, fontWeight = FontWeight.Companion.Bold,
            modifier = Modifier.Companion.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.Companion.height(8.dp))

        LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
            items(4) { index ->
                val labels = listOf("Clothes", "Food", "Electronics", "Books")
                val colors = listOf(
                    Color(0xFFFF8A80), Color(0xFFFFD180),
                    Color(0xFF80D8FF), Color(0xFFCCFF90)
                )
                Box(
                    modifier = Modifier.Companion
                        .padding(end = 12.dp)
                        .size(80.dp)
                        .clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                        .background(colors[index])
                        .clickable { onProductClick() },
                    contentAlignment = Alignment.Companion.Center
                ) {
                    Text(labels[index], fontSize = 12.sp, fontWeight = FontWeight.Companion.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.Companion.height(16.dp))

        // Featured Products
        Text(
            "Featured Products", fontSize = 18.sp, fontWeight = FontWeight.Companion.Bold,
            modifier = Modifier.Companion.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.Companion.height(8.dp))

        LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
            items(5) { index ->
                val names = listOf("T-Shirt", "Pizza", "Headphones", "Novel", "Sneakers")
                val prices = listOf("$15", "$8", "$49", "$12", "$60")
                Card(
                    modifier = Modifier.Companion.padding(end = 12.dp).width(130.dp)
                        .clickable { onProductClick() },
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.Companion.padding(12.dp)) {
                        Box(
                            modifier = Modifier.Companion.fillMaxWidth().height(80.dp)
                                .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                                .background(Color(0xFFEEEEEE)),
                            contentAlignment = Alignment.Companion.Center
                        ) {
                            Text("🛍️", fontSize = 32.sp)
                        }
                        Spacer(modifier = Modifier.Companion.height(8.dp))
                        Text(names[index], fontWeight = FontWeight.Companion.Bold, fontSize = 14.sp)
                        Text(prices[index], color = pink, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}