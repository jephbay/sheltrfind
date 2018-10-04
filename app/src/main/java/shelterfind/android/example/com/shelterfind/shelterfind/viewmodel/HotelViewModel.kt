package shelterfind.android.example.com.shelterfind.shelterfind.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import shelterfind.android.example.com.shelterfind.shelterfind.model.CloudData

class HotelViewModel: ViewModel(){

    private var arraylistmutablelivedata= MutableLiveData<ArrayList<CloudData>>()
    val mFirebaseFirestore: FirebaseFirestore= FirebaseFirestore.getInstance()
    val dbcloud:ArrayList<CloudData>?= ArrayList()

    fun getArrayCloudList(): MutableLiveData<ArrayList<CloudData>> {

        if(arraylistmutablelivedata.value==null) {
            mFirebaseFirestore
                    .collection("usersonboard")
                    .addSnapshotListener { mquerySnapshot, mfirebaseFirestoreException ->
                        if (mfirebaseFirestoreException != null) {

                        } else {

                            for (mdocsize: Int in 0 until mquerySnapshot!!.documents.size) {

                                mFirebaseFirestore
                                        .document("user/facilities")
                                        .collection("hotels").document(mquerySnapshot.documents[mdocsize].id).collection("hotels")
                                        .addSnapshotListener { querySnapshot, firebaseFirestoreException ->


                                            for (documentChange: DocumentChange in querySnapshot!!.documentChanges) {

                                                if (documentChange.type == DocumentChange.Type.ADDED) {

                                                    if (documentChange.document.getBoolean("statuscomplete") == true) {

                                                        val managerData = documentChange.document.toObject(CloudData::class.java)

                                                        dbcloud!!.add(managerData)

                                                        arraylistmutablelivedata.postValue(dbcloud)

                                                    }


                                                }


                                            }


                                        }


                            }
                        }

                    }


        }


        return arraylistmutablelivedata
    }

}