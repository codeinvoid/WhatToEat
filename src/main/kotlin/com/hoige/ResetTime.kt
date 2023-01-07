package com.hoige

import com.hoige.FoodCache.cache
import com.hoige.PluginMain.logger
import com.hoige.PluginMain.reload
import com.hoige.PluginMain.save
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class ResetTime {
    fun getTime() {
        val scheduledExecutorService: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        val runnable = Runnable {
            cache.clear()
            cache["goat"] = 1
            FoodCache.save()
            FoodCache.reload()
            logger.info("[吃什么]缓存已清除")
        }
        val currentZonedDateTime = ZonedDateTime.now()
        val zoneId = ZoneId.of("Asia/Shanghai")
        val zeroZonedDateTime = LocalDateTime.of(
            currentZonedDateTime.year,
            currentZonedDateTime.month,
            currentZonedDateTime.dayOfMonth,
            0,
            0
        ).atZone(zoneId)
        if (currentZonedDateTime.isAfter(zeroZonedDateTime)) {
            zeroZonedDateTime.plusDays(1)
        }
        val delay = zeroZonedDateTime.toInstant().toEpochMilli() - currentZonedDateTime.toInstant().toEpochMilli()
        scheduledExecutorService.scheduleAtFixedRate(runnable, delay, TimeUnit.DAYS.toMillis(1), TimeUnit.MILLISECONDS)
    }
}
