package com.test.drools;

import com.test.drools.pojo.operators.during.ChildPlayEvent;
import com.test.drools.pojo.operators.during.MomCleaningEvent;
import org.drools.core.common.BaseNode;
import org.drools.core.impl.KnowledgeBaseImpl;
import org.drools.core.reteoo.*;
import org.drools.core.spi.BetaNodeFieldConstraint;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

public class ReteNetworkPrinter {


    public static void main(String[] args) {
        String drl = readDrlFromFile("src/main/resources/rules/includes/includesScenario1.drl");

        KieServices ks = KieServices.Factory.get();
        KieFileSystem kfs = ks.newKieFileSystem();
        kfs.write("src/main/resources/rules/includes/includesScenario1.drl", drl);
        ks.newKieBuilder(kfs).buildAll();

        KieContainer kieContainer = ks.newKieContainer(ks.getRepository().getDefaultReleaseId());
        KieBase kieBase = kieContainer.getKieBase();

        printReteNetwork(kieBase);

        KieSession session = kieBase.newKieSession();
        System.out.println("======= [开始执行 Includes 示例 - 场景1 无参数] =======");
        // 妈妈打扫客厅：下午 2 点到 4 点  其实这里表示持续时间为120分钟
        MomCleaningEvent mom = new MomCleaningEvent("Mom-1001", 0, 2 * 60 * 60 * 1000);
        // 孩子玩积木：下午 2 点 30 分 - 3 点 10 分  其实这里表示持续时间为70分钟
        ChildPlayEvent child = new ChildPlayEvent("Child-1001", 30 * 60 * 1000, 70 * 60 * 1000);
        session.insert(mom);
        session.insert(child);
        session.fireAllRules();
        session.fireAllRules();
        session.dispose();
    }
    
    private static String readDrlFromFile(String filePath) {
        StringBuilder drl = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                drl.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("读取规则文件失败: " + e.getMessage());
            e.printStackTrace();
        }
        return drl.toString();
    }

    private static void printReteNetwork(KieBase kieBase) {
        if (!(kieBase instanceof KnowledgeBaseImpl)) {
            System.err.println("无法访问内部 Rete 网络结构。");
            return;
        }
        KnowledgeBaseImpl kBaseImpl = (KnowledgeBaseImpl) kieBase;
        Rete rete = kBaseImpl.getRete();
        System.out.println("\n========== ReteOO 网络结构 ==========");
        Collection<ObjectTypeNode> otns = rete.getObjectTypeNodes();
        for (ObjectTypeNode otn : otns) {
            String className = otn.getObjectType().getClassType().getName();
            if (className.startsWith("com.test")) {
                System.out.println("ObjectTypeNode: " + className);
                printChildren(otn, "  ");
            }
        }
        System.out.println("=====================================\n");
    }

    private static void printChildren(BaseNode node, String indent) {
        if (node instanceof ObjectSource) {
            ObjectSinkPropagator sinks = ((ObjectSource) node).getObjectSinkPropagator();
            for (ObjectSink sink : sinks.getSinks()) {
                BaseNode baseSink = (BaseNode) sink;
                StringBuilder extraInfo = new StringBuilder();

                if (sink instanceof AlphaNode) {
                    extraInfo.append("【AlphaNode】属性过滤条件: ").append(((AlphaNode) sink).getConstraint());
                } else if (sink instanceof BetaNode) {
                    extraInfo.append("【BetaNode: ").append(sink.getClass().getSimpleName()).append("】 联结条件: ");
                    for (BetaNodeFieldConstraint constraint : ((BetaNode) sink).getConstraints()) {
                        extraInfo.append("\n").append(indent).append("    ↳ ").append(constraint);
                    }
                } else if (sink instanceof EvalConditionNode) {
                    extraInfo.append("【EvalConditionNode】嵌套表达式: ").append(getEvalConditionString((EvalConditionNode) sink));
                } else if (sink instanceof ExistsNode) {
                    extraInfo.append("【ExistsNode】是否存在条件");
                } else if (sink instanceof RuleTerminalNode) {
                    extraInfo.append("【RuleTerminalNode】规则终点: ").append(((RuleTerminalNode) sink).getRule().getName());
                }
                System.out.println(indent + baseSink.getClass().getSimpleName() + " (id=" + baseSink.getId() + ") " + extraInfo);
                printChildren(baseSink, indent + "  ");
            }
        }

        if (node instanceof LeftTupleSource) {
            LeftTupleSinkPropagator sinks = ((LeftTupleSource) node).getSinkPropagator();
            for (LeftTupleSink sink : sinks.getSinks()) {
                BaseNode baseSink = (BaseNode) sink;
                StringBuilder extraInfo = new StringBuilder();

                if (sink instanceof BetaNode) {
                    extraInfo.append("【BetaNode: ").append(sink.getClass().getSimpleName()).append("】 联结条件: ");
                    for (BetaNodeFieldConstraint constraint : ((BetaNode) sink).getConstraints()) {
                        extraInfo.append("\n").append(indent).append("    ↳ ").append(constraint);
                    }
                } else if (sink instanceof EvalConditionNode) {
                    extraInfo.append("【EvalConditionNode】嵌套表达式: ").append(getEvalConditionString((EvalConditionNode) sink));
                } else if (sink instanceof ExistsNode) {
                    extraInfo.append("【ExistsNode】是否存在条件");
                } else if (sink instanceof RuleTerminalNode) {
                    extraInfo.append("【RuleTerminalNode】规则终点: ").append(((RuleTerminalNode) sink).getRule().getName());
                }

                System.out.println(indent + baseSink.getClass().getSimpleName() + " (id=" + baseSink.getId() + ") " + extraInfo);
                printChildren(baseSink, indent + "  ");
            }
        }
    }

    private static String getEvalConditionString(EvalConditionNode node) {
        try {
            java.lang.reflect.Field evalField = EvalConditionNode.class.getDeclaredField("eval");
            evalField.setAccessible(true);
            Object eval = evalField.get(node);
            return eval != null ? eval.toString() : "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
