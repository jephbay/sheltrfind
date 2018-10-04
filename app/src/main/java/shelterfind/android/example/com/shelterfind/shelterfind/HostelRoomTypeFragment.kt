package shelterfind.android.example.com.shelterfind.shelterfind

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.Navigation
import com.afollestad.materialdialogs.MaterialDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import android.support.v7.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_account_settings.*
import kotlinx.android.synthetic.main.fragment_hostel_room_type.*
import shelterfind.android.example.com.shelterfind.shelterfind.MainActivity.Companion.Current_User


class HostelRoomTypeFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    val preference_file_key = "MYDESTINATION"
    val myKitchen: MyKitchen = MyKitchen()
    lateinit var mFirebaseFirestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mFirebaseFirestore= FirebaseFirestore.getInstance()

        roomtypeArray.clear()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,

                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hostel_room_type, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val toolbar = view.findViewById<android.widget.Toolbar>(R.id.my_toolbar) as Toolbar


        toolbar.setNavigationOnClickListener {

            Navigation.findNavController(it).navigate(R.id.entryFormoneFragment, null)

        }


        val progressBar: ProgressBar? = view.findViewById<ProgressBar>(R.id.progressBar)


            myKitchen.SetProgress(progressBar!!, 10)




        val buttonnext: FloatingActionButton? = view.findViewById<FloatingActionButton>(R.id.button_next_hostel_roomtype)

        val prefs= activity?.getPreferences(Context.MODE_PRIVATE)


        with(prefs!!.edit()) {
            remove("single_bed")
            remove("double_bed")
            remove("mixed_dorm")
            remove("three_bed")
            remove("four_bed")
            remove("family_room")
            remove("male_dorm")
            remove("female_dorm")

            apply()
        }








        val destin = prefs.getString(preference_file_key,"none")





        buttonnext?.setOnClickListener {
            val dialog = MaterialDialog(this@HostelRoomTypeFragment.context!!)
                    .title(R.string.room_type_title)
                    .message(R.string.roomtype_progress_report)



            when{
                        !single_bed.isChecked &&
                        !double_bed.isChecked &&
                        !three_beds.isChecked &&
                        !four_beds.isChecked&&
                        !female_dorm.isChecked&&
                        !male_dorm.isChecked&&
                        !mixed_dorm.isChecked&&
                        !family_room.isChecked ->{
                    Snackbar.make(
                            view, // Parent view
                            " Choose one of the Room Types to Continue", // Message to show
                            Snackbar.LENGTH_LONG // How long to display the message.
                    ).show()
                }
                else ->{

            when {

                single_bed.isChecked -> {

                    roomtypeArray.add(single_bed.textOn.toString())
                    with(prefs.edit()) {
                        putString("single_bed", "true")
                        apply()
                    }
                }

            }

                    when {

                        double_bed.isChecked -> {

                            roomtypeArray.add(double_bed.textOn.toString())
                            with(prefs.edit()) {
                                putString("double_bed", "true")
                                apply()
                            }

                        }

                    }


                    when {
                        mixed_dorm.isChecked -> {

                            roomtypeArray.add(mixed_dorm.textOn.toString())
                            with(prefs.edit()) {
                                putString("mixed_dorm", "true")
                                apply()
                            }

                        }

                    }

                    when {

                        three_beds.isChecked -> {

                            roomtypeArray.add(three_beds.textOn.toString())
                            with(prefs.edit()) {
                                putString("three_bed", "true")
                                apply()
                            }

                        }

                    }

                    when {


                        four_beds.isChecked -> {

                            roomtypeArray.add(four_beds.textOn.toString())
                            with(prefs.edit()) {
                                putString("four_bed", "true")
                                apply()
                            }
                        }

                    }

                    when {

                        family_room.isChecked -> {

                            roomtypeArray.add(family_room.textOn.toString())
                            with(prefs.edit()) {
                                putString("family_room", "true")
                                apply()
                            }

                        }

                    }


                    when {

                        female_dorm.isChecked -> {

                            roomtypeArray.add(female_dorm.textOn.toString())
                            with(prefs.edit()) {
                                putString("female_dorm", "true")
                                apply()
                            }
                        }

                    }

                    when{
                male_dorm.isChecked->{

                    roomtypeArray.add(male_dorm.textOn.toString())
                    with(prefs.edit()) {
                        putString("male_dorm", "true")
                        apply()
                    }

                }
            }


                dialog.show()

                    when(destin!!){
                        "hostel"->{

                            val myCollectionReference=mFirebaseFirestore.collection("/user/facilities/hostels/"
                                    + Current_User +"/"+"hostels")
                            myCollectionReference.get().addOnCompleteListener {task ->

                                if (task.isSuccessful){

                                    val objdb=HashMap<String,Any?>()


                                    val roomdb=HashMap<String,Any>()
                                    roomdb["roomtype"] =roomtypeArray.toTypedArray().toList()
                                    roomdb["progress"]="10"
                                    mFirebaseFirestore.collection("/user/facilities/hostels/"
                                            +Current_User+"/"+"hostels")
                                            .document(task.result.last().id)
                                            .set(roomdb, SetOptions.merge())
                                            .addOnSuccessListener {succes->
                                                dialog.dismiss()
                                                Navigation.findNavController(it).navigate(R.id.placeAvailability, null)
                                            }.addOnFailureListener { failure->
                                                dialog.dismiss()

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
            fun newInstance() =
                    HostelRoomTypeFragment()

            var roomtypeArray=ArrayList<String>()


        }
    }



