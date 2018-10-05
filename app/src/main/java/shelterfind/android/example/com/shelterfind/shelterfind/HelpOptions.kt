package shelterfind.android.example.com.shelterfind.shelterfind


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_help_topics.*

class HelpOptions : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v=inflater.inflate(R.layout.fragment_help_topics,container,false)
        return v
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val toolbar = view.findViewById<Toolbar>(R.id.help_back) as Toolbar

        toolbar.setNavigationOnClickListener {

            Navigation.findNavController(it).navigate(R.id.profileFragment, null)
        }

        help_options.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.helpNewFacility, null)
        }
        edit_facility.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.helpEditFacility, null)
        }
        help_overview.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.helpOverview, null)
        }
        help_create_accounts.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.helpUserAccounts, null)
        }




    }


}
