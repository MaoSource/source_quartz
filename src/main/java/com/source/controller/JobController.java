package com.source.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.source.bean.Job;
import com.source.service.JobService;
import com.source.util.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Source
 * @date 2021/10/13/10:31
 */
@RestController
@RequestMapping("job")
@RequiredArgsConstructor
@Slf4j
public class JobController {

    private final JobService jobService;

    @GetMapping
    public Result jobList() {
        IPage<Job> allJobs = this.jobService.findAllJobs();
        return Result.ok(allJobs);
    }

    @GetMapping("checkCron")
    public Boolean checkCron(String cron) {
        try {
            return CronExpression.isValidExpression(cron);
        } catch (Exception e) {
            return false;
        }
    }


    @PostMapping
    public Result addJob(Job job) {
        try {
            this.jobService.addJob(job);
            return Result.ok("新增任务成功！");
        } catch (Exception e) {
            log.error("新增任务失败", e);
            return Result.error("新增任务失败，请联系网站管理员！");
        }
    }


    @PutMapping
    public Result updateJob(Job job) {
        try {
            this.jobService.updateJob(job);
            return Result.ok("修改任务成功！");
        } catch (Exception e) {
            log.error("修改任务失败", e);
            return Result.error("修改任务失败，请联系网站管理员！");
        }
    }

    @GetMapping("run")
    public Result runJob(String jobIds) {
        try {
            this.jobService.run(jobIds);
            return Result.ok("执行任务成功！");
        } catch (Exception e) {
            log.error("执行任务失败", e);
            return Result.error("执行任务失败，请联系网站管理员！");
        }
    }

    @GetMapping("pause")
    public Result pauseJob(String jobIds) {
        try {
            this.jobService.pause(jobIds);
            return Result.ok("暂停任务成功！");
        } catch (Exception e) {
            log.error("暂停任务失败", e);
            return Result.error("暂停任务失败，请联系网站管理员！");
        }
    }

    @GetMapping("resume")
    public Result resumeJob(String jobIds) {
        try {
            this.jobService.resume(jobIds);
            return Result.ok("恢复任务成功！");
        } catch (Exception e) {
            log.error("恢复任务失败", e);
            return Result.error("恢复任务失败，请联系网站管理员！");
        }
    }

    /**
     * @param job 定时任务
     * @return ResponseBo
     */
    @PostMapping("getSysCronClazz")
    public Result getSysCronClazz(Job job) {
        List<Job> sysCronClazz = this.jobService.getSysCronClazz(job);
        return Result.ok(sysCronClazz);
    }

}
