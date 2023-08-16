package com.example.psq.greendao.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AnswerDAO {
    @Id(autoincrement = true)
    Long id;
    Long answeredId;

    private String answerId;
    private String bankId;
    private String type;
    private String title;
    private String content;
    private String answer;

    @Generated(hash = 1095264549)
    public AnswerDAO(Long id, Long answeredId, String answerId, String bankId,
                     String type, String title, String content, String answer) {
        this.id = id;
        this.answeredId = answeredId;
        this.answerId = answerId;
        this.bankId = bankId;
        this.type = type;
        this.title = title;
        this.content = content;
        this.answer = answer;
    }

    @Generated(hash = 1304262765)
    public AnswerDAO() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnsweredId() {
        return this.answeredId;
    }

    public void setAnsweredId(Long answeredId) {
        this.answeredId = answeredId;
    }

    public String getAnswerId() {
        return this.answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getBankId() {
        return this.bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
