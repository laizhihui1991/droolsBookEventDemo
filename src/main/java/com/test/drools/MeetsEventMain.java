package com.test.drools;

import com.test.drools.pojo.operators.ProcessingEvent;
import com.test.drools.pojo.operators.QualityCheckEvent;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class MeetsEventMain {
    public static void main(String[] args) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession kieSession = kc.newKieSession("kieSessionMeetsEvents");
        System.out.println("=== Meets 操作符演示 ===");
        ProcessingEvent p1 = new ProcessingEvent("JOB_1001", 5000L, 5000L);
        kieSession.insert(p1);
        QualityCheckEvent q1 = new QualityCheckEvent("JOB_1001", 0L, 5000L);
        kieSession.insert(q1);
        ProcessingEvent p2 = new ProcessingEvent("JOB_1002",10000L, 5000L);
        kieSession.insert(p2);
        QualityCheckEvent q2 = new QualityCheckEvent("JOB_1002", 3000L, 3000L);
        kieSession.insert(q2);
        System.out.println("=== 开始匹配规则 ===");
        kieSession.fireAllRules();
        kieSession.dispose();
    }
}