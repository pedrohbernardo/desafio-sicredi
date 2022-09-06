package com.sicredi.projetosicredi.util.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PautaScheduler {
    

    @Scheduled(cron = "1 * * * ?")
    public void atualizarPautas() {

    }
}
