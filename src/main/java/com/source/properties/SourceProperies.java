package com.source.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Source
 * @date 2021/10/13/11:16
 */
@Configuration
@ConfigurationProperties(prefix = "source")
public class SourceProperies {

    private boolean openAopLog = true;

    public boolean isOpenAopLog() {
        return openAopLog;
    }

    public void setOpenAopLog(boolean openAopLog) {
        this.openAopLog = openAopLog;
    }
}
