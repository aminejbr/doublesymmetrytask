package com.aminejbr.doublesymmetrytask.presentation.music_list.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.aminejbr.doublesymmetrytask.R
import com.aminejbr.doublesymmetrytask.domain.model.Music
import com.aminejbr.doublesymmetrytask.ui.theme.MusicBgGradientEndColor
import com.aminejbr.doublesymmetrytask.ui.theme.MusicBgGradientStartColor

@Composable
fun MusicItem(
    music: Music,
    modifier: Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
    ) {
        AsyncImage(
            model = music.image,
            contentDescription = music.title,
            modifier = Modifier
                .fillMaxWidth()
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MusicBgGradientStartColor,
                        MusicBgGradientEndColor
                    )
                )
            )
        )

        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 8.dp, top = 8.dp)
                .background(Color.White.copy(alpha = 0.45f), CircleShape)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_listener), contentDescription = "listener")
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = music.listenerCount,
                fontSize = 11.sp,
                color = Color.Black
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 8.dp, bottom = 8.dp)
                .align(Alignment.BottomStart)
        ) {
            Text(
                text = music.title,
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = music.genres,
                fontSize = 12.sp,
                color = Color.White
            )
        }
    }
}