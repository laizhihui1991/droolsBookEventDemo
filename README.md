# droolsBookEventDemo

《Drools 规则引擎技术内幕：核心算法、源码实现与架构全景》配套示例代码 —— 复杂事件处理（CEP）篇。

本工程演示 Drools 7.x 在 **STREAM 事件流模式 + 伪时钟（Pseudo Clock）** 下的事件建模、时间运算符、滑动窗口、入口点（Entry Point）等能力，并附带一个打印 ReteOO 网络结构的工具类，便于结合书中源码分析章节对照阅读。

## 环境要求

| 组件 | 版本 |
| --- | --- |
| JDK | 1.8+ |
| Maven | 3.x |
| Drools（drools-core / drools-compiler / kie-api） | 7.40.0.Final |
| 日志 | slf4j-simple 1.7.30 |

## 快速开始

```bash
# 编译
mvn clean compile

# 运行某个示例（以 Before 运算符为例）
mvn exec:java -Dexec.mainClass="com.test.drools.BeforeEventMain"
```

> pom.xml 中未声明 `exec-maven-plugin`，上面的命令会由 Maven 按需解析插件；也可以直接在 IDE 中运行各 `*Main` 类的 `main` 方法。
>
> `ReteNetworkPrinter` 通过相对路径 `src/main/resources/...` 读取 DRL，需以工程根目录为工作目录运行。

## 目录结构

```
droolsBookEventDemo
├── pom.xml
└── src/main
    ├── java/com/test/drools
    │   ├── *Main.java                  # 各示例的启动入口
    │   ├── ReteNetworkPrinter.java     # 打印 ReteOO 网络结构
    │   └── pojo
    │       ├── *.java                  # 登录、交易、通话、火灾等基础事件
    │       └── operators
    │           ├── *.java              # 时间运算符示例使用的事件
    │           └── during/*.java       # during / includes 场景使用的事件
    └── resources
        ├── META-INF/kmodule.xml        # KieBase / KieSession 定义
        └── rules
            ├── *.drl                   # 各示例规则（package rules）
            ├── during/*.drl            # during 四个场景（package rules.during）
            └── includes/*.drl          # includes 四个场景（package rules.includes）
```

## 核心配置

`kmodule.xml` 为每个示例定义了独立的 KieBase / KieSession：

- `eventProcessingMode="stream"`：启用事件流模式，时间运算符、滑动窗口、事件自动过期均依赖该模式。
- `clockType="pseudo"`：会话使用 `SessionPseudoClock`，示例通过 `advanceTime(...)` 手动推进时间，结果可重复。
- `kieBaseMeetsEvents` 显式使用 `cloud` 模式，`kieBaseDuringEvents` 未指定模式（默认 `cloud`），可与其他 STREAM 模式示例对比两种模式的差异。

事件类通过注解声明元数据：

```java
@Role(Role.Type.EVENT)                       // 标记为事件
@Timestamp("startTimestamp")                 // 时间戳字段
@Duration("endTimestamp - startTimestamp")   // 持续时间（区间事件）
@Expires("1s")                               // 过期时间（见 VoiceCallEvent）
```

也可以在 DRL 中用 `declare ... @role(event) @timestamp(...)` 声明事件类型，见 `loginEvent.drl` 与 `DeclareMain`。

> 注意：大部分 KieBase 的 `packages="rules"`，会加载 `rules` 包下全部 DRL，因此运行某个示例时，控制台可能同时出现其他规则的输出。

## 示例索引

### 事件基础

| 启动类 | KieSession | 规则文件 | 演示内容 |
| --- | --- | --- | --- |
| `Main` | kieSessionEvents | loginEvent.drl | 注解方式定义事件 `LoginEvent`，伪时钟推进与 `over window:time` |
| `DeclareMain` | kieSessionEvents | loginEvent.drl | DRL `declare` 声明事件，通过 `FactType` 动态创建事件实例 |
| `TransactionEventMain` | kieSessionTransactionEvents | transactionEvent.drl | 带 `@Duration` 的区间事件，检测交易时间重叠 |
| `VoiceCallEventMain` | kieSessionVoiceCallEvents | voiceCallEvent.drl | `@Expires` 事件过期机制 |

