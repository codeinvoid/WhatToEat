package com.hoige

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value

object FoodCache : AutoSavePluginData("cache") {
    val cache: MutableMap<String, Int> by value()
}

object TimeCache : AutoSavePluginData("time") {
    var time: Int by value()
}

object FoodData : AutoSavePluginConfig("foods") {
    val type: MutableList<String> by value()
}

object Config : AutoSavePluginConfig("config"){
    val maxCount : Int by value(16)
    val delay: Int by value(3)
}
