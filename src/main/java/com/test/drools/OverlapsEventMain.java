package com.test.drools;

import com.test.drools.pojo.operators.AfterSaleEvent;
import com.test.drools.pojo.operators.CustomerSupportEvent;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class OverlapsEventMain {
    public static void main(String[] args) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession kieSession = kc.newKieSession("kieSessionOverlapsEvents");
        System.out.println("=== Overlaps 操作符演示 ===");
        // 订单1：匹配三个规则
        AfterSaleEvent afterSale1 = new AfterSaleEvent("ORDER_1001", 0L, 8000L);
        CustomerSupportEvent support1 = new CustomerSupportEvent("ORDER_1001", 3000L, 9000L);
        kieSession.insert(afterSale1);
        kieSession.insert(support1);

        // 订单2：只匹配单参数规则
        AfterSaleEvent afterSale2 = new AfterSaleEvent("ORDER_1002", 0L, 6000L);
        CustomerSupportEvent support2 = new CustomerSupportEvent("ORDER_1002", 2000L, 20000L);
        kieSession.insert(afterSale2);
        kieSession.insert(support2);

        // 订单3：只匹配双参数规则
        AfterSaleEvent afterSale3 = new AfterSaleEvent("ORDER_1003", 0L, 10000L);
        CustomerSupportEvent support3 = new CustomerSupportEvent("ORDER_1003", 4000L, 18000L);
        kieSession.insert(afterSale3);
        kieSession.insert(support3);

        AfterSaleEvent afterSale4 = new AfterSaleEvent("ORDER_1004", 0L, 5000L);
        CustomerSupportEvent support4 = new CustomerSupportEvent("ORDER_1004", 4000L, 1000L);
        kieSession.insert(afterSale4);
        kieSession.insert(support4);
        System.out.println("=== 开始匹配规则 ===");
        kieSession.fireAllRules();
        kieSession.dispose();
    }
}
