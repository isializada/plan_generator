# Loan Plan Generator

The Service is for generate plan for requested loan date.

There is a following endpoint for generation.

[POST] localhost:8088/plan/generate

Receive: Loan details

```
{
	"loanAmount": 5000,
	"nominalRate": 5,
	"duration": 24,
	"startDate": "2018-01-01"
}
```

Response: List of plan by months

```
[
    {
        "date": "01.01.2018",
        "borrowerPaymentAmount": 219.35694867034286,
        "principal": 198.52361533700952,
        "interest": 20.833333333333336,
        "initialOutstandingPrincipal": 5000.0,
        "remainingOutstandingPrincipal": 4801.47638466299
    },
    {
        "date": "01.02.2018",
        "borrowerPaymentAmount": 219.35694867034292,
        "principal": 199.35079706758046,
        "interest": 20.00615160276246,
        "initialOutstandingPrincipal": 4801.47638466299,
        "remainingOutstandingPrincipal": 4602.12558759541
    }
    
    .
    .
    .
]
```

###Unit tests are implemented for service and controller