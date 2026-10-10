package com.test.drools;

import com.test.drools.pojo.TransactionEvent;
import com.test.drools.pojo.VoiceCallEvent;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.time.SessionPseudoClock;

import java.util.concurrent.TimeUnit;

public class VoiceCallEventMain {

    public static void main(String[] args) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession ksession = kc.newKieSession("kieSessionVoiceCallEvents");
        SessionPseudoClock clock = ksession.getSessionClock();
        // 插入事件
        //VoiceCallEvent call = new VoiceCallEvent("13800138000", clock.getCurrentTime(),TimeUnit.SECONDS.toMillis(0));
        VoiceCallEvent call = new VoiceCallEvent("13800138000", clock.getCurrentTime(),TimeUnit.SECONDS.toMillis(5));
        //用于示例中查看工作内存中是否存在。
        //TransactionEvent e1 = new TransactionEvent("TX001", "U1001", "FAIL", clock.getCurrentTime(), TimeUnit.SECONDS.toMillis(0));
        //ksession.insert(e1);
        ksession.insert(call);
        ksession.fireAllRules();
        // 推进 1 秒
        clock.advanceTime(1, TimeUnit.SECONDS);
        ksession.fireAllRules();
        for (FactHandle fh : ksession.getFactHandles()) {
            Object fact = ksession.getObject(fh);
            System.out.println("推进 1 秒后: 存在的Fact: " + fact);
        }
        // 推进 2 秒 (总时间超过 @Expires 2s)
        clock.advanceTime(6, TimeUnit.SECONDS);
        ksession.fireAllRules();
        for (FactHandle fh : ksession.getFactHandles()) {
            Object fact = ksession.getObject(fh);
            System.out.println("推进 2 秒后 存在的Fact: " + fact);
        }
        ksession.dispose();
    }

//    public static void main(String[] args) {
//        KieServices ks = KieServices.Factory.get();
//        KieContainer kc = ks.getKieClasspathContainer();
//        KieSession ksession = kc.newKieSession("kieSessionVoiceCallEvents");
//        SessionPseudoClock clock = ksession.getSessionClock();
//        // 插入事件
//        VoiceCallEvent call = new VoiceCallEvent("13800138000", clock.getCurrentTime(),TimeUnit.SECONDS.toMillis(5));
//        FactHandle factHandle = ksession.insert(call);
//        ksession.fireAllRules();
//        // 推进 1 秒
//        clock.advanceTime(1, TimeUnit.SECONDS);
//        call.setEventTime(1000);
//        ksession.update(factHandle,call);
//        ksession.fireAllRules();
//        for (FactHandle fh : ksession.getFactHandles()) {
//            Object fact = ksession.getObject(fh);
//            System.out.println("推进 1 秒后: 存在的Fact: " + fact);
//        }
//        // 推进 2 秒 (总时间超过 @Expires 2s)
//        clock.advanceTime(2, TimeUnit.SECONDS);
//        call.setEventTime(2000);
//        ksession.update(factHandle,call);
//        ksession.fireAllRules();
//        for (FactHandle fh : ksession.getFactHandles()) {
//            Object fact = ksession.getObject(fh);
//            System.out.println("推进 2 秒后 存在的Fact: " + fact);
//        }
//        ksession.dispose();
//    }
}
