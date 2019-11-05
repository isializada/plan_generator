package de.alizada.loan.controller;

import de.alizada.loan.model.PlanData;
import de.alizada.loan.model.PlanRequestData;
import de.alizada.loan.service.PlanService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.LinkedList;

@RestController
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PostMapping("/generate")
    public LinkedList<PlanData> generator(@RequestBody @Valid PlanRequestData planRequestData) {
        return planService.calculateLoanData(planRequestData);
    }
}
