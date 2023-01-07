package com.hoige

import com.hoige.Config.delay
import com.hoige.Config.maxCount
import com.hoige.FoodCache.cache
import com.hoige.FoodData.type
import com.hoige.PluginMain.reload
import com.hoige.PluginMain.save
import com.hoige.TimeCache.time
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupMessageEvent

class CommandHandle {
    private fun regex(input: GroupMessageEvent): String {
        val set = input.message.contentToString()
        val rule = Regex("今天吃什么$").replace(set, "")
        val kick = Regex("(#)|([)|(])|(\\s)|(null)").replace(rule, "")
        if (rule.length >= maxCount)
            return kick.substring(0, maxCount)
        return kick
    }

    private fun getCurrentTimeStamp() : Boolean {
        val times = System.currentTimeMillis()
        val count = 1000
        if ((times / count).toInt() - time >= delay) {
            time = (times / count).toInt()
            TimeCache.save()
            TimeCache.reload()
            return true
        }
        return false
    }

    fun handle(){
        GlobalEventChannel
            .filterIsInstance<GroupMessageEvent>()
            .subscribeAlways<GroupMessageEvent> {
                val content = regex(it)
                val rule = Regex("(今天吃什么$)").containsMatchIn(it.message.contentToString())
                if (getCurrentTimeStamp() && rule) { //正则匹配今天吃什么
                    Food().getFood(content)
                    when (regex(it) == "我" || regex(it) == "") {
                        true -> sendContent(it, content, 0)
                        false -> sendContent(it, content, 1)
                    }
                }

            }
    }

    private suspend fun sendContent(it: GroupMessageEvent, content: String, gex: Int) {
        when (gex) {
            1 -> it.group.sendMessage("${content}今天吃${type[cache.getValue(content)]} (${cache[content]})")
            0 -> it.group.sendMessage("你今天吃${type[cache.getValue(content)]} (${cache[content]})")
        }
    }


}
