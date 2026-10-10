package com.test.drools.pojo;

import java.io.Serializable;

public class LoginEntity implements Serializable {
    private String userId;
    private String ip;
    private Long loginTime;
    private boolean success;
    // 全参构造
    public LoginEntity(String userId, String ip, Long loginTime, boolean success) {
        this.userId = userId;
        this.ip = ip;
        this.loginTime = loginTime;
        this.success = success;
    }
    // Getter（规则中必须通过 Getter 获取字段）
    public String getUserId() { return userId; }
    public String getIp() { return ip; }
    public Long getLoginTime() { return loginTime; }
    public boolean isSuccess() { return success; }
}