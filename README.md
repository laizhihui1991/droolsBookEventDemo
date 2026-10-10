# droolsBookEventDemo

《Drools 规则引擎技术内幕：核心算法、源码实现与架构全景》配套示例代码：**复杂事件处理（CEP）**。

本工程基于 Drools 7.40.0.Final，演示在 `STREAM` 事件流模式和伪时钟（`SessionPseudoClock`）下：

- 事件的声明方式：Java 注解，或 DRL `declare`
- 事件元数据：`@timestamp`、`@duration`、`@expires`
- 13 种时间运算符：`after`、`before`、`coincides`、`during`、`includes`、`finishes`、`finishedby`、`meets`、`metby`、`overlaps`、`overlappedby`、`starts`、`startedby`
- 滑动窗口：`window:time`、`window:length`
- 入口点：`entry-point`，以及配合 `not` 检测"缺失事件"
- 打印 ReteOO 网络结构，方便对照书中源码分析章节阅读

## 环境要求

| 组件 | 版本 |
| --- | --- |
| JDK | 1.8 |
| Maven | 3.x |
| drools-core / drools-compiler / kie-api | 7.40.0.Final |
| slf4j-simple | 1.7.30 |

## 运行方式

工程是普通 Maven jar 工程，没有单元测试，每个示例都是一个带 `main` 方法的启动类。

```bash
# 编译（源码为 UTF-8，pom 未声明编码，中文 Windows 下建议显式指定）
mvn clean compile -Dproject.build.sourceEncoding=UTF-8

# 运行指定示例
mvn exec:java -Dexec.mainClass="com.test.drools.BeforeEventMain"
```

- pom.xml 没有声明 `exec-maven-plugin`，`exec:java` 会由 Maven 按插件前缀自动解析。更推荐在 IDE 中直接运行启动类。
- `ReteNetworkPrinter` 通过相对路径 `src/main/resources/...` 读取 DRL，工作目录必须是工程根目录。

## 目录结构

