package com.whyte.socialmediaui.firbaseRealtimeDb.ui

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.whyte.socialmediaui.R
import com.whyte.socialmediaui.firbaseRealtimeDb.RealtimeModelResponse
import com.whyte.socialmediaui.utils.ResultState
import com.whyte.socialmediaui.utils.showMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RealtimeScreen(
    isInsert: MutableState<Boolean>, viewModel: RealtimeViewModel = hiltViewModel()
) {

    val title = remember { mutableStateOf("") }
    val des = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val isDialog = remember { mutableStateOf(false) }
    val res = viewModel.res.value
    val isUpdate = remember { mutableStateOf(false) }

//    var photoUri: List<Uri?> = remember { mutableListOf(null) }
    var photoUri: Uri? by remember { mutableStateOf(null) }
    photoUri = null
    val launcherImages = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            //When the user has selected a photo, its URI is returned here
            photoUri = uri
            Log.e("photoUri", photoUri.toString())
        }

    var videoUri: Uri? by remember { mutableStateOf(null) }
    videoUri = null
    val launcherVideos = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        //When the user has selected a photo, its URI is returned here
        videoUri = uri
        Log.e("videoUri", videoUri.toString())
    }

    if (isInsert.value) {
        title.value = ""
        des.value = ""

        AlertDialog(properties = DialogProperties(usePlatformDefaultWidth = false),
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
            onDismissRequest = { isInsert.value = false }, text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = title.value,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = {
                            title.value = it
                        },
                        placeholder = {
                            Text(text = "Title")
                        })

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = des.value,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = {
                            des.value = it
                        },
                        placeholder = {
                            Text(text = "Description")
                        })

                    Spacer(modifier = Modifier.height(10.dp))

                    if (photoUri != null) {
                        ImageView(uri = photoUri.toString(), modifier = Modifier.size(200.dp))

//                        //Use Coil to display the selected image
//                        val painter = rememberAsyncImagePainter(
//                            ImageRequest
//                                .Builder(LocalContext.current)
//                                .data(data = photoUri)
//                                .build()
//                        )
//                        Image(
//                            painter = painter,
//                            contentDescription = null,
//                            modifier = Modifier
//                                .size(200.dp),
//                            contentScale = ContentScale.Crop
//                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))


                    if (videoUri != null){
                        Log.e("VideoUri", videoUri.toString())

                        VideoView(uri = videoUri.toString(), modifier = Modifier.size(200.dp).padding(4.dp))

//                        Box(
//                            Modifier
//                                .size(200.dp)
//                                .padding(4.dp),
//                        ) {
//
//                            // Fetching the Local Context
//                            val mContext = LocalContext.current
//
//                            // Declaring a string value
//                            // that stores raw video url
//                            val mVideoUrl = videoUri
//
////                // Declaring ExoPlayer
////                val mExoPlayer = remember(mContext) {
////                    ExoPlayer.Builder(mContext).build().apply {
////                        val dataSourceFactory = DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, mContext.packageName))
////                        val source = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
////                            Uri.parse(mVideoUrl))
////                        prepare(source)
////                    }
////                }
////
////                // Implementing ExoPlayer
////                AndroidView(factory = { context ->
////                    PlayerView(context).apply {
////                        player = mExoPlayer
////                    }
////                })
//
//                            val exoPlayer = ExoPlayer.Builder(LocalContext.current)
//                                .build()
//                                .also { exoPlayer ->
//                                    val mediaItem = MediaItem.Builder()
//                                        .setUri(mVideoUrl)
//                                        .build()
//                                    exoPlayer.setMediaItem(mediaItem)
//                                    exoPlayer.prepare()
//                                }
//
//                            var lifecycle by remember {
//                                mutableStateOf(Lifecycle.Event.ON_CREATE)
//                            }
//                            val lifecycleOwner = LocalLifecycleOwner.current
//                            DisposableEffect(lifecycleOwner) {
//                                val observer = LifecycleEventObserver { _, event ->
//                                    lifecycle = event
//                                }
//                                lifecycleOwner.lifecycle.addObserver(observer)
//                                onDispose {
//                                    lifecycleOwner.lifecycle.removeObserver(observer)
//                                }
//                            }
//
//                            DisposableEffect(
//                                AndroidView(factory = {
//                                    StyledPlayerView(mContext).apply {
//                                        player = exoPlayer
//                                    }
//                                },
//                                    update = {
//                                        when (lifecycle) {
//                                            Lifecycle.Event.ON_PAUSE -> {
//                                                it.onPause()
//                                                it.player?.pause()
//                                            }
//                                            Lifecycle.Event.ON_RESUME -> {
//                                                it.onResume()
////                                        it.player?.play()
//                                            }
//                                            else -> Unit
//                                        }
//                                    }, modifier = Modifier.fillMaxSize()
//                                )
//                            ) {
//                                onDispose { exoPlayer.release() }
//                            }
//
//                        }
                    }


                    Spacer(modifier = Modifier.height(10.dp))

                    Button(onClick = {
                        //On button press, launch the photo picker
                        launcherImages.launch(
                            PickVisualMediaRequest(
                                //Here we request only photos. Change this to .ImageAndVideo if
                                //you want videos too.
                                //Or use .VideoOnly if you only want videos.
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }) {
                        Text(text = "Select Photos")
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(onClick = {
                        //On button press, launch the photo picker
                        launcherVideos.launch(
                            PickVisualMediaRequest(
                                //Here we request only photos. Change this to .ImageAndVideo if
                                //you want videos too.
                                //Or use .VideoOnly if you only want videos.
                                mediaType = ActivityResultContracts.PickVisualMedia.VideoOnly
                            )
                        )
                    }) {
                        Text(text = "Select Videos")
                    }

                }
            }, buttons = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Center) {
                    Button(onClick = {
                        scope.launch(Dispatchers.Main) {
//                        viewModel.insert(RealtimeModelResponse.RealtimeItems(title.value, des.value,"")).collect {
//                            when (it) {
//                                is ResultState.Success -> {
//                                    context.showMsg(it.data)
//                                    isDialog.value = false
//                                    isInsert.value = false
//                                }
//                                is ResultState.Failure -> {
//                                    context.showMsg(it.msg.toString())
//                                    isDialog.value = false
//                                }
//                                ResultState.Loading -> {
//                                    isDialog.value = true
//                                }
//                            }
//                        }

                            viewModel.uploadImage(
                                RealtimeModelResponse.RealtimeItems(title.value, des.value, null,null), photoUri, videoUri).collect {
                                when (it) {
                                    is ResultState.Success -> {

                                        context.showMsg(it.data)
                                        isDialog.value = false
                                        isInsert.value = false
                                    }
                                    is ResultState.Failure -> {
                                        context.showMsg(it.msg.toString())
                                        isDialog.value = false
                                    }
                                    ResultState.Loading -> {
                                        isDialog.value = true
                                    }
                                }
                            }
                        }
                    }) {
                        Text(text = "Save")
                    }
                }
            })
    }

    if (isUpdate.value) {
        Update(isUpdate = isUpdate, itemState = viewModel.updateRes.value, viewModel = viewModel)
    }

    if (res.item.isNotEmpty()) {
        LazyColumn(modifier = Modifier.padding(4.dp)) {
            items(res.item, key = {
                it.key!!
            }) { res ->
                EachRow(itemState = res.item!!,
                    onUpdate = {
                        isUpdate.value = true
                        viewModel.setData(res)
                    }
                ) {
                    scope.launch(Dispatchers.Main) {
                        viewModel.delete(res.key!!)
                            .collect {
                                when (it) {
                                    is ResultState.Success -> {
                                        context.showMsg(it.data)
                                        isDialog.value = false

                                    }
                                    is ResultState.Failure -> {
                                        context.showMsg(it.msg.toString())
                                        isDialog.value = false

                                    }
                                    ResultState.Loading -> {
                                        isDialog.value = true
                                    }
                                }
                            }

                    }
                }
            }
        }
    }

    if (res.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
            CircularProgressIndicator()
        }
    }

    if (res.error.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
            Text(text = res.error)
        }
    }
}

