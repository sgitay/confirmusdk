package com.confirmu.up.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CountryCode {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("countries_name")
    @Expose
    private String countriesName;
    @SerializedName("countries_iso_code")
    @Expose
    private String countriesIsoCode;
    @SerializedName("countries_isd_code")
    @Expose
    private String countriesIsdCode;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CountryCode() {
    }

    /**
     * 
     * @param id
     * @param countriesIsoCode
     * @param countriesIsdCode
     * @param countriesName
     */
    public CountryCode(String id, String countriesName, String countriesIsoCode, String countriesIsdCode) {
        super();
        this.id = id;
        this.countriesName = countriesName;
        this.countriesIsoCode = countriesIsoCode;
        this.countriesIsdCode = countriesIsdCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountriesName() {
        return countriesName;
    }

    public void setCountriesName(String countriesName) {
        this.countriesName = countriesName;
    }

    public String getCountriesIsoCode() {
        return countriesIsoCode;
    }

    public void setCountriesIsoCode(String countriesIsoCode) {
        this.countriesIsoCode = countriesIsoCode;
    }

    public String getCountriesIsdCode() {
        return countriesIsdCode;
    }

    public void setCountriesIsdCode(String countriesIsdCode) {
        this.countriesIsdCode = countriesIsdCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("countriesName", countriesName).append("countriesIsoCode", countriesIsoCode).append("countriesIsdCode", countriesIsdCode).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(countriesIsoCode).append(countriesIsdCode).append(countriesName).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CountryCode) == false) {
            return false;
        }
        CountryCode rhs = ((CountryCode) other);
        return new EqualsBuilder().append(id, rhs.id).append(countriesIsoCode, rhs.countriesIsoCode).append(countriesIsdCode, rhs.countriesIsdCode).append(countriesName, rhs.countriesName).isEquals();
    }

}
