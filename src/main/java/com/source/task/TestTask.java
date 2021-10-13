package com.source.task;

import com.source.annotation.CronTag;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Source
 * @date 2021/10/12/14:44
 */
@CronTag("testTask")
@Slf4j
public class TestTask {

    public void test(String params) {
        log.info("我是带参数的test方法，正在被执行，参数为：{}" , params);
    }

    public void test1() {
        log.info("我是不带参数的test1方法，正在被执行");
    }

}
