package com.source.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.source.bean.JobLog;
import com.source.util.QueryRequest;

/**
 * Created with IntelliJ IDEA.
 * @author Source
 * @date 2021/10/12/16:42
 */
public interface JobLogService extends IService<JobLog>{

    /**
     * 分页查询日志
     * @param jobLog job日志对象
     * @param request 分页参数
     * @return 返回值
     */
    IPage<JobLog> findAllJobLogs(JobLog jobLog, QueryRequest request);

    /**
     * 新增日志
     * @param log 日志对象
     */
    void saveJobLog(JobLog log);

    /**
     * 删除日志
     * @param jobLogIds 日志ID
     */
    void deleteBatch(String jobLogIds);

}
