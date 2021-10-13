package com.source.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.source.annotation.CronTag;
import com.source.bean.Job;
import com.source.mapper.JobMapper;
import com.source.service.JobService;
import com.source.util.ScheduleUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * @author Source
 * @date 2021/10/12/17:13
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService{

    @Autowired
    private Scheduler scheduler;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        IPage<Job> scheduleJobList = this.findAllJobs();
        List<Job> records = scheduleJobList.getRecords();
        // 如果不存在，则创建
        records.forEach(scheduleJob -> {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        });
    }

    /**
     * 查询Job
     *
     * @param jobId JobID
     * @return 返回Job对象
     */
    @Override
    public Job findJob(Long jobId) {
        return this.baseMapper.selectById(jobId);
    }

    /**
     * 查询所有Job
     *
     * @return 返回Job集合
     */
    @Override
    public IPage<Job> findAllJobs() {
        Page<Job> page = new Page<>();
        QueryWrapper<Job> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("JOB_ID");
        List<Job> list = this.baseMapper.selectList(wrapper);
        page.setRecords(list);
        return page;
    }

    /**
     * 根据条件查询Job
     *
     * @param job Job对象
     * @return 返回Job集合
     */
    @Override
    public IPage<Job> findAllJobs(Job job) {
        try {
            QueryWrapper<Job> wrapper = new QueryWrapper<>();
            if (StringUtils.isNotBlank(job.getBeanName())) {
                wrapper.lambda().eq(Job::getBeanName,job.getBeanName());
            }
            if (StringUtils.isNotBlank(job.getMethodName())) {
                wrapper.lambda().eq(Job::getMethodName, job.getMethodName());
            }
            if (StringUtils.isNotBlank(job.getStatus())) {
                wrapper.lambda().eq(Job::getStatus, job.getStatus());
            }
            Page<Job> page = new Page<>();
            page.addOrder(OrderItem.desc("jobId"));
            return this.page(page);
        } catch (Exception e) {
            log.error("获取任务失败", e);
            return new Page<>();
        }

    }

    /**
     * 添加Job
     *
     * @param job Job对象
     */
    @Override
    @Transactional
    public void addJob(Job job) {
        job.setCreateTime(new Date());
        job.setStatus(Job.ScheduleStatus.PAUSE.getValue());
        this.save(job);
        ScheduleUtils.createScheduleJob(scheduler, job);
    }

    /**
     * 更新Job
     *
     * @param job Job对象
     */
    @Override
    @Transactional
    public void updateJob(Job job) {
        ScheduleUtils.updateScheduleJob(scheduler, job);
        this.updateById(job);
    }

    /**
     * 根据JobID删除Job
     *
     * @param jobIds JobID集合
     */
    @Override
    @Transactional
    public void deleteBatch(String jobIds) {
        List<String> list = Arrays.asList(jobIds.split(","));
        list.forEach(jobId -> ScheduleUtils.deleteScheduleJob(scheduler, Long.valueOf(jobId)));
        this.baseMapper.deleteBatchIds(list);
    }

    /**
     * 更新Job
     *
     * @param jobIds JobID集合
     * @param status 状态
     * @return 返回值
     */
    @Override
    @Transactional
    public int updateBatch(String jobIds, String status) {
        List<String> list = Arrays.asList(jobIds.split(","));
        QueryWrapper<Job> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(Job::getJobId,list);
        Job job = new Job();
        job.setStatus(status);
        return this.baseMapper.update(job, wrapper);
    }

    /**
     * 执行Job
     *
     * @param jobIds JobID集合
     */
    @Override
    @Transactional
    public void run(String jobIds) {
        String[] list = jobIds.split(",");
        Arrays.stream(list).forEach(jobId -> ScheduleUtils.run(scheduler, this.findJob(Long.valueOf(jobId))));
    }

    /**
     * 停止Job
     *
     * @param jobIds JobID集合
     */
    @Override
    @Transactional
    public void pause(String jobIds) {
        String[] list = jobIds.split(",");
        Arrays.stream(list).forEach(jobId -> ScheduleUtils.pauseJob(scheduler, Long.valueOf(jobId)));
        this.updateBatch(jobIds, Job.ScheduleStatus.PAUSE.getValue());
    }

    /**
     * 恢复Job
     *
     * @param jobIds JobID集合
     */
    @Override
    @Transactional
    public void resume(String jobIds) {
        String[] list = jobIds.split(",");
        Arrays.stream(list).forEach(jobId -> ScheduleUtils.resumeJob(scheduler, Long.valueOf(jobId)));
        this.updateBatch(jobIds, Job.ScheduleStatus.NORMAL.getValue());
    }

    /**
     * 定时任务
     *
     * @param job Job对象
     * @return Job集合
     */
    @SuppressWarnings("rawtypes")
    @Override
    public List<Job> getSysCronClazz(Job job) {
        Reflections reflections = new Reflections("com.source.task");
        List<Job> jobList = new ArrayList<>();
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(CronTag.class);

        for (Class cls : annotated) {
            String clsSimpleName = cls.getSimpleName();
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                Job job1 = new Job();
                String methodName = method.getName();
                Parameter[] methodParameters = method.getParameters();
                String params = String.format("%s(%s)", methodName, Arrays.stream(methodParameters).map(item -> item.getType().getSimpleName() + " " + item.getName()).collect(Collectors.joining(", ")));

                job1.setBeanName(StringUtils.uncapitalize(clsSimpleName));
                job1.setMethodName(methodName);
                job1.setParams(params);
                jobList.add(job1);
            }
        }
        return jobList;
    }
}
