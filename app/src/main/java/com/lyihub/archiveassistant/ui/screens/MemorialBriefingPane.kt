package com.lyihub.archiveassistant.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lyihub.archiveassistant.R
import com.lyihub.archiveassistant.ui.theme.ImperialBronze
import com.lyihub.archiveassistant.ui.theme.ImperialCinnabar
import com.lyihub.archiveassistant.ui.theme.ImperialIvory
import com.lyihub.archiveassistant.ui.theme.ImperialLightGold
import com.lyihub.archiveassistant.ui.theme.ImperialParchment
import com.lyihub.archiveassistant.ui.theme.ImperialUmber
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

private const val MemorialCoverAspect = 10f / 22f
private const val MemorialWheelItemCount = 24
private const val MemorialActiveIndex = 0

@Composable
fun MemorialBriefingPane(
    pendingCount: Int,
    onOpenMemorialDemo: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(ImperialIvory)
            .clickable(onClick = onOpenMemorialDemo),
    ) {
        val expanded = maxWidth >= 620.dp
        Image(
            painter = painterResource(id = R.drawable.memorial_xuan_paper),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.36f,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            ImperialIvory.copy(alpha = 0.1f),
                            ImperialParchment.copy(alpha = 0.45f),
                            ImperialIvory.copy(alpha = 0.82f),
                        ),
                        center = Offset.Infinite,
                        radius = 900f,
                    ),
                ),
        )
        MemorialCoverWheel(
            coverResources = MemorialCoverResources,
            modifier = Modifier.fillMaxSize(),
        )
        BriefingCopy(
            pendingCount = pendingCount,
            expanded = expanded,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(if (expanded) 30.dp else 20.dp),
        )
        RingCenterHint(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = if (expanded) 48.dp else 24.dp),
        )
    }
}

