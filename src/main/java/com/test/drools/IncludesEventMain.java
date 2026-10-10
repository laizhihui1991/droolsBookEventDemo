package com.test.drools;

import com.test.drools.pojo.operators.during.*;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


public class IncludesEventMain {

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
//    public static void main(String[] args) {
//        KieServices ks = KieServices.Factory.get();
//        KieContainer kc = ks.getKieClasspathContainer();
//        KieSession ksession = kc.newKieSession("kieSessionIncludes2Events");
//        System.out.println("======= [开始执行 Includes 示例 - 场景2 1个参数] =======");
//        long meetingStart = System.currentTimeMillis();
//        MeetingEvent meeting = new MeetingEvent("Meeting-2001", meetingStart, meetingStart + 4L * 60 * 60 * 1000);
//        NoteTakingEvent note = new NoteTakingEvent("Note-2001", meetingStart + 20L * 60 * 1000, meetingStart + 3L * 60 * 60 * 1000 + 40L * 60 * 1000);
//        ksession.insert(meeting);
//        ksession.insert(note);
//        ksession.fireAllRules();
//        ksession.dispose();
//        System.out.println("======= [执行结束] =======");
//    }
//
//    public static void main(String[] args) {
//        KieServices ks = KieServices.Factory.get();
//        KieContainer kc = ks.getKieClasspathContainer();
//        KieSession ksession = kc.newKieSession("kieSessionIncludes3Events");
//        System.out.println("======= [开始执行 Includes 示例 - 场景3 2个参数] =======");
//        long movieStart = System.currentTimeMillis();
//        MovieScreeningEvent movie = new MovieScreeningEvent("Movie-3001", movieStart, movieStart + 2L * 60 * 60 * 1000);
//        BathroomBreakEvent bathroom = new BathroomBreakEvent("Bathroom-3001", movieStart + 6L * 60 * 1000, movieStart + 1L * 60 * 60 * 1000 + 52L * 60 * 1000);
//        ksession.insert(movie);
//        ksession.insert(bathroom);
//        ksession.fireAllRules();
//        ksession.dispose();
//        System.out.println("======= [执行结束] =======");
//    }
//
//    public static void main(String[] args) {
//        KieServices ks = KieServices.Factory.get();
//        KieContainer kc = ks.getKieClasspathContainer();
//        KieSession ksession = kc.newKieSession("kieSessionIncludes4Events");
//        System.out.println("======= [开始执行 Includes 示例 - 场景4 4个参数] =======");
//        long lunchStart = System.currentTimeMillis();
//        // 午餐事件：12:00 ~ 13:00（持续1小时）
//        LunchServiceEvent lunch = new LunchServiceEvent("Lunch-4001", lunchStart, lunchStart + 60L * 60 * 1000);
//        // 顾客事件：12:15 ~ 12:50（即开始15分钟后、结束前10分钟）
//        DiningEvent dining = new DiningEvent("Dining-4001", lunchStart + 15L * 60 * 1000, lunchStart + 50L * 60 * 1000);
//        ksession.insert(lunch);
//        ksession.insert(dining);
//        ksession.fireAllRules();
//        ksession.dispose();
//        System.out.println("======= [执行结束] =======");
//    }

}
