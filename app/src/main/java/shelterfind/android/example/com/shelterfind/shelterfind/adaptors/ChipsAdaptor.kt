package shelterfind.android.example.com.shelterfind.shelterfind.adaptors

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.chips_layout.view.*
import shelterfind.android.example.com.shelterfind.shelterfind.R

class ChipsAdaptor(context:Context,itemList:ArrayList<String>,Htype:String ):BaseAdapter() {

    private var entryList:ArrayList<String> =itemList
    private var  mcontext=context
    private var mtype:String=Htype


    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        Log.v("ENTRYLIST",entryList.toString())
        val item= entryList[position]
        Log.v("ENTRYLIST",item)
        val inflator = mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val itemView=inflator.inflate(R.layout.chips_layout,null)


        itemView.item_chip.text=item


        val chip_img=getitemm(mtype)
        itemView.item_chip.chipIcon=mcontext.resources.getDrawable( chip_img, null)

        return itemView
    }



    fun getitemm(item:String):Int {
        val itemList = HashMap<String, Int>()


        when(item){
            "hostels"->{

                return R.drawable.ic_person_add_black_24dp
            }else->{

            return R.drawable.ic_house_repair

        }


        }

    }
    

    override fun getItem(position: Int): Any {

    return  entryList[position]
    }

    override fun getItemId(position: Int): Long {
     return position.toLong()
    }

    override fun getCount(): Int {
      return  entryList.size
    }


}