package com.xiaoyingbo.lib_alarmmanager.data.bean

class CalendarAlarm(id: Int, timeInMinutes: Int, days: Int, isEnabled: Boolean, vibrate: Boolean,
                    soundTitle: String, soundUri: String, title: String
) : Alarm(id, timeInMinutes, days,
    isEnabled,
    vibrate, soundTitle, soundUri, title
){
    /**
     * 日程的类型
     * 比如日程：0，生日：1，纪念日：3，倒数日：4*/
    public var calendarAlarmType:Int = 0

    /**
     * 是否是全天时间*/
    public var isAllDay:Boolean = false
}