# 🤖 DeepSeek AI Assistant (Java Spring Boot)

一个基于 Spring Boot 和 DeepSeek 官方 API 构建的极简、沉浸式私人 AI 导师终端。

## ✨ 项目亮点
* **开箱即用**：零前端构建工具依赖，纯原生 HTML/JS/CSS 实现类似 Apple iMessage 的高级交互界面。
* **优雅的后端架构**：采用 Spring Boot 3.x 构建 RESTful API，通过泛型 `Result<T>` 实现企业级统一 JSON 响应。
* **会话级记忆**：前端集成 LocalStorage，实现跨页面的持久化聊天记录。
* **绝对安全**：强制使用系统环境变量读取 API Key，杜绝硬编码泄露风险。

## 🛠️ 技术栈
* **后端**：Java 17, Spring Boot, OkHttp3, Gson
* **前端**：HTML5, 原生 JavaScript (Fetch API), CSS3 (Flexbox 弹性布局)

## 🚀 快速启动

**1. 配置环境变量**
在运行前，必须在系统或 IDE 的运行配置中添加环境变量：
`DEEPSEEK_API_KEY` = `sk-你的真实API密钥`

**2. 启动 Spring Boot**
运行 `DeepseekApiApplication.java` 中的 `main` 方法。
控制台出现 `Tomcat started on port(s): 8080` 即为启动成功。

**3. 访问终端**
打开浏览器，访问：[http://localhost:8080](http://localhost:8080) 即可开始对话。

## 👨‍💻 作者
宋子涵 - [面向 Offer 驱动学习的项目]