### 时间运算符（13 种 Allen 区间关系）

| 启动类 | KieSession | 规则文件 | 运算符 | 业务场景 |
| --- | --- | --- | --- | --- |
| `AfterEventMain` | kieSessionAfterEvents | afterEvent.drl | `after[3m30s, 4m]`、负区间 | 支付后按时生成物流单 |
| `BeforeEventMain` | kieSessionBeforeEvents | beforeEvent.drl | `before[3m30s, 4m]` | 入库须在发货前完成 |
| `CoincidesEventMain` | kieSessionCoincidesEvents | coincidesEvent.drl | `coincides` / `[5s]` / `[15s,10s]` | 转账请求与扣款流水对齐 |
| `DuringEventMain` | kieSessionDuring1~4Events | during/duringScenario1~4.drl | `during` 0/1/2/4 个参数 | 打扫与玩耍、会议与记录、观影与离场、午餐与用餐 |
| `IncludesEventMain` / `IncludesMain` | kieSessionIncludes1~4Events | includes/includesScenario1~4.drl | `includes` 0/1/2/4 个参数 | `during` 的逆关系 |
| `FinishesEventMain` | kieSessionFinishesEvents | finishesRules.drl | `finishes` / `[5s]` | 退款与售后同步结束 |
| `FinishedByEventMain` | kieSessionFinishedByEvents | finishedByRules.drl | `finishedby` / `[5s]` | `finishes` 的逆关系 |
| `MeetsEventMain` | kieSessionMeetsEvents | meetsRules.drl | `meets` / `[5s]` | 加工结束后立即质检（cloud 模式） |
| `MetByEventMain` | kieSessionMetByEvents | metByRules.drl | `metby` / `[5s]` | `meets` 的逆关系 |
| `OverlapsEventMain` | kieSessionOverlapsEvents | overlapsEvent.drl | `overlaps` / `[4s]` / `[6s,10s]` | 客服介入与售后时间交叠 |
| `OverlappedByEventMain` | kieSessionOverlappedByEvents | overlappedbyEvent.drl | `overlappedby` / `[4s]` / `[6s,10s]` | `overlaps` 的逆关系 |
| `StartsEventMain` | kieSessionStartsEvents | startsRules.drl | `starts` / `[10s]` | 装载与运输同步启动 |
| `StartedByEventMain` | kieSessionStartedByEvents | startedByEvent.drl | `startedby` / `[10s]` | `starts` 的逆关系 |

> `DuringEventMain`、`IncludesEventMain`、`AfterEventMain` 中保留了多个被注释的 `main` 方法，分别对应不同场景，切换注释即可运行。
>
> `metByRules.drl` 中的规则目前处于注释状态，运行 `MetByEventMain` 时实际命中的是同包下 `meetsRules.drl` 的规则，可借此观察 `metby` 与 `meets` 的对称关系；取消注释即可运行 `metby` 规则本身。

### 滑动窗口与入口点

| 启动类 | KieSession | 规则文件 | 演示内容 |
| --- | --- | --- | --- |
| `WindowEventMain` | kieSessionWindowEvents | windowEvent.drl | `over window:time(2m)` 时间窗口求平均温度；`over window:length(20)` 长度窗口统计高温次数 |
| `FireAlarmMain` | kieSessionFireEvents | fireAlarm.drl | `from entry-point "FireStream"` + `not` + `after[0s,10s]`：火灾发生后 10 秒内喷淋未启动则报警 |

### 源码分析辅助

| 启动类 | 说明 |
| --- | --- |
| `ReteNetworkPrinter` | 编译 `includesScenario1.drl`，通过 `KnowledgeBaseImpl#getRete()` 遍历 `ObjectTypeNode → AlphaNode → BetaNode → RuleTerminalNode`，打印 ReteOO 网络中各节点及约束，再执行一次规则。可替换 DRL 路径观察其他规则生成的网络结构。 |
