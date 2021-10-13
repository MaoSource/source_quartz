package com.source.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * @author Source
 * @date 2021/10/12/17:13
 */
@Data
@TableName(value = "t_job")
public class Job implements Serializable {

    /**
     * 任务调度参数key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL("0"),
        /**
         * 暂停
         */
        PAUSE("1");

        private String value;

        private ScheduleStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 任务id
     */
    @TableId(value = "JOB_ID", type = IdType.AUTO)
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
     * cron表达式
     */
    @TableField(value = "CRON_EXPRESSION")
    private String cronExpression;

    /**
     * 任务状态  0：正常  1：暂停
     */
    @TableField(value = "`STATUS`")
    private String status;

    /**
     * 备注
     */
    @TableField(value = "REMARK")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_JOB_ID = "JOB_ID";

    public static final String COL_BEAN_NAME = "BEAN_NAME";

    public static final String COL_METHOD_NAME = "METHOD_NAME";

    public static final String COL_PARAMS = "PARAMS";

    public static final String COL_CRON_EXPRESSION = "CRON_EXPRESSION";

    public static final String COL_STATUS = "STATUS";

    public static final String COL_REMARK = "REMARK";

    public static final String COL_CREATE_TIME = "CREATE_TIME";
}