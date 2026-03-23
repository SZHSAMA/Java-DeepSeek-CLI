package com.szh.deepseek_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// @RestController 告诉 Spring：这个类是一个 Web 窗口，返回的内容直接给用户看
@RestController
public class ChatController {

    // 1. 把昨天写好的 AI 大脑搬过来
    private DeepSeekClient aiClient = new DeepSeekClient();

    // 2. 定义窗口的“地址”。
    // 当有人访问 http://localhost:8080/chat?msg=你好 时，这个方法就会被触发
    @GetMapping("/chat")
    public Result<String> chat(@RequestParam("msg") String msg) {
        try{
            // 3. 调用 AI 大脑获取回答
            String answer = aiClient.chat(msg);
            return Result.success(answer);
        }catch(Exception e){
            return Result.error("AI引擎暂时熄火了，原因：" +  e.getMessage());
        }

    }
}