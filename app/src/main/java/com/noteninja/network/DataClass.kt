package com.noteninja.network

// Data model for authentication response.
data class AuthModel(
    val `data`: Profile,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)

// Data model representing a user's profile.
data class Profile(
    val created_at: String,
    val device_model: String,
    val device_token: String,
    val device_type: Int,
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val otp: Int,
    val role: Int,
    val token: String,
    val updated_at: String,
    val country: Any,
    val country_code: Any,
    val date_of_birth: Any,

    val email_verified_at: String,

    val is_online: Int,
    val is_verified: Int,

    val latitude: Any,
    val location: Any,
    val login_type: Any,
    val longitude: Any,

    val phone: Any,
    val profile_image: Any,

    val social_id: Any,
    val social_login_type: Any,
    val status: Int,

)

// Data model for the response when fetching notes.
data class GetNotesResponse(
    val `data`: List<GetNotesData>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)

// Data model representing individual note data.
data class GetNotesData(
    val created_at: String,
    val id: Int,
    val note: String,
    val status: Int,
    val tittle: String,
    val updated_at: String,
    val user_id: Int
)

// Data model for the response when adding a note.
data class AddNotesModel(
    val `data`: AddNotesData,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)

// Data model representing the data for a note being added.
data class AddNotesData(
    val created_at: String,
    val id: Int,
    val note: String,
    val tittle: String,
    val updated_at: String,
    val user_id: Int
)

// Data model for the response when editing a note.
data class EditNotesModel(
    val `data`: EditNotesData,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)

// Data model representing the data for a note being edited.
data class EditNotesData(
    val created_at: String,
    val id: Int,
    val note: String,
    val status: Int,
    val tittle: String,
    val updated_at: String,
    val user_id: Int
)

// Data model for the response when deleting a note.
data class DeleteModel(
    val `data`: Any,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)












