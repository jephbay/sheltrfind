package shelterfind.android.example.com.shelterfind.shelterfind

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_lifting_status.*
import kotlinx.android.synthetic.main.fragment_lifting_status.view.*
import shelterfind.android.example.com.shelterfind.shelterfind.MainActivity.Companion.Current_User
import shelterfind.android.example.com.shelterfind.shelterfind.model.CloudData
import shelterfind.android.example.com.shelterfind.shelterfind.viewmodel.ProgressViewModel


class LiftingStatus : Fragment(){


    var  lift_userID=""
    lateinit var mFirebaseFirestore: FirebaseFirestore
    private var dataRecyclerView:RecyclerView?=null

    lateinit var mViewModel: ProgressViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFirebaseFirestore= FirebaseFirestore.getInstance()
        mViewModel= ViewModelProviders.of(this).get(ProgressViewModel::class.java)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lifting_status, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val toolbar = view.findViewById<Toolbar>(R.id.my_toolbar) as Toolbar


        toolbar.setNavigationOnClickListener {

            Navigation.findNavController(it).navigate(R.id.profileFragment, null)
        }



        btn_start.setOnClickListener {



            val facilities=HashMap<String,String>()
            facilities.put("homes","homes")
            facilities.put("hostels","hostels")
            facilities.put("hotels","hotels")
            facilities.put("apartments","apartments")

            for (i:String in   facilities.keys){
                val mTarget=facilities[i]

                mFirebaseFirestore.collection("user/facilities/"+mTarget)
                        .document(Current_User)
                        .collection(mTarget!!)
                        .addSnapshotListener { basequerySnapshot, basefirebaseFirestoreException ->

                            if ( basefirebaseFirestoreException!=null){


                            }else {

                                for (basedocumentChange: DocumentChange in basequerySnapshot!!.documentChanges) {

                                    if (basedocumentChange.document.exists()){

                                        if (basedocumentChange.document.getBoolean("statuscomplete")==false){

                                            mFirebaseFirestore.collection("user/facilities/"+mTarget)
                                                    .document(Current_User)
                                                    .collection(mTarget)
                                                    .document(basedocumentChange.document.id)
                                                    .delete()
                                                    .addOnSuccessListener {tg->


                                                        Navigation.findNavController(it).navigate(R.id.entryFormoneFragment, null)
                                                    }


                                        }

                                    }
                                }





                            }


                        }



            }







        }




        lift_userID=Current_User
        var data: CloudData?=null
        val facilities=HashMap<String,String>()
        facilities.put("homes","homes")
        facilities.put("hostels","hostels")
        facilities.put("hotels","hotels")
        facilities.put("apartments","apartments")

        for (i:String in   facilities.keys){
            val mTarget=facilities[i]



            mFirebaseFirestore
                    .collection("/user/facilities/"+ mTarget +"/"
                    +Current_User+"/"+ mTarget)
                    .addSnapshotListener { querySnapshot, firebaseFirestoreException ->

                        if (firebaseFirestoreException != null) {


                        }else{

                            for (documentChange: DocumentChange in querySnapshot!!.documentChanges) {


                                if (documentChange.document.exists()) {
                                    if ( documentChange.document.getBoolean("statuscomplete") == false) {

                                        val managerData = documentChange.document.toObject(CloudData::class.java)


                                        data=managerData

                                        val nameData = managerData.overview


                                        if (nameData!=null){
                                            val moverview=nameData as HashMap<String,String>
                                           view.progress_name!!.text = moverview["title"].plus(" Progress")
                                        }else{
                                            view.progress_name!!.text="Progress"
                                        }

                                       view. progressbar_number!!.text =   managerData.progress
                                        view.liftingStatus_progress!!.progress =  managerData.progress!!.toInt()









                                    }


                                }

                            }



                        }


                    }

        }


        btn_continue.setOnClickListener {










            when( data!!.progress){

                "1"->{
                    if (data!!.type=="home" || data!!.type=="apartment" || data!!.type=="hotel") Navigation.findNavController(this.view!!).navigate(R.id.generalRoomTypeFragment, null)
                    else Navigation.findNavController(this.view!!).navigate(R.id.hostelRoomTypeFragment, null)
                }
                "10"->{ Navigation.findNavController(this.view!!).navigate(R.id.placeAvailability, null) }
                "30"->{
                    if (data!!.type=="home" || data!!.type=="apartment") Navigation.findNavController(this.view!!).navigate(R.id.generalPriceFragment, null)
                    else if (data!!.type=="hotel")  Navigation.findNavController(this.view!!).navigate(R.id.hotelGeneralPriceFragment, null)
                    else if (data!!.type=="hostel")  Navigation.findNavController(this.view!!).navigate(R.id.priceHostelFragment, null)
                }
                "40"->{ Navigation.findNavController(this.view!!).navigate(R.id.overviewFragment, null) }
                "50"->{ Navigation.findNavController(this.view!!).navigate(R.id.profilePictureFragment, null) }
                "60"->{ Navigation.findNavController(this.view!!).navigate(R.id.addPlacePicturesFragment, null) }
                "70"->{ Navigation.findNavController(this.view!!).navigate(R.id.amenitiesFragment, null) }
                "80"->{ Navigation.findNavController(this.view!!).navigate(R.id.addressFragment, null) }

            }


        }







    }

    companion object {

        @JvmStatic
        fun newInstance() = LiftingStatus()
    }
}
