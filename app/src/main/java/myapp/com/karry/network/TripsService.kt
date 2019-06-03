package myapp.com.karry.network

import android.util.Log
import com.google.gson.Gson
import myapp.com.karry.entity.Trip
import myapp.com.karry.modules.ApiManager
import myapp.com.karry.modules.TokenManager
import okhttp3.*
import java.io.IOException

class TripsService {

    companion object {
        fun create(tripJson: String, token: String, success: (response: Response) -> Unit, failure: () -> Unit) {
            val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), tripJson)

            val request = okhttp3.Request.Builder().header("x-auth", token).url(ApiManager.URL.TRIP_CREATE).post(body).build()

            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    Log.d("yay", response.toString())

                    if (response.code() == 201) {
                        success(response)
                    } else {
                        Log.d("yay", response.body()?.string())

                        failure()
                    }
                }
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("yay", e.toString())
                    failure()
                }
            })
        }



        fun searchByCities(departureCity: String, destinationCity: String, token: String, success: (tripsList: List<Trip>) -> Unit, failure: () -> Unit) {
            val request = okhttp3.Request.Builder().header("X-Auth", token).url(ApiManager.URL.TRIP_SEARCH + "?departureCity=$departureCity&destinationCity=$destinationCity").build()

            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.code() == 200) {
                        val tripsArray = Gson().fromJson(response.body()?.string(), Array<Trip>::class.java).toList()
                        success(tripsArray)
                    } else {
                        failure()
                    }
                }
                override fun onFailure(call: Call, e: IOException) {
                    failure()
                }
            })
        }

        fun tripById(tripID: String, token: String, success: (trip: Trip) -> Unit, failure: () -> Unit) {
            val request = okhttp3.Request.Builder().header("X-Auth", token).url(ApiManager.URL.TRIP_DETAIL(tripID)).build()

            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.code() == 200) {
                        val tripDetails = Gson().fromJson(response.body()?.string(), Trip::class.java)
                        success(tripDetails)
                    } else {
                        failure()
                    }
                }
                override fun onFailure(call: Call, e: IOException) {
                    failure()
                }
            })
        }
    }
}