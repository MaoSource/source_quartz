package com.source.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.source.bean.Job;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author Source
 * @date 2021/10/12/17:13
 */
@CacheConfig(cacheNames = "JobService")
public interface JobService extends IService<Job> {

    /**
     * 查询Job
     * @param jobId JobID
     * @return 返回Job对象
     */
    Job findJob(Long jobId);

    /**
     * 查询所有Job
     * @return 返回Job集合
     */
    IPage<Job> findAllJobs();

    /**
     * 根据条件查询Job
     * @param job Job对象
     * @return 返回Job集合
     */
    IPage<Job> findAllJobs(Job job);

    /**
     * 添加Job
     * @param job Job对象
     */
    void addJob(Job job);

    /**
     * 更新Job
     * @param job Job对象
     */
    void updateJob(Job job);

    /**
     * 根据JobID删除Job
     * @param jobIds JobID集合
     */
    void deleteBatch(String jobIds);

    /**
     * 更新Job
     * @param jobIds JobID集合
     * @param status 状态
     * @return 返回值
     */
    int updateBatch(String jobIds, String status);

    /**
     * 执行Job
     * @param jobIds JobID集合
     */
    void run(String jobIds);

    /**
     * 停止Job
     * @param jobIds JobID集合
     */
    void pause(String jobIds);

    /**
     * 恢复Job
     * @param jobIds JobID集合
     */
    void resume(String jobIds);

    /**
     * 定时任务
     * @param job Job对象
     * @return Job集合
     */
    @Cacheable(key = "#p0")
    List<Job> getSysCronClazz(Job job);
}
