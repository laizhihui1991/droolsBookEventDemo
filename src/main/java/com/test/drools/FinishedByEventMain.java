package com.test.drools;

import com.test.drools.pojo.operators.AfterSaleEvent;
import com.test.drools.pojo.operators.RefundEvent;
import org.drools.core.time.SessionPseudoClock;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.concurrent.TimeUnit;

public class FinishedByEventMain {

    public static void main(String[] args) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession kieSession = kc.newKieSession("kieSessionFinishedByEvents");
        SessionPseudoClock clock = kieSession.getSessionClock();
        System.out.println("=== Finished By 操作符演示 ===");
        AfterSaleEvent afterSale1 = new AfterSaleEvent("ORDER_1001", clock.getCurrentTime(), 5000L);
        kieSession.insert(afterSale1);
        clock.advanceTime(2, TimeUnit.SECONDS);
        RefundEvent refund1 = new RefundEvent("ORDER_1001", clock.getCurrentTime(), 3000L);
        kieSession.insert(refund1);
        clock.advanceTime(10, TimeUnit.SECONDS);
        AfterSaleEvent afterSale2 = new AfterSaleEvent("ORDER_1002", clock.getCurrentTime(), 5000L);
        kieSession.insert(afterSale2);
        clock.advanceTime(6, TimeUnit.SECONDS);
        RefundEvent refund2 = new RefundEvent("ORDER_1002", clock.getCurrentTime(), 4000L);
        kieSession.insert(refund2);
        System.out.println("=== 开始匹配规则 ===");
        kieSession.fireAllRules();
        kieSession.dispose();
    }
}
