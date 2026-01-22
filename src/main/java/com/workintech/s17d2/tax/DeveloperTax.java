package com.workintech.s17d2.tax;
import org.springframework.stereotype.Component;

@Component
public class DeveloperTax implements Taxable {

    @Override
    public Double getSimpleTaxRate() {
        return 0.15;
    }

    @Override
    public Double getMiddleTaxRate() {
        return 0.25;
    }

    @Override
    public Double getUpperTaxRate() {
        return 0.35;
    }
//
}

