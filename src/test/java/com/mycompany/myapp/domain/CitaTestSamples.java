package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CitaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Cita getCitaSample1() {
        return new Cita()
            .id(1L)
            .duracion(1)
            .personaId(1L)
            .nombrePersona("nombrePersona1")
            .establecimientoId(1L)
            .nombreEstablecimiento("nombreEstablecimiento1")
            .servicioId(1L)
            .empleadoId(1L)
            .nombreEmpleado("nombreEmpleado1");
    }

    public static Cita getCitaSample2() {
        return new Cita()
            .id(2L)
            .duracion(2)
            .personaId(2L)
            .nombrePersona("nombrePersona2")
            .establecimientoId(2L)
            .nombreEstablecimiento("nombreEstablecimiento2")
            .servicioId(2L)
            .empleadoId(2L)
            .nombreEmpleado("nombreEmpleado2");
    }

    public static Cita getCitaRandomSampleGenerator() {
        return new Cita()
            .id(longCount.incrementAndGet())
            .duracion(intCount.incrementAndGet())
            .personaId(longCount.incrementAndGet())
            .nombrePersona(UUID.randomUUID().toString())
            .establecimientoId(longCount.incrementAndGet())
            .nombreEstablecimiento(UUID.randomUUID().toString())
            .servicioId(longCount.incrementAndGet())
            .empleadoId(longCount.incrementAndGet())
            .nombreEmpleado(UUID.randomUUID().toString());
    }
}
