package net.thechance.mena.identity.data.dataSource.local.storage

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import net.thechance.mena.identity.data.dataSource.local.storage.fake.ImageCacheManagerFake
import kotlin.test.Test

class ImageCacheManagerTest {
    private val imageCacheManager = ImageCacheManagerFake()

    @Test
    fun `getCachedImage should return null when key is not cached`() {
        val key = "nonexistent_key"

        val result = imageCacheManager.getCachedImage(key)

        assertThat(result).isNull()
    }

    @Test
    fun `cacheImage should cache image with given key`() {
        val key = "test_key"
        val imageByteArray = byteArrayOf(1, 2, 3)
        imageCacheManager.cacheImage(key, imageByteArray)

        val result = imageCacheManager.getCachedImage(key)

        assertThat(result).isEqualTo(imageByteArray)
    }

    @Test
    fun `removeCachedImage should remove cached image with given key`() {
        val key = "test_key"
        val imageByteArray = byteArrayOf(1, 2, 3)
        imageCacheManager.cacheImage(key, imageByteArray)

        imageCacheManager.removeCachedImage(key)
        val result = imageCacheManager.getCachedImage(key)

        assertThat(result).isNull()
    }
}