@Composable
private fun MemorialCoverWheel(
    coverResources: List<Int>,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier) {
        val expanded = maxWidth >= 620.dp
        val panelMin = min(maxWidth.value, maxHeight.value).dp
        val radius = panelMin * if (expanded) 1.12f else 1.06f
        val centerX = maxWidth * if (expanded) 1.28f else 1.22f
        val centerY = maxHeight * if (expanded) 0.98f else 0.94f
        val cardWidth = if (expanded) 62.dp else 48.dp
        val startDegrees = 225f
        val stepDegrees = 360f / MemorialWheelItemCount

        repeat(MemorialWheelItemCount) { index ->
            if (index == MemorialActiveIndex) return@repeat
            val degrees = startDegrees + index * stepDegrees
            MemorialWheelCover(
                resId = coverResources[index % coverResources.size],
                index = index,
                degrees = degrees,
                centerX = centerX,
                centerY = centerY,
                radius = radius,
                width = cardWidth,
                active = false,
                modifier = Modifier.fillMaxSize(),
            )
        }
        MemorialWheelCover(
            resId = coverResources[MemorialActiveIndex % coverResources.size],
            index = MemorialActiveIndex,
            degrees = startDegrees + MemorialActiveIndex * stepDegrees,
            centerX = centerX,
            centerY = centerY,
            radius = radius,
            width = cardWidth * 1.28f,
            active = true,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun MemorialWheelCover(
    resId: Int,
    index: Int,
    degrees: Float,
    centerX: Dp,
    centerY: Dp,
    radius: Dp,
    width: Dp,
    active: Boolean,
    modifier: Modifier = Modifier,
) {
    val radians = Math.toRadians(degrees.toDouble())
    val height = width / MemorialCoverAspect
    val x = centerX + radius * cos(radians).toFloat() - width / 2f
    val y = centerY + radius * sin(radians).toFloat() - height / 2f
    Box(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .offset(x = x, y = y)
                .width(width)
                .aspectRatio(MemorialCoverAspect)
                .graphicsLayer(rotationZ = degrees + 90f)
                .background(ImperialParchment, RoundedCornerShape(if (active) 5.dp else 3.dp))
                .border(
                    width = if (active) 1.4.dp else 0.8.dp,
                    color = if (active) ImperialCinnabar.copy(alpha = 0.68f) else ImperialBronze.copy(alpha = 0.48f),
                    shape = RoundedCornerShape(if (active) 5.dp else 3.dp),
                ),
        ) {
            Image(
                painter = painterResource(id = resId),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.White.copy(alpha = 0.05f),
                                ImperialUmber.copy(alpha = if (active) 0.08f else 0.13f),
                            ),
                        ),
                    ),
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = if (active) 5.dp else 3.dp,
                        color = ImperialIvory.copy(alpha = if (active) 0.24f else 0.18f),
                        shape = RoundedCornerShape(if (active) 5.dp else 3.dp),
                    ),
            )
            MemorialCoverLabel(
                active = active,
                index = index,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

@Composable
private fun MemorialCoverLabel(
    active: Boolean,
    index: Int,
    modifier: Modifier = Modifier,
) {
    val labelWidth = if (active) 0.5f else 0.48f
    val labelHeight = if (active) 0.58f else 0.56f
    BoxWithConstraints(modifier = modifier) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .width(maxWidth * labelWidth)
                .aspectRatio(MemorialCoverAspect / labelHeight * labelWidth)
                .background(ImperialIvory.copy(alpha = if (active) 0.88f else 0.78f), RoundedCornerShape(2.dp))
                .border(1.dp, ImperialBronze.copy(alpha = if (active) 0.76f else 0.52f), RoundedCornerShape(2.dp))
                .padding(if (active) 5.dp else 4.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(0.8.dp, ImperialBronze.copy(alpha = if (active) 0.66f else 0.46f), RoundedCornerShape(1.dp)),
            )
            Image(
                painter = painterResource(id = R.drawable.memorial_cover_corner),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(if (active) 11.dp else 8.dp),
                alpha = if (active) 0.72f else 0.5f,
                colorFilter = ColorFilter.tint(ImperialCinnabar.copy(alpha = if (active) 0.7f else 0.46f)),
            )
            Image(
                painter = painterResource(id = R.drawable.memorial_cover_corner),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(if (active) 11.dp else 8.dp)
                    .graphicsLayer(rotationZ = 90f),
                alpha = if (active) 0.72f else 0.5f,
                colorFilter = ColorFilter.tint(ImperialCinnabar.copy(alpha = if (active) 0.7f else 0.46f)),
            )
            Image(
                painter = painterResource(id = R.drawable.memorial_cover_corner),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(if (active) 11.dp else 8.dp)
                    .graphicsLayer(rotationZ = 180f),
                alpha = if (active) 0.72f else 0.5f,
                colorFilter = ColorFilter.tint(ImperialCinnabar.copy(alpha = if (active) 0.7f else 0.46f)),
            )
            Image(
                painter = painterResource(id = R.drawable.memorial_cover_corner),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .size(if (active) 11.dp else 8.dp)
                    .graphicsLayer(rotationZ = 270f),
                alpha = if (active) 0.72f else 0.5f,
                colorFilter = ColorFilter.tint(ImperialCinnabar.copy(alpha = if (active) 0.7f else 0.46f)),
            )
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(if (active) 2.dp else 1.dp),
            ) {
                Text(
                    text = "奏\n章",
                    style = if (active) MaterialTheme.typography.titleMedium else MaterialTheme.typography.labelMedium,
                    color = ImperialUmber,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = if (active) MaterialTheme.typography.titleMedium.lineHeight else MaterialTheme.typography.labelMedium.lineHeight,
                )
                Text(
                    text = if (active) "甲辰" else ((index % 9) + 1).toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = ImperialCinnabar.copy(alpha = if (active) 0.82f else 0.62f),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun BriefingCopy(
    pendingCount: Int,
    expanded: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(if (expanded) 10.dp else 8.dp),
    ) {
        Text(
            text = "奏章",
            style = if (expanded) MaterialTheme.typography.displayLarge else MaterialTheme.typography.displayMedium,
            color = ImperialUmber,
            fontWeight = FontWeight.Black,
        )
        Text(
            text = "门下既审，呈于御前",
            style = MaterialTheme.typography.titleMedium,
            color = ImperialUmber.copy(alpha = 0.82f),
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = "今日尚有 $pendingCount 封待批。轻触此页，展开奏章堆叠，准、驳、留中皆可一笔批下。",
            style = MaterialTheme.typography.bodyMedium,
            color = ImperialUmber.copy(alpha = 0.72f),
            modifier = Modifier.fillMaxWidth(if (expanded) 0.44f else 0.62f),
        )
    }
}

@Composable
private fun RingCenterHint(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .size(154.dp)
            .background(ImperialIvory.copy(alpha = 0.72f))
            .border(1.dp, ImperialParchment)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.dashboard_placeholder),
            contentDescription = null,
            modifier = Modifier.size(42.dp),
            colorFilter = ColorFilter.tint(ImperialBronze),
            alpha = 0.82f,
        )
        Text(
            text = "批阅",
            style = MaterialTheme.typography.titleLarge,
            color = ImperialUmber,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "奏折在此",
            style = MaterialTheme.typography.bodySmall,
            color = ImperialUmber.copy(alpha = 0.64f),
            textAlign = TextAlign.Center,
        )
    }
}
