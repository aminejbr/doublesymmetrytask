package com.aminejbr.doublesymmetrytask.presentation.music_list.composables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.aminejbr.doublesymmetrytask.R
import com.aminejbr.doublesymmetrytask.common.LoadingSpinner
import com.aminejbr.doublesymmetrytask.presentation.music_list.MusicListState
import com.aminejbr.doublesymmetrytask.presentation.music_list.MusicsViewModel
import com.aminejbr.doublesymmetrytask.ui.theme.AppBarBackgroundColor
import com.aminejbr.doublesymmetrytask.ui.theme.HintTextColor
import com.aminejbr.doublesymmetrytask.ui.theme.SearchBarColor
import kotlin.math.max

@Composable
fun MusicAppBar(
    scrollOffset: Float,
    viewModel: MusicsViewModel
) {

    val appBarTopPaddingCollapsed = 54.dp
    val appBarTopPaddingExpanded = 54.dp

    val appBarTitleSizeCollapsed = 17f
    val appBarTitleSizeExpanded = 32f

    val appBarTitleBottomCollapsed = 12f
    val appBarTitleBottomExpanded = 14f

    val appBarTitleStartCollapsed = 0f
    val appBarTitleStartExpanded = 16f

    val searchBarHeightCollapsed = 0f
    val searchBarHeightExpanded = 36f

    val searchBarBottomPaddingCollapsed = 0f
    val searchBarBottomPaddingExpanded = 10f

    val appBarTopPadding by animateDpAsState(targetValue = max(appBarTopPaddingCollapsed, appBarTopPaddingExpanded * scrollOffset))
    val appBarTitleSize = max(appBarTitleSizeCollapsed, appBarTitleSizeExpanded * scrollOffset).toInt()
    val appBarTitleBottomPadding = max(appBarTitleBottomCollapsed, appBarTitleBottomExpanded * scrollOffset).toInt()
    val appBarTitleStartPadding = max(appBarTitleStartCollapsed, appBarTitleStartExpanded * scrollOffset).toInt()
    val searchBarHeight = max(searchBarHeightCollapsed, searchBarHeightExpanded * scrollOffset).toInt()
    val searchBarBottomPadding = max(searchBarBottomPaddingCollapsed, searchBarBottomPaddingExpanded * scrollOffset).toInt()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = AppBarBackgroundColor)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = BiasAlignment(calculateHorizontalBias(scrollOffset), 0f)
        ) {
            Text(
                text = stringResource(R.string.discover),
                color = Color.White,
                fontSize = appBarTitleSize.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = appBarTitleStartPadding.dp, top = appBarTopPadding, bottom = appBarTitleBottomPadding.dp)
            )
        }
        SearchBar(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = searchBarBottomPadding.dp)
                .fillMaxWidth()
                .height(searchBarHeight.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(SearchBarColor, RectangleShape)
                .padding(horizontal = 10.dp, vertical = 8.dp),
            hint = stringResource(R.string.search),
            viewModel.uiState
        ) {
            viewModel.searchMusic(it)
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier,
    hint: String = "",
    searchLoadingState: State<MusicListState>,
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (searchLoadingState.value.isLoadingSearch)
            LoadingSpinner()
        else
            Image(painter = painterResource(id = R.drawable.ic_search), contentDescription = "Search")
        Spacer(modifier = Modifier.width(8.dp))
        Box {
            BasicTextField(
                value = text,
                onValueChange = {
                    isHintDisplayed = it.isBlank()
                    text = it
                    onSearch(it)
                },
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
            )
            if (isHintDisplayed) {
                Text(
                    text = hint,
                    color = HintTextColor
                )
            }
        }
    }
}

fun calculateHorizontalBias(scrollOffset: Float): Float {
    return if (scrollOffset >= 0f) {
        -1f + (1f - scrollOffset)
    }
    else
        0f
}