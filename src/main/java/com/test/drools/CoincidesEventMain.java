package com.test.drools;

import com.test.drools.pojo.operators.TransferRequestEvent;
import com.test.drools.pojo.operators.DebitTransactionEvent;
import org.drools.core.time.SessionPseudoClock;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.concurrent.TimeUnit;

/**
 * Coincides 运算符示例
 * 模拟银行转账请求事件与扣款流水事件的时间同步检测
 */
public class CoincidesEventMain {

    public static void main(String[] args) {

        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("kieSessionCoincidesEvents");

        // 使用 pseudo clock，便于控制时间
        SessionPseudoClock clock = kieSession.getSessionClock();

        System.out.println("======= [开始执行 Coincides 运算符示例] =======");

        //场景一：完全相同的时间（规则1命中）
        TransferRequestEvent req1 = new TransferRequestEvent("REQ-1001", clock.getCurrentTime(), clock.getCurrentTime() + 5000);
        DebitTransactionEvent txn1 = new DebitTransactionEvent("TXN-1001", clock.getCurrentTime(), clock.getCurrentTime() + 5000);
        kieSession.insert(req1);
        kieSession.insert(txn1);
        System.out.println("[场景1] 插入完全相同时间区间的事件：REQ-1001 与 TXN-1001");
        kieSession.fireAllRules();
        System.out.println();

        //场景二：时间差在5秒容差内（规则2命中）
        clock.advanceTime(10, TimeUnit.SECONDS);
        long baseTime = clock.getCurrentTime();
        TransferRequestEvent req2 = new TransferRequestEvent("REQ-1002", baseTime, baseTime + 10000);
        // 延迟 3 秒开始，结束也延迟 3 秒，容差在5秒内
        DebitTransactionEvent txn2 = new DebitTransactionEvent("TXN-1002", baseTime + 3000, baseTime + 13000);
        kieSession.insert(req2);
        kieSession.insert(txn2);
        System.out.println("[场景2] 插入时间差在5秒内的事件：REQ-1002 与 TXN-1002");
        kieSession.fireAllRules();
        System.out.println();
        //场景三：时间差在 [15s,10s] 容差内（规则3命中）
        clock.advanceTime(20, TimeUnit.SECONDS);
        long baseTime2 = clock.getCurrentTime();
        TransferRequestEvent req3 = new TransferRequestEvent("REQ-1003", baseTime2, baseTime2 + 10000);
        DebitTransactionEvent txn3 = new DebitTransactionEvent("TXN-1003", baseTime2 + 6000, baseTime2 + 15000);


        kieSession.insert(req3);
        kieSession.insert(txn3);
        System.out.println("[场景3] 插入时间差满足 [15s,10s] 的事件：REQ-1003 与 TXN-1003");
        kieSession.fireAllRules();
        System.out.println();
        //关闭会话
        kieSession.dispose();

        System.out.println("======= [执行结束] =======");
    }
}
