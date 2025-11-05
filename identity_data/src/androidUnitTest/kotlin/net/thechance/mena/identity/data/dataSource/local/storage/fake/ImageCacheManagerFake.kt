package net.thechance.mena.identity.data.dataSource.local.storage.fake

class ImageCacheManagerFake {
    private val cache = mutableMapOf<String, ByteArray>()

    fun getCachedImage(key: String): ByteArray? = cache[key]

    fun cacheImage(key: String, imageByteArray: ByteArray) {
        cache[key] = imageByteArray
    }

    fun removeCachedImage(key: String) {
        cache.remove(key)
    }
}