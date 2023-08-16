package com.example.psq.bean;

import java.util.List;

public class UpLoadAnswerBean {
    public String name;//名字
    public String bankId;//题库id
    public String answerTime;//答题时间
    public boolean upload;//上传状态
    public List<AnswerDao> answerDAOS;

    public static class AnswerDao {
        public int answeredId;//题目id
        public String content;//答案
    }
}
