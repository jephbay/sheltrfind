package shelterfind.android.example.com.shelterfind.shelterfind

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.Navigation
import com.afollestad.materialdialogs.MaterialDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.fragment_hostelprice.*
import kotlinx.android.synthetic.main.fragment_hostelprice.view.*
import kotlinx.android.synthetic.main.monthly_layout.view.*
import kotlinx.android.synthetic.main.peroid_layout.view.*
import kotlinx.android.synthetic.main.price_per_night_layout.*
import kotlinx.android.synthetic.main.price_per_night_layout.view.*
import kotlinx.android.synthetic.main.yearly_layout.view.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import shelterfind.android.example.com.shelterfind.shelterfind.MainActivity.Companion.Current_User


class PriceHostelFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    val myKitchen:MyKitchen=MyKitchen()

    lateinit var mFirebaseFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        totalprice.clear()
        pricepernightMap.clear()
        priceperperoid.clear()
        yearly.clear()
        monthly.clear()

        mFirebaseFirestore= FirebaseFirestore.getInstance()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       val view=inflater.inflate(R.layout.fragment_hostelprice, container, false)


        val prefs= activity?.getPreferences(Context.MODE_PRIVATE)

        val mixed_dorm:String?=prefs!!.getString("mixed_dorm","none")
        val family_dorm:String? =prefs.getString("family_room","none")
        val male_dorm:String?   =prefs.getString("male_dorm","none")
        val femal_dorm:String?   =prefs.getString("female_dorm","none")
        val four_bed:String?=prefs.getString("four_bed","none")
        val three_bed:String? =prefs.getString("three_bed","none")
        val double_bed:String?   =prefs.getString("double_bed","none")
        val single_bed:String?   =prefs.getString("single_bed","none")

        Log.v("MSFG",single_bed.plus(double_bed).plus(three_bed).plus(four_bed).plus(femal_dorm).plus(male_dorm).plus(family_dorm).plus(mixed_dorm))
        if(single_bed != "none"){
            view.single_room_price.visibility=View.VISIBLE
            view.month_single_room_price.visibility=View.VISIBLE
            view.yearly_single_room_price.visibility=View.VISIBLE


        }
        if(double_bed != "none"){
            view.double_room_price.visibility=View.VISIBLE
            view.month_double_room_price.visibility=View.VISIBLE
            view.yearly_double_room_price.visibility=View.VISIBLE


        }
        if(three_bed != "none"){
            view.three_room_price.visibility=View.VISIBLE
            view.month_three_room_price.visibility=View.VISIBLE
            view.yearly_three_room_price.visibility=View.VISIBLE

        }
        if(four_bed != "none"){

            view.four_room_price.visibility=View.VISIBLE
            view.month_four_room_price.visibility=View.VISIBLE
            view.yearly_four_room_price.visibility=View.VISIBLE

        }
        if(mixed_dorm != "none"){
            view.mixed_room_price.visibility=View.VISIBLE
            view.month_mixed_room_price.visibility=View.VISIBLE
            view.yearly_mixed_room_price.visibility=View.VISIBLE

        }
        if(femal_dorm != "none"){
            view.femal_room_price.visibility=View.VISIBLE
            view.month_femal_room_price.visibility=View.VISIBLE
            view.yearly_femal_room_price.visibility=View.VISIBLE

        }
        if(male_dorm != "none"){
            view.male_room_price.visibility=View.VISIBLE
            view.month_male_room_price.visibility=View.VISIBLE
            view.yearly_male_room_price.visibility=View.VISIBLE

        }
        if(family_dorm != "none"){
            view.family_room_price.visibility=View.VISIBLE
            view.month_family_room_price.visibility=View.VISIBLE
            view.yearly_family_room_price.visibility=View.VISIBLE

        }


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        totalprice.clear()
        pricepernightMap.clear()
        priceperperoid.clear()
        yearly.clear()
        monthly.clear()


        val prefs= activity?.getPreferences(Context.MODE_PRIVATE)

        val mixed_dorm:String?=prefs!!.getString("mixed_dorm","none")
        val family_dorm:String? =prefs.getString("family_room","none")
        val male_dorm:String?   =prefs.getString("male_dorm","none")
        val femal_dorm:String?   =prefs.getString("female_dorm","none")
        val four_bed:String?=prefs.getString("four_bed","none")
        val three_bed:String? =prefs.getString("three_bed","none")
        val double_bed:String?   =prefs.getString("double_bed","none")
        val single_bed:String?   =prefs.getString("single_bed","none")




        view.pricepernight_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->

