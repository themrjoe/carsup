package com.el.opu.carsup.job;

import com.el.opu.carsup.service.ApiScenario;
import com.el.opu.carsup.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiScheduledJob {

    private final ApiScenario apiScenario;
    private final CarService carService;

    @Scheduled(cron = "${el-opu-carsup.schedule.cron}")
    public void job() {
        log.info("Starting collecting data");
        apiScenario.getCars();
        log.info("All data collected");
    }

    @Scheduled(cron = "${el-opu-carsup.schedule.clean-up-cron}")
    public void cleanUpJob() {
        log.info("Starting cleaning");
        long deletedItems = carService.cleanDb();
        log.info("Deleted {} rows", deletedItems);
    }
}
