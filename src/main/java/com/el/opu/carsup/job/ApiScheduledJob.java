package com.el.opu.carsup.job;

import com.el.opu.carsup.service.ApiScenario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiScheduledJob {

    private final ApiScenario apiScenario;

    @Scheduled(cron = "${el-opu-carsup.schedule.cron}")
    public void job() {
        log.info("Starting collecting data");
        apiScenario.getCars();
        log.info("All data collected");
    }
}
