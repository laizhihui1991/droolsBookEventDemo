package com.test.drools;

import com.test.drools.pojo.operators.InboundEvent;
import com.test.drools.pojo.operators.ShipmentEvent;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.time.SessionPseudoClock;

import java.util.concurrent.TimeUnit;

public class BeforeEventMain {
    public static void main(String[] args) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession kieSession = kc.newKieSession("kieSessionBeforeEvents");
        SessionPseudoClock clock = kieSession.getSessionClock();

        // 插入入库事件
        InboundEvent inbound = new InboundEvent("ORDER_2001", clock.getCurrentTime());
        kieSession.insert(inbound);
        System.out.println("插入入库事件：" + inbound);

        // 推进 220 秒 (3分40秒)
        clock.advanceTime(241, TimeUnit.SECONDS);

        // 插入发货事件
        ShipmentEvent shipment = new ShipmentEvent("ORDER_2001", clock.getCurrentTime());
        kieSession.insert(shipment);
        System.out.println("插入发货事件：" + shipment);

        kieSession.fireAllRules();
        kieSession.dispose();
    }
}
