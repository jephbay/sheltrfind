package shelterfind.android.example.com.shelterfind.shelterfind

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home_filter.*
import kotlinx.android.synthetic.main.fragment_hostel.*
import shelterfind.android.example.com.shelterfind.shelterfind.R
import shelterfind.android.example.com.shelterfind.shelterfind.adaptors.HostelAdaptor
import shelterfind.android.example.com.shelterfind.shelterfind.model.CloudData
import shelterfind.android.example.com.shelterfind.shelterfind.viewmodel.HostelViewModel


class HostelFragment : Fragment(), HostelAdaptor.OnItemClickListener {


    override fun onItemClick(data: CloudData) {

    }


    private var dataRecyclerView:EmptyRecyclerView?=null
    private var recyclerViewAdapter: HostelAdaptor? = null

    private var listmyData:ArrayList<Data>?=ArrayList()
    lateinit var mFirebaseFirestore: FirebaseFirestore
    lateinit var mViewModel: HostelViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





        mViewModel= ViewModelProviders.of(this).get(HostelViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hostel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        dataRecyclerView= rvDatahostel as EmptyRecyclerView?





        mViewModel.getArrayCloudList().observe(this, Observer {cloudata->


            recyclerViewAdapter = HostelAdaptor(cloudata!!, this)
            dataRecyclerView?.layoutManager = LinearLayoutManager(this.context)
            dataRecyclerView?.adapter = recyclerViewAdapter
            //dataRecyclerView?.setEmptyView(empty_view_hostel)




        })



    }



    override fun onAttach(context: Context) {
        super.onAttach(context)

    }


    companion object {

        @JvmStatic
        fun newInstance() =
                HostelFragment()
    }
}