@Composable
fun EachRow(
    itemState: RealtimeModelResponse.RealtimeItems,
    onUpdate: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp), elevation = 2.dp, shape = RoundedCornerShape(8.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onUpdate()
            }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ) {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "",
                        modifier = Modifier
                            .size(32.dp)
//                    .padding(4.dp)
                    )
                    Text(
                        text = itemState.title!!, style = TextStyle(
                            fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black
                        )
                    )
                    IconButton(
                        onClick = {
                            onDelete()
                        }, modifier = Modifier.align(CenterVertically)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "", tint = Color.Red)
                    }
                }
                Text(
                    text = itemState.description!!, style = TextStyle(
                        fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Color.Gray
                    )
                )
                if (itemState.imageUri?.isNotEmpty()!!) {
                    ImageView(uri = itemState.imageUri!!, modifier = Modifier.size(400.dp).padding(0.dp,4.dp,0.dp,0.dp))

//                    Image(
//                        painter = rememberAsyncImagePainter(itemState.imageUri),
//                        contentDescription = "",
//                        modifier = Modifier
//                            .size(400.dp)
//                            .padding(0.dp, 4.dp, 0.dp, 0.dp),
//                        contentScale = ContentScale.Crop
//                    )
                }

                if (itemState.videoUri?.isNotEmpty()!!){
                    Log.e("VideoUri",itemState.videoUri!!)

                    VideoView(uri = itemState.videoUri!!, modifier = Modifier.size(400.dp).padding(0.dp, 4.dp, 0.dp, 0.dp))


//                    Column(
//                        Modifier
//                            .fillMaxSize()
//                            .size(400.dp)
//                            .padding(0.dp, 4.dp, 0.dp, 0.dp),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ) {
//
//                        // Fetching the Local Context
//                        val mContext = LocalContext.current
//
//                        // Declaring a string value
//                        // that stores raw video url
//                        val mVideoUrl = itemState.videoUri
//
////                // Declaring ExoPlayer
////                val mExoPlayer = remember(mContext) {
////                    ExoPlayer.Builder(mContext).build().apply {
////                        val dataSourceFactory = DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, mContext.packageName))
////                        val source = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
////                            Uri.parse(mVideoUrl))
////                        prepare(source)
////                    }
////                }
////
////                // Implementing ExoPlayer
////                AndroidView(factory = { context ->
////                    PlayerView(context).apply {
////                        player = mExoPlayer
////                    }
////                })
//
//                        val exoPlayer = ExoPlayer.Builder(LocalContext.current)
//                            .build()
//                            .also { exoPlayer ->
//                                val mediaItem = MediaItem.Builder()
//                                    .setUri(mVideoUrl)
//                                    .build()
//                                exoPlayer.setMediaItem(mediaItem)
//                                exoPlayer.prepare()
//                            }
//
//                        var lifecycle by remember {
//                            mutableStateOf(Lifecycle.Event.ON_CREATE)
//                        }
//                        val lifecycleOwner = LocalLifecycleOwner.current
//                        DisposableEffect(lifecycleOwner) {
//                            val observer = LifecycleEventObserver { _, event ->
//                                lifecycle = event
//                            }
//                            lifecycleOwner.lifecycle.addObserver(observer)
//                            onDispose {
//                                lifecycleOwner.lifecycle.removeObserver(observer)
//                            }
//                        }
//
//                        DisposableEffect(
//                            AndroidView(factory = {
//                                StyledPlayerView(mContext).apply {
//                                    player = exoPlayer
//                                }
//                            },
//                                update = {
//                                    when (lifecycle) {
//                                        Lifecycle.Event.ON_PAUSE -> {
//                                            it.onPause()
//                                            it.player?.pause()
//                                        }
//                                        Lifecycle.Event.ON_RESUME -> {
//                                            it.onResume()
////                                        it.player?.play()
//                                        }
//                                        else -> Unit
//                                    }
//                                }, modifier = Modifier.fillMaxSize()
//                            )
//                        ) {
//                            onDispose { exoPlayer.release() }
//                        }
//
//                    }
                }
            }
        }

    }
}

