package com.test.drools;

import com.test.drools.pojo.operators.during.ChildPlayEvent;
import com.test.drools.pojo.operators.during.MomCleaningEvent;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class IncludesMain {
    public static void main(String[] args) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession ksession = kc.newKieSession("kieSessionIncludes1Events");
        System.out.println("======= [开始执行 Includes 示例 - 场景1 无参数] =======");
        // 妈妈打扫客厅：下午 2 点到 4 点  其实这里表示持续时间为120分钟
        MomCleaningEvent mom = new MomCleaningEvent("Mom-1001", 0, 2 * 60 * 60 * 1000);
        // 孩子玩积木：下午 2 点 30 分 - 3 点 10 分  其实这里表示持续时间为70分钟
        ChildPlayEvent child = new ChildPlayEvent("Child-1001", 30 * 60 * 1000, 70 * 60 * 1000);
        ksession.insert(mom);
        ksession.insert(child);
        ksession.fireAllRules();
        ksession.dispose();
        System.out.println("======= [执行结束] =======");
    }
}