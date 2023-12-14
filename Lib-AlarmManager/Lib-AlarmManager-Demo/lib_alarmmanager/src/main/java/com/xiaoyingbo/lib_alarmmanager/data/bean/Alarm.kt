package com.xiaoyingbo.lib_alarmmanager.data.bean

open class Alarm(id: Int, timeInMinutes: Int, days: Int, isEnabled: Boolean, vibrate: Boolean, soundTitle: String,
                 soundUri: String, title: String){

    /**
     * 提醒的id，同时也是数据库表中的主键，*/
    public var id: Int? = null

    /**
     * 选择的时间在当天以分钟表示,-1未初始化*/
    public var timeInMinutes: Int = -1

    /**
     * 响应类型
     * -2 今天的闹钟，只相应一次
     * -1 明天的闹钟，只响应一次
     * Int的7bit，每一位表示一个星期几是否选中，比如0000001:星期天,0001001:星期天和星期三*/
    public var days: Int = 0

    /**
     * 闹钟能否使用*/
    public var isEnabled: Boolean = false

    /**
     * 震动能否使用*/
    public var vibrate: Boolean = false

    /**
     * 响铃的标题*/
    public var soundTitle: String =""

    /**
     * 响铃的uri,通过uri找contentProvider提供的系统铃声*/
    public val soundUri: String = ""
}