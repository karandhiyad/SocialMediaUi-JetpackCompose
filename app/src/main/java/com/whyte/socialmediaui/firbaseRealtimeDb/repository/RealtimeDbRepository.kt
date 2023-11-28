package com.whyte.socialmediaui.firbaseRealtimeDb.repository

import android.net.Uri
import android.system.Os
import android.system.Os.close
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import com.whyte.socialmediaui.firbaseRealtimeDb.RealtimeModelResponse
import com.whyte.socialmediaui.utils.ResultState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.job
import javax.inject.Inject

class RealtimeDbRepository @Inject constructor(
    private val realTimeDatabaseReference: DatabaseReference,
    private val storageReference: StorageReference
) : RealtimeRepository {

    private val mediaStorageReference = storageReference.child("Media")
    private val videoStorageReference = storageReference.child("Video")


    override fun insert(item: RealtimeModelResponse.RealtimeItems): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)

            realTimeDatabaseReference.push().setValue(item).addOnCompleteListener {
                if (it.isSuccessful)
                    trySend(ResultState.Success("Data inserted Successfully.."))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }

            awaitClose {
                close()
            }
        }

    override fun getItems(): Flow<ResultState<List<RealtimeModelResponse>>> = callbackFlow {

        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.map {
                    RealtimeModelResponse(
                        it.getValue(RealtimeModelResponse.RealtimeItems::class.java),
                        key = it.key
                    )
                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Failure(error.toException()))
            }

        }

        realTimeDatabaseReference.addValueEventListener(valueEvent)

        awaitClose {
            realTimeDatabaseReference.removeEventListener(valueEvent)
            close()
        }
    }

    override fun delete(key: String): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        realTimeDatabaseReference.child(key).removeValue()
            .addOnCompleteListener {
                trySend(ResultState.Success("Item Deleted."))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }

        awaitClose {
            close()
        }
    }

    override fun update(
        res: RealtimeModelResponse,
        imageUri: Uri?,
        videoUri: Uri?
    ): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)

//        val map = HashMap<String, Any>()
//        map["title"] = res.item?.title!!
//        map["description"] = res.item.description!!
//
//        realTimeDatabaseReference.child(res.key!!).updateChildren(map)
//            .addOnCompleteListener {
//                trySend(ResultState.Success("Update Successfully."))
//            }.addOnFailureListener {
//                trySend(ResultState.Failure(it))
//            }
//        awaitClose {
//            close()
//        }

            mediaStorageReference.child(res.item!!.title!!).child("1").putFile(imageUri!!)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        trySend(ResultState.Success("Image Upload Successfully.."))
                        Log.e("Image Upload", "Image Upload Successfully")

                        mediaStorageReference.child(res.item.title!!)
                            .child("1").downloadUrl.addOnSuccessListener { uri ->
                                Log.e("Download Url", "Download Url $uri")
//                    insert(RealtimeModelResponse.RealtimeItems(res.title, res.description, uri), uri)

                                val map = HashMap<String, Any>()
                                map["title"] = res.item.title
                                map["description"] = res.item.description!!
                                map["imageUri"] = uri.toString()

                                realTimeDatabaseReference.child(res.key!!).updateChildren(map)
                                    .addOnCompleteListener {
                                        trySend(ResultState.Success("Update Successfully."))
                                    }.addOnFailureListener {
                                        trySend(ResultState.Failure(it))
                                    }

                            }.addOnFailureListener {
                                trySend(ResultState.Failure(it))
                            }
                    }

                }.addOnFailureListener {
                    trySend(ResultState.Failure(it))
                }
            awaitClose {
                close()
            }
        }

    override fun uploadImage(
        res: RealtimeModelResponse.RealtimeItems,
        imageUri: Uri?,
        videoUri: Uri?
    ): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        if (imageUri == null && videoUri == null) {
            realTimeDatabaseReference.push().setValue(res).addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(ResultState.Success("Data inserted Successfully.."))
                    Log.e("Data inserted", "Data inserted Successfully..")
                }
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
                Log.e("Data inserted failed", it.toString())
            }
        }

        if (imageUri != null) {
            mediaStorageReference.child(res.title!!).child("1").putFile(imageUri)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        trySend(ResultState.Success("Image Upload Successfully.."))
                        Log.e("Image Upload", "Image Upload Successfully")

                        mediaStorageReference.child(res.title)
                            .child("1").downloadUrl.addOnSuccessListener { uri ->
                                Log.e("Download Url", "Download Url $uri")
//                    insert(RealtimeModelResponse.RealtimeItems(res.title, res.description, uri), uri)
                                res.imageUri = uri.toString()

                                if (videoUri == null) {
                                    realTimeDatabaseReference.push().setValue(res)
                                        .addOnCompleteListener {
                                            if (it.isSuccessful) {
                                                trySend(ResultState.Success("Data inserted Successfully.."))
                                                Log.e(
                                                    "Data inserted",
                                                    "Data inserted Successfully.."
                                                )
                                            }
                                        }.addOnFailureListener {
                                            trySend(ResultState.Failure(it))
                                            Log.e("Data inserted failed", it.toString())
                                        }
                                }

                            }.addOnFailureListener {
                                trySend(ResultState.Failure(it))
                            }
                    }

                }.addOnFailureListener {
                    trySend(ResultState.Failure(it))
                }
        }

        if (videoUri != null) {
            videoStorageReference.child(res.title!!).child("1").putFile(videoUri)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        trySend(ResultState.Success("Video Upload Successfully.."))
                        Log.e("Video Upload", "Video Upload Successfully")

                        videoStorageReference.child(res.title)
                            .child("1").downloadUrl.addOnSuccessListener { uri ->
                                Log.e("Download Url", "Download Url $uri")
//                    insert(RealtimeModelResponse.RealtimeItems(res.title, res.description, uri), uri)
                                res.videoUri = uri.toString()
                                realTimeDatabaseReference.push().setValue(res)
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            trySend(ResultState.Success("Data inserted Successfully.."))
                                            Log.e("Data inserted", "Data inserted Successfully..")
                                        }
                                    }.addOnFailureListener {
                                        trySend(ResultState.Failure(it))
                                        Log.e("Data inserted failed", it.toString())
                                    }

                            }.addOnFailureListener {
                                trySend(ResultState.Failure(it))
                            }
                    }

                }.addOnFailureListener {
                    trySend(ResultState.Failure(it))
                }
        }

        awaitClose {
            close()
        }


