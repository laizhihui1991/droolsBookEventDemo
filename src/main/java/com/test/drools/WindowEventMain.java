package com.test.drools;

import com.test.drools.pojo.operators.TemperatureEvent;
import org.drools.core.time.SessionPseudoClock;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class WindowEventMain {

    public static void main(String[] args) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession kieSession = kc.newKieSession("kieSessionWindowEvents");
        SessionPseudoClock clock = kieSession.getSessionClock();
        Random random = new Random();
        DecimalFormat df = new DecimalFormat("#.#");
        // 时间滑动窗口示例
        System.out.println("=== 时间滑动窗口示例 ===");
        for (int i = 1; i <= 10; i++) {
            double temp = 28 + random.nextDouble() * 10;
            temp = Double.parseDouble(df.format(temp));
            TemperatureEvent event = new TemperatureEvent("S" + i, temp, clock.getCurrentTime());
            kieSession.insert(event);
            clock.advanceTime(10, TimeUnit.SECONDS);
        }
        kieSession.fireAllRules();
        // 长度滑动窗口示例
        System.out.println("\n=== 长度滑动窗口示例 ===");
        for (int i = 1; i <= 25; i++) {
            double temp = 29 + random.nextDouble() * 6;
            temp = Double.parseDouble(df.format(temp));
            TemperatureEvent event = new TemperatureEvent("Sensor" + i, temp, clock.getCurrentTime());
            kieSession.insert(event);
            clock.advanceTime(5, TimeUnit.SECONDS);
        }
        kieSession.fireAllRules(); // 触发规则
        kieSession.dispose();
    }
}
