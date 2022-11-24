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
        val kick = Regex("(#)|([)|(])|(\\s)").replace(rule,"")
        if (rule.length >= maxCount) {
            return kick.substring(0, maxCount)
        }
        return kick
    }

    private fun getCurrentTimeStamp() : Boolean {
        val times = System.currentTimeMillis()
        if ((times / 1000).toInt() - time >= delay) {
            time = (times / 1000).toInt()
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
                if (getCurrentTimeStamp()){
                    if (regex(it) == "我" || regex(it) == "") {
                        getContentQid(it)
                    } else {
                        getContent(it)
                    }
                }
                return@subscribeAlways
        }
    }

    private suspend fun getContent(it:GroupMessageEvent) {
        if (Regex("(今天吃什么$)").containsMatchIn(it.message.contentToString())){ //正则匹配今天吃什么
            val content = regex(it)
            if (cache.containsKey(content)) {
                it.group.sendMessage("${content}今天吃${type[cache.getValue(content)]} (${cache[content]})")
            } else {
                Food().getFood(content) //返回纯文本值 input:自定 #今天${input}吃什么
                it.group.sendMessage("${content}今天吃${type[cache.getValue(content)]} (${cache[content]})")
            }
        }
    }

    private suspend fun getContentQid(it:GroupMessageEvent) {
        if (Regex("(今天吃什么$)").containsMatchIn(it.message.contentToString())){ //正则匹配今天吃什么
            val content = it.sender.id.toString()
            if (cache.containsKey(content)) {
                it.group.sendMessage("你今天吃${type[cache.getValue(content)]} (${cache[content]})")
            } else {
                Food().getFood(content) //返回纯文本值 input:自定 #今天${input}吃什么
                it.group.sendMessage("你今天吃${type[cache.getValue(content)]} (${cache[content]})")
            }
        }
    }
}
