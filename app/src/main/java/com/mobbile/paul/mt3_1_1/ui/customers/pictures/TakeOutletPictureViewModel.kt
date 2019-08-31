package com.mobbile.paul.mt3_1_1.ui.customers.pictures

import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class TakeOutletPictureViewModel @Inject constructor(private val repository: Repository) : ViewModel(){


    fun uploadPhoto(postPath: String) {

        //var mResult = MutableLiveData<String>()

        val map = HashMap<String, RequestBody>()
        val file = File(postPath!!)
        val requestBody = RequestBody.create(MediaType.parse("*/*"), file)
        map["map\"; filename=\"" + file.name + "\""] = requestBody

        repository.getUploadImage(map)
            .subscribe({
               // mResult.postValue(it.body()!!.customer_code)
            },{
                //mResult.postValue(null)
            }).isDisposed
    }
}