package com.test.drools;

import com.test.drools.pojo.FireDetectedEvent;
import com.test.drools.pojo.SprinklerActivatedEvent;
import org.drools.core.time.SessionPseudoClock;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.concurrent.TimeUnit;

public class FireAlarmMain {

    public static void main(String[] args) throws InterruptedException {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession kieSession = kc.newKieSession("kieSessionFireEvents");
        SessionPseudoClock clock = kieSession.getSessionClock();
        // ==== 场景一：喷淋未按时启动 ====
        System.out.println("=== 场景一：喷淋未按时启动 ===");
        FireDetectedEvent fire1 = new FireDetectedEvent("Warehouse-1", clock.getCurrentTime());
        kieSession.getEntryPoint("FireStream").insert(fire1);
        System.out.println("插入火灾事件：" + fire1);
        // 推进 11 秒，超过 10 秒窗口
        clock.advanceTime(11, TimeUnit.SECONDS);
        kieSession.fireAllRules();
        // ==== 场景二：喷淋按时启动 ====
        System.out.println("\n=== 场景二：喷淋按时启动 ===");
        FireDetectedEvent fire2 = new FireDetectedEvent("Warehouse-2", clock.getCurrentTime());
        kieSession.getEntryPoint("FireStream").insert(fire2);
        System.out.println("插入火灾事件：" + fire2);
        // 推进 5 秒并插入喷淋事件
        clock.advanceTime(5, TimeUnit.SECONDS);
        SprinklerActivatedEvent sprinkler = new SprinklerActivatedEvent("Warehouse-2", clock.getCurrentTime());
        kieSession.getEntryPoint("FireStream").insert(sprinkler);
        System.out.println("插入喷淋事件：" + sprinkler);
        // 再推进 6 秒，总时间超过10秒
        clock.advanceTime(6, TimeUnit.SECONDS);
        kieSession.fireAllRules();
        kieSession.dispose();
    }
}
