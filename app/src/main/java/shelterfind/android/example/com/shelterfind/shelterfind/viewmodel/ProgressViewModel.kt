package shelterfind.android.example.com.shelterfind.shelterfind.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import shelterfind.android.example.com.shelterfind.shelterfind.model.CloudData

class ProgressViewModel: ViewModel(){

    private var arraylistmutablelivedata= MutableLiveData<ArrayList<CloudData>>()



    fun getArrayCloudList( cloudData: ArrayList<CloudData>): MutableLiveData<ArrayList<CloudData>> {

        arraylistmutablelivedata.value=cloudData

        return arraylistmutablelivedata
    }

}