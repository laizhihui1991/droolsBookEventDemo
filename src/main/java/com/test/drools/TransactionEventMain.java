package com.test.drools;
import com.test.drools.pojo.TransactionEvent;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.time.SessionPseudoClock;
import java.util.concurrent.TimeUnit;

public class TransactionEventMain {

    public static void main(String[] args) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession ksession = kc.newKieSession("kieSessionTransactionEvents");
        SessionPseudoClock clock = ksession.getSessionClock();

        System.out.println("=== 示例1：瞬时事件（不触发） ===");
        TransactionEvent e1 = new TransactionEvent("TX001", "U1001", "FAIL", clock.getCurrentTime(), TimeUnit.SECONDS.toMillis(0));
        ksession.insert(e1);

        clock.advanceTime(2, TimeUnit.SECONDS);
        TransactionEvent e2 = new TransactionEvent("TX002", "U1001", "FAIL", clock.getCurrentTime(), TimeUnit.SECONDS.toMillis(0));
        ksession.insert(e2);

        ksession.fireAllRules();

        System.out.println("\n=== 示例2：区间事件（触发） ===");
        TransactionEvent e3 = new TransactionEvent("TX003", "U1002", "FAIL", clock.getCurrentTime(), TimeUnit.SECONDS.toMillis(20));
        ksession.insert(e3);

        clock.advanceTime(3, TimeUnit.SECONDS);
        TransactionEvent e4 = new TransactionEvent("TX004", "U1002", "FAIL", clock.getCurrentTime(), TimeUnit.SECONDS.toMillis(20));
        ksession.insert(e4);

        ksession.fireAllRules();

        for (FactHandle fh : ksession.getFactHandles()) {
            Object fact = ksession.getObject(fh);
            System.out.println("存在的Fact: " + fact);
        }

        ksession.dispose();
    }
}


//    public static void main(String[] args) {
//        KieServices ks = KieServices.Factory.get();
//        KieContainer kc = ks.getKieClasspathContainer();
//        KieSession ksession = kc.newKieSession("kieSessionCallEvents");
//
//        SessionPseudoClock clock = ksession.getSessionClock();
//
//        // 第一次失败交易，持续3分钟
////        TransactionEvent tx1 = new TransactionEvent("TX001", "U1001", "FAIL", clock.getCurrentTime(), TimeUnit.MINUTES.toMillis(3));
//        TransactionEvent tx1 = new TransactionEvent("TX001", "U1001", "FAIL", clock.getCurrentTime(), 1000);
//        ksession.insert(tx1);
//        System.out.println("插入事件1: " + tx1);
//        ksession.fireAllRules();
//
//        // 推进5分钟
//        clock.advanceTime(5, TimeUnit.MINUTES);
//
//        // 第二次失败交易，持续2分钟
////        TransactionEvent tx2 = new TransactionEvent("TX002", "U1001", "FAIL", clock.getCurrentTime(), TimeUnit.MINUTES.toMillis(2));
//        TransactionEvent tx2 = new TransactionEvent("TX002", "U1001", "FAIL", clock.getCurrentTime(), 1000);
//        ksession.insert(tx2);
//        System.out.println("插入事件2: " + tx2);
//        ksession.fireAllRules();
//
//        // 推进15分钟，超出10分钟窗口
//        clock.advanceTime(15, TimeUnit.MINUTES);
//
//        // 第三次失败交易
////        TransactionEvent tx3 = new TransactionEvent("TX003", "U1001", "FAIL", clock.getCurrentTime(), TimeUnit.MINUTES.toMillis(1));
//        TransactionEvent tx3 = new TransactionEvent("TX003", "U1001", "FAIL", clock.getCurrentTime(), 1000);
//        ksession.insert(tx3);
//        System.out.println("插入事件3: " + tx3);
//        ksession.fireAllRules();
//
//        ksession.dispose();
//    }