package com.source.controller;

import com.source.bean.JobLog;
import com.source.service.JobLogService;
import com.source.util.QueryRequest;
import com.source.util.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Source
 * @date 2021/10/13/10:53
 */
@RestController
@RequestMapping("jobLog")
@RequiredArgsConstructor
@Slf4j
public class JobLogController {

    private final JobLogService jobLogService;

    @GetMapping
    public Result jobLogList(QueryRequest request, JobLog log) {
        return Result.ok(this.jobLogService.findAllJobLogs(log, request));
    }

    @DeleteMapping
    public Result deleteJobLog(String ids) {
        try {
            this.jobLogService.deleteBatch(ids);
            return Result.ok("删除调度日志成功！");
        } catch (Exception e) {
            log.error("删除调度日志失败", e);
            return Result.error("删除调度日志失败，请联系网站管理员！");
        }
    }

}
