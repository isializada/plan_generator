package de.alizada.loan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.alizada.loan.model.PlanData;
import de.alizada.loan.model.PlanRequestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertTrue;

public class PlanServiceUnitTest {
    private PlanService planService;
    private DecimalFormat df = new DecimalFormat("#.##");

    @BeforeEach
    void setUp(){
        planService = new PlanService();
    }

    @Test
    void shouldCalculateCorrectly(){
        PlanRequestData planRequestData = generateLoanRequestByJson("request");
        assertTrue("can not generate PlanRequestData", null != planRequestData);

        List<PlanData> planDataList = planService.calculateLoanData(planRequestData);

        assertThat(df.format(planDataList.get(0).getRemainingOutstandingPrincipal())).isEqualTo("4801,48");
        assertThat(df.format(planDataList.get(0).getBorrowerPaymentAmount())).isEqualTo("219,36");
        assertThat(df.format(planDataList.get(0).getInitialOutstandingPrincipal())).isEqualTo("5000");
        assertThat(df.format(planDataList.get(0).getInterest())).isEqualTo("20,83");
        assertThat(df.format(planDataList.get(0).getPrincipal())).isEqualTo("198,52");
    }

    private PlanRequestData generateLoanRequestByJson(String jsonName) {
        try {
            return new ObjectMapper().readValue(new String(Files.readAllBytes(Paths.get("src/test/resources/" + jsonName + ".json")),
                    StandardCharsets.UTF_8), PlanRequestData.class);
        }catch (Exception ex){
            return null;
        }
    }

}
