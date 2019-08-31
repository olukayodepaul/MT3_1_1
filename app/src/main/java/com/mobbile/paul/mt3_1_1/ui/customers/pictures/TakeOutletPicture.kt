package com.mobbile.paul.mt3_1_1.ui.customers.pictures

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
import android.graphics.BitmapFactory
import android.os.Build
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
import com.mobiletraderv.paul.daggertraining.BaseActivity
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class TakeOutletPicture : BaseActivity(),  View.OnClickListener  {

    @Inject
    internal lateinit var modelFactory: ViewModelProvider.Factory

    lateinit var vmodel: TakeOutletPictureViewModel

    private lateinit var pickImage: Button

    private lateinit var upload: Button

    private lateinit var save_pics: ImageView

    private var fileUri: Uri? = null

    private var mediaPath: String? = null

    private var mImageFileLocation = ""

    private lateinit var pDialog: ProgressDialog

    private var imageView: ImageView? = null

    private var postPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_outlet_picture)

        vmodel = ViewModelProviders.of(this, modelFactory)[TakeOutletPictureViewModel::class.java]

        pickImage = findViewById(R.id.pickImage)
        upload = findViewById(R.id.upload)
        save_pics = findViewById(R.id.save_pics)

        pickImage.setOnClickListener(this)
        upload.setOnClickListener(this)
        save_pics.setOnClickListener(this)

        imageView = findViewById(R.id.imageView)
        initDialog()

    }

    protected fun initDialog() {

        pDialog = ProgressDialog(this)
        pDialog.setMessage(getString(R.string.msg_loading))
        pDialog.setCancelable(true)

    }

    protected fun showpDialog() {
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
                    imagePermissionRationare()
                }
            }
        }
    }

    private fun imagePermissionRationare() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Without allowing Camera permission, you will not bbe able to take picture")
            .setTitle("Camera Permission")
            .setCancelable(false)
            .setNegativeButton("OK") { _, _ ->
                requestImagePermission()
            }
        val dialog  = builder.create()
        dialog.show()
    }

    override fun onClick(v: View?) {

        when (v!!.id) {

            R.id.pickImage-> {
                checkCameraPermission(1)
            }
            R.id.upload-> {
                checkCameraPermission(2)
            }
            R.id.save_pics-> {
                if(!postPath.isNullOrBlank()){
                    showpDialog()
                    vmodel.uploadPhoto(postPath!!)
                }else{
                    //Comment from here
                }
            }
        }
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

                    Glide.with(this).load(mImageFileLocation).into(imageView!!)
                    postPath = mImageFileLocation

                    Log.d(TAG, "$postPath part 2")
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



    companion object{
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_PICK_PHOTO = 2
        private val CAMERA_PIC_REQUEST = 1111
        val TAG = "TakeOutletPicture"
    }
}
