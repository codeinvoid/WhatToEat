package com.hoige

import com.hoige.FoodCache.cache
import com.hoige.PluginMain.logger
import com.hoige.PluginMain.reload
import com.hoige.PluginMain.save
import java.text.SimpleDateFormat
import java.util.*


class ResetTime {
    fun getTime(){
        val everyTime = Timer()
        val timerTask = object: TimerTask(){
            override fun run(){
                val time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                if (time == "00:00:00") {
                    cache.clear()
                    cache["goat"] = 1
                    FoodCache.save()
                    FoodCache.reload()
                    logger.info("[吃什么]缓存已清除")
                    //清空缓存
                }
            }
        }
        everyTime.schedule(timerTask,0)
    }
}
