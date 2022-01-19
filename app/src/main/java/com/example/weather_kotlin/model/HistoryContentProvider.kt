//package com.example.weather_kotlin.model
//
//import android.content.ContentProvider
//import android.content.ContentValues
//import android.content.UriMatcher
//import android.database.Cursor
//import android.net.Uri
//
//
//private const val IRI_ALL = 1
//private const val IRI_ID = 2
//private const val ENTITY_PATH = "HistoryEntity"
//
//class HistoryContentProvider : ContentProvider() {
//
//    private var authorities : String? = null
//    private lateinit var uriMatcher : UriMatcher
//
//    private var entityContentType : String? = null
//    private var entityContentItemType : String? = null
//
//    private lateinit var contentUri : Uri
//
//
//
//
//    override fun onCreate(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun query(
//        uri: Uri,
//        projection: Array<out String>?,
//        selection: String?,
//        selectionArgs: Array<out String>?,
//        sortOrder: String?
//    ): Cursor? {
//        TODO("Not yet implemented")
//    }
//
//    override fun getType(uri: Uri): String? {
//        TODO("Not yet implemented")
//    }
//
//    override fun insert(uri: Uri, values: ContentValues?): Uri? {
//        TODO("Not yet implemented")
//    }
//
//    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
//        TODO("Not yet implemented")
//    }
//
//    override fun update(
//        uri: Uri,
//        values: ContentValues?,
//        selection: String?,
//        selectionArgs: Array<out String>?
//    ): Int {
//        TODO("Not yet implemented")
//    }
//}