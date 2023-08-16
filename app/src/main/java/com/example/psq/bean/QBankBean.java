package com.example.psq.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class QBankBean {
    public String id;
    public String title;
    @JSONField(name = "abstract")
    public String abstractX;
    public String created_at;
    public String sort;
    public Integer topicCount;
    public List<TopicDTO> topic;

    public static class TopicDTO {
        public String id;
        public String question_bank_id;
        public String type;
        public String title;
        public String content;
        public String sort;
    }
}
