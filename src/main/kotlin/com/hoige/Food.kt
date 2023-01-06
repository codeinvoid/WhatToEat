package com.hoige

import com.hoige.FoodData.type
import com.hoige.FoodCache.cache
import com.hoige.PluginMain.logger
import com.hoige.PluginMain.reload
import com.hoige.PluginMain.save

class Food {
    // 具体食物
    fun getFood(input:String) {
        if (checkFoods()) {
            cache["Jeb_"] = 1
            FoodCache.save()
            FoodCache.reload()
            cache[input] = (1 until type.size).random()
            FoodCache.save()
            FoodCache.reload()
        } else {
            logger.error("[WAWET]未配置foods.yml")
        }
    }

    private fun checkFoods(): Boolean {
        return type.isNotEmpty()
    }
}

