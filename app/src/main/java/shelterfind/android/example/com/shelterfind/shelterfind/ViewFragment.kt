package shelterfind.android.example.com.shelterfind.shelterfind

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.navigation.Navigation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.viewpagerindicator.CirclePageIndicator
import kotlinx.android.synthetic.main.fragment_view.*
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.google.android.gms.maps.model.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.stepstone.apprating.AppRatingDialog
import kotlinx.android.synthetic.main.fragment_view.view.*
import shelterfind.android.example.com.shelterfind.shelterfind.AllFragment.Companion.mData
import shelterfind.android.example.com.shelterfind.shelterfind.adaptors.AmenitiesViewAdaptor
import shelterfind.android.example.com.shelterfind.shelterfind.adaptors.ChipsAdaptor
import shelterfind.android.example.com.shelterfind.shelterfind.adaptors.ImageSliderViewPagerAdaptor
import shelterfind.android.example.com.shelterfind.shelterfind.adaptors.ReviewAdaptor
import shelterfind.android.example.com.shelterfind.shelterfind.model.CloudData
import shelterfind.android.example.com.shelterfind.shelterfind.viewmodel.SimilarFacilityViewModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class ViewFragment : Fragment(),
        OnMapReadyCallback,
        SimilarFacilityRecycleAdaptor.OnItemClickListener{


    private lateinit var mMap: GoogleMap
    private lateinit var mMapView: MapView
    private val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey1"
    lateinit var mFirebaseFirestore: FirebaseFirestore
    lateinit var mViewModel: SimilarFacilityViewModel
    private var similarFacilityAdd:SimilarFacilityRecycleAdaptor? = null
    lateinit var passingDataCloudData: CloudData
    val REQUEST_CALL_PERMISSION=23


    override fun onItemClick(data: CloudData) {


    }
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        val maddress = passingDataCloudData.address as HashMap<String,Any>
        val moverView=passingDataCloudData.overview!! as HashMap<String,String>
        val mlocation= maddress["location"] as GeoPoint
        mMap = googleMap


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(mlocation.latitude,mlocation.longitude), 15f))

        val mymarker: Marker = mMap.addMarker(MarkerOptions()
                .position(LatLng(mlocation.latitude,mlocation.longitude)))
        mymarker.title=moverView["title"]




        mymarker.setIcon(bitmapDescriptorFromVector(this@ViewFragment.context!!,R.drawable.ic_marker_34))

//        val circleOptions: CircleOptions = CircleOptions()
//                .center(LatLng(mlocation.latitude,mlocation.longitude))   //set center
//                .radius(500.00)   //set radius in meters
//                .fillColor(  0x40ff1000)  //semi-transparent
//                .strokeColor(Color.BLUE)
//                .strokeWidth(2f)
//
//        mMap.addCircle(circleOptions)


    }



    private  fun bitmapDescriptorFromVector  ( context: Context , vectorDrawableResourceId:  Int):BitmapDescriptor {
       val vectorDrawable:Drawable  = ContextCompat.getDrawable(context,vectorDrawableResourceId)!!
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight())
       val bitmap: Bitmap  = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888)
       val canvas: Canvas  =  Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)


}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFirebaseFirestore= FirebaseFirestore.getInstance()

        passingDataCloudData=mData!!
        Log.v("GETM",mData!!.photourl.toString())

        mViewModel= ViewModelProviders.of(this).get( SimilarFacilityViewModel::class.java)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        val view= inflater.inflate(R.layout.fragment_view, container, false)
        mMapView=view.findViewById(R.id.view_Mapview)

        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }

        mMapView.onCreate(mapViewBundle)
        mMapView.getMapAsync(this)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)










        review_float.setOnClickListener {

            showDialog()

        }

        call_float.setOnClickListener {



            if ( ActivityCompat.checkSelfPermission(this.context!!, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED   ) {

                ActivityCompat.requestPermissions(this.activity!!, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL_PERMISSION)
            } else {
                val moverView=passingDataCloudData.overview!! as HashMap<String,String>

                val phonenumber= moverView["phonenumber"] as String

                val  intent =Intent(Intent.ACTION_CALL, Uri.parse("tel:$phonenumber"));
                startActivity(intent)
            }



        }

        val type = InputType.TYPE_TEXT_FLAG_MULTI_LINE
        chat_float.setOnClickListener {


            MaterialDialog(this@ViewFragment.context!!)
                    .title(text = "Chat with the host")
                    .message(text="Send message to Host")
                    .input(hint = "Write message to host", inputType = type, waitForPositiveButton = true,maxLength = 300) { dialog, text ->
                        // Text submitted with the action button
                    }
                .positiveButton(text="Send")

                .show()


    }




        var exp=true

        expande_view.setOnClickListener {

            if (exp){
                view.reviews_list.layoutParams= ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                expande_view.text="Collapse Reviews"
                exp=false
            }else{
                view.reviews_list.layoutParams= ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180)
                expande_view.text="View All Reviews"

            }


        }







        my_toolbar.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.homeFragment, null)
        }





        val amenlist=passingDataCloudData.amenities as ArrayList<String>
        val imagelist=passingDataCloudData.photourl
        var roomtypelist=ArrayList<String>()





        if(passingDataCloudData.type=="hostels"){

         val rmtype=   passingDataCloudData.roomtype as ArrayList<String>

            roomtypelist=rmtype

        }else{
          val mtyp=passingDataCloudData.roomtype as HashMap<String,String>

          val bedroom= " Bedrooms ".plus( mtyp["bedrooms"].toString())
          val bathroom= " BathRooms ".plus( mtyp["bathrooms"].toString())
          val balcony= " Balcony ".plus( mtyp["balcony"].toString())
          val mainhall= " Mainhall ".plus( mtyp["mainhall"].toString())



            roomtypelist.apply {

                add(bedroom)
                add(bathroom)
                add(balcony)
                add(mainhall)
            }


        }



        val moverView=passingDataCloudData.overview!! as HashMap<String,String>

        facility_name.text= moverView["title"]

        mtype=passingDataCloudData.type
        mname= moverView["title"]

        mid=passingDataCloudData.userID




        val rating_view=LayoutInflater.from(this@ViewFragment.context).inflate(R.layout.rate_layout, null, false)




        val facility_type=passingDataCloudData.type as String



      val  dataRecyclerView=similar_facility_recycleview
        val dbcloud:ArrayList<CloudData>?= ArrayList()


