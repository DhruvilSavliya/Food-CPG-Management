package com.food.cpg.analytics;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AnalyticsController {

    private static final String ANALYTICS_ROUTE = "analytics/analytics";
    private static final String LOAD_BALANCE_SHEET_ROUTE = "analytics/balance-sheet";
    private static final String LOAD_INVENTORY_USAGE_ROUTE = "analytics/inventory-usage";
    private static final String VIEW_INVENTORY_USAGE = "inventoryUsageList";
    private static final String LOAD_SALES_PERFORMANCE_ROUTE = "analytics/sales-performance";

    @GetMapping("/analytics")
    public String showAnalytics() {
        return ANALYTICS_ROUTE;
    }

    @RequestMapping("/load-balance-sheet")
    public String loadBalanceSheet(BalanceSheet balanceSheet) {
        balanceSheet.generateBalanceSheet();

        return LOAD_BALANCE_SHEET_ROUTE;
    }

    @RequestMapping("/load-inventory-usage")
    public String loadInventoryUsage(InventoryUsage inventoryUsage, Model model) {
        inventoryUsage.generateInventoryUsage();

        return LOAD_INVENTORY_USAGE_ROUTE;
    }

    @RequestMapping("/load-sales-performance")
    public String loadSalesPerformance(SalesPerformance salesPerformance) {
        salesPerformance.generateSalesPerformance();

        return LOAD_SALES_PERFORMANCE_ROUTE;
    }
}