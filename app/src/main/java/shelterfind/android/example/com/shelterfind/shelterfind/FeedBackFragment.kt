package shelterfind.android.example.com.shelterfind.shelterfind


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_feed_back.*


class FeedBackFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_feed_back, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        val toolbar = view.findViewById<Toolbar>(R.id.my_toolbar) as Toolbar

        toolbar.setNavigationOnClickListener {

            Navigation.findNavController(it).navigate(R.id.profileFragment, null)
        }

        img1.setOnClickListener {

            satisfaction.text=getString(R.string.dissatisfied)

        }


        img2.setOnClickListener {

            satisfaction.text=getString(R.string.normal)

        }


        img3.setOnClickListener {

            satisfaction.text=getString(R.string.satisfied)

        }


        img4.setOnClickListener {

            satisfaction.text=getString(R.string.verydissatisfied)

        }


        img5.setOnClickListener {

            satisfaction.text=getString(R.string.verysatisfied)

        }




    }

    companion object {

        @JvmStatic
        fun newInstance() =FeedBackFragment()
    }
}
