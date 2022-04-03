package com.el.opu.carsup.job;

import com.el.opu.carsup.service.ApiScenario;
import com.el.opu.carsup.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "el.opu.carsup.schedule", value = "active", havingValue = "true")
public class ApiScheduledJob {

    private final ApiScenario apiScenario;
    private final CarService carService;

    @Scheduled(cron = "${el-opu-carsup.schedule.get-active-lot-links}")
    public void getLotLinks() {
        log.info("Starting collecting page links");
        apiScenario.getCars();
        log.info("All page links collected");
    }

    @Scheduled(cron = "${el-opu-carsup.schedule.parse-cars}")
    public void getCarInfo() {
        log.info("Starting collecting car info");
        apiScenario.getAllCarsInfo();
        log.info("All cars was processed");
    }

    @Scheduled(cron = "${el-opu-carsup.schedule.clean-up-cron}")
    public void cleanUpJob() {
        log.info("Starting cleaning");
        long deletedItems = carService.cleanDb();
        log.info("Deleted {} rows", deletedItems);
    }
}
