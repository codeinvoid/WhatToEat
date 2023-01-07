package com.hoige

import com.hoige.FoodCache.cache
import com.hoige.FoodData.type
import com.hoige.PluginMain.reload
import com.hoige.PluginMain.save

class Food {
    // 具体食物
    fun getFood(input: String) {
        if (checkFoods(input))
            cache[input] = (1 until type.size).random()
        foodCache()
        return
    }

    private fun checkFoods(input: String): Boolean {
        if (type.isNotEmpty() && cache.containsKey(input).not())
            return true
        return false
    }

    private fun foodCache() {
        FoodCache.save()
        FoodCache.reload()
    }

}