if (isChecked){
    view.pricepernight_row.setBackgroundColor(Color.parseColor("#ea1535")  )

}else{
    view.pricepernight_row.setBackgroundColor( Color.parseColor("#807f7f"))
}


}

        view.priceperperiod_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){
                view.priceperperiod_row.setBackgroundColor(Color.parseColor("#ea1535")  )

            }else{
                view.priceperperiod_row.setBackgroundColor( Color.parseColor("#807f7f"))
            }


        }


        val toolbar = view.findViewById<android.widget.Toolbar>(R.id.my_toolbar) as Toolbar
        toolbar.setNavigationOnClickListener {

            Navigation.findNavController(it).navigate(R.id.placeAvailability, null)
        }


        val mycheck_roomtype_money:HashMap<String,Int>?=HashMap()
        val mycheck_roomtype_peroid:HashMap<String,String>?=HashMap()

        val progressBar: ProgressBar?=view.findViewById<ProgressBar>(R.id.progressBar)


            myKitchen.SetProgress(progressBar!!,40)



        val buttonnext: FloatingActionButton?= view.findViewById<FloatingActionButton>(R.id.button_next)


        buttonnext?.setOnClickListener{
            val dialog = MaterialDialog(this@PriceHostelFragment.context!!)
                    .title(R.string.room_type_title)
                    .message(R.string.roomtype_progress_report)



            totalprice.clear()
            pricepernightMap.clear()
            priceperperoid.clear()
            yearly.clear()
            monthly.clear()
            if (!view.pricepernight_checkbox.isChecked && !view.priceperperiod_checkbox.isChecked){

                Snackbar.make(view,"Please Select at least one price type to continue",Snackbar.LENGTH_LONG).show()

                return@setOnClickListener
            }
            else{


                when {

                    view.pricepernight_checkbox.isChecked -> {
                        if (single_bed != "none" && view.single_room_enter_amount.text.isEmpty()) {
                            Snackbar.make(view, "Please Fill in the price to continue", Snackbar.LENGTH_LONG).show()
                            return@setOnClickListener
                        } else if (double_bed != "none" && view.double_room_enter_amount.text.isEmpty()) {

                            Snackbar.make(view, "Please Fill in the price to continue", Snackbar.LENGTH_LONG).show()
                            return@setOnClickListener
                        } else if (three_bed != "none" && view.three_beds_room_enter_amount.text.isEmpty()) {

                            Snackbar.make(view, "Please Fill in the price to continue", Snackbar.LENGTH_LONG).show()
                            return@setOnClickListener
                        } else if (four_bed != "none" && view.four_beds_enter_amount.text.isEmpty()) {

                            Snackbar.make(view, "Please Fill in the price to continue", Snackbar.LENGTH_LONG).show()
                            return@setOnClickListener
                        } else if (femal_dorm != "none" && view.femal_room_enter_amount.text.isEmpty()) {

                            Snackbar.make(view, "Please Fill in the price to continue", Snackbar.LENGTH_LONG).show()
                            return@setOnClickListener
                        } else if (male_dorm != "none" && view.male_room_enter_amount.text.isEmpty()) {

                            Snackbar.make(view, "Please Fill in the price to continue", Snackbar.LENGTH_LONG).show()
                            return@setOnClickListener
                        } else if (family_dorm != "none" && view.family_room_enter_amount.text.isEmpty()) {

                            Snackbar.make(view, "Please Fill in the price to continue", Snackbar.LENGTH_LONG).show()
                            return@setOnClickListener
                        } else if (mixed_dorm != "none" && view.mixed_room_enter_amount.text.isEmpty()) {

                            Snackbar.make(view, "Please Fill in the price to continue", Snackbar.LENGTH_LONG).show()
                            return@setOnClickListener
                        } else {
                            dialog.show()
                            when {
                                single_bed != "none" && !view.single_room_enter_amount.text.isEmpty() -> {
                                    pricepernightMap["single bed"] = view.single_room_enter_amount.text.toString()

                                }
                            }

                            when {
                                double_bed != "none" && !view.double_room_enter_amount.text.isEmpty() -> {
                                    pricepernightMap["double bed"] = view.double_room_enter_amount.text.toString()

                                }
                            }

                            when {
                                three_bed != "none" && !view.three_beds_room_enter_amount.text.isEmpty() -> {
                                    pricepernightMap["three beds"] = view.three_beds_room_enter_amount.text.toString()

                                }
                            }
                            when {
                                four_bed != "none" && !view.four_beds_enter_amount.text.isEmpty() -> {
                                    pricepernightMap["four beds"] = view.four_beds_enter_amount.text.toString()

                                }
                            }
                            when {
                                femal_dorm != "none" && !view.femal_room_enter_amount.text.isEmpty() -> {
                                    pricepernightMap["female dorm"] = view.femal_room_enter_amount.text.toString()

                                }
                            }
                            when {
                                male_dorm != "none" && !view.male_room_enter_amount.text.isEmpty() -> {
                                    pricepernightMap["male dorm"] = view.male_room_enter_amount.text.toString()

                                }
                            }
                            when {
                                mixed_dorm != "none" && !view.mixed_room_enter_amount.text.isEmpty() -> {
                                    pricepernightMap["mixed dorm"] = view.mixed_room_enter_amount.text.toString()

                                }
                            }
                            when{
                                family_dorm != "none" && !view.family_room_enter_amount.text.isEmpty() -> {
                                    pricepernightMap["family dorm"] = view.family_room_enter_amount.text.toString()

                                }

                            }

                            if (!pricepernightMap.isEmpty()) {
                                totalprice["pricepernight"] = pricepernightMap

                            }else{
                                totalprice["pricepernight"] = ""

                            }

                        }

                    }
                }

                when{
                    view.priceperperiod_checkbox.isChecked->{

                        if (!view.monthly_checkbox.isChecked && !view.yearly_checkbox.isChecked  ){

                            Snackbar.make(view,"Please select whether monthly or yearly or both to continue",Snackbar.LENGTH_LONG).show()
                            return@setOnClickListener

                        }else{


                            when {
                                view.monthly_checkbox.isChecked -> {

                                    if (single_bed != "none" && view.month_single_room_enter_amount.text.isEmpty()) {
                                        Snackbar.make(view, "Please Fill in the price to continue", Snackbar.LENGTH_LONG).show()
                                        return@setOnClickListener
                                    } else if (double_bed != "none" && view.month_double_room_enter_price.text.isEmpty()) {

                                        Snackbar.make(view, "Please Fill in the price to continue", Snackbar.LENGTH_LONG).show()
                                        return@setOnClickListener
                                    } else if (three_bed != "none" && view.month_three_beds_room_enter_price.text.isEmpty()) {

                                        Snackbar.make(view, "Please Fill in the price to continue", Snackbar.LENGTH_LONG).show()
                                        return@setOnClickListener
                                    } else if (four_bed != "none" && view.month_four_beds_enter_amount.text.isEmpty()) {

                                        Snackbar.make(view, "Please Fill in the price to continue", Snackbar.LENGTH_LONG).show()
                                        return@setOnClickListener
                                    } else if (femal_dorm != "none" && view.month_femal_room_enter_amount.text.isEmpty()) {

                                        Snackbar.make(view, "Please Fill in the price to continue", Snackbar.LENGTH_LONG).show()
                                        return@setOnClickListener
                                    } else if (male_dorm != "none" && view.month_male_room_enter_amount.text.isEmpty()) {

                                        Snackbar.make(view, "Please Fill in the price to continue", Snackbar.LENGTH_LONG).show()
                                        return@setOnClickListener
                                    } else if (family_dorm != "none" && view.month_family_room_enter_amount.text.isEmpty()) {

                                        Snackbar.make(view, "Please Fill in the price to continue", Snackbar.LENGTH_LONG).show()
                                        return@setOnClickListener
                                    } else if (mixed_dorm != "none" && view.month_mixed_room_enter_amount.text.isEmpty()) {

                                        Snackbar.make(view, "Please Fill in the price to continue", Snackbar.LENGTH_LONG).show()
                                        return@setOnClickListener
                                    } else {
                                        dialog.show()
                                        when {
                                            single_bed != "none" && !view.month_single_room_enter_amount.text.isEmpty() -> {
                                                monthly["single bed"] = view.month_single_room_enter_amount.text.toString()

                                            }
                                        }
                                        when {

                                            double_bed != "none" && !view.month_double_room_enter_price.text.isEmpty() -> {
                                                monthly["double bed"] = view.month_double_room_enter_price.text.toString()

                                            }
                                        }
                                        when {
                                            three_bed != "none" && !view.month_three_beds_room_enter_price.text.isEmpty() -> {
                                                monthly["three beds"] = view.month_three_beds_room_enter_price.text.toString()

                                            }
                                        }
                                        when {
                                            four_bed != "none" && !view.month_four_beds_enter_amount.text.isEmpty() -> {
                                                monthly["four beds"] = view.month_four_beds_enter_amount.text.toString()

                                            }
                                        }
                                        when {
                                            femal_dorm != "none" && !view.month_femal_room_enter_amount.text.isEmpty() -> {
                                                monthly["female dorm"] = view.month_femal_room_enter_amount.text.toString()

                                            }
                                        }
                                        when {
                                            male_dorm != "none" && !view.month_male_room_enter_amount.text.isEmpty() -> {
                                                monthly["male dorm"] = view.month_male_room_enter_amount.text.toString()

                                            }
                                        }
                                        when {
                                            mixed_dorm != "none" && !view.month_mixed_room_enter_amount.text.isEmpty() -> {
                                                monthly["mixed dorm"] = view.month_mixed_room_enter_amount.text.toString()

                                            }
                                        }
                                        when {
                                            family_dorm != "none" && !view.month_family_room_enter_amount.text.isEmpty() -> {
                                                monthly["family dorm"] = view.month_family_room_enter_amount.text.toString()

                                            }

                                        }

                                    }


                                }

                            }

                            when{
                                view.yearly_checkbox.isChecked->{

                                    if(single_bed!="none" &&   view.yearly_single_room_enter_amount.text.isEmpty()){
                                        Snackbar.make(view,"Please Fill in the price to continue",Snackbar.LENGTH_LONG).show()
                                        return@setOnClickListener
                                    }
                                    else if (double_bed!="none" && view.yearly_double_room_enter_price.text.isEmpty() ){

                                        Snackbar.make(view,"Please Fill in the price to continue",Snackbar.LENGTH_LONG).show()
                                        return@setOnClickListener
                                    }else if(three_bed!="none" && view.yearly_three_beds_room_enter_price.text.isEmpty() ){

                                        Snackbar.make(view,"Please Fill in the price to continue",Snackbar.LENGTH_LONG).show()
                                        return@setOnClickListener
                                    }else if(four_bed!="none" && view.yearly_four_beds_enter_amount.text.isEmpty() ){

                                        Snackbar.make(view,"Please Fill in the price to continue",Snackbar.LENGTH_LONG).show()
                                        return@setOnClickListener
                                    }else if(femal_dorm!="none" && view.yearly_femal_room_enter_amount.text.isEmpty() ){

                                        Snackbar.make(view,"Please Fill in the price to continue",Snackbar.LENGTH_LONG).show()
                                        return@setOnClickListener
                                    }else if(male_dorm!="none" && view.yearly_male_room_enter_amount.text.isEmpty() ){

                                        Snackbar.make(view,"Please Fill in the price to continue",Snackbar.LENGTH_LONG).show()
                                        return@setOnClickListener
                                    }else if(family_dorm!="none" && view.yearly_family_room_enter_amount.text.isEmpty() ){

                                        Snackbar.make(view,"Please Fill in the price to continue",Snackbar.LENGTH_LONG).show()
                                        return@setOnClickListener
                                    }else if(mixed_dorm!="none" && view.yearly_mixed_room_enter_amount.text.isEmpty() ){

                                        Snackbar.make(view,"Please Fill in the price to continue",Snackbar.LENGTH_LONG).show()
                                        return@setOnClickListener
                                    }else {
                                        dialog.show()
                                        when {
                                            single_bed != "none" && !view.yearly_single_room_enter_amount.text.isEmpty() -> {
                                                yearly["single bed"] = view.yearly_single_room_enter_amount.text.toString()

                                            }
                                        }
                                        when {
                                            double_bed != "none" && !view.yearly_double_room_enter_price.text.isEmpty() -> {
                                                yearly["double bed"] = view.yearly_double_room_enter_price.text.toString()

                                            }
                                        }
                                        when {
                                            three_bed != "none" && !view.yearly_three_beds_room_enter_price.text.isEmpty() -> {
                                                yearly["three beds"] = view.yearly_three_beds_room_enter_price.text.toString()

                                            }
                                        }
                                        when {
                                            four_bed != "none" && !view.yearly_four_beds_enter_amount.text.isEmpty() -> {
                                                yearly["four beds"] = view.yearly_four_beds_enter_amount.text.toString()

                                            }
                                        }
                                        when {
                                            femal_dorm != "none" && !view.yearly_femal_room_enter_amount.text.isEmpty() -> {
                                                yearly["female dorm"] = view.yearly_femal_room_enter_amount.text.toString()

                                            }
                                        }
                                        when {
                                            male_dorm != "none" && !view.yearly_male_room_enter_amount.text.isEmpty() -> {
                                                yearly["male dorm"] = view.yearly_male_room_enter_amount.text.toString()

                                            }
                                        }
                                        when {
                                            mixed_dorm != "none" && !view.yearly_mixed_room_enter_amount.text.isEmpty() -> {
                                                yearly["mixed dorm"] = view.yearly_mixed_room_enter_amount.text.toString()

                                            }
                                        }
                                        when{
                                            family_dorm != "none" && !view.yearly_family_room_enter_amount.text.isEmpty() -> {
                                                yearly["family dorm"] = view.yearly_family_room_enter_amount.text.toString()

                                            }

                                        }

                                    }




                                }

                            }


                            if (monthly.isNotEmpty() ){

                                priceperperoid["monthly"]=monthly
                            }else{
                                priceperperoid["monthly"]=""
                            }

                            if(yearly.isNotEmpty()){
                                priceperperoid["yearly"]=yearly
                            }else{
                                priceperperoid["yearly"]=""
                            }


                            if (priceperperoid.isNotEmpty()){

                                totalprice["priceperperiod"]= priceperperoid
                            }else{
                                totalprice["priceperperiod"]= ""
                            }



                        }




                    }
                }


                val myCollectionReference=mFirebaseFirestore.collection("/user/facilities/hostels/"
                        +Current_User+"/"+"hostels")
                myCollectionReference.get().addOnCompleteListener {task ->

                    if (task.isSuccessful){




                        val objdb=HashMap<String,Any?>()

                        val pricedb=HashMap<String,Any>()
                        pricedb["roomtypeprice"]=""
                        mFirebaseFirestore.collection("/user/facilities/hostels/"
                                +Current_User+"/"+"hostels")
                                .document(task.result.last().id)
                                .set(pricedb, SetOptions.merge())
                                .addOnSuccessListener { succes ->

                                }

                        pricedb["roomtypeprice"] =totalprice
                        pricedb["progress"]="50"
                        mFirebaseFirestore.collection("/user/facilities/hostels/"
                                +Current_User+"/"+"hostels")
                                .document(task.result.last().id)
                                .set(pricedb, SetOptions.merge())
                                .addOnSuccessListener {succes->
                                    dialog.dismiss()
                                    Navigation.findNavController(it).navigate(R.id.overviewFragment, null)
                                }.addOnFailureListener { failure->
                                    dialog.dismiss()
                                    Log.e("FailureCloud",failure.toString())
                                }


                    }
                }










            }



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
        @JvmStatic
        fun newInstance() =
                PriceHostelFragment()

        var totalprice=HashMap<String,Any>()
        var pricepernightMap=HashMap<String,String>()
        var priceperperoid=HashMap<String,Any>()
        var yearly=HashMap<String,String>()
        var monthly=HashMap<String,String>()
    }
}
