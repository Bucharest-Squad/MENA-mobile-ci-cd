package net.thechance.mena.identity.data.repository

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.thechance.mena.identity.data.dataSource.local.memory.ImageCacheManager
import net.thechance.mena.identity.data.dataSource.local.storage.ImagesGalleryManager
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNull


class ImagesRepositoryImplTest {

    private lateinit var imageCacheManager: ImageCacheManager
    private lateinit var imageGalleryManager: ImagesGalleryManager
    private lateinit var cachedImageRepository: ImagesRepositoryImpl

    @BeforeTest
    fun setup() {
        imageCacheManager = mockk()
        cachedImageRepository = ImagesRepositoryImpl(imageCacheManager, imageGalleryManager)
    }

    @Test
    fun `getCachedImage should delegate call to imageCacheManager and return cached image if it exists`() {
        val key = "profile_image"
        val expectedBytes = byteArrayOf()
        every {  imageCacheManager.getCachedImage(key)} returns expectedBytes

        val result = cachedImageRepository.getCachedImage(key)

        assert(result === expectedBytes)
    }
    @Test
    fun `getCachedImage should delegate call to imageCacheManager and return null if no image exists`() {
        val key = "profile_image"

        every {  imageCacheManager.getCachedImage(key)} returns null

        val result = cachedImageRepository.getCachedImage(key)

        assertNull(result)
    }

    @Test
    fun `cacheImage should delegate call to imageCacheManager`() {
        val key = "profile_image"
        val imageBytes = byteArrayOf()
        every { imageCacheManager.cacheImage(key,imageBytes) } returns Unit

        cachedImageRepository.cacheImage(key, imageBytes)

        verify(exactly = 1) { imageCacheManager.cacheImage(key, imageBytes) }
    }

    @Test
    fun `removeCachedImage should delegate call to imageCacheManager`() {
        val key = "profile_image"

        every { imageCacheManager.removeCachedImage(key) } returns Unit

        cachedImageRepository.removeCachedImage(key)

        verify(exactly = 1) { imageCacheManager.removeCachedImage(key) }


    }
}
