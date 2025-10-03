package net.thechance.mena.faith.data.remote.service

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import io.ktor.client.statement.HttpResponse
import net.thechance.mena.faith.data.remote.dto.bookmark.AddBookmarkRequest

interface BookmarkApiService {

    @POST("faith/ayah/bookmark")
    suspend fun addBookmark(@Body request: AddBookmarkRequest): HttpResponse

    @GET("faith/ayah/bookmark")
    suspend fun getBookmarks(
        @Query("page") pageNumber: Int,
        @Query("size") pageSize: Int
    ): HttpResponse

    @DELETE("faith/ayah/bookmark/{id}")
    suspend fun deleteBookmark(@Path("id") bookmarkId: Int): HttpResponse
}

