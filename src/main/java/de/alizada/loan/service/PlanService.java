package de.alizada.loan.service;

import de.alizada.loan.config.Constants;
import de.alizada.loan.model.PlanRequestData;
import de.alizada.loan.model.PlanData;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

@Service
public class PlanService {

    public LinkedList<PlanData> calculateLoanData(PlanRequestData planRequestData) {
        final LinkedList<PlanData> responseDataList = new LinkedList<>();
        //initialOutstandingPrinciple is total amount for 1st month
        double initialOutstandingPrinciple = planRequestData.getLoanAmount();

        for(int i = 0; i< planRequestData.getDuration(); i++){
            PlanData responsePlanData = generateLoanData(planRequestData, i, initialOutstandingPrinciple);
            responseDataList.add(responsePlanData);
            //update initialOutstandingPrinciple for next month
            initialOutstandingPrinciple = responsePlanData.getRemainingOutstandingPrincipal();
        }

        return responseDataList;
    }

    private PlanData generateLoanData(final PlanRequestData planRequestData, final int currentMonthNumber, final double initialOutstandingPrinciple){
        final PlanData planData = new PlanData();

        final double annuity = calculateAnnuity(initialOutstandingPrinciple, planRequestData.getNominalRate(), planRequestData.getDuration() - currentMonthNumber);
        final double interest = calculateInterest(planRequestData.getNominalRate(), initialOutstandingPrinciple);
        final double principle = calculatePrinciple(annuity, interest <= initialOutstandingPrinciple ? interest : initialOutstandingPrinciple);

        planData.setInitialOutstandingPrincipal(initialOutstandingPrinciple);
        planData.setPrincipal(principle);
        planData.setBorrowerPaymentAmount(calculateBorrowerPaymentAmount(principle, interest));
        planData.setInterest(interest);
        planData.setDate(increaseDateMonth(planRequestData.getStartDate(), currentMonthNumber));
        planData.setRemainingOutstandingPrincipal(calculateRemainingAmount(initialOutstandingPrinciple, principle));

        return planData;
    }

    private double calculateRemainingAmount(double initialOutstandingPrinciple, double principle) {
        return initialOutstandingPrinciple >= principle ? initialOutstandingPrinciple - principle : 0;
    }

    private double calculateBorrowerPaymentAmount(final double principle, final double interest) {
        return principle + interest;
    }

    private double calculateInterest(final double nominalRate, double initialOutstandingPrinciple) {
        return ((nominalRate * Constants.MONTH_DAYS * initialOutstandingPrinciple)/ Constants.YEAR_DAYS)/100d;
    }

    private double calculatePrinciple(final double annuity, final double interest) {
        return annuity - interest;
    }

    private double calculateAnnuity(final double presentValue, final double rate, final int duration){
        return (presentValue*((rate/100d)/12d))/(1d-Math.pow(1d+((rate/100d)/12d),-(duration/12d)*12));
    }

    private Date increaseDateMonth(final Date date, final int reduceMonthCount){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        calendar.add(Calendar.MONTH, reduceMonthCount);

        return calendar.getTime();
    }
}
