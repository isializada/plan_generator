package de.alizada.loan.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

public class PlanRequestData {
    @NotNull
    @Positive(message = "Amount can not be negative or zero")
    private Double loanAmount;
    @NotNull
    @Positive(message = "Rate can not be negative or zero")
    @Max(value = 100, message = "Rate can not be more than 100")
    private Double nominalRate;
    @NotNull
    @Min(value = 2, message = "Duration should be minimum 2 months")
    private Integer duration;
    @NotNull
    private Date startDate;

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Double getNominalRate() {
        return nominalRate;
    }

    public void setNominalRate(Double nominalRate) {
        this.nominalRate = nominalRate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
