package shelterfind.android.example.com.shelterfind.shelterfind.adaptors

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.commentreview.view.*
import shelterfind.android.example.com.shelterfind.shelterfind.R
import java.text.DateFormat
import java.util.*

class ReviewAdaptor(context: Context, itemList:ArrayList<HashMap<String,String>>): BaseAdapter()  {

    private var entryList:ArrayList<HashMap<String,String>> =itemList
    private var  mcontext=context



    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val item= entryList[position]

        val inflator = mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val itemView=inflator.inflate(R.layout.commentreview,null)




        if (item["photo"]!!.isNotEmpty()){

            Picasso.get().load(Uri.parse(item["photo"])) .fit().into( itemView.commenter_profile)
        }




        itemView.commenter_name.text=item["name"]
        itemView.commenter_date.text=getDate(  item["time"]!!.toLong())
        itemView.commenter_message.text=item["comment"]
        itemView.commenter_rating.rating= item["rate"]!!.toInt().toFloat()

        return itemView


    }

    private fun getDate(time: Long): String {
        val df: DateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRANCE)

        return df.parse(time.toString()).toString()
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