//        mFirebaseFirestore
//                .document("user/facilities")
//                .collection(facility_type)
//                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
//
//                    if (firebaseFirestoreException!=null){
//
//                    }else{
//                        for (documentChange: DocumentChange in querySnapshot!!.documentChanges){
//
//                            if (documentChange.type== DocumentChange.Type.ADDED){
//
//
//                                if (documentChange.document.getBoolean("statuscomplete")==false){
//
//                                    val managerData=documentChange.document.toObject(CloudData::class.java)
//
//                                    dbcloud!!.add(managerData)
//
//                                    mViewModel.getArrayCloudList(dbcloud).observe(this,  android.arch.lifecycle.Observer {cloudata->
//
//
//                                        similarFacilityAdd= SimilarFacilityRecycleAdaptor(cloudata!!,this)
//
//                                        dataRecyclerView?.layoutManager = LinearLayoutManager(this.context)
//                                        dataRecyclerView?.adapter =similarFacilityAdd
//
//
//                                    })
//
//
//
//
//
//                                }
//
//
//
//
//
//
//                            }
//
//                        }
//                    }
//
//
//
//                }
//


        val data_review_list=passingDataCloudData.comment as HashMap<String,Any>


        val list_comment=ArrayList<HashMap<String,String>>()



        for (keys:String in data_review_list.keys){


            val md=data_review_list[keys] as ArrayList<String>

            val For_view_Comment=HashMap<String,String>()


            For_view_Comment["comment"]=  md[0] //comment
            For_view_Comment["rate"]=    md[1] //rate
            For_view_Comment["time"]=   md[2] //time
            For_view_Comment["name"]=  md[3] //name
            For_view_Comment["photo"]=  md[4] //photo

            list_comment.add(For_view_Comment)





        }






        if (list_comment.isNotEmpty()){

            val review_daptor= ReviewAdaptor(this@ViewFragment.context!!,list_comment)

            reviews_list.adapter=review_daptor

            expande_view.visibility=View.VISIBLE

        }





        about_description.text= moverView["description"]
        descript=moverView["description"]
        val roomtype_chipsadaptor= ChipsAdaptor(this@ViewFragment.context!!,roomtypelist,passingDataCloudData.type!!)

        roomtype_list.adapter=roomtype_chipsadaptor


        val amen_adaptor= AmenitiesViewAdaptor(this@ViewFragment.context,amenlist)

        showamenities_list.adapter=amen_adaptor


        if(imagelist!=null){

            val slider_adaptor= ImageSliderViewPagerAdaptor(this@ViewFragment.context!!,imagelist!!)
            pager.adapter=slider_adaptor
            val mIndicator= view.findViewById<CirclePageIndicator>(R.id.indicator)

            mIndicator.setViewPager(pager)

            val density: Float  = resources.displayMetrics.density

            mIndicator.radius=5*density
            NUM_PAGES =imagelist.size


            // Auto start of viewpager
            val handler = Handler()
            val Update = Runnable {
                if (currentPage ==NUM_PAGES) {
                    currentPage = 0
                }
                if (pager!=null){
                    pager.setCurrentItem(currentPage++, true)

                }


            }
            val swipeTimer = Timer()
            swipeTimer.schedule(object : TimerTask() {
                override fun run() {
                    handler.post(Update)
                }
            }, 3000, 3000)
            mIndicator.setOnPageChangeListener(object:ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(position: Int) {
                    currentPage = position
                }

                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

                }

                override fun onPageSelected(p0: Int) {

                }


            })





        }






    }




    private fun showDialog() {
        AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNeutralButtonText("Later")
                .setNoteDescriptions(Arrays.asList("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!"))
                .setDefaultRating(2)
                .setTitle("Rate this facility")
                .setDescription("Please select some stars and give your feedback")
                .setCommentInputEnabled(true)
                .setCommentBackgroundColor(R.color.mt_gray3)
                .setHint("Please write your comment here ...")
                .setCancelable(false)
                .setWindowAnimation(R.style.MyDialogFadeAnimation)
                .setCanceledOnTouchOutside(false)
                .create(activity!!)
                .setTargetFragment(this@ViewFragment.parentFragment!!, 0) // only if listener is implemented by fragment
                .show()
    }






    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()

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
        mMapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMapView.onStop()
    }

    override fun onPause() {
        mMapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mMapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }



    companion object {

        @JvmStatic
        fun newInstance() = ViewFragment()


        var mtype:String?=""
        var mname:String?=""
        var mid:String?=""
        var descript:String?=""
        private  var currentPage:Int = 0
        private  var NUM_PAGES:Int = 0
    }
}
