import com.google.gson.Gson;
import okhttp3.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DeepSeekClient {
    private static final String API_KEY = System.getenv("DEEPSEEK_API_KEY"); // ⚠️切记：这里填回你的 API Key！
    private static final String URL = "https://api.deepseek.com/chat/completions";

    private OkHttpClient client;
    private Gson gson;

    // ⭐ 新增：这就是 AI 的“记事本”，用来保存上下文记忆！
    private List<Map<String, String>> messageHistory;

    public DeepSeekClient() {
        // 检查到底读到了多长的字符串，以及前4个字母对不对（绝不暴露完整Key）

        this.client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        this.gson = new Gson();

        // 初始化记事本
        this.messageHistory = new ArrayList<>();

        // (可选) 给 AI 注入灵魂：第一条消息可以是 system 角色，设定它的人设
        Map<String, String> systemPrompt = new HashMap<>();
        systemPrompt.put("role", "system");
        systemPrompt.put("content", "你是一个非常幽默的Java私人导师，你的主人叫宋子涵（如果他没改名的话）。");
        this.messageHistory.add(systemPrompt);
    }

    public String chat(String userMessage) {
        // 1. 把用户的新话写进记事本
        Map<String, String> userMsgMap = new HashMap<>();
        userMsgMap.put("role", "user");
        userMsgMap.put("content", userMessage);
        messageHistory.add(userMsgMap);
        int MAX_HISTORY = 11; // 1条 System 人设 + 最近 10 条对话
        // 如果记事本太厚了，就撕掉最前面的旧记录（但永远保留第0页的 System 人设）
        while (messageHistory.size() > MAX_HISTORY) {
            // 注意：我们移除的是索引 1（也就是最老的一条对话），这样新的对话就能塞进来了。
            // 索引 0 永远是 System 人设，不能删！
            messageHistory.remove(1);
        }
        // 2. 用 Map 优雅地组装整个包裹（再也不用手拼恶心的转义双引号了！）
        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("model", "deepseek-chat");
        requestBodyMap.put("messages", messageHistory); // 直接把整个记事本塞进去！
        requestBodyMap.put("stream", false);

        // 使用 Gson 把 Map 完美转换成 JSON 字符串
        String jsonBody = gson.toJson(requestBodyMap);

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(URL)
                .addHeader("Authorization", "Bearer " + API_KEY.trim())
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String rawJson = response.body().string();
                DeepSeekResponse data = gson.fromJson(rawJson, DeepSeekResponse.class);
                String aiAnswer = data.choices.get(0).message.content;

                // 3. 重点：把 AI 的回答也写进记事本，形成闭环！
                Map<String, String> aiMsgMap = new HashMap<>();
                aiMsgMap.put("role", "assistant"); // AI 的角色叫 assistant
                aiMsgMap.put("content", aiAnswer);
                messageHistory.add(aiMsgMap);

                return aiAnswer;
            } else {
                return "【通信失败】错误代码：" + response.code();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "【网络异常】请检查你的网络连接。";
        }
    }
}