package com.demo.search;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class KeywordSpamFilter implements SpamFilter {

    private static final List<String> spamKeywords = Arrays.asList(
            "招聘",
            "cp",
            "cpdd",
            "nm","nmsl","mm"
    );

    @Override
    public boolean isSpamDetected(String content) {
        // 将内容转换为小写，以不区分大小写
        content = content.toLowerCase();

        // 检查内容中是否包含垃圾关键词
        for (String keyword : spamKeywords) {
            if (content.contains(keyword.toLowerCase())) {
                return true; // 包含垃圾关键词，认为是垃圾帖子
            }
        }

        return false; // 未包含垃圾关键词，认为不是垃圾帖子
    }
}
