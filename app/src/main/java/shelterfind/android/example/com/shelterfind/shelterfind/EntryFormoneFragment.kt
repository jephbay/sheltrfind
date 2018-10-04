package shelterfind.android.example.com.shelterfind.shelterfind

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.design.button.MaterialButton
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RadioGroup
import androidx.navigation.Navigation
import com.afollestad.materialdialogs.MaterialDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.fragment_account_settings.*
import kotlinx.android.synthetic.main.fragment_entry_formone.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import shelterfind.android.example.com.shelterfind.shelterfind.MainActivity.Companion.Current_User
import shelterfind.android.example.com.shelterfind.shelterfind.R
import java.util.ArrayList


class EntryFormoneFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    val preference_file_key="MYDESTINATION"
    val myKitchen:MyKitchen=MyKitchen()
    lateinit var mFirebaseFirestore: FirebaseFirestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFirebaseFirestore= FirebaseFirestore.getInstance()




    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entry_formone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val toolbar = view.findViewById(R.id.my_toolbar) as Toolbar



          toolbar.setNavigationOnClickListener {
                      Navigation.findNavController(it).navigate(R.id.profileFragment, null)
                }



        val buttonnext:FloatingActionButton?= view.findViewById<FloatingActionButton>(R.id.button_next)


        val radioGroup:RadioGroup?=view.findViewById(R.id.facility_group)



        val progressBar:ProgressBar?=view.findViewById<ProgressBar>(R.id.progressBar)


            myKitchen.SetProgress(progressBar!!,1)


        buttonnext?.setOnClickListener {



            if(radioGroup?.checkedRadioButtonId == -1) {

                Snackbar.make(
                        view, // Parent view
                        "Please Choose one of the Faclities to Continue", // Message to show
                        Snackbar.LENGTH_LONG // How long to display the message.
                ).show()

            }else{
                val dialog = MaterialDialog(this@EntryFormoneFragment.context!!)
                        .title(R.string.room_type_title)
                        .message(R.string.roomtype_progress_report)

                dialog.show()

                when (radioGroup?.checkedRadioButtonId) {


                    R.id.facility_hostel -> {

                        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)!!

                        with(sharedPref.edit()) {
                            putString(preference_file_key, "hostel")
                            apply()
                        }


                        val myCollectionReference=mFirebaseFirestore.collection("/user/facilities/hostels/"
                                + Current_User +"/"+"hostels")
                        myCollectionReference.get().addOnCompleteListener {task ->

                            if (task.isSuccessful){

                                val dbcount=    task.result.size()
                                val tekcount=dbcount+1
                                if (dbcount==0){

                                    val objdb=HashMap<String,Any?>()
                                    objdb["type"]="hostels"
                                    objdb["userID"]=Current_User
                                    objdb["progress"]="1"
                                    objdb["statuscomplete"]=false
                                    mFirebaseFirestore.collection("/user/facilities/hostels/"
                                            +Current_User+"/"+"hostels")
                                            .document("hostel1")
                                            .set(objdb, SetOptions.merge())
                                            .addOnSuccessListener {succes->
                                                dialog.dismiss()
                                                Navigation.findNavController(it).navigate(R.id.hostelRoomTypeFragment, null)
                                            }.addOnFailureListener { failure->
                                                dialog.dismiss()
                                                Log.e("FailureCloud",failure.toString())
                                            }

                                }else{

                                    val objdb=HashMap<String,Any?>()
                                    objdb["type"]="hostels"
                                    objdb["userID"]=Current_User
                                    objdb["progress"]="1"
                                    objdb["statuscomplete"]=false
                                    mFirebaseFirestore.collection("/user/facilities/hostels/"+Current_User+"/"+"hostels")
                                            .document("hostel"+tekcount.toString())
                                            .set(objdb, SetOptions.merge())
                                            .addOnSuccessListener {succes->
                                                dialog.dismiss()
                                                Navigation.findNavController(it).navigate(R.id.hostelRoomTypeFragment, null)
                                            }.addOnFailureListener { failure->
                                                dialog.dismiss()
                                                Log.e("FailureCloud",failure.toString())
                                            }



                                }

                            }
                        }




                    }

                    R.id.facility_house -> {


                        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)!!
                        with(sharedPref.edit()) {
                            putString(preference_file_key, "house")
                            apply()
                        }

                        val myCollectionReference=mFirebaseFirestore.collection("/user/facilities/homes/"
                                +Current_User+"/"+"homes")
                        myCollectionReference.get().addOnCompleteListener {task ->
                            Log.v("HUPIhome","yeerrrrr")
                            if (task.isSuccessful){


                                val dbcount=    task.result.size()
                                val tekcount=dbcount+1
                                if (dbcount==0 ) {


                                    val objdb=HashMap<String,Any?>()
                                    objdb["type"]="homes"
                                    objdb["userID"]=Current_User
                                    objdb["progress"]="1"
                                    objdb["statuscomplete"]=false
                                    mFirebaseFirestore.collection("/user/facilities/homes/"
                                            +Current_User+"/"+"homes")
                                            .document("home1")
                                            .set(objdb, SetOptions.merge())
                                            .addOnSuccessListener {succes->
                                                dialog.dismiss()

                                                Navigation.findNavController(it).navigate(R.id.generalRoomTypeFragment, null)

                                            }.addOnFailureListener { failure->
                                                dialog.dismiss()
                                                Log.e("FailureCloud",failure.toString())
                                            }



                                }else{

                                    val objdb=HashMap<String,Any?>()
                                    objdb["type"]="homes"
                                    objdb["userID"]=Current_User
                                    objdb["progress"]="1"
                                    objdb["statuscomplete"]=false
                                    mFirebaseFirestore.collection("/user/facilities/homes/"
                                            +Current_User+"/"+"homes")
                                            .document("home"+tekcount.toString())
                                            .set(objdb, SetOptions.merge())
                                            .addOnSuccessListener {succes->
                                                dialog.dismiss()
                                                Navigation.findNavController(it).navigate(R.id.generalRoomTypeFragment, null)

                                            }.addOnFailureListener { failure->
                                                dialog.dismiss()
                                                Log.e("FailureCloud",failure.toString())
                                            }





                                }


                            }
                        }

                    }

                    R.id.facility_apartment -> {

                        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)!!
                        with(sharedPref.edit()) {
                            putString(preference_file_key, "apartment")
                            apply()
                        }



                        val myCollectionReference=mFirebaseFirestore.collection("/user/facilities/apartments/"
                                +Current_User+"/"+"apartments")
                        myCollectionReference.get().addOnCompleteListener {task ->

                            if (task.isSuccessful){


                                val dbcount=    task.result.size()
                                val tekcount=dbcount+1
                                if (dbcount==0 ) {


                                    val objdb=HashMap<String,Any?>()
                                    objdb["type"]="apartments"
                                    objdb["userID"]=Current_User
                                    objdb["progress"]="1"
                                    objdb["statuscomplete"]=false
                                    mFirebaseFirestore.collection("/user/facilities/apartments/"
                                            +Current_User+"/"+"apartments")
                                            .document("apartment1")
                                            .set(objdb, SetOptions.merge())
                                            .addOnSuccessListener {succes->
                                                dialog.dismiss()
                                                Navigation.findNavController(it).navigate(R.id.generalRoomTypeFragment, null)

                                            }.addOnFailureListener { failure->
                                                dialog.dismiss()
                                                Log.e("FailureCloud",failure.toString())
                                            }





                                }else{



                                    val objdb=HashMap<String,Any?>()
                                    objdb["type"]="apartments"
                                    objdb["userID"]=Current_User
                                    objdb["progress"]="1"
                                    objdb["statuscomplete"]=false
                                    mFirebaseFirestore.collection("/user/facilities/apartments/"
                                            +Current_User+"/"+"apartments")
                                            .document("apartment"+tekcount.toString())
                                            .set(objdb, SetOptions.merge())
                                            .addOnSuccessListener {succes->
                                                dialog.dismiss()
                                                Navigation.findNavController(it).navigate(R.id.generalRoomTypeFragment, null)

                                            }.addOnFailureListener { failure->
                                                dialog.dismiss()
                                                Log.e("FailureCloud",failure.toString())
                                            }




                                }



                            }
                        }




                    }

                    R.id.facility_hotel -> {

                        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)!!
                        with(sharedPref.edit()) {
                            putString(preference_file_key, "hotel")
                            apply()
                        }



                        val myCollectionReference=mFirebaseFirestore.collection("/user/facilities/hotels/"
                                +Current_User+"/"+"hotels")
                        myCollectionReference.get().addOnCompleteListener {task ->

                            if (task.isSuccessful){


                                val dbcount=    task.result.size()
                                val tekcount=dbcount+1
                                if (dbcount==0 ) {



                                    val objdb=HashMap<String,Any?>()
                                    objdb["type"]="hotels"
                                    objdb["userID"]=Current_User
                                    objdb["progress"]="1"
                                    objdb["statuscomplete"]=false
                                    mFirebaseFirestore.collection("/user/facilities/hotels/"
                                            +Current_User+"/"+"hotels")
                                            .document("hotel1")
                                            .set(objdb, SetOptions.merge())
                                            .addOnSuccessListener {succes->
                                                dialog.dismiss()
                                                Navigation.findNavController(it).navigate(R.id.generalRoomTypeFragment, null)

                                            }.addOnFailureListener { failure->
                                                Log.e("FailureCloud",failure.toString())
                                                dialog.dismiss()
                                            }




                                }else{




                                    val objdb=HashMap<String,Any?>()
                                    objdb["type"]="hotels"
                                    objdb["userID"]=Current_User
                                    objdb["progress"]="1"
                                    objdb["statuscomplete"]=false
                                    mFirebaseFirestore.collection("/user/facilities/hotels"
                                            +Current_User+"/"+"hotels")
                                            .document("hotel"+tekcount.toString())
                                            .set(objdb, SetOptions.merge())
                                            .addOnSuccessListener {succes->
                                                dialog.dismiss()
                                                Navigation.findNavController(it).navigate(R.id.generalRoomTypeFragment, null)

                                            }.addOnFailureListener { failure->
                                                dialog.dismiss()
                                                Log.e("FailureCloud",failure.toString())
                                            }





                                }


                            }
                        }




                    }


                }






            }

        }


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
        fun newInstance() =EntryFormoneFragment()

        var myuserID=""
    }
}