//        mediaStorageReference.child(res.title!!).child("1").putFile(imageUri)
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    trySend(ResultState.Success("Image Upload Successfully.."))
//                    Log.e("Image Upload", "Image Upload Successfully")
//
//                    mediaStorageReference.child(res.title)
//                        .child("1").downloadUrl.addOnSuccessListener { uri ->
//                            Log.e("Download Url", "Download Url $uri")
////                    insert(RealtimeModelResponse.RealtimeItems(res.title, res.description, uri), uri)
//
//                            res.imageUri = uri.toString()
//                            realTimeDatabaseReference.push().setValue(res).addOnCompleteListener {
//                                if (it.isSuccessful) {
//                                    trySend(ResultState.Success("Data inserted Successfully.."))
//                                    Log.e("Data inserted", "Data inserted Successfully..")
//                                }
//
//                            }.addOnFailureListener {
//                                trySend(ResultState.Failure(it))
//                                Log.e("Data inserted failed", it.toString())
//                            }
//
//                        }.addOnFailureListener {
//                            trySend(ResultState.Failure(it))
//                        }
//                }
//
//            }.addOnFailureListener {
//            trySend(ResultState.Failure(it))
//        }
//        awaitClose {
//            close()
//        }
    }


    override fun uploadToStorage(
        storageReference: StorageReference,
        child: String,
        uri: Uri
    ): Flow<Uri> = callbackFlow {
        storageReference.child(child).child("1").putFile(uri).addOnCompleteListener {
            if (it.isSuccessful) {
//                trySend(ResultState.Success("Upload Successfully.."))
                Log.e("Upload", "Upload Successfully")

                storageReference.child(child).child("1").downloadUrl.addOnSuccessListener { uri ->
                    Log.e("Download Url", "Download Url $uri")
                    trySend(uri)
                }.addOnFailureListener {
//                    trySend(ResultState.Failure(it))
                }
            }

        }.addOnFailureListener {
//            trySend(ResultState.Failure(it))
        }
        awaitClose {
            close()
        }
    }
}