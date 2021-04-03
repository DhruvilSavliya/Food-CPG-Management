package com.food.cpg.analytics;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AnalyticsController {

    private static final String ANALYTICS_ROUTE = "analytics/analytics";
    private static final String LOAD_BALANCE_SHEET_ROUTE = "analytics/balance-sheet";

    @GetMapping("/analytics")
    public String showAnalytics() {
        return ANALYTICS_ROUTE;
    }

    @RequestMapping("/load-balance-sheet")
    public String loadBalanceSheet(BalanceSheet balanceSheet) {
        balanceSheet.generateBalanceSheet();

        return LOAD_BALANCE_SHEET_ROUTE;
    }
}