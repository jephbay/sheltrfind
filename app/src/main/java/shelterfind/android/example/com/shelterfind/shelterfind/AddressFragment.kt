package shelterfind.android.example.com.shelterfind.shelterfind

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.support.design.button.MaterialButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.Navigation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import android.os.Build
import android.provider.Settings
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import android.support.v7.widget.Toolbar
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.location.places.*
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.gms.maps.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.SetOptions
import com.seatgeek.placesautocomplete.DetailsCallback
import com.seatgeek.placesautocomplete.model.AddressComponent
import com.seatgeek.placesautocomplete.model.AddressComponentType
import com.seatgeek.placesautocomplete.model.AutocompleteResultType
import com.seatgeek.placesautocomplete.model.PlaceDetails
import kotlinx.android.synthetic.main.fragment_address.view.*
import kotlinx.android.synthetic.main.search_on_map_fragment.*
import shelterfind.android.example.com.shelterfind.shelterfind.MainActivity.Companion.Current_User
import shelterfind.android.example.com.shelterfind.shelterfind.MyKitchen.Companion.TAG
import shelterfind.android.example.com.shelterfind.shelterfind.R
import java.util.*
import kotlin.collections.HashMap

class AddressFragment : Fragment(), OnMapReadyCallback,
        GoogleMap.OnMyLocationClickListener,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener {





    private var listener: OnFragmentInteractionListener? = null
    val myKitchen:MyKitchen= MyKitchen()
    private lateinit var mMap: GoogleMap
    private lateinit var mMapView: MapView
    private val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey2"
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val PLACE_AUTOCOMPLETE_REQUEST_CODE = 1
    private val GOOGLE_API_CLIENT_ID = 0
    private lateinit var mGoogleApiClient: GoogleApiClient


    val preference_file_key="MYDESTINATION"
    lateinit var mFirebaseFirestore: FirebaseFirestore

 private var  BOUNDS_GREATER_SYDNEY: LatLngBounds = LatLngBounds(
            LatLng(-34.041458, 150.790100), LatLng(-33.682247, 151.383362))



    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {

            mMap = googleMap as GoogleMap
            mMap.setOnMyLocationClickListener(this)
            mMap.uiSettings.isScrollGesturesEnabled = true
            mMap.setOnMarkerDragListener(this)

        }






    override fun onMarkerDragEnd(marker: Marker?) {

        val location=marker!!.position
        pointerLocation=GeoPoint( location.latitude,location.longitude)
        val geocoder=Geocoder(this@AddressFragment.context, Locale.getDefault())

        val addresses=geocoder.getFromLocation(location.latitude, location.longitude,1)



            mPlaceDetailsName=addresses[0].countryName
            mPlaceDetailsLocality=addresses[0].locality
            mPlaceDatialsAddressLine=addresses[0].featureName
            mPlaceDatialsState=addresses[0].adminArea



        Log.v("GUTI",addresses.toString())


    }

    override fun onMarkerDragStart(marker: Marker?) {

    }

    override fun onMarkerDrag(marker: Marker?) {

    }


    override fun onMyLocationClick(location: Location) {

    }


    private fun drawMarker(location:Location ) {


			val gps  =LatLng(location.latitude, location.longitude)
			mMap.addMarker(MarkerOptions()
					.position(gps)
					.title("Current Position"))
			//mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 12f))


	}



    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFirebaseFirestore= FirebaseFirestore.getInstance()
            mGoogleApiClient = GoogleApiClient.Builder(this@AddressFragment.context!!)
                    .enableAutoManage(this@AddressFragment.activity!!, GOOGLE_API_CLIENT_ID /* clientId */, this)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .build()

        mPlaceDetailsName=""
        mPlaceDetailsLocality=""
        mPlaceDatialsAddressLine=""
        mPlaceDatialsState=""
        pointerLocation=null

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@AddressFragment.context!!)


    }





    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.errorCode)

        Toast.makeText(this@AddressFragment.context,
                "Could not connect to Google API Client: Error " + connectionResult.errorCode,
                Toast.LENGTH_SHORT).show()
    }

    private fun checkPermissions(): Boolean {
        val permissionState1 = ActivityCompat.checkSelfPermission(this@AddressFragment.context!!,
                Manifest.permission.ACCESS_FINE_LOCATION)

        val permissionState2 = ActivityCompat.checkSelfPermission(this@AddressFragment.context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION)


        return permissionState1 == PackageManager.PERMISSION_GRANTED && permissionState2==PackageManager.PERMISSION_GRANTED
    }



    fun String.toSpanned(): Spanned {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            return Html.fromHtml(this)
        }
    }


    private fun formatPlaceDetails(res: Resources, name: CharSequence, id: String, address: CharSequence?, phoneNumber: CharSequence?, websiteUri: Uri?): Spanned {

        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber, websiteUri))
        return res.getString(R.string.place_details, name, id, address, phoneNumber, websiteUri).toSpanned()

    }





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view:View=inflater.inflate(R.layout.fragment_address, container, false)
        mMapView=view.findViewById<MapView>(R.id.mapviewaddress)

        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }

        mMapView.onCreate(mapViewBundle)
        mMapView.getMapAsync(this)

        return view
    }







    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        val toolbar = view.findViewById<Toolbar>(R.id.my_toolbar) as Toolbar



        toolbar.setNavigationOnClickListener {

            Navigation.findNavController(it).navigate(R.id.amenitiesFragment, null)
        }



        val bottomSheetViewgroup:LinearLayout=view.findViewById(R.id.app_bar)
       val bottomSheetBehavior  = BottomSheetBehavior.from(bottomSheetViewgroup)


        val progressBar: ProgressBar?=view.findViewById<ProgressBar>(R.id.progressBar)


            myKitchen.SetProgress(progressBar!!,95)




        val buttonnext: MaterialButton?= view.findViewById<MaterialButton>(R.id.button_finish)

        buttonnext?.setOnClickListener{btn->

            if (pointerLocation==null){
                Snackbar
                        .make(view,"Please Point to a location to continue", Snackbar.LENGTH_LONG)
                        .show()
                return@setOnClickListener
            }


            val dialog = MaterialDialog(this@AddressFragment.context!!)
                    .title(R.string.room_type_title)
                    .message(R.string.roomtype_progress_report)

            dialog.show()
            val prefs= activity?.getPreferences(Context.MODE_PRIVATE)


            val destin = prefs!!.getString(preference_file_key,"none")



            val myPutAddress=HashMap<String,Any>()

            myPutAddress["placename"]=mPlaceDetailsName!!
            myPutAddress["placelocality"]=mPlaceDetailsLocality!!
            myPutAddress["placestate"]=mPlaceDatialsState!!
            myPutAddress["placeaddress"]=mPlaceDatialsAddressLine!!
            myPutAddress["location"]= pointerLocation!!

            when(destin!!.toString()){
                "house"->{
                    val myCollectionReference=mFirebaseFirestore.collection("/user/facilities/homes/"
                            +Current_User+"/"+"homes")
                    myCollectionReference.get().addOnCompleteListener {task ->
                        if (task.isSuccessful){
                            val locationdb=HashMap<String,Any>()
                            locationdb["address"] =myPutAddress
                            locationdb["progress"]="95"
                            locationdb["statuscomplete"]=true
                            mFirebaseFirestore.collection("/user/facilities/homes/"
                                    +Current_User+"/"+"homes")
                                    .document(task.result.last().id)
                                    .set( locationdb, SetOptions.merge())
                                    .addOnFailureListener { failure->
                                        dialog.dismiss()
                                    }.addOnSuccessListener {
                                        dialog.dismiss()
                                        Navigation.findNavController(view).navigate(R.id.profileFragment, null)
                                    }
                        }
                    }
                }
                "hostel"->{

                    val myCollectionReference=mFirebaseFirestore.collection("/user/facilities/hostels/"
                            +Current_User+"/"+"hostels")
                    myCollectionReference.get().addOnCompleteListener {task ->
                        if (task.isSuccessful){
                            val locationdb=HashMap<String,Any>()
                            locationdb["address"] =myPutAddress
                            locationdb["progress"]="95"
                            locationdb["statuscomplete"]=true

                            mFirebaseFirestore.collection("/user/facilities/hostels/"
                                    +Current_User+"/"+"hostels")
                                    .document(task.result.last().id)
                                    .set(locationdb, SetOptions.merge())
                                    .addOnFailureListener { failure->
                                        dialog.dismiss()
                                        Log.e("FailureCloud",failure.toString())
                                    }.addOnSuccessListener {
                                        dialog.dismiss()
                                        Navigation.findNavController(view).navigate(R.id.profileFragment, null)
                                    }
                        }
                    }
                }
                "hotel"->{
                    val myCollectionReference=mFirebaseFirestore.collection("/user/facilities/hotels/"
                            +Current_User+"/"+"hotels")
                    myCollectionReference.get().addOnCompleteListener {task ->
                        if (task.isSuccessful){
                            val locationdb=HashMap<String,Any>()
                            locationdb["address"] =myPutAddress
                            locationdb["progress"]="95"
                            locationdb["statuscomplete"]=true
                            mFirebaseFirestore.collection("/user/facilities/hotels/"
                                    +Current_User+"/"+"hotels")
                                    .document(task.result.last().id)
                                    .set(locationdb, SetOptions.merge())
                                    .addOnFailureListener { failure->
                                        dialog.dismiss()
                                    }.addOnSuccessListener {
                                        dialog.dismiss()
                                        Navigation.findNavController(view).navigate(R.id.profileFragment, null)
                                    }
                        }
                    }
                }
                "apartment"->{
                    val myCollectionReference=mFirebaseFirestore.collection("/user/facilities/apartments/"
                            +Current_User+"/"+"apartments")
                    myCollectionReference.get().addOnCompleteListener {task ->
                        if (task.isSuccessful){
                            val locationdb=HashMap<String,Any>()
                            locationdb["address"] =myPutAddress
                            locationdb["progress"]="95"
                            locationdb["statuscomplete"]=true
                            mFirebaseFirestore.collection("/user/facilities/apartments/"
                                    +Current_User+"/"+"apartments")
                                    .document(task.result.last().id)
                                    .set(locationdb, SetOptions.merge())
                                    .addOnFailureListener { failure->
                                        dialog.dismiss()
                                    }.addOnSuccessListener {
                                        dialog.dismiss()
                                        Navigation.findNavController(view).navigate(R.id.profileFragment, null)
                                    }
                        }
                    }
                }

            }










        }


        view.fab.setOnClickListener { fabview ->
            mMap.clear()

            fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                        // Got last known location. In some rare situations this can be null.
        try{


            if (location!=null){


                val gps  =LatLng(location.latitude, location.longitude)

                val mymarker: Marker = mMap.addMarker(MarkerOptions()
                        .position(gps).title("Current Location"))
                mymarker.showInfoWindow()
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 17f))

                val geocoder=Geocoder(this@AddressFragment.context, Locale.getDefault())

                val addresses=geocoder.getFromLocation(location.latitude, location.longitude,1)

                pointerLocation= GeoPoint(gps.latitude,gps.longitude)
                mPlaceDetailsName=addresses[0].countryName
                mPlaceDetailsLocality=addresses[0].locality
                mPlaceDatialsAddressLine=addresses[0].featureName
                mPlaceDatialsState=addresses[0].adminArea

                Log.v("GUTI",addresses.toString())

            }

        }catch (e:Exception){

        }



                    }




        }


        places_autocomplete.resultType=AutocompleteResultType.NO_TYPE


        places_autocomplete.setOnPlaceSelectedListener { place ->

            places_autocomplete.getDetailsFor(place,object :DetailsCallback{
                override fun onSuccess(getplace: PlaceDetails?) {
                  val mgp=      getplace!!.geometry.location


                    street.text=getplace.name
                    Log.v("GHIF20",getplace.geometry.location.toString())

                    val lat=getplace.geometry.location.lat
                    val long=getplace.geometry.location.lng


                    val mlocation= LatLng(lat,long)
                    pointerLocation= GeoPoint(lat,long)
                    Log.v("GHIF",pointerLocation.toString())
                    for (component:AddressComponent in getplace.address_components){

                        for (type:AddressComponentType in component.types ){

                            when(type){

                                AddressComponentType.NATURAL_FEATURE->{
                                    mPlaceDatialsAddressLine=component.long_name
                                }

                                  AddressComponentType.NEIGHBORHOOD->{

                                }

                                  AddressComponentType.SUBLOCALITY_LEVEL_1->{

                                }

                                  AddressComponentType.SUBLOCALITY->{

                                }

                                  AddressComponentType.LOCALITY->{
                                    city.text=component.long_name
                                      mPlaceDetailsLocality=component.long_name
                                }

                                  AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1->{
                                    state.text=component.short_name
                                      mPlaceDatialsState=component.short_name
                                }

                                  AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_2->{

                                }

                                  AddressComponentType.COUNTRY->{
                                    country.text=component.long_name
                                      mPlaceDetailsName=component.long_name
                                }

                                  AddressComponentType.POSTAL_CODE->{

                                      zip.text=component.long_name
                                }


                                else -> {
                                }
                            }

                        }

                    }





                }

                override fun onFailure(getplace: Throwable?) {


                }


            })
        }



        locate_place.setOnClickListener {

            mMap.clear()

            if (pointerLocation!=null){

                val gps=LatLng(pointerLocation!!.latitude, pointerLocation!!.longitude)
                val mymarker: Marker = mMap.addMarker(MarkerOptions()
                        .position(gps).title("Drag to your place if not properly placed"))
                mymarker.showInfoWindow()

                mymarker.isDraggable=true

               // mymarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon))

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 10f))


            }

        }

    }




    private fun vectorToBitmap(@DrawableRes id : Int, @ColorInt color : Int): BitmapDescriptor {
        val vectorDrawable: Drawable? = ResourcesCompat.getDrawable(resources, id, null)
        if (vectorDrawable == null) {
            Log.e(TAG, "Resource not found")
            return BitmapDescriptorFactory.defaultMarker()
        }
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(vectorDrawable, color)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)


        var mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle)
        }

        mMapView.onSaveInstanceState(mapViewBundle)

    }


    override fun onResume() {
        super.onResume()

        mMapView.onResume()
    }

    override fun onStart() {
        super.onStart()

        if (!checkPermissions()) {
            myKitchen.CheckPermission(this@AddressFragment.context!!, this@AddressFragment.activity!!)
        }
        mMapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMapView.onStop()
    }

    override fun onPause() {
        mMapView.onPause()
        mGoogleApiClient.stopAutoManage(this.activity!!)
        mGoogleApiClient.disconnect()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        mMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }



    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                AddressFragment()

         var mcountry_record:String=""

          var  mPlaceDetailsName:String?=""
          var  mPlaceDetailsLocality:String?=""
          var  mPlaceDatialsAddressLine:String?=""
          var  mPlaceDatialsState:String?=""
          var  pointerLocation:GeoPoint?=null
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


        if (requestCode != myKitchen.REQUEST_LOCATION_PERMISSION) return


        when {
            grantResults.isEmpty() ->
                Log.i(TAG, "User interaction was cancelled.")
            grantResults[0] == PackageManager.PERMISSION_GRANTED -> // Permission granted.
                Log.i(TAG, "Permission Granted")
            else ->

                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        View.OnClickListener {
                            // Build intent that displays the App settings screen.
                            val intent = Intent().apply {
                                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            startActivity(intent)
                        })
        }

    }


    private fun showSnackbar(
            mainTextStringId: Int,
            actionStringId: Int,
            listener: View.OnClickListener
    ) {
        Snackbar.make(this.view!!, getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener)
                .show()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val TAGP="SEARCH AUTOCOMPLETE"
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                RESULT_OK -> {
                    val place: Place  = PlaceAutocomplete.getPlace(this@AddressFragment.context, data)
                    Log.i(TAGP, "Place: " + place.name)
                }
                PlaceAutocomplete.RESULT_ERROR -> {
                    val status: Status = PlaceAutocomplete.getStatus(this@AddressFragment.context, data)
                    // TODO: Handle the error.
                    Log.i(TAGP, status.statusMessage)

                }
                RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
        }
    }
}
