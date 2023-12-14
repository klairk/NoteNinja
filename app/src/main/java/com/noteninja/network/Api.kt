package com.justmovies.Api_base


import com.noteninja.network.AddNotesModel
import com.noteninja.network.AuthModel
import com.noteninja.network.DeleteModel
import com.noteninja.network.EditNotesModel
import com.noteninja.network.GetNotesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

// Interface defining the API endpoints.
interface Api {

    // Endpoint for user registration.
    @POST("sign_up")
    fun register(@Body map: HashMap<String, Any>): Call<AuthModel>

    // Endpoint for OTP verification.
    @POST("verify_otp")
    fun otp(@Body map: HashMap<String, Any>): Call<AuthModel>

    // Endpoint for user login.
    @POST("login")
    fun login(@Body map: HashMap<String, Any>): Call<AuthModel>

    // Endpoint to retrieve notes.
    @GET("get_notes")
    fun getnotes() : Call<GetNotesResponse>

    // Endpoint to add a new note.
    @POST("add_note")
    fun add_note(@Body map: HashMap<String, Any>): Call<AddNotesModel>

    // Endpoint to edit an existing note.
    @POST("edit_note")
    fun edit_note(@Body map: HashMap<String, Any>): Call<EditNotesModel>

    // Endpoint to delete a note.
    @POST("note_delete")
    fun note_delete(@Body map: HashMap<String, Any>): Call<DeleteModel>

    // Endpoint to change user password.
    @POST("change_password")
    fun changePassword(@Body map: HashMap<String, Any>): Call<AuthModel>
}