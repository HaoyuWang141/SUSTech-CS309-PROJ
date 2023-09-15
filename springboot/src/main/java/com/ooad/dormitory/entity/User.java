package com.ooad.dormitory.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Objects;

@TableName("t_user")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User {

  @TableId
  @JsonProperty("account_id")
  private Integer accountId;

  @JsonProperty("display_name")
  private String displayName;

  private Integer questionNumber;

  private Integer answerNumber;

  private Integer commentNumber;

  public Integer getAccountId() {
    return accountId;
  }

  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public Integer getQuestionNumber() {
    return questionNumber;
  }

  public void setQuestionNumber(Integer questionNumber) {
    this.questionNumber = questionNumber;
  }

  public Integer getAnswerNumber() {
    return answerNumber;
  }

  public void setAnswerNumber(Integer answerNumber) {
    this.answerNumber = answerNumber;
  }

  public Integer getCommentNumber() {
    return commentNumber;
  }

  public void setCommentNumber(Integer commentNumber) {
    this.commentNumber = commentNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return accountId.equals(user.accountId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId);
  }

  @Override
  public String toString() {
    return "User{" +
        "accountId=" + accountId +
        ", displayName='" + displayName + '\'' +
        ", questionNum=" + questionNumber +
        ", answerNum=" + answerNumber +
        ", commentNum=" + commentNumber +
        '}';
  }
}
