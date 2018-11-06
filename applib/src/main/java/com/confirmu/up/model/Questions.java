
package com.confirmu.up.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Questions {

    @SerializedName("car")
    @Expose
    private Car car;
    @SerializedName("wedding")
    @Expose
    private Wedding wedding;
    @SerializedName("wheelers")
    @Expose
    private Wheelers wheelers;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Questions() {
    }

    /**
     * 
     * @param car
     * @param wheelers
     * @param wedding
     */
    public Questions(Car car, Wedding wedding, Wheelers wheelers) {
        super();
        this.car = car;
        this.wedding = wedding;
        this.wheelers = wheelers;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Wedding getWedding() {
        return wedding;
    }

    public void setWedding(Wedding wedding) {
        this.wedding = wedding;
    }

    public Wheelers getWheelers() {
        return wheelers;
    }

    public void setWheelers(Wheelers wheelers) {
        this.wheelers = wheelers;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("car", car).append("wedding", wedding).append("wheelers", wheelers).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(car).append(wheelers).append(wedding).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Questions) == false) {
            return false;
        }
        Questions rhs = ((Questions) other);
        return new EqualsBuilder().append(car, rhs.car).append(wheelers, rhs.wheelers).append(wedding, rhs.wedding).isEquals();
    }

}
