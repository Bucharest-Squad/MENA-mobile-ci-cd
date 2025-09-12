package net.thechance.mena.trends.data.repository

import net.thechance.mena.trends.domain.entity.Category
import net.thechance.mena.trends.domain.repository.CategoryRepository

class CategoryRepositoryFakeImpl : CategoryRepository {
    override suspend fun getAllCategories(): List<Category> {
        return FakeCategoriesSource.categories
    }

    override suspend fun isInterestedCategoriesSelected(): Boolean {
        return FakeCategoriesSource.userCategories.isNotEmpty()
    }

    override suspend fun updateUserInterestedCategories(categoriesIds: List<Int>) {
        FakeCategoriesSource.userCategories.clear()
        FakeCategoriesSource.categories.forEach { category ->
            if (categoriesIds.contains(category.id)) {
                FakeCategoriesSource.userCategories.add(category)
            }
        }
    }
}

private object FakeCategoriesSource {
    val categories = mutableListOf(
        Category(id = 1, name = "Sports", emoji = "⚽️"),
        Category(id = 2, name = "Politics", emoji = "⚖️"),
        Category(id = 3, name = "Technology", emoji = "💻"),
        Category(id = 4, name = "Health", emoji = "🩺"),
        Category(id = 5, name = "Education", emoji = "📚"),
        Category(id = 6, name = "Science", emoji = "🔬"),
        Category(id = 7, name = "Music", emoji = "🎵"),
        Category(id = 8, name = "Movies", emoji = "🎬"),
        Category(id = 9, name = "Art", emoji = "🎨"),
        Category(id = 10, name = "Travel", emoji = "✈️"),
        Category(id = 11, name = "Food", emoji = "🍔"),
        Category(id = 12, name = "Fashion", emoji = "👗"),
        Category(id = 13, name = "History", emoji = "📜"),
        Category(id = 14, name = "Books", emoji = "📖"),
        Category(id = 15, name = "Gaming", emoji = "🎮"),
        Category(id = 16, name = "Nature", emoji = "🌳"),
        Category(id = 17, name = "Animals", emoji = "🐶"),
        Category(id = 18, name = "Space", emoji = "🚀"),
        Category(id = 19, name = "Finance", emoji = "💰"),
        Category(id = 20, name = "Business", emoji = "📈"),
        Category(id = 21, name = "Economy", emoji = "💹"),
        Category(id = 22, name = "Fitness", emoji = "🏋️"),
        Category(id = 23, name = "Relationships", emoji = "❤️"),
        Category(id = 24, name = "Parenting", emoji = "👶"),
        Category(id = 25, name = "DIY", emoji = "🛠️"),
        Category(id = 26, name = "Photography", emoji = "📷"),
        Category(id = 27, name = "Cars", emoji = "🚗"),
        Category(id = 28, name = "Aviation", emoji = "🛩️"),
        Category(id = 29, name = "Maritime", emoji = "🚢"),
        Category(id = 30, name = "Weather", emoji = "🌦️"),
        Category(id = 31, name = "Gardening", emoji = "🌱"),
        Category(id = 32, name = "Cooking", emoji = "🍳"),
        Category(id = 33, name = "Baking", emoji = "🥐"),
        Category(id = 34, name = "Wine", emoji = "🍷"),
        Category(id = 35, name = "Coffee", emoji = "☕"),
        Category(id = 36, name = "Tea", emoji = "🍵"),
        Category(id = 37, name = "Podcasts", emoji = "🎙️"),
        Category(id = 38, name = "Theatre", emoji = "🎭"),
        Category(id = 39, name = "Comics", emoji = "📒"),
        Category(id = 40, name = "Anime", emoji = "🍥"),
        Category(id = 41, name = "Manga", emoji = "📓"),
        Category(id = 42, name = "Crafts", emoji = "✂️"),
        Category(id = 43, name = "Coding", emoji = "👨‍💻"),
        Category(id = 44, name = "Robotics", emoji = "🤖"),
        Category(id = 45, name = "AI", emoji = "🧠"),
        Category(id = 46, name = "Philosophy", emoji = "🤔"),
        Category(id = 47, name = "Psychology", emoji = "🧩"),
        Category(id = 48, name = "Languages", emoji = "🗣️"),
        Category(id = 49, name = "Culture", emoji = "🌍"),
        Category(id = 50, name = "News", emoji = "📰"),
        Category(id = 51, name = "Memes", emoji = "😂"),
        Category(id = 52, name = "Pets", emoji = "🐾")
    )

    val userCategories: MutableList<Category> = mutableListOf()
}