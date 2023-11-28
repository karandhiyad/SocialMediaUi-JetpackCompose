package com.whyte.socialmediaui

import android.net.Uri
import android.provider.MediaStore.Video
import android.widget.VideoView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

@Preview
@Composable
fun PreviewItem() {
    LazyColumn(content = {
        items(getCategoryList()) { item ->
            CardViewSample(
                imageProfile = item.imageProfile,
                name = item.name,
                contentText = item.contentText,
                imageContent = item.imageContent,
                contentVideoUrl = item.contentVideoUrl
            )
        }
    })

//    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
//        getCategoryList().map { item ->
//            ListViewItemSample(imageId = item.img, name = item.name, job = item.job)
//        }
//    }
}

@Composable
fun CardViewSample(
    imageProfile: Int,
    name: String,
    contentText: String,
    imageContent: Int,
    contentVideoUrl: String
) {
    Card(elevation = 8.dp, modifier = Modifier.padding(4.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Title(imageProfile, name)
            Content(imageContent, contentText, contentVideoUrl)

        }
    }
}

@Composable
fun Title(imageId: Int, name: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(4.dp)
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "",
            modifier = Modifier
                .size(32.dp)
                .padding(4.dp)
                .weight(.2f)
        )
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(.8f),
            fontSize = 16.sp
        )
    }
}

@Composable
fun Content(imageContent: Int, content: String, contentVideoUrl: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (content != "") {
            Text(
                text = content,
                fontWeight = FontWeight.Thin,
                modifier = Modifier.padding(4.dp, 0.dp, 4.dp, 4.dp),
                fontSize = 12.sp
            )
        }
        if (imageContent != 0) {
            Image(
                painter = painterResource(id = imageContent),
                contentDescription = "",
                modifier = Modifier
                    .size(400.dp)
                    .padding(4.dp),
                contentScale = ContentScale.Crop
            )
        }
        if (contentVideoUrl != "") {
            Column(
                Modifier
                    .fillMaxSize()
                    .size(400.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // Fetching the Local Context
                val mContext = LocalContext.current

                // Declaring a string value
                // that stores raw video url
                val mVideoUrl = contentVideoUrl

//                // Declaring ExoPlayer
//                val mExoPlayer = remember(mContext) {
//                    ExoPlayer.Builder(mContext).build().apply {
//                        val dataSourceFactory = DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, mContext.packageName))
//                        val source = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
//                            Uri.parse(mVideoUrl))
//                        prepare(source)
//                    }
//                }
//
//                // Implementing ExoPlayer
//                AndroidView(factory = { context ->
//                    PlayerView(context).apply {
//                        player = mExoPlayer
//                    }
//                })

               val  exoPlayer = ExoPlayer.Builder(LocalContext.current)
                    .build()
                    .also { exoPlayer ->
                        val mediaItem = MediaItem.Builder()
                            .setUri(mVideoUrl)
                            .build()
                        exoPlayer.setMediaItem(mediaItem)
                        exoPlayer.prepare()
                    }



                DisposableEffect(
                    AndroidView(factory = {
                        StyledPlayerView(mContext).apply {
                            player = exoPlayer
                        }
                    })
                ) {
                    onDispose { exoPlayer.release() }
                }

//                exoPlayer.playWhenReady = true
            }
        }
    }
}




@Composable
fun ListViewItemSample(imageId: Int, name: String, job: String) {
    Card(elevation = 8.dp, modifier = Modifier.padding(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = "",
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
                    .weight(.2f)
            )
            ItemDescription(name, job, Modifier.weight(.8f))
        }
    }
}

@Composable
fun ItemDescription(name: String, job: String, modifier: Modifier) {
    Column(modifier = modifier)
    {
        Text(
            text = name,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = job,
            fontWeight = FontWeight.Thin,
            fontSize = 12.sp
        )
    }
}

