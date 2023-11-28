package com.whyte.socialmediaui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.whyte.socialmediaui.firbaseRealtimeDb.ui.RealtimeScreen
import com.whyte.socialmediaui.ui.theme.SocialMediaUiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocialMediaUiTheme {
                val isInsert = remember {
                    mutableStateOf(false)
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        floatingActionButton = {
                            FloatingActionButton(onClick = {
                                isInsert.value = true

                            }) {
                                Icon(Icons.Default.Add, contentDescription = "")
                            }
                        }
                    ) {
                        RealtimeScreen(isInsert)
                    }
                }

//                ToolBar()
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun ToolBar(){
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "Whyte Ui")
                        },
                        backgroundColor = Color.LightGray,
                        navigationIcon = {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(Icons.Filled.Menu , contentDescription = "Menu")
                            }
                        },
                        actions = {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(Icons.Filled.Notifications , contentDescription = "Notification")
                            }
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(Icons.Filled.Search , contentDescription = "Search")
                            }
                        },
                    )
                }
//                floatingActionButton = {
//                    FloatingActionButton(onClick = { /*TODO*/ }) {
//                        IconButton(onClick = { /*TODO*/ }) {
//                            Icon(Icons.Filled.Add , contentDescription = "Add")
//                        }
//                    }
//                }
            ) {
                PreviewItem()
            }
    }

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
    fun CardViewSample(imageProfile: Int, name: String, contentText: String, imageContent: Int, contentVideoUrl: String) {
        Card(elevation = 8.dp, modifier = Modifier.padding(4.dp), shape = RoundedCornerShape(8.dp)) {
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
//                    .padding(4.dp)
                    .weight(.1f)
            )
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(.9f)
                    .padding(4.dp, 0.dp, 0.dp, 0.dp),
                fontSize = 16.sp
            )
        }
    }

    @Composable
    fun Content(imageContent: Int, content: String, contentVideoUrl: String) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(4.dp)) {
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
                        .size(400.dp)
                        .padding(4.dp),
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

                    val exoPlayer = ExoPlayer.Builder(LocalContext.current)
                        .build()
                        .also { exoPlayer ->
                            val mediaItem = MediaItem.Builder()
                                .setUri(mVideoUrl)
                                .build()
                            exoPlayer.setMediaItem(mediaItem)
                            exoPlayer.prepare()
                        }

                    var lifecycle by remember {
                        mutableStateOf(Lifecycle.Event.ON_CREATE)
                    }
                    val lifecycleOwner = LocalLifecycleOwner.current
                    DisposableEffect(lifecycleOwner) {
                        val observer = LifecycleEventObserver { _, event ->
                            lifecycle = event
                        }
                        lifecycleOwner.lifecycle.addObserver(observer)
                        onDispose {
                            lifecycleOwner.lifecycle.removeObserver(observer)
                        }
                    }

                    DisposableEffect(
                        AndroidView(factory = {
                            StyledPlayerView(mContext).apply {
                                player = exoPlayer
                            }
                        },
                            update = {
                                when (lifecycle) {
                                    Lifecycle.Event.ON_PAUSE -> {
                                        it.onPause()
                                        it.player?.pause()
                                    }
                                    Lifecycle.Event.ON_RESUME -> {
                                        it.onResume()
//                                        it.player?.play()
                                    }
                                    else -> Unit
                                }
                            }, modifier = Modifier.fillMaxSize()
                        )
                    ) {
                        onDispose { exoPlayer.release() }
                    }

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

}


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun SayHello(name: String = "Karan Desai") {
//    Text(text = "Hello $name")
//
//    Image(
//        painter = painterResource(id = R.drawable.food),
//        contentDescription = "Food",
//    )
//}

//@Preview(showSystemUi = true)
//@Composable
//fun PreviewFunction() {
//    Column {
//        ListViewItem(R.drawable.user, "Karan Dhiyad", "Android Developer")
//        ListViewItem(R.drawable.user, "Karan Dhiyad", "Android Developer")
//        ListViewItem(R.drawable.user, "Karan Dhiyad", "Android Developer")
//        ListViewItem(R.drawable.user, "Karan Dhiyad", "Android Developer")
//        ListViewItem(R.drawable.user, "Karan Dhiyad", "Android Developer")
//        ListViewItem(R.drawable.user, "Karan Dhiyad", "Android Developer")
//        ListViewItem(R.drawable.user, "Karan Dhiyad", "Android Developer")
//        ListViewItem(R.drawable.user, "Karan Dhiyad", "Android Developer")
//    }
//}

//@Composable
//fun ListViewItem(imageId: Int, name: String, job: String) {
//    Row(Modifier.padding(8.dp)) {
//        Image(
//            painter = painterResource(id = imageId),
//            contentDescription = "",
//            Modifier.size(40.dp)
//        )
//        Column(Modifier.padding(4.dp)) {
//            Text(text = name, fontWeight = FontWeight.Bold)
//            Text(text = job, fontWeight = FontWeight.Thin)
//        }
//
//    }
//}

//@Composable
//fun TextInput() {
//    val state = remember { mutableStateOf(""); }
//    TextField(
//        value = state.value,
//        onValueChange = {
//            state.value = it;
//        },
//        label = { (Text(text = "Enter Name")) }
//    )
//}

//@Composable
//fun Greeting(name: String) {
//    Text(text = "Hello $name!")
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    SocialMediaUiTheme {
//        Greeting("Android")
//    }
//}