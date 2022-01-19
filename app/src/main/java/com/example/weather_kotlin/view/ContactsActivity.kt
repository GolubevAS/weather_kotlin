package com.example.weather_kotlin.view

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weather_kotlin.R

class ContactsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        checkPermission()

    }

    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->
            when {
                result -> getContacts()
                !ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.READ_CONTACTS
                ) -> { AlertDialog.Builder(this).setTitle(" Предоставьте доступ")
                            .setMessage(" Необходимдоступ")
                            .setPositiveButton(" Дать доступ") {_ , _ -> checkPermission()}
                            .setNegativeButton(" Не дать доступ") {dialog, _ -> dialog.dismiss()}
                            .create()
                            .show()
                } else -> Toast.makeText(this,
                "T_T", Toast.LENGTH_SHORT).show()

            }
        }

    private fun checkPermission() {
        permissionResult.launch(Manifest.permission.READ_CONTACTS)
    }



    fun getContacts() {
        val contentResolver: ContentResolver = contentResolver
        val contacts_list = findViewById<TextView>(R.id.contacts_list).apply {
            text = ""
        }

        val cursor: Cursor? = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )


        cursor?.let { cursor->

            val columnIndex = cursor.getColumnIndex( ContactsContract.Contacts.DISPLAY_NAME)
            if (columnIndex >= 0) {
                for (i in 0..cursor.count) {
                    if (cursor.moveToPosition(i)) {
                        val name = cursor.getString(columnIndex)
                        contacts_list.text = "${contacts_list.text}$name \n"
                    }
            }

            }
            cursor.close()
        }

    }

}