```
droolsBookEventDemo
├── pom.xml                                   # Drools 7.40.0.Final 依赖，Java 1.8 编译
└── src
    └── main
        ├── java
        │   └── com/test/drools
        │       ├── Main.java                     # LoginEvent 注解事件 + 伪时钟推进
        │       ├── DeclareMain.java              # DRL declare 事件 + FactType 动态建实例
        │       ├── TransactionEventMain.java     # 瞬时事件 vs 区间事件（@Duration）
        │       ├── VoiceCallEventMain.java       # @Expires 事件自动过期
        │       ├── AfterEventMain.java           # after 运算符
        │       ├── BeforeEventMain.java          # before 运算符
        │       ├── CoincidesEventMain.java       # coincides 运算符
        │       ├── DuringEventMain.java          # during 运算符（4 个场景）
        │       ├── IncludesEventMain.java        # includes 运算符（4 个场景）
        │       ├── IncludesMain.java             # includes 场景 1 的精简版
        │       ├── FinishesEventMain.java        # finishes 运算符
        │       ├── FinishedByEventMain.java      # finishedby 运算符
        │       ├── MeetsEventMain.java           # meets 运算符
        │       ├── MetByEventMain.java           # metby 运算符
        │       ├── OverlapsEventMain.java        # overlaps 运算符
        │       ├── OverlappedByEventMain.java    # overlappedby 运算符
        │       ├── StartsEventMain.java          # starts 运算符
        │       ├── StartedByEventMain.java       # startedby 运算符
        │       ├── WindowEventMain.java          # 时间滑动窗口 / 长度滑动窗口
        │       ├── FireAlarmMain.java            # entry-point + not：火灾后喷淋未启动报警
        │       ├── ReteNetworkPrinter.java       # 遍历并打印 ReteOO 网络节点
        │       └── pojo
        │           ├── LoginEvent.java               # @Role/@Timestamp 注解的登录事件
        │           ├── LoginEntity.java              # 无注解普通 POJO，用于 DRL 中 declare 补充事件元数据
        │           ├── TransactionEvent.java         # 交易事件（@Timestamp + @Duration）
        │           ├── VoiceCallEvent.java           # 通话事件（@Duration + @Expires("1s") + @ClassReactive）
        │           ├── FireDetectedEvent.java        # 火灾探测事件
        │           ├── SprinklerActivatedEvent.java  # 喷淋启动事件
        │           └── operators
        │               ├── PaymentEvent.java         # 支付（after）
        │               ├── LogisticsEvent.java       # 物流单（after）
        │               ├── InboundEvent.java         # 入库（before）
        │               ├── ShipmentEvent.java        # 发货（before）
        │               ├── TransferRequestEvent.java # 转账请求（coincides）
        │               ├── DebitTransactionEvent.java# 扣款流水（coincides）
        │               ├── AfterSaleEvent.java       # 售后（finishes / finishedby / overlaps / overlappedby）
        │               ├── RefundEvent.java          # 退款（finishes / finishedby）
        │               ├── CustomerSupportEvent.java # 客服介入（overlaps / overlappedby）
        │               ├── ProcessingEvent.java      # 加工（meets / metby）
        │               ├── QualityCheckEvent.java    # 质检（meets / metby）
        │               ├── LoadingEvent.java         # 装载（starts / startedby）
        │               ├── TransportEvent.java       # 运输（starts / startedby）
        │               ├── TemperatureEvent.java     # 温度读数（滑动窗口）
        │               └── during                    # during / includes 共用，@Duration 为 "endTimestamp - startTimestamp"
        │                   ├── MomCleaningEvent.java     # 场景1：妈妈打扫
        │                   ├── ChildPlayEvent.java       # 场景1：孩子玩积木
        │                   ├── MeetingEvent.java         # 场景2：会议
        │                   ├── NoteTakingEvent.java      # 场景2：记录
        │                   ├── MovieScreeningEvent.java  # 场景3：电影放映
        │                   ├── BathroomBreakEvent.java   # 场景3：中途离场
        │                   ├── LunchServiceEvent.java    # 场景4：午餐供应
        │                   └── DiningEvent.java          # 场景4：顾客用餐
        └── resources
            ├── META-INF
            │   └── kmodule.xml               # 所有 KieBase / KieSession 定义
            └── rules                         # package rules
                ├── loginEvent.drl            # declare DeclareLoginEvent + over window:time(10s)
                ├── transactionEvent.drl      # 交易区间重叠（overlaps）
                ├── voiceCallEvent.drl        # 通话事件检测
                ├── afterEvent.drl
                ├── beforeEvent.drl
                ├── coincidesEvent.drl
                ├── finishesRules.drl
                ├── finishedByRules.drl
                ├── meetsRules.drl
                ├── metByRules.drl            # 规则当前全部注释
                ├── overlapsEvent.drl
                ├── overlappedbyEvent.drl
                ├── startsRules.drl
                ├── startedByEvent.drl
                ├── windowEvent.drl           # window:time(2m) + window:length(20)
                ├── fireAlarm.drl             # entry-point "FireStream"
                ├── during                    # package rules.during
                │   ├── duringScenario1.drl   # during（无参数）
                │   ├── duringScenario2.drl   # during[30m]
                │   ├── duringScenario3.drl   # during[5m,10m]
                │   └── duringScenario4.drl   # during[10m,20m,5m,15m]
                └── includes                  # package rules.includes
                    ├── includesScenario1.drl # includes（无参数）
                    ├── includesScenario2.drl # includes[30m]
                    ├── includesScenario3.drl # includes[5m,10m]
                    └── includesScenario4.drl # includes[10m,20m,5m,15m]
```

## 核心配置：kmodule.xml

每个示例对应一组独立的 KieBase / KieSession：

