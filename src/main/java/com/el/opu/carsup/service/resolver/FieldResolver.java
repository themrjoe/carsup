package com.el.opu.carsup.service.resolver;

import com.el.opu.carsup.utils.CarsupConstants;
import lombok.RequiredArgsConstructor;
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
import java.util.Optional;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
public class FieldResolver {

    public String resolveBrandName(String title) {
        return title.substring(5);
    }

    public String resolveCarYear(String title) {
        return title.subSequence(0, 4).toString();
    }

    public String resolveField(Elements uls, String field) {
        Elements li = uls.select("li.data-list__item");
        return Optional.ofNullable(li).stream()
                .flatMap(Elements::stream)
                .filter(element -> element.select("span.data-list__label").first().text().equals(field))
                .map(element -> element.select("span.data-list__value").first().text())
                .findFirst()
                .orElse(null);
    }

    public String resolveAuctionDateTime(Elements uls) {
        Elements li = uls.select("li.data-list__item");
        Element href = li.select("a.data-list__value.link").first();
        return href.text();
    }

    public String resolvePrice(String price) {
        if (StringUtils.isEmpty(price)) {
            return null;
        }
        return price.substring(1).replace(",", "");
    }

    public String resolveOdometerValue(String odometer) {
        if (StringUtils.isEmpty(odometer)) {
            return null;
        }
        return odometer.replaceAll("[^\\d]", "");
    }

    public String dateToUkrainianDate(String date) {
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
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return LocalDateTime.parse(date, dateTimeFormatter).atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
    }
}
