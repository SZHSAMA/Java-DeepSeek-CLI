package com.szh.deepseek_api;

import java.util.List;

public class DeepSeekResponse {
    // 对应 JSON 里的 "choices" 数组
    public List<Choice> choices;

    public static class Choice {
        // 对应 choices 里的 "message" 对象
        public Message message;
    }

    public static class Message {
        // 对应 message 里的 "content" 字符串
        public String content;
    }
}