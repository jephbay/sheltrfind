package shelterfind.android.example.com.shelterfind.shelterfind.adaptors

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_card_view.view.*
import kotlinx.android.synthetic.main.manage_layout_recycle.view.*
import shelterfind.android.example.com.shelterfind.shelterfind.MainActivity.Companion.Current_User
import shelterfind.android.example.com.shelterfind.shelterfind.R
import shelterfind.android.example.com.shelterfind.shelterfind.model.CloudData

class ManageAdaptor(data: ArrayList<CloudData>, context: Context):
        RecyclerView.Adapter<ManageAdaptor.RecyclerViewHolder>() {


    val mcontext=context

lateinit var  mFirebaseFirestore:FirebaseFirestore
    private var listData: ArrayList<CloudData> = data
     val PICK_REQUEST = 1


    interface OnItemClickListener {
        fun onItemClick(data: CloudData)
    }




    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): RecyclerViewHolder {
        mFirebaseFirestore= FirebaseFirestore.getInstance()
        return RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.manage_layout_recycle, parent, false))

    }



    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        val currentData: CloudData = listData[position]




        val nameData = currentData.overview

        val moverview=nameData as HashMap<String,String>

        val imageData = android.net.Uri.parse(currentData.captionurl)


        holder.mName.text = moverview["title"]
        Picasso.get().load(imageData) .fit().into(holder.mImage)




        holder.delete_manage.setOnClickListener {
            Log.v("MDEL","About to  yerr")
        val mtype= currentData.type as String

            Log.v("MDEL",mtype)
            val mName= moverview["title"]
            val description=moverview["description"]

            mFirebaseFirestore.collection("user/facilities/"+mtype)
                    .document(Current_User)
                    .collection(mtype)
                    .addSnapshotListener { basequerySnapshot, basefirebaseFirestoreException ->

                        if ( basefirebaseFirestoreException!=null){

                        }else {

                            for (basedocumentChange: DocumentChange in basequerySnapshot!!.documentChanges) {

                                if (basedocumentChange.document.exists()){
                                    Log.v("MDEL","Tooo to  yerr")

                                    val goverview=basedocumentChange.document["overview"] as HashMap<String,String>
                                    if (goverview["title"]==mName
                                            &&goverview["description"]==description  ){
                                        Log.v("MDEL","Exist to  yerr")
                                        val managerData=basedocumentChange.document
                                        mFirebaseFirestore.collection("user/facilities/"+mtype)
                                                .document(Current_User)
                                                .collection(mtype)
                                                .document(managerData.id)
                                                .delete()
                                                .addOnSuccessListener {success->

                                                    removeitem(position)

                                                    val intent: Intent =Intent().apply {
                                                        putExtra("isDeleted","true")
                                                        action=Intent.ACTION_SEND
                                                    }
                                                    startActivity(mcontext, intent,null)

                                                }


                                    }

                                }
                            }





                        }


                    }




        }

        holder.edit_manage.setOnClickListener {

            holder.edit_page_manage.visibility=View.VISIBLE


        }




        holder.toolbar_manage.setNavigationOnClickListener {


            holder.edit_page_manage.visibility=View.GONE

        }










    }


    override fun getItemCount(): Int {

        return listData.size
    }


    fun removeitem(position: Int) {
        listData.removeAt(position)
        notifyDataSetChanged()
    }


    class RecyclerViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {

        var mName = itemView.manage_name!!
        var mImage = itemView.manage_caption!!
        var delete_manage=itemView.delete_btn
        var edit_manage=itemView.edit_btn
        var toolbar_manage=itemView.my_toolbar
        var edit_page_manage=itemView.edit_ll

        }



}