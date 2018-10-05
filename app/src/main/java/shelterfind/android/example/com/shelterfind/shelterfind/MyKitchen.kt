package shelterfind.android.example.com.shelterfind.shelterfind

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.ProgressBar
import com.google.android.gms.maps.model.LatLng

class MyKitchen {

    data class myPlace(val location:LatLng,
                       val id:String,
                       val name:String,
                       val placeId:String,
                       val vicinity:String,
                       val type: MutableList<Int>)


    data class roomtype(val bedrooms:Int,val bathrooms:Int,val balcony:Boolean,val mainhall:Boolean )


     val REQUEST_LOCATION_PERMISSION = 100

    fun SetProgress(progressBar: ProgressBar, progInt: Int) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(progInt, true)
        } else {
            progressBar.progress = progInt
        }

    }




    fun CheckPermission(context: Context, activity: Activity) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED   ) {

            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_LOCATION_PERMISSION)
        } else {
            Log.e("DB", "PERMISSION NOT GRANTED")
        }


    }


    companion object {


        val TAG = "LocationTrackingService"



    }




}


