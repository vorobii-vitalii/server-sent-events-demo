package com.example.serversent.executors;

import com.example.serversent.annotations.Executor;
import com.example.serversent.models.Temperature;
import com.example.serversent.util.RandomTemperature;
import org.springframework.context.ApplicationEventPublisher;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Executor
public class TemperatureSensor {
    private final ApplicationEventPublisher publisher;
    private final RandomTemperature randomTemperature;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public TemperatureSensor(ApplicationEventPublisher publisher, RandomTemperature randomTemperature) {
        this.publisher = publisher;
        this.randomTemperature = randomTemperature;
    }

    @PostConstruct
    public void startProcessing() {
        executor.schedule(this::probe, 1, TimeUnit.SECONDS);
    }

    private void probe() {
        final double temperature = randomTemperature.generate();
        publisher.publishEvent(new Temperature(temperature));
        executor.schedule(this::probe, 3, TimeUnit.SECONDS);
    }

}
