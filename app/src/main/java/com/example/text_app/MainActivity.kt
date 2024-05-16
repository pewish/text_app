package com.example.text_app

import android.content.ClipData
import android.content.ClipData.*
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.text_app.databinding.ActivityMainBinding


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var bitmap: Bitmap

    private lateinit var buttonLoad: Button
    private lateinit var buttonMake: Button
    private lateinit var buttonResult : Button
    private lateinit var textResult: TextView
    private lateinit var imageView: ImageView
    private lateinit var buttonBack: ImageButton
    private lateinit var buttonSettings: ImageButton
    private lateinit var buttonCopy: ImageButton

    private val binging: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    //private val CAMERA_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binging = ActivityMainBinding.inflate(LayoutInflater)
        setContentView(binging.root)

        buttonBack = findViewById(R.id.buttonBack)
        buttonSettings = findViewById(R.id.buttonSettings)
        buttonLoad = findViewById(R.id.buttonLoad)
        buttonMake = findViewById(R.id.buttonMake)
        buttonResult = findViewById(R.id.buttonResult)
        textResult = findViewById(R.id.textResult)
        imageView = findViewById(R.id.pic)
        buttonCopy = findViewById(R.id.copyButton)

        buttonBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

        buttonSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        buttonLoad.setOnClickListener {
            val intent = Intent()
            intent.setAction(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent, 200)
        }

        buttonMake.setOnClickListener (View.OnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 200)
        })


        buttonResult.setOnClickListener {


        }

       buttonCopy.setOnClickListener {
           var clipBoard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
           val resultText = textResult.text.toString()
           val clip = ClipData.newPlainText("Скопировано", resultText)
           clipBoard.setPrimaryClip(clip)
           Toast.makeText(this, "Скопировано", Toast.LENGTH_SHORT).show()
       }
        get_permissons()
    }

    fun get_permissons() {
        var permissionsList = mutableListOf<String>()
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            permissionsList.add(android.Manifest.permission.CAMERA)
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissionsList.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissionsList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permissionsList.size > 0) {
            requestPermissions(permissionsList.toTypedArray(), 101)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            get_permissons()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == 200) {
            val uri = data?.data
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            imageView.setImageBitmap(bitmap)
        }
    }
}