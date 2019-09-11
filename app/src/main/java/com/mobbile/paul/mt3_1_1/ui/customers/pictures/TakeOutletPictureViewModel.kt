package com.mobbile.paul.mt3_1_1.ui.customers.pictures


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_project.providers.Repository
import com.mobbile.paul.mt3_1_1.models.Attendance
import com.mobbile.paul.mt3_1_1.models.EmployeesApi
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class TakeOutletPictureViewModel @Inject constructor(private val repository: Repository) : ViewModel(){


    fun uploadPhoto(postPath: String, urno : Int) : LiveData<Attendance> {

        var mResult = MutableLiveData<Attendance>()

        val map = HashMap<String, RequestBody>()
        val file = File(postPath!!)
        val requestBody = RequestBody.create(MediaType.parse("*/*"), file)
        map["map\"; filename=\"" + file.name + "\""] = requestBody
        repository.getUploadImage(map, urno)
            .subscribe({
               mResult.postValue(it.body()!!)
            },{
                mResult.postValue(null)
            }).isDisposed

        return mResult
    }
}