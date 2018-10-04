package shelterfind.android.example.com.shelterfind.shelterfind

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_all.*
import shelterfind.android.example.com.shelterfind.shelterfind.R
import shelterfind.android.example.com.shelterfind.shelterfind.adaptors.HomeRecyclerViewAdaptor
import shelterfind.android.example.com.shelterfind.shelterfind.model.CloudData
import shelterfind.android.example.com.shelterfind.shelterfind.viewmodel.MyViewModel


class AllFragment : Fragment(), HomeRecyclerViewAdaptor.OnItemClickListener {



    override fun onItemClick(data: CloudData) {

        mData=data

        Navigation.findNavController(view!!).navigate(R.id.viewFragment)

    }

    private var listener: OnFragmentInteractionListener? = null

    private var dataRecyclerView:EmptyRecyclerView?=null
    private var recyclerViewAdapter: HomeRecyclerViewAdaptor? = null

    lateinit var mViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        mViewModel= ViewModelProviders.of(this).get(MyViewModel::class.java)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_all, container, false)
    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        dataRecyclerView= rvDataall as EmptyRecyclerView?



        mViewModel.getArrayCloudList().observe(this, Observer { cloudata ->


            recyclerViewAdapter = HomeRecyclerViewAdaptor(cloudata!!, this)
            dataRecyclerView?.layoutManager = LinearLayoutManager(this.context)
            dataRecyclerView?.adapter = recyclerViewAdapter
            //dataRecyclerView?.setEmptyView(empty_view)


        })








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

        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                AllFragment()


        var mData:CloudData?=null
    }
}
