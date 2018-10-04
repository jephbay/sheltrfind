package shelterfind.android.example.com.shelterfind.shelterfind.workers

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.work.Worker
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

class ComUploadSingleImage: Worker() {


    lateinit var mFirebaseStorage: FirebaseStorage
    lateinit var mStorageRef: StorageReference
    lateinit var mImageRef: StorageReference
    lateinit var mFirebaseFirestore: FirebaseFirestore
    private val myListuri = ArrayList<String>()
    private val KEY_USER = "USERID"


    override fun doWork(): Result = try {

        mFirebaseFirestore = FirebaseFirestore.getInstance()
        mFirebaseStorage = FirebaseStorage.getInstance()
        val imgAuth = inputData.getString(KEY_IMG_PATH)
        val userID = inputData.getString(KEY_USER)





        myCompressThenUpload(imgAuth!!,userID!!)




        Result.SUCCESS
    } catch (e: Throwable) {
        Log.e(LOG_TAG, "Error executing work: " + e.message, e)
        Result.FAILURE
    }


    private fun myCompressThenUpload(muri: String,userID:String) {


            val uri = Uri.parse(muri)

            val uriPath = uri.lastPathSegment
            mStorageRef = mFirebaseStorage.reference
            mImageRef = mStorageRef.child("user profile photos/$uriPath")


            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.applicationContext.contentResolver, uri)
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos)
            val imgdata: ByteArray = baos.toByteArray()
            val path: String = MediaStore.Images.Media.insertImage(this.applicationContext.contentResolver, bitmap, uriPath, null)

            val compressedImg = Uri.parse(path)


            mImageRef.putFile(compressedImg).addOnSuccessListener { taskSnapshot ->


                mImageRef.downloadUrl.addOnSuccessListener { uri ->
                    Log.v("WSE","Cool download")
                    val duri = uri.toString()
                    val imgdb = HashMap<String, Any>()
                    imgdb["photourl"] =duri
                    mFirebaseFirestore.document("usersonboard/"+userID)
                            .set(imgdb, SetOptions.merge())
                            .addOnFailureListener { failure ->

                                Log.v("WSE"," Not Cool")
                            }.addOnSuccessListener {
                                Log.v("WSE","Cool")
                            }


                }




        }


    }

    companion object {
        private const val KEY_IMG_PATH = "IMG_PATH"
        private const val LOG_TAG = "UploadWorker"


    }


}