@Composable
fun Update(
    isUpdate: MutableState<Boolean>, itemState: RealtimeModelResponse, viewModel: RealtimeViewModel
) {

    val title = remember { mutableStateOf(itemState.item?.title) }
    val des = remember { mutableStateOf(itemState.item?.description) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var photoUri: Uri? by remember { mutableStateOf(null) }

    val photoUriString = remember { mutableStateOf(itemState.item?.imageUri) }
    val launcherImages = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            //When the user has selected a photo, its URI is returned here
            photoUri = uri
            photoUriString.value = uri.toString()
            Log.e("photoUri", photoUri.toString())
        }

    var videoUri: Uri? by remember { mutableStateOf(null) }
    val videoUriString = remember { mutableStateOf(itemState.item?.videoUri) }
    val launcherVideos = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        //When the user has selected a photo, its URI is returned here
        videoUri = uri
        videoUriString.value = uri.toString()
//        Log.e("videoUri", videoUri.toString())
    }

    if (isUpdate.value) {
        AlertDialog(properties = DialogProperties(usePlatformDefaultWidth = false),
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
            onDismissRequest = { isUpdate.value = false },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = title.value!!,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = {
                            title.value = it
                        },
                        placeholder = {
                            Text(text = "Title")
                        })

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = des.value!!,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = {
                            des.value = it
                        },
                        placeholder = {
                            Text(text = "Description")
                        })

                    Spacer(modifier = Modifier.height(10.dp))

                    if (photoUriString.value!!.isNotEmpty()) {
                        Log.e("photoUriString update", photoUriString.value.toString())
                        ImageView(uri = photoUriString.value!!, modifier = Modifier.size(200.dp))

//                        //Use Coil to display the selected image
//                        val painter = rememberAsyncImagePainter(
//                            ImageRequest
//                                .Builder(LocalContext.current)
//                                .data(data = photoUriString.value)
//                                .build()
//                        )
//                        Image(
//                            painter = painter,
//                            contentDescription = null,
//                            modifier = Modifier
//                                .size(200.dp),
//                            contentScale = ContentScale.Crop
//                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(onClick = {
                        //On button press, launch the photo picker
                        launcherImages.launch(
                            PickVisualMediaRequest(
                                //Here we request only photos. Change this to .ImageAndVideo if
                                //you want videos too.
                                //Or use .VideoOnly if you only want videos.
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }) {
                        Text(text = "Select Photos")
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (itemState.item?.videoUri?.isNotEmpty()!!){
                        Log.e("videoUriString Update", videoUriString.value.toString())

                        VideoView(uri = itemState.item.videoUri!!, modifier = Modifier.size(200.dp).padding(4.dp))

//                        Column(
//                            Modifier
//                                .size(200.dp)
//                                .padding(4.dp),
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                            verticalArrangement = Arrangement.Center
//                        ) {
//
//                            // Fetching the Local Context
//                            val mContext = LocalContext.current
//
//                            // Declaring a string value
//                            // that stores raw video url
//                            val mVideoUrl = itemState.item.videoUri
//
////                // Declaring ExoPlayer
////                val mExoPlayer = remember(mContext) {
////                    ExoPlayer.Builder(mContext).build().apply {
////                        val dataSourceFactory = DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, mContext.packageName))
////                        val source = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
////                            Uri.parse(mVideoUrl))
////                        prepare(source)
////                    }
////                }
////
////                // Implementing ExoPlayer
////                AndroidView(factory = { context ->
////                    PlayerView(context).apply {
////                        player = mExoPlayer
////                    }
////                })
//
//                            val exoPlayer = ExoPlayer.Builder(LocalContext.current)
//                                .build()
//                                .also { exoPlayer ->
//                                    val mediaItem = MediaItem.Builder()
//                                        .setUri(mVideoUrl)
//                                        .build()
//                                    exoPlayer.setMediaItem(mediaItem)
//                                    exoPlayer.prepare()
//                                }
//
//                            var lifecycle by remember {
//                                mutableStateOf(Lifecycle.Event.ON_CREATE)
//                            }
//                            val lifecycleOwner = LocalLifecycleOwner.current
//                            DisposableEffect(lifecycleOwner) {
//                                val observer = LifecycleEventObserver { _, event ->
//                                    lifecycle = event
//                                }
//                                lifecycleOwner.lifecycle.addObserver(observer)
//                                onDispose {
//                                    lifecycleOwner.lifecycle.removeObserver(observer)
//                                }
//                            }
//
//                            DisposableEffect(
//                                AndroidView(factory = {
//                                    StyledPlayerView(mContext).apply {
//                                        player = exoPlayer
//                                    }
//                                },
//                                    update = {
//                                        when (lifecycle) {
//                                            Lifecycle.Event.ON_PAUSE -> {
//                                                it.onPause()
//                                                it.player?.pause()
//                                            }
//                                            Lifecycle.Event.ON_RESUME -> {
//                                                it.onResume()
////                                        it.player?.play()
//                                            }
//                                            else -> Unit
//                                        }
//                                    }, modifier = Modifier.fillMaxSize()
//                                )
//                            ) {
//                                onDispose { exoPlayer.release() }
//                            }
//
//                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(onClick = {
                        //On button press, launch the photo picker
                        launcherVideos.launch(
                            PickVisualMediaRequest(
                                //Here we request only photos. Change this to .ImageAndVideo if
                                //you want videos too.
                                //Or use .VideoOnly if you only want videos.
                                mediaType = ActivityResultContracts.PickVisualMedia.VideoOnly
                            )
                        )
                    }) {
                        Text(text = "Select Videos")
                    }
                }
            },
            buttons = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Button(onClick = {
                        scope.launch(Dispatchers.Main) {
                            viewModel.update(
                                RealtimeModelResponse(
                                    item = RealtimeModelResponse.RealtimeItems(title.value, des.value, photoUriString.value), key = itemState.key),photoUri!!, videoUri!!).collect {
                                when (it) {
                                    is ResultState.Success -> {
                                        context.showMsg(it.data)
                                        isUpdate.value = false

                                    }
                                    is ResultState.Failure -> {
                                        context.showMsg(it.msg.toString())
                                    }
                                    ResultState.Loading -> {
                                    }
                                }
                            }
                        }

                    }) {
                        Text(text = "Update")
                    }
                }
            })
    }

}

@Composable
fun ImageView(uri: String, modifier: Modifier){
    //Use Coil to display the selected image
    val painter = rememberAsyncImagePainter(
        ImageRequest
            .Builder(LocalContext.current)
            .data(data = uri)
            .build()
    )
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun VideoView(uri: String, modifier: Modifier){
    Box(modifier = modifier) {
        val mContext = LocalContext.current

        val exoPlayer = ExoPlayer.Builder(LocalContext.current)
            .build()
            .also { exoPlayer ->
                val mediaItem = MediaItem.Builder()
                    .setUri(uri)
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
