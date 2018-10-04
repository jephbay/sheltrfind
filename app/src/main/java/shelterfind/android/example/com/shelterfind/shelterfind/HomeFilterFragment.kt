package shelterfind.android.example.com.shelterfind.shelterfind

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_all.*
import kotlinx.android.synthetic.main.fragment_home_filter.*
import shelterfind.android.example.com.shelterfind.shelterfind.adaptors.HomeFilterAdaptor
import shelterfind.android.example.com.shelterfind.shelterfind.model.CloudData
import shelterfind.android.example.com.shelterfind.shelterfind.viewmodel.HomeViewModel


class HomeFilterFragment : Fragment(), HomeFilterAdaptor.OnItemClickListener {



    override fun onItemClick(data: CloudData) {

    }


    private var dataRecyclerView:EmptyRecyclerView?=null
    private var recyclerViewAdapter: HomeFilterAdaptor? = null

    private var listmyData:ArrayList<Data>?=ArrayList()
    lateinit var mFirebaseFirestore: FirebaseFirestore
    lateinit var mViewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





        mViewModel= ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        dataRecyclerView= rvDatahome as EmptyRecyclerView?






        mViewModel.getArrayCloudList().observe(this, Observer {cloudata->


            recyclerViewAdapter = HomeFilterAdaptor(cloudata!!, this)
            dataRecyclerView?.layoutManager = LinearLayoutManager(this.context)
            dataRecyclerView?.adapter = recyclerViewAdapter
           // dataRecyclerView?.setEmptyView(empty_view_homefilter)




        })




    }






    companion object {

        @JvmStatic
        fun newInstance() =
                HomeFilterFragment()
    }
}
