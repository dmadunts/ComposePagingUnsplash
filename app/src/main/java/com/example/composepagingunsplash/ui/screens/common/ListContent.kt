package com.example.composepagingunsplash.ui.screens.common

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.example.composepagingunsplash.R
import com.example.composepagingunsplash.data.models.remote.Links
import com.example.composepagingunsplash.data.models.remote.UnsplashImage
import com.example.composepagingunsplash.data.models.remote.Urls
import com.example.composepagingunsplash.data.models.remote.User

@Composable
fun ListContent(items: LazyPagingItems<UnsplashImage>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = items, key = { item: UnsplashImage -> item.id }) { unsplashImage ->
            unsplashImage?.let { UnsplashItem(it) }
        }
    }
}

@Composable
fun UnsplashItem(
    unsplashImage: UnsplashImage
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader(context)
    imageLoader
        .newBuilder()
        .crossfade(true)
        .error(R.drawable.ic_placeholder)
        .placeholder(R.drawable.ic_placeholder)
        .build()

    val painter =
        rememberAsyncImagePainter(model = unsplashImage.urls.regular, imageLoader = imageLoader)

    Box(modifier = Modifier
        .clickable {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://unsplash.com/@${unsplashImage.user.username}?utm_source=DemoApp&utm_medium=referral")
            )
            startActivity(context, browserIntent, null)
        }
        .fillMaxWidth()
        .height(300.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = painter,
            contentDescription = "Unsplash Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Surface(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .height(40.dp)
                .fillMaxWidth(),
            color = Color.Black
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .height(40.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Photo by ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                            append(unsplashImage.user.username)
                        }
                        append(" on Unsplash")
                    },
                    color = Color.White,
                    fontSize = MaterialTheme.typography.caption.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                LikeCounter(
                    modifier = Modifier.weight(3f),
                    painter = painterResource(id = R.drawable.ic_heart),
                    likes = "${unsplashImage.likes}"
                )
            }
        }
    }
}

@Composable
fun LikeCounter(modifier: Modifier, painter: Painter, likes: String) {
    Row(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Icon(painter = painter, contentDescription = "Heart Icon", tint = Color.Red)
        Spacer(Modifier.width(6.dp))
        Text(
            text = likes,
            color = Color.White,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun UnsplashImagePreview() {
    UnsplashItem(
        unsplashImage = UnsplashImage(
            id = "1",
            urls = Urls(regular = ""),
            likes = 100,
            user = User(username = "Stevdza-San", links = Links(html = ""))
        )
    )
}
