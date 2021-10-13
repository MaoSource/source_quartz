package com.source.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.source.bean.JobLog;
import com.source.constant.Constant;
import com.source.mapper.JobLogMapper;
import com.source.service.JobLogService;
import com.source.util.QueryRequest;
import com.source.util.SortUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author Source
 * @date 2021/10/12/16:42
 */
@Service("JobLogService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobLogServiceImpl extends ServiceImpl<JobLogMapper, JobLog> implements JobLogService {

    /**
     * 分页查询日志
     * @param jobLog job日志对象
     * @param request 分页参数
     * @return 返回值
     */
    @Override
    public IPage<JobLog> findAllJobLogs(JobLog jobLog, QueryRequest request) {
        try {
            QueryWrapper<JobLog> wrapper = new QueryWrapper<>();
            if (StringUtils.isNotBlank(jobLog.getBeanName())) {
                wrapper.lambda().eq(JobLog::getBeanName,jobLog.getBeanName().toLowerCase());
            }
            if (StringUtils.isNotBlank(jobLog.getMethodName())) {
                wrapper.lambda().eq(JobLog::getMethodName,jobLog.getMethodName().toLowerCase());
            }
            if (StringUtils.isNotBlank(jobLog.getStatus())) {
                wrapper.lambda().eq(JobLog::getStatus,Long.valueOf(jobLog.getStatus()));
            }
            Page<JobLog> page = new Page<>(request.getPageNum(), request.getPageSize());
            SortUtil.handlePageSort(request, page, "createTime", Constant.ORDER_DESC, true);
            return this.page(page, wrapper);
        } catch (Exception e) {
            log.error("获取调度日志信息失败", e);
            return null;
        }

    }

    /**
     * 新增日志
     * @param log 日志对象
     */
    @Override
    @Transactional
    public void saveJobLog(JobLog log) {
        this.save(log);
    }

    /**
     * 删除日志
     * @param jobLogIds 日志ID
     */
    @Override
    @Transactional
    public void deleteBatch(String jobLogIds) {
        List<String> list = Arrays.asList(jobLogIds.split(","));
        this.baseMapper.deleteBatchIds(list);
    }
}
