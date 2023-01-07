package com.hoige

import kotlinx.coroutines.launch
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin

object PluginMain : KotlinPlugin(
    JvmPluginDescription(
        id = "com.hoige.wawet",
        name = "What are we eating today",
        version = "0.1.0",
    ) {
        author("hoige")
    }
) {
    override fun onEnable() {
        launch {
            CommandHandle().handle()
            ResetTime().getTime()
        }
        //load config
        FoodCache.reload()
        FoodData.reload()
        Config.reload()
    }
}
