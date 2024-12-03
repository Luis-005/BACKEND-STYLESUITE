package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PagoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Pago getPagoSample1() {
        return new Pago().id(1L).estado("estado1").citaId(1L).carritoId(1L).userId(1L);
    }

    public static Pago getPagoSample2() {
        return new Pago().id(2L).estado("estado2").citaId(2L).carritoId(2L).userId(2L);
    }

    public static Pago getPagoRandomSampleGenerator() {
        return new Pago()
            .id(longCount.incrementAndGet())
            .estado(UUID.randomUUID().toString())
            .citaId(longCount.incrementAndGet())
            .carritoId(longCount.incrementAndGet())
            .userId(longCount.incrementAndGet());
    }
}
