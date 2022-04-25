package com.el.opu.carsup.service.resolver;

import com.el.opu.carsup.utils.CarsupConstants;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldResolver {

    public String resolveBrandName(String title) {
        if (StringUtils.isBlank(title)) {
            return null;
        }
        return title.substring(5);
    }

    public String resolveCarYear(String title) {
        if (StringUtils.isBlank(title)) {
            return null;
        }
        return title.subSequence(0, 4).toString();
    }

    public String resolveField(Elements uls, String field) {
        Elements li = uls.select("li.data-list__item");
        if (CollectionUtils.isEmpty(li)) {
            return null;
        }
        List<Element> elements = Optional.of(li).stream()
                .flatMap(Elements::stream)
                .filter(element -> {
                    if (element.select("span.data-list__label").first() == null || !element.select("span.data-list__label").first().hasText()) {
                        return false;
                    }
                    return element.select("span.data-list__label").first().text().equals(field);
                })
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(elements)) {
            return null;
        }
        return elements.get(0).text();
    }

    public String getLotNumber(String text) {
        if (text.startsWith("Stock #: ")) {
            return text.substring(9);
        }
        return null;
    }


    public String resolveAuctionDateTime(Elements uls) {
        Elements li = uls.select("li.data-list__item");
        if (CollectionUtils.isEmpty(li)) {
            return null;
        }
        Element href = li.select("a.data-list__value.link").first();
        if (href == null) {
            return null;
        }
        return href.text();
    }

    public String resolvePrice(String price) {
        if (StringUtils.isBlank(price)) {
            return null;
        }
        return price.substring(1).replace(",", "");
    }

    public String resolveFuelType(String type) {
        if (StringUtils.startsWith(type, "Fuel Type: ")) {
            return type.substring(11);
        }
        return null;
    }

    public String resolveLocation(String location) {
        if (StringUtils.startsWith(location, "Selling Branch: ")) {
            return location.substring(16);
        }
        return null;
    }

    public String resolveVehicle(String vehicle) {
        if (StringUtils.startsWith(vehicle, "Vehicle: ")) {
            return vehicle.substring(9);
        }
        return null;
    }

    public String resolveSeries(String series) {
        if (StringUtils.startsWith(series, "Series: ")) {
            return series.substring(8);
        }
        return null;
    }

    public String resolveSecondaryDamage(String secondaryDamage) {
        if (StringUtils.startsWith(secondaryDamage, "Secondary Damage: ")) {
            return secondaryDamage.substring(18);
        }
        return null;
    }

    public String resolvePrimaryDamage(String damage) {
        if (StringUtils.startsWith(damage, "Primary Damage: ")) {
            String substring = damage.substring(16);
            StringBuilder result = new StringBuilder();
            String[] strings = substring.split(" ");
            for (int i = 0; i < strings.length / 2; i++) {
                result.append(strings[i]).append(" ");
            }
            return result.toString();
        }
        return null;
    }

    public String resolveModel(String model) {
        if (StringUtils.startsWith(model, "Model: ")) {
            return model.substring(7);
        }
        return null;
    }

    public String resolveEngineDec(String value) {
        StringBuilder deduplication = new StringBuilder();
        String[] strings = value.split(" ");
        for (int i = 0; i < strings.length / 2; i++) {
            deduplication.append(strings[i]).append(" ");
        }
        if (StringUtils.isAlpha(deduplication.toString())) {
            return null;
        }
        String[] s = deduplication.toString().split("\\d{1,}\\W\\d{1}[L]");
        String result = deduplication.toString();
        for (String trim : s) {
            result = result.replace(trim, "");
        }
        return result.replace("L", "");
    }

    public String resolveConditionalValue(String value) {
        if (StringUtils.startsWith(value, "Start Code: ")) {
            String substring = value.substring(12);
            StringBuilder result = new StringBuilder();
            String[] strings = substring.split(" ");
            for (int i = 0; i < strings.length / 2; i++) {
                result.append(strings[i]).append(" ");
            }
            return result.toString();
        }
        return null;
    }

    public String resolveCurrentBid(String bid) {
        if (StringUtils.startsWith(bid, "Current Bid: $")) {
            return bid.substring(14);
        }
        return null;
    }

    public String resolveEngine(String engine) {
        if (StringUtils.startsWith(engine, "Engine: ")) {
            return engine.substring(8);
        }
        return null;
    }

    public String resolveBuyNowPrice(String price) {
        if (StringUtils.isBlank(price)) {
            return null;
        }
        if (StringUtils.startsWith(price, "uy Now Price: $")) {
            return price.substring(15);
        }
        return null;
    }

    public String resolveOdometerValue(String odometer) {
        if (StringUtils.isBlank(odometer)) {
            return null;
        }
        return odometer.replaceAll("[^\\d]", "");
    }

    public String dateToUkrainianDate(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        String yearFormatter = "yyyy";
        String timePattern = "dd.MM.yyyy HH:mm";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(yearFormatter);
        String month = CarsupConstants.MONTHS.get(date.substring(4, 7));
        String day = date.substring(date.indexOf(',') - 2, date.indexOf(','));
        if (day.startsWith(" ")) {
            day = day.replace(" ", "0");
        }
        String hours = date.substring(date.indexOf(',') + 2, date.indexOf(':'));
        String minutes = date.substring(date.indexOf(':') + 1, date.indexOf(':') + 3);
        String am = date.substring(date.indexOf(':') + 3, date.indexOf(':') + 5);
        ZoneId timeZoneD = ZoneId.of(CarsupConstants.CDT_TIMEZONE);
        String year = dateTimeFormatter.format(LocalDateTime.ofInstant(Clock.systemDefaultZone().instant(), ZoneOffset.UTC));
        if (am.equals("pm")) {
            int hoursInt = Integer.parseInt(hours) + 12;
            hours = Integer.toString(hoursInt);
        } else {
            if (hours.length() == 1) {
                hours = "0" + hours;
            }
        }
        dateTimeFormatter = DateTimeFormatter.ofPattern(timePattern);
        String bufferedTime = day + "." + month + "." + year + " " + hours + ":" + minutes;
        long timeEpoch = LocalDateTime.parse(bufferedTime, dateTimeFormatter).atZone(timeZoneD).toInstant().toEpochMilli();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timePattern);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.of(CarsupConstants.UKRAINE_TIMEZONE)));
        return simpleDateFormat.format(new Date(timeEpoch));
    }

    public Long dateToMillisInUtc(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return LocalDateTime.parse(date, dateTimeFormatter).atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
    }
}
