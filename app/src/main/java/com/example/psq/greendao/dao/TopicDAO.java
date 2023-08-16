package com.example.psq.greendao.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 题库/题目
 */
@Entity
public class TopicDAO {
    @Id(autoincrement = true)
    Long id;
    Long qBankId;

    private String topicId;
    private String bankId;
    private String type;
    private String title;
    private String content;
    private String sort;

    @Generated(hash = 1841743137)
    public TopicDAO(Long id, Long qBankId, String topicId, String bankId,
                    String type, String title, String content, String sort) {
        this.id = id;
        this.qBankId = qBankId;
        this.topicId = topicId;
        this.bankId = bankId;
        this.type = type;
        this.title = title;
        this.content = content;
        this.sort = sort;
    }

    @Generated(hash = 1471399806)
    public TopicDAO() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQBankId() {
        return this.qBankId;
    }

    public void setQBankId(Long qBankId) {
        this.qBankId = qBankId;
    }

    public String getTopicId() {
        return this.topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
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

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
