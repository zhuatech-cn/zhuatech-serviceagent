# ServiceAgent · 知华科技客户服务智能体


## 本次升级：服务积压净消化与班次负荷

按已积压、预计新流入和可用坐席分钟计算本班处理容量及期末积压；区分理论清理与持续新增流量。

新增已认证的结构化录入工作台（`/operations.html`），支持批量核算、逐条结果依据、分级复核和CSV/完整JSON导出。结果由真实后端算法计算，不是预置演示数字。所有数值精确传递，不调用外部模型。

这是**只读核算**：不写库、不审批、不自动执行财务、签署、销毁或设备控制。输入条件满足不是执行授权。[算法、输入边界与接口说明](docs/OPERATIONS-REVIEW.md)。
## 本次功能更新：工作日服务时限计算

按办公时段、周末及显式节假日计算每张工单的到期时间；不修改工单。时间使用不带时区的组织本地时间。 登录后从「业务计算工具」进入，支持真实后端校验与结果导出。详见[接口、示例与边界说明](docs/AI_BUSINESS_UPGRADE_20261023.md)。本次不改变既有许可及联系方式。

[简体中文](README.md) | [English](README.en.md)

[知华科技官网](https://www.zhuatech.cn/) · 上海如静知华信息科技有限公司

ServiceAgent 不是一个“自动回复一切”的机器人，而是一套面向在线客服、电话客服、企业微信和客户成功团队的协同系统：智能体识别诉求、检索有效政策并起草回复；人工坐席负责沟通、承诺与结案。

## 从消息到结案

`客户消息 → 诉求与情绪识别 → 服务知识检索 → 回复草案 → 风险路由 → 人工确认 → 回访与评测`

![客户服务智能体运营中心](docs/images/serviceagent-operations.png)

运营中心展示全渠道工单、首次响应、知识就绪度、隐私风险、退款赔付及服务质量评测。

![客服坐席协同工作台](docs/images/serviceagent-workspace.png)

坐席端为一线服务人员保留引用依据、建议动作、客户上下文和人工接管入口，避免在高情绪场景继续机械回复。

## 社区版覆盖

- 多渠道服务工单与 SLA 队列
- 客户诉求、情绪和优先级辅助识别
- 产品政策、服务流程与历史案例引用
- 回复草案及敏感字段默认遮蔽
- 退款、赔付、投诉和隐私事项主管审批
- 回复评测、回访记录与知识缺口反馈

`CustomerResponseGuardService` 会根据情绪风险、隐私数据、退款赔付和主管审批状态，返回 `DRAFT_RESPONSE`、`HUMAN_TAKEOVER`、`PRIVACY_REVIEW` 或 `SUPERVISOR_APPROVAL` 等可解释路由。

## 技术栈与运行

- Java 21 / Spring Boot / Spring Security / JWT / JPA
- Vue 3 / Pinia / Vue Router / Vite / Axios
- MySQL 8 / Flyway / H2 Test
- Docker Compose / Nginx / GitHub Actions

~~~bash
cd frontend
npm install
npm run dev:demo
~~~

演示地址为 `http://localhost:5173`；`planner / Demo@2026` 进入管理端，`operator / Demo@2026` 进入坐席端。默认演示不发送真实消息，也不连接真实 CRM 或模型服务。

## 许可说明

本项目**仅能用于个人学习、研究和非商业交流，不得商用**。企业内部部署、生产使用、二次开发交付、SaaS、收费服务或品牌替换，需获得上海如静知华信息科技有限公司书面授权，完整条款见 [LICENSE](LICENSE)。

需要 AI 客服定制、知识库建设、系统集成、私有化部署或软件项目外包，可访问[知华科技官网](https://www.zhuatech.cn/)或扫码联系。

| 技术咨询 | 商务合作 |
| --- | --- |
| ![微信咨询一](docs/images/zhuatech-wechat-consulting.png) | ![微信咨询二](docs/images/zhuatech-wechat-consulting-2.png) |

SEO 关键词：Customer Service Agent、AI 客服系统、智能工单、客服知识库、人工接管、Java Vue 开源项目、知华科技。


## 2026 企业级热度项目升级

本次根据公开仓库访问热度补充 **客户服务 SLA 编排与升级**：按客户等级、工单优先级、已耗时和敏感数据状态计算响应时限、升级层级与自动回复边界。

- 企业 API：`POST /api/enterprise/service-sla/orchestrate`
- 决策输出：执行许可、量化指标、阻断/升级路线、控制清单
- 可审计性：规则确定、输入输出可留痕，并附正常与阻断场景测试
- AI 接入：预留 DeepSeek-compatible 建议层配置，AI 不直接绕过审批或改变正式业务状态

详细设计见 [企业级升级说明](docs/ENTERPRISE_UPGRADE.md)。深度开发、企业部署和系统集成请联系[知华科技（上海如静知华信息科技有限公司）](https://www.zhuatech.cn/)。

## 输入对照与版本化快照

在既有「服务积压净消化与班次负荷」基础上，新增 `/comparison.html`：基准与候选两组数据都由当前真实算法重算，按编号展示新增、移除、逐字段变化和精确数值差，支持筛选、分页和全量导出。原核算入口可导出输入快照，对照页经后端完整校验后整体导入；失败不覆盖当前输入。

快照区分项目、字段契约及算法版本。建议较宽松不代表风险已下降，输入摘要不是签名；操作人和时间仅为响应元数据，不是持久化审计。本次未新增模型调用或业务写入，也不代表全系统生产认证。详见[输入对照操作与接口说明](docs/INPUT-COMPARISON.md)。
