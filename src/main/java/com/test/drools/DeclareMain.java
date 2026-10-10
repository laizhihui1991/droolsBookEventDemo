package com.test.drools;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.definition.type.FactType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.time.SessionPseudoClock;
import org.kie.internal.io.ResourceFactory;

import java.util.concurrent.TimeUnit;

public class DeclareMain {
    public static void main(String[] args) throws Exception {
        // 1. 初始化 Drools
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession ksession = kieContainer.newKieSession("kieSessionEvents");

        // 2. 获取伪时钟
        SessionPseudoClock pseudoClock = ksession.getSessionClock();

        // 3. 获取 declare 事件类型
        FactType factType = ksession.getKieBase().getFactType("rules", "DeclareLoginEvent");

        // === 模拟3个事件 ===
        // 事件1：成功登录
        pseudoClock.advanceTime(1, TimeUnit.SECONDS);
        Object event1 = factType.newInstance();
        factType.set(event1, "userId", "Declare_Lai_User_Id_1");
        factType.set(event1, "ip", "192.168.1.1");
        factType.set(event1, "success", true);
        factType.set(event1, "loginTime", pseudoClock.getCurrentTime());
        ksession.insert(event1);
        System.out.println("插入事件1：成功登录，时间戳=" + factType.get(event1, "loginTime"));

        // 事件2：失败登录
        pseudoClock.advanceTime(5, TimeUnit.SECONDS);
        Object event2 = factType.newInstance();
        factType.set(event2, "userId", "Declare_Lai_User_Id_2");
        factType.set(event2, "ip", "192.168.1.2");
        factType.set(event2, "success", false);
        factType.set(event2, "loginTime", pseudoClock.getCurrentTime());
        ksession.insert(event2);
        System.out.println("插入事件2：失败登录，时间戳=" + factType.get(event2, "loginTime"));

        // 事件3：成功登录
        pseudoClock.advanceTime(6, TimeUnit.SECONDS);
        Object event3 = factType.newInstance();
        factType.set(event3, "userId", "Declare_Lai_User_Id_3");
        factType.set(event3, "ip", "192.168.1.3");
        factType.set(event3, "success", true);
        factType.set(event3, "loginTime", pseudoClock.getCurrentTime());
        ksession.insert(event3);
        System.out.println("插入事件3：成功登录，时间戳=" + factType.get(event3, "loginTime"));

        // 4. 第一次触发规则
        System.out.println("\n=== 第一次触发规则 ===");
        int fired = ksession.fireAllRules();
        System.out.println("触发规则数：" + fired);

        // 5. 推进30秒后，观察事件过期效果
        pseudoClock.advanceTime(30, TimeUnit.SECONDS);
        System.out.println("\n=== 30秒后再次触发规则（过期检查） ===");
        int expiredFired = ksession.fireAllRules();
        System.out.println("触发规则数：" + expiredFired);

        // 6. 释放资源
        ksession.dispose();
    }

//    public static void main(String[] args) {
//        // 1. 初始化 Drools
//        KieServices kieServices = KieServices.Factory.get();
//        KieContainer kieContainer = kieServices.getKieClasspathContainer();
//        KieSession ksession = kieContainer.newKieSession("kieSessionEvents");
//
//        // 2. 获取伪时钟（此时已能正常获取，无类型转换错误）
//        SessionPseudoClock pseudoClock = ksession.getSessionClock();
//
//        // 3. 模拟3个事件（按时间顺序插入）
//        pseudoClock.advanceTime(1, TimeUnit.SECONDS);
//        LoginEntity event1 = new LoginEntity("Declare_Lai_User_Id_1", "192.168.1.1", pseudoClock.getCurrentTime(), true);
//        ksession.insert(event1);
//        System.out.println("插入事件1：Declare_Lai_User_Id_1 登录，时间戳=" + event1.getLoginTime());
//
//        pseudoClock.advanceTime(5, TimeUnit.SECONDS);
//        LoginEntity event2 = new LoginEntity("Declare_Lai_User_Id_2", "192.168.1.2", pseudoClock.getCurrentTime(), false);
//        ksession.insert(event2);
//        System.out.println("插入事件2：Declare_Lai_User_Id_2 登录失败，时间戳=" + event2.getLoginTime());
//
//        pseudoClock.advanceTime(6, TimeUnit.SECONDS);
//        LoginEntity event3 = new LoginEntity("Declare_Lai_User_Id_3", "192.168.1.3", pseudoClock.getCurrentTime(), true);
//        ksession.insert(event3);
//        System.out.println("插入事件3：Declare_Lai_User_Id_3 登录，时间戳=" + event3.getLoginTime());
//
//        // 4. 触发规则
//        System.out.println("\n=== 第一次触发规则 ===");
//        int fired = ksession.fireAllRules();
//        System.out.println("触发规则数：" + fired);
//
//        // 5. 推进30秒后，插入新事件
//        pseudoClock.advanceTime(30, TimeUnit.SECONDS);
//        // 6. 模拟事件过期
//        System.out.println("\n=== 30秒后触发规则 ===");
//        int expiredFired = ksession.fireAllRules();
//        System.out.println("触发规则数：" + expiredFired);
//        // 7. 释放资源
//        ksession.dispose();
//    }
}