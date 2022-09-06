package com.sicredi.projetosicredi.util.scheduler;

import com.sicredi.projetosicredi.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class PautaScheduler {

    @Autowired
    private PautaService service;

    static Logger logger = Logger.getLogger(PautaScheduler.class.getName());

    @Scheduled(cron = "0 * * * * ?")
    public void atualizarPautas() {
        service.encerrarPautas();
        logger.info("Job Encerrar Pautas");

    }
}
