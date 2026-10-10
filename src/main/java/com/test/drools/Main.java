package com.test.drools;

import com.test.drools.pojo.LoginEvent;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.time.SessionPseudoClock;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // 1. 初始化 Drools
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession ksession = kieContainer.newKieSession("kieSessionEvents");

        // 2. 获取伪时钟（此时已能正常获取，无类型转换错误）
        SessionPseudoClock pseudoClock = ksession.getSessionClock();

        // 3. 模拟3个事件（按时间顺序插入）
        pseudoClock.advanceTime(1, TimeUnit.SECONDS);
        LoginEvent event1 = new LoginEvent("Lai_User_Id_1", "192.168.1.1", pseudoClock.getCurrentTime(), true);
        ksession.insert(event1);
        System.out.println("插入事件1：Lai_User_Id_1 登录，时间戳=" + event1.getLoginTime());

        pseudoClock.advanceTime(5, TimeUnit.SECONDS);
        LoginEvent event2 = new LoginEvent("Lai_User_Id_2", "192.168.1.2", pseudoClock.getCurrentTime(), false);
        ksession.insert(event2);
        System.out.println("插入事件2：Lai_User_Id_2 登录失败，时间戳=" + event2.getLoginTime());

        pseudoClock.advanceTime(6, TimeUnit.SECONDS);
        LoginEvent event3 = new LoginEvent("Lai_User_Id_3", "192.168.1.3", pseudoClock.getCurrentTime(), true);
        ksession.insert(event3);
        System.out.println("插入事件3：Lai_User_Id_3 登录，时间戳=" + event3.getLoginTime());

        // 4. 触发规则
        System.out.println("\n=== 第一次触发规则 ===");
        int fired = ksession.fireAllRules();
        System.out.println("触发规则数：" + fired);


        // 5. 推进30秒后，插入新事件
        pseudoClock.advanceTime(30, TimeUnit.SECONDS);
        // 6. 模拟事件过期
        System.out.println("\n=== 30秒后触发规则 ===");
        int expiredFired = ksession.fireAllRules();
        System.out.println("触发规则数：" + expiredFired);
        // 7. 释放资源
        ksession.dispose();
    }
}