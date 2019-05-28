package myapp.com.karry.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_register_profil.*
import myapp.com.karry.R
import myapp.com.karry.fragments.register.RegisterProfilFragment
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.modules.UserInfoManager
import myapp.com.karry.network.UsersService
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    private lateinit var userFirstname: String
    private lateinit var userLastname: String
    private lateinit var userPhone: String
    private lateinit var userEmail: String
    private lateinit var userPassword: String


    private fun replaceFragment(fragment: Fragment) {

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.cityPickerContainer, fragment)
        fragmentTransaction.commit()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        if (savedInstanceState == null) {

            replaceFragment(RegisterProfilFragment())
        }

    }

    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    private fun validateForm(): Boolean {
        //val firstname = registerFirstname.text.toString()
        //val lastname = registerLastname.text.toString()
        //val phone = registerPhone.text.toString()
        val email = registerEmail.text.toString()
        val password = registerPassword.text.toString()
        //val confirmPassword = registerConfirmPassword.text.toString()

        // TODO: Check is firstname is valid
        //userFirstname = firstname

        // TODO: Check is lastname is valid
        //userLastname = lastname

        // TODO: Check is phone is valid
        //userPhone = phone

        // TODO: Check is email is valid
        userEmail = email

        // TODO: Check is password is valid
        userPassword = password

        return true // or false
    }

    private fun userInfoAsJson(): String {
        val randomFirstName = (1..8)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
        val randomLastName = (1..8)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
        val userObject = JSONObject()
        userObject.put("firstname", randomFirstName)
        userObject.put("lastname", randomLastName)
        userObject.put("email", userEmail)
        userObject.put("password", userPassword)
        userObject.put("phone", "0000000000")
        return userObject.toString()
    }

    private fun registerUser() {
        if (validateForm()) {

            registerButton.visibility = View.INVISIBLE
            registerProgress.visibility = View.VISIBLE

            // Registering User in db
            UsersService.register(userInfoAsJson(), { user, token ->

                TokenManager(baseContext).deviceToken = token
                UserInfoManager(baseContext).id = user._id
                UserInfoManager(baseContext).firstname = user.firstname
                UserInfoManager(baseContext).lastname = user.lastname
                UserInfoManager(baseContext).phone = user.phone
                UserInfoManager(baseContext).email = user.email
                UserInfoManager(baseContext).profilePicture = user.profilePicture

                //startMainActivity()
            }, {
                runOnUiThread {
                    registerError.text = getString(R.string.LoginActivity_loginError_text)
                    registerButton.visibility = View.VISIBLE
                    registerProgress.visibility = View.INVISIBLE
                }
            })
        }
    }

    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    //private fun startMainActivity() {
    //    startActivity(Intent(this, MainActivity::class.java))
    //    finish()
    //}
}