| KieBase | packages | 事件模式 | KieSession（均为 `clockType="pseudo"`） |
| --- | --- | --- | --- |
| kieBaseEvents | rules | stream | kieSessionEvents |
| kieBaseTransactionEvents | rules | stream | kieSessionTransactionEvents |
| kieBaseVoiceCallEvents | rules | stream | kieSessionVoiceCallEvents |
| kieBaseAfterEvents | rules | stream | kieSessionAfterEvents |
| kieBaseBeforeEvents | rules | stream | kieSessionBeforeEvents |
| kieBaseCoincidesEvents | rules | stream | kieSessionCoincidesEvents |
| kieBaseDuringEvents | rules.during | 未指定（默认 cloud） | kieSessionDuring1Events ~ kieSessionDuring4Events |
| kieBaseIncludesEvents | rules.includes | stream | kieSessionIncludes1Events ~ kieSessionIncludes4Events |
| kieBaseFinishesEvents | rules | stream | kieSessionFinishesEvents |
| kieBaseFinishedByEvents | rules | stream | kieSessionFinishedByEvents |
| kieBaseMeetsEvents | rules | **cloud** | kieSessionMeetsEvents |
| kieBaseMetByEvents | rules | stream | kieSessionMetByEvents |
| kieBaseOverlapsEvents | rules | stream | kieSessionOverlapsEvents |
| kieBaseOverlappedByEvents | rules | stream | kieSessionOverlappedByEvents |
| kieBaseStartsEvents | rules | stream | kieSessionStartsEvents |
| kieBaseStartedByEvents | rules | stream | kieSessionStartedByEvents |
| kieBaseWindowEvents | rules | stream | kieSessionWindowEvents |
| kieBaseFireEvents | rules | stream | kieSessionFireEvents |

几点要注意：

- 除 during / includes 外，其余 KieBase 都是 `packages="rules"`，会加载 `rules` 包下的**全部** DRL。只要插入的事件类型被其他规则用到，控制台就会出现这些规则的输出。
- `during` 和 `includes` 的规则拆在子包里，每个 KieBase 只加载自己的 4 个场景。不过同一个 KieBase 下的 4 个 KieSession 共享这 4 个场景的规则。
- 伪时钟让事件时间完全由代码 `advanceTime(...)` 控制，示例结果可以稳定复现。

## 事件建模

所有事件类都用 `org.kie.api.definition.type` 下的注解声明事件元数据，分两种：

```java
// 瞬时事件：只有时间戳
@Role(Role.Type.EVENT)
@Timestamp("timestamp")
public class PaymentEvent { ... }

// 区间事件：时间戳 + 持续时间（duration 可以是字段，也可以是表达式）
@Role(Role.Type.EVENT)
@Timestamp("startTimestamp")
@Duration("endTimestamp - startTimestamp")
public class MomCleaningEvent { ... }
```

`loginEvent.drl` 里用 `declare` 直接在规则中定义事件类型 `DeclareLoginEvent`，`DeclareMain` 再通过 `KieBase#getFactType("rules", "DeclareLoginEvent")` 动态创建实例。

## 示例说明

### 1. 事件基础

| 启动类 | KieSession | 说明 |
| --- | --- | --- |
| `Main` | kieSessionEvents | 插入 3 个注解方式定义的 `LoginEvent`，伪时钟依次推进 1s、5s、6s，再推进 30s 观察事件过期。`loginEvent.drl` 中匹配 `LoginEvent` 的规则目前是注释状态，需要切换注释块才能看到规则命中。 |
| `DeclareMain` | kieSessionEvents | 与 `Main` 流程相同，改用 `declare` 声明的 `DeclareLoginEvent`。命中"检测登录失败事件"（salience 50）和"检测10秒内的登录事件"（`over window:time(10s)`，salience 10）两条规则。文件下方注释块是用 `LoginEntity` + `declare` 补充元数据的写法。 |
| `TransactionEventMain` | kieSessionTransactionEvents | 示例 1：duration 为 0 的瞬时事件，不触发 `overlaps`；示例 2：duration 为 20s 的区间事件，触发 `overlaps`。最后打印工作内存中残留的 Fact。 |
| `VoiceCallEventMain` | kieSessionVoiceCallEvents | `VoiceCallEvent` 声明了 `@Expires("1s")`，推进时钟后打印工作内存，观察事件被自动回收。 |

### 2. 时间运算符

