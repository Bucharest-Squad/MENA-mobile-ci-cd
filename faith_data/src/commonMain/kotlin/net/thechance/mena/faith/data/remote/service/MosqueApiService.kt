package net.thechance.mena.faith.data.remote.service

import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST
import io.ktor.client.request.forms.MultiPartFormDataContent
import net.thechance.mena.faith.data.remote.model.mosque.MosqueResponseDto

interface MosqueApiService {

    @POST("faith/mosques")
    suspend fun createMosque(
        @Body body: MultiPartFormDataContent
    ): Response<MosqueResponseDto>

}