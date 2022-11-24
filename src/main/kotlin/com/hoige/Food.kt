package com.hoige

import com.hoige.FoodData.type
import com.hoige.FoodCache.cache
import com.hoige.PluginMain.reload
import com.hoige.PluginMain.save

class Food {
    // 食物序列
    private val count = (1 until type.size).random()
    // 具体食物
    fun getFood(input:String) {
        cache[input] = count
        emptyCache()
        reloadCache()
    }

    private fun emptyCache() {
        if (cache.isEmpty()) cache["goat"] = 1
    }

    fun reloadCache() {
        FoodCache.save()
        FoodCache.reload()
    }
}

