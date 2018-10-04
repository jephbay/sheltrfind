package shelterfind.android.example.com.shelterfind.shelterfind


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*

import android.widget.Toolbar
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_the_filter.*
import java.util.*

import android.view.Menu


class TheFilterFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_the_filter, container, false)


    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        val toolbar = view.findViewById<Toolbar>(R.id.jeph_toolbar) as Toolbar








        toolbar.setNavigationOnClickListener {
            val options = NavOptions.Builder()
                    .setEnterAnim(R.anim.fui_slide_in_right)
                    .setPopExitAnim(R.anim.fui_slide_out_left)
                    .build()
            Navigation.findNavController(it).navigate(R.id.homeFragment, null,options)


        }

        val facility_selector=HashMap<String,String>()
        facility_selector.put("0","Hostels")
        facility_selector.put("1","Hotels")
        facility_selector.put("2","Home")
        facility_selector.put("3","Apartment")

        var mcounter=0

        i_button_back.setOnClickListener {

            mcounter-=1

            if (mcounter<0){

                mcounter=0
            }

            val change= facility_selector[mcounter.toString()]

            t_box.text=change

            when (mcounter){
                0->{
                    t_layout.visibility=View.GONE
                    t_layout2.visibility=View.VISIBLE
                }
                else ->{

                    t_layout.visibility=View.VISIBLE
                    t_layout2.visibility=View.GONE

                }

            }

        }
        i_button_forward.setOnClickListener {

            mcounter+=1

            if (mcounter>3){

                mcounter=3
            }

            val change= facility_selector[mcounter.toString()]

            t_box.text=change

            when (mcounter){
                0->{
                    t_layout.visibility=View.GONE
                    t_layout2.visibility=View.VISIBLE
                }
                else ->{

                    t_layout.visibility=View.VISIBLE
                    t_layout2.visibility=View.GONE

                }

            }


        }

        var num=1
        plus.setOnClickListener {
            num+=1

            if (num>4){
                num=4
            }

            bed_num.text=num.toString()

        }


        minus.setOnClickListener {

            num-=1

            if (num<1){
                num=1
            }
            bed_num.text=num.toString()

        }

        var num2=1
        plus2.setOnClickListener {
            num2+=1

            if (num2>4){
                num2=4
            }

            bedroom_num.text=num2.toString()

        }


        minus2.setOnClickListener {

            num2-=1

            if (num2<1){
                num2=1
            }
            bedroom_num.text=num2.toString()

        }





    }






}