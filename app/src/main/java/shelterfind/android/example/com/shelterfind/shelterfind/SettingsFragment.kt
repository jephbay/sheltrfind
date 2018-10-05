package shelterfind.android.example.com.shelterfind.shelterfind


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.fragment_main_settings.*
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment :Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_settings, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)!!
        settings_show_notification.isChecked=   sharedPref.getBoolean("notifications",true)
        settings_currency_txt.text=   sharedPref.getString("settings_currency","Cedis")

        settings_currency.setOnClickListener{
            MaterialDialog(view.context)
                    .title(text = "Select currency")
                    .listItemsSingleChoice(R.array.settings_list_preference_titles_currency, initialSelection = 1) { _, index, text ->
                        settings_currency_txt.text=text
                        with(sharedPref.edit()) {
                            putString("settings_currency", text)
                            apply()
                        }
                    }
                    .show()
        }


        settings_show_notification.setOnCheckedChangeListener { buttonView, isChecked ->

            with(sharedPref.edit()) {
                putBoolean("notifications", isChecked)
                apply()
            }
        }


        settings_logout.setOnClickListener {
            AuthUI.getInstance().signOut(this@SettingsFragment.context!!)
        }

    }


    companion object {
        @JvmStatic
        fun newInstance() =
                SettingsFragment()
    }
}
