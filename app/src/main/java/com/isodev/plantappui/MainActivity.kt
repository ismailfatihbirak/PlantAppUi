package com.isodev.plantappui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isodev.plantappui.ui.theme.PlantAppUiTheme
import kotlin.math.absoluteValue
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                scrim = getResources().getColor(R.color.gray),
            ),
            navigationBarStyle = SystemBarStyle.light(
                scrim = getResources().getColor(R.color.gray),
                darkScrim = getResources().getColor(R.color.gray)
            )
        )
        setContent {
            PlantAppUiTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(modifier: Modifier) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.gray))
    ) {
        Image(
            painter = painterResource(id = R.drawable.lets_make),
            contentDescription = "",
            modifier = Modifier.padding(start = 20.dp, top = 30.dp)
        )
        SelectableItemsList()
        Image(
            painter = painterResource(id = R.drawable.plant_collections),
            contentDescription = "",
            modifier = Modifier.padding(start = 20.dp, top = 30.dp)
        )
        PlantPager(pagerState = pagerState)
        NavigationBar()
    }
}

@Composable
fun SelectableItemsList() {
    val items = listOf("Plants", "Flowers", "Cacti", "Herbs", "Bonsai", "Hercai")
    var selectedIndex = remember { mutableStateOf(0) }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(start = 20.dp, top = 30.dp)
    ) {
        items(items.size) { index ->
            Box(
                modifier = Modifier
                    .size(90.dp, 46.dp)
                    .clip(RoundedCornerShape(60.dp))
                    .background(color = colorResource(R.color.white))
                    .clickable { selectedIndex.value = index }
            ) {
                Box(
                    modifier = Modifier
                        .size(74.dp, 30.dp)
                        .clip(RoundedCornerShape(60.dp))
                        .background(
                            color = if (selectedIndex.value == index) {
                                colorResource(R.color.green)
                            } else {
                                colorResource(R.color.white)
                            }
                        )
                        .align(Alignment.Center)
                ) {
                    Text(
                        text = items[index],
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlantPager(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    HorizontalPager(
        state = pagerState,
        pageSpacing = (-100).dp,
        contentPadding = PaddingValues(start = 54.dp, end = 54.dp, top = 30.dp),
        modifier = modifier
    ) { page ->
        Box(
            Modifier
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue
                    val rotationAngle = if (page < pagerState.currentPage) {
                        lerp(start = -10f, stop = 0f, fraction = 1f - pageOffset.coerceIn(0f, 1f))
                    } else {
                        lerp(start = 10f, stop = 0f, fraction = 1f - pageOffset.coerceIn(0f, 1f))
                    }

                    rotationZ = rotationAngle

                    val scale = lerp(
                        start = 0.70f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                    scaleX = scale
                    scaleY = scale
                }
                .zIndex(if (page == pagerState.currentPage) 1f else 0f)
                .size(325.dp, 464.dp)
                .blur(
                    radius = if (page == pagerState.currentPage) 0.dp else 5.dp
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(42.dp))
                    .background(color = colorResource(R.color.white))
            ) {
                Box(
                    modifier = Modifier
                        .padding(all = 20.dp)
                        .clip(RoundedCornerShape(23.dp))
                        .size(285.dp, 243.dp)
                        .align(Alignment.TopCenter)
                        .background(color = colorResource(R.color.gray_card))

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.plant_image),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Column(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("The Potted Head", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                    Text(
                        text = "Perfect for beginners or anyone looking\nfor an easy-to-care-for plant",
                        fontSize = 14.sp,
                        color = Color(0xFF8B8B8B),
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 20.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Row(
                            modifier = Modifier.size(215.dp, 40.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.bag),
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Text(
                                text = "Add to Cart",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.size(35.dp))
                            Text(
                                text = "$50.00",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}

@Composable
fun NavigationBar(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.padding(top = 15.dp)) {
        Image(painter = painterResource(R.drawable.rectangle_1), contentDescription = "")
        Image(
            painter = painterResource(R.drawable.group_14),
            contentDescription = "",
            modifier = Modifier.align(
                Alignment.CenterStart
            ).padding(start = 20.dp)
        )
        Image(
            painter = painterResource(R.drawable.image),
            contentDescription = "",
            modifier = Modifier
                .size(109.dp, 48.dp)
                .align(
                    Alignment.CenterEnd
                ).padding(end = 20.dp),
            )
    }
}