| 启动类 | 规则文件 | 运算符写法 | 业务场景 |
| --- | --- | --- | --- |
| `AfterEventMain` | afterEvent.drl | `after[3m30s, 4m]`、`after[-4m, -3m30s]` | 支付后 3分30秒~4分钟内生成物流单；负区间等价于反向判断 |
| `BeforeEventMain` | beforeEvent.drl | `before[3m30s, 4m]` | 入库必须在发货前 3分30秒~4分钟完成 |
| `CoincidesEventMain` | coincidesEvent.drl | `coincides`、`coincides[5s]`、`coincides[15s,10s]` | 转账请求与扣款流水的时间对齐，三个场景分别命中三条规则 |
| `DuringEventMain` | during/duringScenario1~4.drl | `during`、`[30m]`、`[5m,10m]`、`[10m,20m,5m,15m]` | 打扫/玩耍、会议/记录、观影/离场、午餐/用餐 |
| `IncludesEventMain` | includes/includesScenario1~4.drl | `includes`，参数同上 | `during` 的逆关系，场景与 during 相同 |
| `IncludesMain` | includes/includesScenario1.drl | `includes` | `IncludesEventMain` 场景 1 的单独版本 |
| `FinishesEventMain` | finishesRules.drl | `finishes`、`finishes[5s]` | 退款与售后同时结束 / 5 秒容差内结束 |
| `FinishedByEventMain` | finishedByRules.drl | `finishedby`、`finishedby[5s]` | `finishes` 的逆关系 |
| `MeetsEventMain` | meetsRules.drl | `meets`、`meets[5s]` | 加工结束后立即开始质检（该 KieBase 为 cloud 模式） |
| `MetByEventMain` | metByRules.drl | `metby`、`metby[5s]` | `meets` 的逆关系 |
| `OverlapsEventMain` | overlapsEvent.drl | `overlaps`、`overlaps[4s]`、`overlaps[6s,10s]` | 客服介入与售后时间交叠，4 个订单分别对应不同匹配结果 |
| `OverlappedByEventMain` | overlappedbyEvent.drl | `overlappedby`、`[4s]`、`[6s,10s]` | `overlaps` 的逆关系 |
| `StartsEventMain` | startsRules.drl | `starts`、`starts[10s]` | 装载与运输同步启动 / 10 秒容差内启动 |
| `StartedByEventMain` | startedByEvent.drl | `startedby`、`startedby[10s]` | `starts` 的逆关系 |

- **多场景切换**：`DuringEventMain`、`IncludesEventMain`、`AfterEventMain`、`VoiceCallEventMain` 里保留了多个被注释的 `main` 方法，每个对应一个场景，切换注释即可运行。`DuringEventMain` 当前启用的是场景 3，`IncludesEventMain` 当前启用的是场景 1。`AfterEventMain` 的注释块还演示了"先插入物流、后插入支付"和多线程并发插入两种情况。
- **metby**：`metByRules.drl` 的规则目前全部注释。`MetByEventMain` 和 `MeetsEventMain` 插入的数据完全相同，又都会加载 `rules` 包，所以运行时实际命中的是 `meetsRules.drl`，正好可以用来观察 `metby` 与 `meets` 的对称关系。取消注释即可运行 `metby` 规则本身。

### 3. 滑动窗口与入口点

| 启动类 | 规则文件 | 说明 |
| --- | --- | --- |
| `WindowEventMain` | windowEvent.drl | 时间窗口：10 条读数、每 10 秒一条，`accumulate` + `over window:time(2m)` 求平均温度，超过 30℃ 告警。长度窗口：25 条读数，`over window:length(20)` 统计 >32℃ 的次数，超过 15 条告警。温度值随机生成，每次运行结果可能不同。 |
| `FireAlarmMain` | fireAlarm.drl | 事件写入 `entry-point "FireStream"`。规则用 `not(SprinklerActivatedEvent(... this after[0s,10s] $f))` 检测火灾后 10 秒内喷淋未启动。场景 1 触发报警，场景 2 在第 5 秒启动喷淋，不报警。 |

### 4. Rete 网络结构打印

`ReteNetworkPrinter` 用 `KieFileSystem` 单独编译 `includesScenario1.drl`，通过 `KnowledgeBaseImpl#getRete()` 获取 Rete 根节点，从 `ObjectTypeNode` 开始递归遍历：

- `ObjectSource` 的 `ObjectSinkPropagator`：`AlphaNode` 及其约束、`BetaNode` 及其联结约束
- `LeftTupleSource` 的 `LeftTupleSinkPropagator`：`BetaNode`、`EvalConditionNode`（通过反射读取 `eval` 字段）、`ExistsNode`、`RuleTerminalNode`

只打印包名以 `com.test` 开头的事件类型。打印完网络结构后会执行一次规则。把 `readDrlFromFile` 的路径换成其他 DRL，就能观察对应规则生成的网络结构。该类直接依赖 `drools-core` 的内部 API（`org.drools.core.reteoo.*`），只适用于 7.x。
