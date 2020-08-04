package com.cpfei.speedmanager.speed;

import java.io.Serializable;

/**
 * 测速日志
 * @Author: cpfei
 * @CreateDate: 2020-07-29
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020-07-29
 * @description:
 */
public class SpeedLog implements Serializable {
    
    private static final long serialVersionUID = 4799817919908922183L;

    /**
     * 开始时间
     */
    public String startTime;
    
    /**
     * 结束时间
     */
    public String endTime;
    
    /**
     * 页面大小
     */
    public String pageSize;
    
    /**
     * url Id
     */
    public String urlId;
    
    /**
     * 联网方式
     */
    public String lineType;
    /**
     * dns解析时间
     */
    public String dnsTime;
    /**
     * http响应时间
     */
    public String responseTime;
    /**
     * dns解析出来的ip
     */
    public String vip;
    /**
     * 网关ip
     */
    public String gwip;
    /**
     * http状态码
     */
    public String httpcode;
    /**
     * 网络连接时间
     */
    public String firstpagetime;
    
	
	@Override
	public String toString() {
		return new StringBuilder().append("starttime:").append(startTime).append(" endtime:").append(endTime)
		.append(" pagesize:").append(pageSize).append(" lineType:").append(lineType).append(" dnstime:").append(dnsTime)
		.append(" responsetime:").append(responseTime).append(" ip:").append(vip).append(" gwip:").append(gwip)
		.append(" httpcode:").append(httpcode).append(" firstpagetime:").append(firstpagetime).toString();
	}
}