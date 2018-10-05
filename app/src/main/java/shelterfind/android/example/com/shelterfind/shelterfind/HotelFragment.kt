package shelterfind.android.example.com.shelterfind.shelterfind

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_hotel.*
import shelterfind.android.example.com.shelterfind.shelterfind.model.CloudData
import shelterfind.android.example.com.shelterfind.shelterfind.viewmodel.HotelViewModel


class HotelFragment : Fragment(),HotelAdaptor.OnItemClickListener {



    override fun onItemClick(data: CloudData) {

    }

    private var dataRecyclerView:EmptyRecyclerView?=null
    private var recyclerViewAdapter: HotelAdaptor? = null

    lateinit var mViewModel: HotelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





        mViewModel= ViewModelProviders.of(this).get(HotelViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hotel, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        dataRecyclerView= rvDatahotel as EmptyRecyclerView?








        mViewModel.getArrayCloudList().observe(this, Observer {cloudata->

            recyclerViewAdapter = HotelAdaptor(cloudata!!, this)
            dataRecyclerView?.layoutManager = LinearLayoutManager(this.context)
            dataRecyclerView?.adapter = recyclerViewAdapter
            //dataRecyclerView?.setEmptyView(empty_view_hotel)

        })





    }


    interface OnFragmentInteractionListener {

        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                HotelFragment()
    }
}
