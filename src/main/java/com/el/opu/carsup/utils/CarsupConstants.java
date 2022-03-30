package com.el.opu.carsup.utils;

import lombok.NoArgsConstructor;

import java.rmi.MarshalledObject;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

@NoArgsConstructor
public final class CarsupConstants {

    public static final String ODOMETER = "Odometer:";
    public static final String STOCK = "Stock #:";
    public static final String ENGINE = "Engine:";
    public static final String CONDITION = "Start Code:";
    public static final String MODEL = "Model:";
    public static final String SECONDARY_DAMAGE = "Secondary Damage:";
    public static final String PRIMARY_DAMAGE = "Primary Damage:";
    public static final String VEHICLE_TYPE = "Vehicle:";
    public static final String SERIES = "Series:";
    public static final String FUEL_TYPE = "Fuel Type:";
    public static final String BUY_NOW_PRICE = "Buy Now Price:";
    public static final String CURRENT_BID = "Current Bid:";
    public static final String SELLING_BRANCH = "Selling Branch:";

    public static final String CDT_TIMEZONE = "America/Chicago";
    public static final String UKRAINE_TIMEZONE = "Europe/Kiev";

    public static final Map<String, String> UNRESOLVED_TIMEZONE = Map.ofEntries(
        entry("CDT","America/Chicago")
    );

    public static final Map<String, String> MONTHS = Map.ofEntries(
            entry("Jan", "01"),
            entry("Feb", "02"),
            entry("Mar", "03"),
            entry("Apr", "04"),
            entry("May", "05"),
            entry("Jun", "06"),
            entry("Jul", "07"),
            entry("Aug", "08"),
            entry("Sep", "09"),
            entry("Oct", "10"),
            entry("Nov", "11"),
            entry("Dec", "12")
    );
}
