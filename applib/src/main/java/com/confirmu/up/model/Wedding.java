
package com.confirmu.up.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Wedding {

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
    @SerializedName("question_14")
    @Expose
    private String question14;
    @SerializedName("question_15")
    @Expose
    private String question15;
    @SerializedName("question_16")
    @Expose
    private String question16;
    @SerializedName("question_17")
    @Expose
    private String question17;
    @SerializedName("question_18")
    @Expose
    private String question18;
    @SerializedName("question_19")
    @Expose
    private String question19;
    @SerializedName("question_2")
    @Expose
    private String question2;
    @SerializedName("question_20")
    @Expose
    private String question20;
    @SerializedName("question_21")
    @Expose
    private String question21;
    @SerializedName("question_22")
    @Expose
    private String question22;
    @SerializedName("question_23")
    @Expose
    private String question23;
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
    public Wedding() {
    }

    /**
     * 
     * @param question1
     * @param question2
     * @param question3
     * @param question4
     * @param question5
     * @param question7
     * @param question6
     * @param question9
     * @param question8
     * @param question23
     * @param question10
     * @param question22
     * @param question21
     * @param question12
     * @param question20
     * @param question11
     * @param question14
     * @param question13
     * @param question16
     * @param question15
     * @param question18
     * @param question17
     * @param question19
     */
    public Wedding(String question1, String question10, String question11, String question12, String question13, String question14, String question15, String question16, String question17, String question18, String question19, String question2, String question20, String question21, String question22, String question23, String question3, String question4, String question5, String question6, String question7, String question8, String question9) {
        super();
        this.question1 = question1;
        this.question10 = question10;
        this.question11 = question11;
        this.question12 = question12;
        this.question13 = question13;
        this.question14 = question14;
        this.question15 = question15;
        this.question16 = question16;
        this.question17 = question17;
        this.question18 = question18;
        this.question19 = question19;
        this.question2 = question2;
        this.question20 = question20;
        this.question21 = question21;
        this.question22 = question22;
        this.question23 = question23;
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

    public String getQuestion14() {
        return question14;
    }

    public void setQuestion14(String question14) {
        this.question14 = question14;
    }

    public String getQuestion15() {
        return question15;
    }

    public void setQuestion15(String question15) {
        this.question15 = question15;
    }

    public String getQuestion16() {
        return question16;
    }

    public void setQuestion16(String question16) {
        this.question16 = question16;
    }

    public String getQuestion17() {
        return question17;
    }

    public void setQuestion17(String question17) {
        this.question17 = question17;
    }

    public String getQuestion18() {
        return question18;
    }

    public void setQuestion18(String question18) {
        this.question18 = question18;
    }

    public String getQuestion19() {
        return question19;
    }

    public void setQuestion19(String question19) {
        this.question19 = question19;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getQuestion20() {
        return question20;
    }

    public void setQuestion20(String question20) {
        this.question20 = question20;
    }

    public String getQuestion21() {
        return question21;
    }

    public void setQuestion21(String question21) {
        this.question21 = question21;
    }

    public String getQuestion22() {
        return question22;
    }

    public void setQuestion22(String question22) {
        this.question22 = question22;
    }

    public String getQuestion23() {
        return question23;
    }

    public void setQuestion23(String question23) {
        this.question23 = question23;
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
        return new ToStringBuilder(this).append("question1", question1).append("question10", question10).append("question11", question11).append("question12", question12).append("question13", question13).append("question14", question14).append("question15", question15).append("question16", question16).append("question17", question17).append("question18", question18).append("question19", question19).append("question2", question2).append("question20", question20).append("question21", question21).append("question22", question22).append("question23", question23).append("question3", question3).append("question4", question4).append("question5", question5).append("question6", question6).append("question7", question7).append("question8", question8).append("question9", question9).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(question1).append(question2).append(question3).append(question4).append(question5).append(question7).append(question6).append(question9).append(question8).append(question23).append(question10).append(question22).append(question21).append(question12).append(question20).append(question11).append(question14).append(question13).append(question16).append(question15).append(question18).append(question17).append(question19).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Wedding) == false) {
            return false;
        }
        Wedding rhs = ((Wedding) other);
        return new EqualsBuilder().append(question1, rhs.question1).append(question2, rhs.question2).append(question3, rhs.question3).append(question4, rhs.question4).append(question5, rhs.question5).append(question7, rhs.question7).append(question6, rhs.question6).append(question9, rhs.question9).append(question8, rhs.question8).append(question23, rhs.question23).append(question10, rhs.question10).append(question22, rhs.question22).append(question21, rhs.question21).append(question12, rhs.question12).append(question20, rhs.question20).append(question11, rhs.question11).append(question14, rhs.question14).append(question13, rhs.question13).append(question16, rhs.question16).append(question15, rhs.question15).append(question18, rhs.question18).append(question17, rhs.question17).append(question19, rhs.question19).isEquals();
    }

}
