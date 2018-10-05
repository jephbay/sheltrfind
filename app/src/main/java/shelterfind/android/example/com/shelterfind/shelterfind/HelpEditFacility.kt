package shelterfind.android.example.com.shelterfind.shelterfind


import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_help_edit_facility.view.*
import java.io.File

class HelpEditFacility : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help_edit_facility, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<Toolbar>(R.id.help_edit_facility_back) as Toolbar

        toolbar.setNavigationOnClickListener {

            Navigation.findNavController(it).navigate(R.id.helpOptions, null)
        }

        view.help_edit_facility.settings.javaScriptEnabled=true
        val mfile: File =  File("//android_asset/help_edit_facility.html")
        val muri= Uri.fromFile(mfile)
        view. help_edit_facility.loadUrl(muri.toString())
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                HelpEditFacility().apply {
                    arguments = Bundle()
                }
    }
}
