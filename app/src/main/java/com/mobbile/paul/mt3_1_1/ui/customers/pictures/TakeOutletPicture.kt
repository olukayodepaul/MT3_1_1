package com.mobbile.paul.mt3_1_1.ui.customers.pictures

import androidx.lifecycle.Observer
import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.mobbile.paul.mt3_1_1.R
import androidx.core.app.ActivityCompat
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.mobbile.paul.mt3_1_1.BuildConfig
import com.mobbile.paul.mt3_1_1.models.Attendance
import com.mobbile.paul.mt3_1_1.ui.modules.ModulesActivity
import com.mobiletraderv.paul.daggertraining.BaseActivity
import kotlinx.android.synthetic.main.activity_take_outlet_picture.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class TakeOutletPicture : BaseActivity(),  View.OnClickListener  {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: TakeOutletPictureViewModel

    private lateinit var upload: Button

    private lateinit var save_pics: ImageView

    private var fileUri: Uri? = null

    private var mediaPath: String? = null

    private var mImageFileLocation = ""

    private lateinit var pDialog: ProgressDialog

    private var imageView: ImageView? = null

    private var postPath: String? = null

    var urno: Int? = 0

    var url: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_outlet_picture)
        vmodel = ViewModelProviders.of(this, modelFactory)[TakeOutletPictureViewModel::class.java]
        upload = findViewById(R.id.upload)
        save_pics = findViewById(R.id.save_pics)


        upload.setOnClickListener(this)
        save_pics.setOnClickListener(this)

        imageView = findViewById(R.id.imageView)

        urno = intent.getIntExtra("urno",0)

        url = intent.getStringExtra("url")

        Glide.with(this)
            .load(url)
            .centerCrop()
            .into(imageView!!)

        showProgressBar(false)

        back_btn.setOnClickListener {
            onBackPressed()
        }

        initDialog()
    }

    protected fun initDialog() {
        pDialog = ProgressDialog(this)
        pDialog.setMessage(getString(R.string.msg_loading))
        pDialog.setCancelable(true)
    }

    protected fun showDialog() {
        if (!pDialog.isShowing) pDialog.show()
    }

    protected fun hidepDialog() {
        if (pDialog.isShowing) pDialog.dismiss()
    }

    fun checkCameraPermission(values: Int) {
        val accessPermissionStatus = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (accessPermissionStatus != PackageManager.PERMISSION_GRANTED) {
            requestImagePermission()
        }else{
            when(values){
                1->{
                    var  pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    startActivityForResult(pickPhoto, REQUEST_PICK_PHOTO)
                }
                2->{
                    val callCameraApplicationIntent = Intent()
                    callCameraApplicationIntent.action = MediaStore.ACTION_IMAGE_CAPTURE
                    var photoFile: File? = null
                    try {
                        photoFile = createImageFile()
                        val outputUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile)
                        callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
                        callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        startActivityForResult(callCameraApplicationIntent, CAMERA_PIC_REQUEST)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                3->{
                    if(!postPath.isNullOrBlank()){
                        showDialog()
                        vmodel.uploadPhoto(postPath!!, urno!!).observe(this, ObserveImage)
                    }
                }
            }
        }
    }

    @Throws(IOException::class)
    internal fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmSS").format(Date())
        val imageFileName = "MT_" + timeStamp
        var storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/Images")
        if (!storageDirectory!!.exists()) storageDirectory.mkdir()
        val image = File(storageDirectory, imageFileName + ".jpg")
        mImageFileLocation = image.absolutePath
        return image
    }

    private fun requestImagePermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_PICK_PHOTO)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PICK_PHOTO -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    requestImagePermission()
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.upload-> {
                checkCameraPermission(2)
            }
            R.id.save_pics-> {
                //check permission here
                checkCameraPermission(3)
            }
        }
    }

    val ObserveImage = Observer<Attendance> {
        if(it!=null){
            if(it.status==200) {
                Messages(1, "Success", it.msg)
            }else{
                Messages(2, "Error", it.msg)
            }
        }else{
            Messages(1, "Error", "Error with the Internet, check your network. Thanks!")
        }
        initDialog()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("file_uri", fileUri)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        fileUri = savedInstanceState.getParcelable("file_uri")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO || requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    val dp = getPathDeprecated(this, data.data)
                    imageView!!.setImageURI(data.data)
                    Log.d(TAG, dp!!)
                    postPath = dp
                }
            } else if (requestCode == CAMERA_PIC_REQUEST) {
                    Glide.with(this)
                        .load(mImageFileLocation)
                        .centerCrop()
                        .into(imageView!!)
                    postPath = mImageFileLocation
            }
        } else if (resultCode != Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Sorry, there was an error!", Toast.LENGTH_LONG).show()
        }
    }

    private fun getPathDeprecated(ctx: Context, uri: Uri?): String? {
        if (uri == null) {
            return null
        }
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = ctx.contentResolver.query(uri, projection, null, null, null)
        if (cursor != null) {
            val columnindex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(columnindex)
        }
        return uri.path
    }

    companion object {
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_PICK_PHOTO = 2
        private val CAMERA_PIC_REQUEST = 1111
        val TAG = "TakeOutletPicture"
    }

    private fun Messages(status: Int, msg: String, title: String) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogDanger)
        builder.setMessage(title)
            .setTitle(msg)
            .setIcon(R.drawable.icons8_google_alerts_100)
            .setCancelable(false)
            .setNegativeButton("OK") { _, _ ->

                when(status){
                   1->{
                       var intent = Intent(this, ModulesActivity::class.java )
                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                       startActivity(intent)
                   }
                }
            }
        val dialog  = builder.create()
        dialog.show()
    }
}
