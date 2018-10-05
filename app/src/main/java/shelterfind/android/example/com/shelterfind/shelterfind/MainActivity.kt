package shelterfind.android.example.com.shelterfind.shelterfind
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_main.*
import ru.cleverpumpkin.calendar.CalendarDate
import java.util.*
import kotlin.collections.HashMap
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.stepstone.apprating.listener.RatingDialogListener
import shelterfind.android.example.com.shelterfind.shelterfind.R.layout.activity_main
import shelterfind.android.example.com.shelterfind.shelterfind.ViewFragment.Companion.descript
import shelterfind.android.example.com.shelterfind.shelterfind.ViewFragment.Companion.mid
import shelterfind.android.example.com.shelterfind.shelterfind.ViewFragment.Companion.mname
import shelterfind.android.example.com.shelterfind.shelterfind.ViewFragment.Companion.mtype


class MainActivity : AppCompatActivity() ,CalenderDialogFragment.CalenderInteractionListener
, RatingDialogListener {



    override fun onNegativeButtonClicked() {
        Toast.makeText(this, "Negative button clicked", Toast.LENGTH_LONG).show()
    }

    override fun onNeutralButtonClicked() {
        Toast.makeText(this, "Neutral button clicked", Toast.LENGTH_LONG).show()
    }

    override fun onPositiveButtonClicked(rate: Int, comment: String) {

        val mlist=ArrayList<String>()
        val tsLong = System.currentTimeMillis() / 1000
        val ts = tsLong.toString()
        mlist.add(comment)
        mlist.add(rate.toString())
        mlist.add(ts)
        mlist.add(Current_User_name)
        mlist.add(Current_User_photo)
        val commentapp=HashMap<String,Any>()
        commentapp[Current_User] = mlist.toTypedArray()




        val rateapp=HashMap<String,Int>()
        rateapp["ratings"]=rate

        val totalcomrate=HashMap<String,Any>()

        totalcomrate["ratings"]=rate
        totalcomrate["comment"]=commentapp

        mFirebaseFirestore.collection("user/facilities/"+mtype)
                .document(mid!!)
                .collection(mtype!!)
                .addSnapshotListener { basequerySnapshot, basefirebaseFirestoreException ->

                    if ( basefirebaseFirestoreException!=null){

                    }else {

                        for (basedocumentChange: DocumentChange in basequerySnapshot!!.documentChanges) {
                            if (basedocumentChange.document.exists()){
                                val goverview=basedocumentChange.document["overview"] as HashMap<String,String>
                                if (goverview["title"]==mname && goverview["description"]== descript){
                                    val managerData=basedocumentChange.document
                                    mFirebaseFirestore.collection("user/facilities/"+mtype)
                                            .document(mid!!)
                                            .collection(mtype!!)
                                            .document(managerData.id)
                                            .set(totalcomrate, SetOptions.merge())
                                            .addOnSuccessListener {

                                                Toast.makeText(this, "Rating Successful", Toast.LENGTH_SHORT).show()
                                            }
                                    break
                                }

                            }
                        }





                    }


                }



    }








    private val ROOM_AVB="roomavailability"


    val myKitchen:MyKitchen= MyKitchen()

   lateinit var mFirebaseAuth: FirebaseAuth
   lateinit var mAuthStateListener: FirebaseAuth.AuthStateListener
    var mUsername= ANONYMUS
  lateinit var mFirebaseFirestore:FirebaseFirestore
    var peroidavailable:List<CalendarDate>?=null




    override fun onDateInteraction(date: List<CalendarDate>) {

        peroidavailable=date

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)

        val settings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build()

        mFirebaseAuth= FirebaseAuth.getInstance()

        mFirebaseFirestore= FirebaseFirestore.getInstance()
       // mFirebaseFirestore.firestoreSettings=settings
        bottom_navigation.labelVisibilityMode=1


        val host: NavHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return


        val navController = host.navController



        setupBottomNavMenu(navController)




        mAuthStateListener=FirebaseAuth.AuthStateListener {auth->

            val user=auth.currentUser



            if (user!=null){
                Current_User=user.uid
            OnSignedInInitialized(user)

            }else{
               OnSignedOutCleanUp()
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setAvailableProviders(Arrays.asList(
                                        AuthUI.IdpConfig.GoogleBuilder().build(),
                                        AuthUI.IdpConfig.EmailBuilder().build()))
                                .setTheme(R.style.FullscreenTheme)
                                .build(),
                        RC_SIGN_IN)
            }
        }


    }

    private fun OnSignedOutCleanUp() {

        mUsername= ANONYMUS

    }

    private fun OnSignedInInitialized(user: FirebaseUser) {

        mUsername= user.displayName!!

        val userinfo:HashMap<String,String> = HashMap()
        userinfo["name"]=mUsername
        userinfo["email"] =user.email!!
        userinfo["type"]="normal user"
        userinfo["photourl"]=""
        Current_User_name=mUsername


            mFirebaseFirestore.
                        collection("usersonboard")
                        .document(user.uid)
              .set(userinfo as Map<String, Any>, SetOptions.merge())
                    .addOnSuccessListener { task->

                    }





    }




    override fun onResume() {
        super.onResume()

mFirebaseAuth.addAuthStateListener(mAuthStateListener)

    }


    override fun onPause() {
        super.onPause()

            mFirebaseAuth.removeAuthStateListener(mAuthStateListener)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

            if (requestCode== RC_SIGN_IN){
               if (resultCode== Activity.RESULT_OK){

               }else if(resultCode== Activity.RESULT_CANCELED){

                   finish()
               }

            }
    }

    private fun setupBottomNavMenu(navController: NavController) {
        findViewById<BottomNavigationView>(R.id.bottom_navigation)?.let { bottomNavView ->
            NavigationUI.setupWithNavController(bottomNavView, navController)
        }
    }


    override fun onStart() {
        super.onStart()



    }






    companion object {
        const val  RC_SIGN_IN=1
        const val ANONYMUS="Anonymus"
        var Current_User=""
        var Current_User_photo=""
        var Current_User_name=""
    }


}

