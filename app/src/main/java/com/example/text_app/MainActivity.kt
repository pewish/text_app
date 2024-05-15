package com.example.text_app

import android.content.ClipData
import android.content.ClipData.*
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var buttonLoad: Button
    private lateinit var buttonResult : Button
    private lateinit var textResult: TextView
    private lateinit var imageView: ImageView
    private lateinit var buttonBack: ImageButton
    private lateinit var buttonSettings: ImageButton
    private lateinit var bitmap: Bitmap
    private lateinit var buttonCopy: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonBack = findViewById(R.id.buttonBack)
        buttonSettings = findViewById(R.id.buttonSettings)
        buttonLoad = findViewById(R.id.buttonLoad)
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

        buttonResult.setOnClickListener {


        }

       buttonCopy.setOnClickListener {
           var clipBoard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
           val resultText = textResult.text.toString()
           val clip = ClipData.newPlainText("Скопировано", resultText)
           clipBoard.setPrimaryClip(clip)
           Toast.makeText(this, "Скопировано", Toast.LENGTH_SHORT).show()
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