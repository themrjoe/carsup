package com.el.opu.carsup.utils;

import lombok.NoArgsConstructor;

import java.util.List;
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

    public static List<String> PAGE_LINKS = List.of(
            "%2fphPQHAwgKKUMQESmxpuQ98DIa4rE6zo0cNZ06cVu%2fA%3d",
            "651chJ4y%2fLKn5SPC4lptDUi9OdwdOEIJAoGoYH4nceo%3d",
            "IveNBayEVHSb7O%2fC9ybcg9tk9katUF%2f5jGzW%2fgu692c%3d",
            "epkoUFOO8gymtrRiJ2yLtMK%2bSj9n%2fBt7XgajcZxw69Q%3d",
            "MZr5cYm6KOO9RSN3k7Mi1CgCjvHuJTvtNQbuKqgUIcg%3d",
            "czKwWSH%2fwoGNitmOB%2fRCEhFIARtk4uiAbIG7K5VTmOg%3d",
            "vVk0ohpQ8r6k8L2uBDN54jSXKsT6Zz1PWWiTPh0s8Wo%3d",
            "g1G3JIxGCWZqIhC8nZlI64p9VMPEYBfbYqATr1ZACb8%3d",
            "CU77Ij0qxeu4Wsi%2brZ5JBtNqP8TMtoQTnQxdBXMUeCg%3d",
            "fUEkP7qQOhkjNafl3D0xGoGaLzBsGgfqOZzt0yKQ4vs%3d",
            "cDuQZKBnxntVjPbfwY2K03wAstAD%2bBg8DoqcNuSPXGc%3d",
            "h49iyiOdzwWEk17PcYHMSRTI%2bFP6Rj3pQamoYkvnFLQ%3d",
            "HOhquqMpGrtt20oLdeSRbSj5CGa6B0XgMno6oIbE8Kk%3d",
            "FKb0G95em4HWsc7%2fZYcoOEDWeO9G%2b40bYfMp6tGxK5c%3d",
            "FcAEJnvQzlfetc4S3fFQgRb%2bPplp5cmNKm7uXQ0MVyg%3d",
            "hKQ%2bvk6nc0%2bCrF2a9Fo7on8W2OTrbUl%2feQzrprP01DE%3d",
            "2tA%2fBUo1B9%2fbZKfnSgE9y5X5mJ%2b3ojnWzhxCOEfb1NQ%3d",
            "QuADUusYR2SAsKulArWWuz1hLa%2fQr8hPumbHOhFzOLs%3d",
            "H9T%2bzuD71yCidqQzhLLOsuOo7JjLGBBV%2bIvhG5LGv9k%3d",
            "fjMw6EDfKp1%2f7TKQNF3SpNZcFuRKFvsuzBmeA10a4TI%3d",
            "DEFp0s8uEpyeuM3%2bhPshuzbvguL%2fDKoSQpYWXUQdJnA%3d",
            "UgeOL3mZsRw4nAEOg3GLPx7YpucI8XY92phDuRYJE4M%3d",
            "Egqri0%2fWfX0kvfWitK8zwSq67NJ1AYLraF0CQA%2fnVRg%3d",
            "wnYkC1N63n2sU8a54nOSVW%2fxqp25eMAvYJoSxzSXJQU%3d",
            "SisV5dDGLBU1aycMwM4D2rVBYHE%2b4484cHK8Od3aj4c%3d",
            "Nx5X%2fkd9LB3xL0qDPMZ3NKKh8ZHbq2yEisO%2f1ZJ9d3M%3d",
            "jxe7gVbjsATl0I3bsAAgjUv4h4FlZzSQP51KAtHbNo8%3d",
            "oj2H9NXN0n6P6BBdtZLF%2bFYvPOqPlf8mkK%2bgl8tw%2bic%3d",
            "%2bUR66pmM8PQqdto1%2bffWzKAYE3Q4XCx97K5WhE7Kn7Y%3d",
            "QkkfnsCMd2L3M5xe7OoAtIZoiU7Z7%2fdmEixulxoBCpM%3d",
            "ZDA7kgwmJDQOXC3lW0sXVD0XWmIVQJv7BprlSivHqCY%3d",
            "xxURbgN2Ay3M5juosp9cukL0j25difVP5YW1sHEVYsU%3d",
            "wEYXTD%2b8kPQoNSfXkBzfLSb3BQezcGOi%2bJPKIV63YhM%3d",
            "PVmu9vc0n%2bVNECv0ZjBKrxDB0%2f757e3CJBUsAebypi4%3d",
            "9c30IjPila8p7cNIQvdisZUzglXAaI%2bz4H3MQhP2koQ%3d",
            "cYAroGMuHplunHE7aBRXsYNfkDDQovp6NdcbPELI4w8%3d",
            "zL994VUgw9tu%2b72phjV40y1jEufUYBJa2z2IUwf895w%3d",
            "oWXtTsleURWr2Ryiuxg9LASpUIPrcpZ7ZIIwWMl92C0%3d",
            "VrzWOJHFJcEhdeB5YGsr2TWcEDlG0l1Yw7%2bUOCryLX0%3d",
            "XGor5wPQbF912zLI2rYU3xkoLRbJvNQzgkoxAfWaoy4%3d",
            "ZFAOn06lqKnaLUVZp26sGfw2ox%2f%2fL4oTfBy5p3J%2fOzA%3d",
            "ryf1336DMnQ6jsOz3fR1YhNoM%2frWoxF4VN2KRzReHzs%3d",
            "amnDKueFpYBX2tf4VNPH1FFK79LapxEb79dgVWiNZwE%3d",
            "qCeIp1WR3Ry5mxghjsKY69h%2fDijSGIlKkLMFCMLULHc%3d",
            "EfukYA6LD37u%2bgZ5KEjckBIm%2bcsqrQJ5cSQsz%2fniaAU%3d",
            "ccnBe5V1MEnHDj0hd0%2bb6yV1xu6P%2bC921k1B8V5qB5I%3d"
    );
}
