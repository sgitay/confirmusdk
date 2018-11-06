
package com.confirmu.up.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Car {

    @SerializedName("question_1")
    @Expose
    private String question1;
    @SerializedName("question_10")
    @Expose
    private String question10;
    @SerializedName("question_11")
    @Expose
    private String question11;
    @SerializedName("question_12")
    @Expose
    private String question12;
    @SerializedName("question_13")
    @Expose
    private String question13;
    @SerializedName("question_2")
    @Expose
    private String question2;
    @SerializedName("question_3")
    @Expose
    private String question3;
    @SerializedName("question_4")
    @Expose
    private String question4;
    @SerializedName("question_5")
    @Expose
    private String question5;
    @SerializedName("question_6")
    @Expose
    private String question6;
    @SerializedName("question_7")
    @Expose
    private String question7;
    @SerializedName("question_8")
    @Expose
    private String question8;
    @SerializedName("question_9")
    @Expose
    private String question9;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Car() {
    }

    /**
     * 
     * @param question7
     * @param question6
     * @param question9
     * @param question8
     * @param question10
     * @param question12
     * @param question11
     * @param question13
     * @param question1
     * @param question2
     * @param question3
     * @param question4
     * @param question5
     */
    public Car(String question1, String question10, String question11, String question12, String question13, String question2, String question3, String question4, String question5, String question6, String question7, String question8, String question9) {
        super();
        this.question1 = question1;
        this.question10 = question10;
        this.question11 = question11;
        this.question12 = question12;
        this.question13 = question13;
        this.question2 = question2;
        this.question3 = question3;
        this.question4 = question4;
        this.question5 = question5;
        this.question6 = question6;
        this.question7 = question7;
        this.question8 = question8;
        this.question9 = question9;
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getQuestion10() {
        return question10;
    }

    public void setQuestion10(String question10) {
        this.question10 = question10;
    }

    public String getQuestion11() {
        return question11;
    }

    public void setQuestion11(String question11) {
        this.question11 = question11;
    }

    public String getQuestion12() {
        return question12;
    }

    public void setQuestion12(String question12) {
        this.question12 = question12;
    }

    public String getQuestion13() {
        return question13;
    }

    public void setQuestion13(String question13) {
        this.question13 = question13;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getQuestion3() {
        return question3;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public String getQuestion4() {
        return question4;
    }

    public void setQuestion4(String question4) {
        this.question4 = question4;
    }

    public String getQuestion5() {
        return question5;
    }

    public void setQuestion5(String question5) {
        this.question5 = question5;
    }

    public String getQuestion6() {
        return question6;
    }

    public void setQuestion6(String question6) {
        this.question6 = question6;
    }

    public String getQuestion7() {
        return question7;
    }

    public void setQuestion7(String question7) {
        this.question7 = question7;
    }

    public String getQuestion8() {
        return question8;
    }

    public void setQuestion8(String question8) {
        this.question8 = question8;
    }

    public String getQuestion9() {
        return question9;
    }

    public void setQuestion9(String question9) {
        this.question9 = question9;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("question1", question1).append("question10", question10).append("question11", question11).append("question12", question12).append("question13", question13).append("question2", question2).append("question3", question3).append("question4", question4).append("question5", question5).append("question6", question6).append("question7", question7).append("question8", question8).append("question9", question9).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(question1).append(question2).append(question3).append(question4).append(question5).append(question7).append(question6).append(question9).append(question8).append(question10).append(question12).append(question11).append(question13).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Car) == false) {
            return false;
        }
        Car rhs = ((Car) other);
        return new EqualsBuilder().append(question1, rhs.question1).append(question2, rhs.question2).append(question3, rhs.question3).append(question4, rhs.question4).append(question5, rhs.question5).append(question7, rhs.question7).append(question6, rhs.question6).append(question9, rhs.question9).append(question8, rhs.question8).append(question10, rhs.question10).append(question12, rhs.question12).append(question11, rhs.question11).append(question13, rhs.question13).isEquals();
    }

}
