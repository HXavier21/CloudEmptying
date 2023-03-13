package com.example.wintercamp

import android.app.Activity


object ActivityCollector {
    var activities: MutableList<Activity> = ArrayList()

    //添加一个新活动
    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    //移除一个活动
    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    //关闭所有活动
    fun finishAll() {
        for (activity in activities) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
    }
}