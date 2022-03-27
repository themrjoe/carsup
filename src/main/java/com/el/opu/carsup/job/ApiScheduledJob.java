package com.el.opu.carsup.job;

import com.el.opu.carsup.service.ApiScenario;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiScheduledJob {
    private final ApiScenario apiScenario;

    @Scheduled(cron = "${el-opu-carsup.schedule.cron}")
    public void job() {
        apiScenario.getCars();
    }
}
