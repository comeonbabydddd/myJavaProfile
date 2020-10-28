package com.athjx.commonutils.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程工具类
 *
 * @author 刘朋
 * <br/>date 2019-06-28
 */
public class ThreadUtils {

    /**
     * 延迟并以固定休息时间循环执行命令
     *
     * @param command 需要重复执行的线程
     * @param delay   执行的时间间隔（秒）
     * @return
     * @author 刘朋
     * <br/>date 2018-10-11
     */
    public static void scheduleWithFixedDelay(Runnable command, long delay) {
        scheduleWithFixedDelayMillisecond(command, delay * 1000);
    }

    /**
     * @param command 需要重复执行的线程
     * @param delay   执行的时间间隔（毫秒）
     * @return
     * @author 刘朋
     * <br/>date 2019-04-29
     */
    public static void scheduleWithFixedDelayMillisecond(Runnable command, long delay) {
        //定义计划执行者服务，单线程执行器
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        //将线程放入到服务中，它就会自己去跑啦
        executorService.scheduleWithFixedDelay(command, 0, delay, TimeUnit.MILLISECONDS);
    }
}
