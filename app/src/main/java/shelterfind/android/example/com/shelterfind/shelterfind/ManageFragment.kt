package shelterfind.android.example.com.shelterfind.shelterfind

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_manage.*
import kotlinx.android.synthetic.main.fragment_manage.view.*
import shelterfind.android.example.com.shelterfind.shelterfind.MainActivity.Companion.Current_User
import shelterfind.android.example.com.shelterfind.shelterfind.adaptors.ManageAdaptor
import shelterfind.android.example.com.shelterfind.shelterfind.model.CloudData


class ManageFragment : Fragment() {
    private var dataRecyclerView:RecyclerView?=null
    private var recyclerViewAdapter: ManageAdaptor? = null
    lateinit var  mFirebaseFirestore:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mFirebaseFirestore= FirebaseFirestore.getInstance()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataRecyclerView=view.recycle_manage_view

        add_facility_fab.setOnClickListener {

            Navigation.findNavController(it).navigate(R.id.entryFormoneFragment, null)
        }



        my_toolba1r.setNavigationOnClickListener {

            Navigation.findNavController(it).navigate(R.id.profileFragment, null)
        }

        val dbcloud:ArrayList<CloudData>?= ArrayList()
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

                                    if (basedocumentChange.document.getBoolean("statuscomplete")==true){
                                        val managerData=basedocumentChange.document.toObject(CloudData::class.java)
                                        dbcloud!!.add(managerData)

                                        recyclerViewAdapter = ManageAdaptor(dbcloud,this.context!!)
                                        dataRecyclerView?.layoutManager = LinearLayoutManager(this.context)
                                        dataRecyclerView?.adapter = recyclerViewAdapter

                                    }

                                }
                            }





                        }


                    }



        }





    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode==Activity.RESULT_OK){

            if (data!=null){

             if( data.getStringExtra("Delete") =="true"){

                 Snackbar.make(view!!,"Facility Successfully Deleted",Snackbar.LENGTH_LONG).show()

             }


            }

        }

    }

    companion object {

        @JvmStatic
        fun newInstance() =
                ManageFragment()
    }
}
