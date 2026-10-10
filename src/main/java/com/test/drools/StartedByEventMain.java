package com.test.drools;

import com.test.drools.pojo.operators.LoadingEvent;
import com.test.drools.pojo.operators.TransportEvent;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class StartedByEventMain {
    public static void main(String[] args) {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer container = kieServices.getKieClasspathContainer();
        KieSession kieSession = container.newKieSession("kieSessionStartedByEvents");
        System.out.println("=== StartedBy 操作符演示 ===");
        // 示例1：完全同步启动（严格started by）
        LoadingEvent load1 = new LoadingEvent("SHIP001", 0L, 8000L);
        TransportEvent trans1 = new TransportEvent("SHIP001", 0L, 15000L);
        kieSession.insert(load1);
        kieSession.insert(trans1);

        // 示例2：延迟5秒启动（容差10秒内）
        LoadingEvent load2 = new LoadingEvent("SHIP002", 0L, 10000L);
        TransportEvent trans2 = new TransportEvent("SHIP002", 5000L, 12000L);
        kieSession.insert(load2);
        kieSession.insert(trans2);
        System.out.println("=== 开始匹配规则 ===");
        kieSession.fireAllRules();
        kieSession.dispose();
    }
}
