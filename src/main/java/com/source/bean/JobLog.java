package com.source.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * @author Source
 * @date 2021/10/12/16:42
 */
@Data
@TableName(value = "t_job_log")
public class JobLog implements Serializable {
    /**
     * 任务日志id
     */
    @TableId(value = "LOG_ID", type = IdType.AUTO)
    private Long logId;

    /**
     * 任务id
     */
    @TableField(value = "JOB_ID")
    private Long jobId;

    /**
     * spring bean名称
     */
    @TableField(value = "BEAN_NAME")
    private String beanName;

    /**
     * 方法名
     */
    @TableField(value = "METHOD_NAME")
    private String methodName;

    /**
     * 参数
     */
    @TableField(value = "PARAMS")
    private String params;

    /**
     * 任务状态    0：成功    1：失败
     */
    @TableField(value = "`STATUS`")
    private String status;

    /**
     * 失败信息
     */
    @TableField(value = "ERROR")
    private String error;

    /**
     * 耗时(单位：毫秒)
     */
    @TableField(value = "TIMES")
    private Long times;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_LOG_ID = "LOG_ID";

    public static final String COL_JOB_ID = "JOB_ID";

    public static final String COL_BEAN_NAME = "BEAN_NAME";

    public static final String COL_METHOD_NAME = "METHOD_NAME";

    public static final String COL_PARAMS = "PARAMS";

    public static final String COL_STATUS = "STATUS";

    public static final String COL_ERROR = "ERROR";

    public static final String COL_TIMES = "TIMES";

    public static final String COL_CREATE_TIME = "CREATE_TIME";
}