package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DisponibilidadEmpleadoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DisponibilidadEmpleado getDisponibilidadEmpleadoSample1() {
        return new DisponibilidadEmpleado().id(1L).diaSemana("diaSemana1").empleadoId(1L);
    }

    public static DisponibilidadEmpleado getDisponibilidadEmpleadoSample2() {
        return new DisponibilidadEmpleado().id(2L).diaSemana("diaSemana2").empleadoId(2L);
    }

    public static DisponibilidadEmpleado getDisponibilidadEmpleadoRandomSampleGenerator() {
        return new DisponibilidadEmpleado()
            .id(longCount.incrementAndGet())
            .diaSemana(UUID.randomUUID().toString())
            .empleadoId(longCount.incrementAndGet());
    }
}