data class Category(
    val imageProfile: Int,
    val name: String,
    val contentText: String,
    val imageContent: Int,
    val contentVideoUrl: String
)

fun getCategoryList(): MutableList<Category> {
    val list = mutableListOf<Category>()
    val url = "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4"
    list.add(
        Category(
            R.drawable.user,
            "Karan Dhiyad",
            "Hi, I am Professional Native Android Developer with 2 Years Experience. I published more than 5 apps on play store.",
            R.drawable.foodimage,
            url
        )
    )
    list.add(
        Category(
            R.drawable.user,
            "Karan Dhiyad",
            "Hi, I am Professional Native Android Developer with 2 Years Experience. I published more than 5 apps on play store.",
            0,
            url
        )
    )
    list.add(
        Category(
            R.drawable.user,
            "Karan Dhiyad",
            "",
            R.drawable.foodimage,
            url
        )
    )
    list.add(
        Category(
            R.drawable.user,
            "Karan Dhiyad",
            "Hi, I am Professional Native Android Developer with 2 Years Experience. I published more than 5 apps on play store.",
            R.drawable.foodimage,
            ""
        )
    )
    list.add(
        Category(
            R.drawable.user,
            "Karan Dhiyad",
            "Hi, I am Professional Native Android Developer with 2 Years Experience. I published more than 5 apps on play store.",
            0,
            url
        )
    )
    list.add(
        Category(
            R.drawable.user,
            "Karan Dhiyad",
            "Hi, I am Professional Native Android Developer with 2 Years Experience. I published more than 5 apps on play store.",
            R.drawable.foodimage,
            url
        )
    )
    list.add(
        Category(
            R.drawable.user,
            "Karan Dhiyad",
            "Hi, I am Professional Native Android Developer with 2 Years Experience. I published more than 5 apps on play store.",
            R.drawable.foodimage,
            ""
        )
    )
    list.add(
        Category(
            R.drawable.user,
            "Karan Dhiyad",
            "",
            0,
            url
        )
    )
    list.add(
        Category(
            R.drawable.user,
            "Karan Dhiyad",
            "Hi, I am Professional Native Android Developer with 2 Years Experience. I published more than 5 apps on play store.",
            R.drawable.foodimage,
            url
        )
    )
    list.add(
        Category(
            R.drawable.user,
            "Karan Dhiyad",
            "",
            0,
            url
        )
    )
    list.add(Category(R.drawable.user, "Karan Dhiyad", "", R.drawable.foodimage, ""))
    list.add(
        Category(
            R.drawable.user,
            "Karan Dhiyad",
            "Hi, I am Professional Native Android Developer with 2 Years Experience. I published more than 5 apps on play store.",
            0,
            url
        )
    )
    list.add(
        Category(
            R.drawable.user,
            "Karan Dhiyad",
            "Hi, I am Professional Native Android Developer with 2 Years Experience. I published more than 5 apps on play store.",
            R.drawable.foodimage,
            url
        )
    )
    list.add(
        Category(
            R.drawable.user,
            "Karan Dhiyad",
            "Hi, I am Professional Native Android Developer with 2 Years Experience. I published more than 5 apps on play store.",
            R.drawable.foodimage,
            url
        )
    )
    list.add(
        Category(
            R.drawable.user,
            "Karan Dhiyad",
            "Hi, I am Professional Native Android Developer with 2 Years Experience. I published more than 5 apps on play store.",
            R.drawable.foodimage,
            url
        )
    )
    list.add(
        Category(
            R.drawable.user,
            "Karan Dhiyad",
            "Hi, I am Professional Native Android Developer with 2 Years Experience. I published more than 5 apps on play store.",
            R.drawable.foodimage,
            url
        )
    )
    list.add(
        Category(
            R.drawable.user,
            "Karan Dhiyad",
            "Hi, I am Professional Native Android Developer with 2 Years Experience. I published more than 5 apps on play store.",
            R.drawable.foodimage,
            ""
        )
    )

    return list;
}