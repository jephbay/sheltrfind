package shelterfind.android.example.com.shelterfind.shelterfind

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.chip.ChipGroup
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import shelterfind.android.example.com.shelterfind.shelterfind.R
import shelterfind.android.example.com.shelterfind.shelterfind.R.layout.fragment_home
import shelterfind.android.example.com.shelterfind.shelterfind.adaptors.CustomPagerAdaptor


class HomeFragment : Fragment() {


    private var listener: OnFragmentInteractionListener? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



      val  adapterViewPager = CustomPagerAdaptor(childFragmentManager)

        vpPager.adapter=adapterViewPager

        view_pager_tab.setupWithViewPager(vpPager)

        category.setOnClickListener {
            val options = NavOptions.Builder()
                    .setEnterAnim(R.anim.fui_slide_in_right)
                    .setPopExitAnim(R.anim.fui_slide_out_left)
                    .build()

            Navigation.findNavController(it).navigate(R.id.theFilterFragment, null,options)

        }



        places_fab.setOnClickListener {



            Navigation.findNavController(view).navigate(R.id.placesFragment, null)

        }



    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }



    override fun onResume() {
        super.onResume()






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
        fun newInstance() = HomeFragment()



    }
}
