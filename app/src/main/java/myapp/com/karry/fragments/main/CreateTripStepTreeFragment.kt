package myapp.com.karry.fragments.main


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_create_trip_step_tree.view.*

import myapp.com.karry.R
import myapp.com.karry.model.SharedViewModel
import java.lang.Integer.parseInt

class CreateTripStepTreeFragment : Fragment() {

    private lateinit var model: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val v = inflater.inflate(R.layout.fragment_create_trip_step_tree, container, false)

        v.backButton.setOnClickListener { replaceFragment(CreateTripStepTwoFragment()) }
        v.closeIcon.setOnClickListener { replaceFragment(SearchFragment()) }


        v.carryTax.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                model.carryTax.value = parseInt(s.toString())
            }
        })


        v.validStepTree.setOnClickListener {
            Log.d("yay", model.fillCreateOrderFormPayload().toString())
        }

        return v
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = fragmentManager!!.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)

        fragmentTransaction.replace(R.id.fragmentContainer2, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


}