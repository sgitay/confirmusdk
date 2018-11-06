
package com.confirmu.up.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class QuestionsResponse {

    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("IsSuccess")
    @Expose
    private Boolean isSuccess;
    @SerializedName("Questions")
    @Expose
    private Questions questions;

    /**
     * No args constructor for use in serialization
     * 
     */
    public QuestionsResponse() {
    }

    /**
     * 
     * @param questions
     * @param isSuccess
     * @param code
     */
    public QuestionsResponse(Integer code, Boolean isSuccess, Questions questions) {
        super();
        this.code = code;
        this.isSuccess = isSuccess;
        this.questions = questions;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Questions getQuestions() {
        return questions;
    }

    public void setQuestions(Questions questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("code", code).append("isSuccess", isSuccess).append("questions", questions).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(questions).append(isSuccess).append(code).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof QuestionsResponse) == false) {
            return false;
        }
        QuestionsResponse rhs = ((QuestionsResponse) other);
        return new EqualsBuilder().append(questions, rhs.questions).append(isSuccess, rhs.isSuccess).append(code, rhs.code).isEquals();
    }

}
