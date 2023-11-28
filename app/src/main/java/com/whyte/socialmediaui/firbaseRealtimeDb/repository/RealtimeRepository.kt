package com.whyte.socialmediaui.firbaseRealtimeDb.repository

import android.net.Uri
import com.google.firebase.storage.StorageReference
import com.whyte.socialmediaui.firbaseRealtimeDb.RealtimeModelResponse
import com.whyte.socialmediaui.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface RealtimeRepository {

    fun insert (item: RealtimeModelResponse.RealtimeItems) : Flow<ResultState<String>>

    fun getItems() : Flow<ResultState<List<RealtimeModelResponse>>>

    fun delete(key:String): Flow<ResultState<String>>

    fun update(res:RealtimeModelResponse,imageUri: Uri?,videoUri: Uri?) : Flow<ResultState<String>>

    fun uploadImage(res:RealtimeModelResponse.RealtimeItems,imageUri: Uri?,videoUri: Uri?) : Flow<ResultState<String>>

    fun uploadToStorage(storageReference: StorageReference, child : String, uri: Uri) : Flow<Uri>
}