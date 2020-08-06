package com.example.serversent.util.impl;

import com.example.serversent.util.RandomTemperature;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomTemperatureImpl implements RandomTemperature {
    private static final Random random = new Random();

    @Override
    public double generate() {
        return 16 + random.nextGaussian() * 10;
    }
}
