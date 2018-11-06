package com.confirmu.up.model;

public class LoanType {
    private String loanName;
    private String key;
    private int image;

    public LoanType(String loanName, int image, String key) {
        this.loanName = loanName;
        this.image = image;
        this.key = key;
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
