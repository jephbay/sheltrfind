package shelterfind.android.example.com.shelterfind.shelterfind

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_all.*
import kotlinx.android.synthetic.main.fragment_profile.*
import shelterfind.android.example.com.shelterfind.shelterfind.MainActivity.Companion.Current_User
import shelterfind.android.example.com.shelterfind.shelterfind.R.layout.fragment_profile


class ProfileFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    lateinit var mFirebaseFirestore: FirebaseFirestore
    var checkifAnyExist=0
    var itemcount=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mFirebaseFirestore= FirebaseFirestore.getInstance()





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
                                        Log.v("GATI","Welcome not")

                                        Log.v("NOGF",basedocumentChange.document.toString())
                                        checkifAnyExist+=1
                                    }else{
                                        Log.v("GATI","Welcome not not not")
                                        checkifAnyExist=0
                                    }

                                }
                            }





                        }


                    }



        }





    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        profile_facility.setOnClickListener {

            Log.v("GATI",checkifAnyExist.toString())
            if(checkifAnyExist==0){

                Navigation.findNavController(it).navigate(R.id.entryFormoneFragment,null)

            }else{

                Navigation.findNavController(it).navigate(R.id.liftingStatus,null)
            }





        }

        profile_account.setOnClickListener {

            Navigation.findNavController(it).navigate(R.id.accountSettingsFragment,null)

        }

        profile_feedback.setOnClickListener {


            Navigation.findNavController(it).navigate(R.id.feedBackFragment, null)

        }

        profile_settings.setOnClickListener {

            Navigation.findNavController(it).navigate(R.id.settingsFragment, null)
        }

         profile_notification.setOnClickListener {

            Navigation.findNavController(it).navigate(R.id.notificationFragment, null)
        }

       profile_manange.setOnClickListener {

            Navigation.findNavController(it).navigate(R.id.manageFragment, null)
        }


    }

    // TODO: Rename method, update argument and hook method into UI event
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

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                ProfileFragment()
    }
}
