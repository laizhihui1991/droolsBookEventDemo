package com.test.drools;


import com.test.drools.pojo.operators.LogisticsEvent;
import com.test.drools.pojo.operators.PaymentEvent;
import org.drools.core.time.SessionPseudoClock;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.concurrent.TimeUnit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AfterEventMain {

    public static void main(String[] args) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession kieSession = kc.newKieSession("kieSessionAfterEvents");
        SessionPseudoClock clock = kieSession.getSessionClock();
        // 插入支付事件
        PaymentEvent payment = new PaymentEvent("ORDER_1001", clock.getCurrentTime());
        kieSession.insert(payment);
        System.out.println("插入支付事件：" + payment);
        // 推进 3分40秒（220 秒），符合 after[3m30s, 4m] 区间
        clock.advanceTime(220, TimeUnit.SECONDS);
        // 插入物流事件
        LogisticsEvent logistics = new LogisticsEvent("ORDER_1001", clock.getCurrentTime());
        kieSession.insert(logistics);
        System.out.println("插入物流事件：" + logistics);

        kieSession.fireAllRules();
        kieSession.dispose();
    }


//    public static void main(String[] args) {
//        KieServices ks = KieServices.Factory.get();
//        KieContainer kc = ks.getKieClasspathContainer();
//        KieSession kieSession = kc.newKieSession("kieSessionAfterEvents");
//        SessionPseudoClock clock = kieSession.getSessionClock();
//        clock.advanceTime(220, TimeUnit.SECONDS);
//        LogisticsEvent logistics = new LogisticsEvent("ORDER_1001", clock.getCurrentTime());
//        System.out.println("插入物流事件：" + logistics);
//        kieSession.insert(logistics);
//        PaymentEvent payment = new PaymentEvent("ORDER_1001", clock.getCurrentTime());
//        System.out.println("插入支付事件：" + payment);
//        kieSession.insert(payment);
//        kieSession.fireAllRules();
//        kieSession.dispose();
//    }



//    public static void main(String[] args) throws InterruptedException {
//        KieServices ks = KieServices.Factory.get();
//        KieContainer kc = ks.getKieClasspathContainer();
//        KieSession kieSession = kc.newKieSession("kieSessionAfterEvents");
//        SessionPseudoClock clock = kieSession.getSessionClock();
//        ExecutorService executor = Executors.newFixedThreadPool(2);
//        // 线程 1：推进时间并插入支付事件
//        Runnable insertPayment = () -> {
//            // 不同步 clock
//            PaymentEvent payment = new PaymentEvent("ORDER_1001", clock.getCurrentTime());
//            kieSession.insert(payment);
//            clock.advanceTime(220, TimeUnit.SECONDS); // 220 秒
//            System.out.println("线程1 插入支付事件：" + payment);
//        };
//        // 线程 2：推进时间并插入物流事件
//        Runnable insertLogistics = () -> {
//            // 不同步 clock
//            LogisticsEvent logistics = new LogisticsEvent("ORDER_1001", clock.getCurrentTime());
//            kieSession.insert(logistics);
//            clock.advanceTime(220, TimeUnit.SECONDS); // 220 秒
//            System.out.println("线程2 插入物流事件：" + logistics);
//        };
//        // 并发执行
//        executor.submit(insertPayment);
//        executor.submit(insertLogistics);
//        executor.shutdown();
//        kieSession.fireAllRules();
//        kieSession.dispose();
//    }
}
