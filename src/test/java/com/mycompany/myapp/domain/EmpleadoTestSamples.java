package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EmpleadoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Empleado getEmpleadoSample1() {
        return new Empleado().id(1L).nombre("nombre1").apellido("apellido1").cargo("cargo1").urlmg("urlmg1").establecimientoId(1L);
    }

    public static Empleado getEmpleadoSample2() {
        return new Empleado().id(2L).nombre("nombre2").apellido("apellido2").cargo("cargo2").urlmg("urlmg2").establecimientoId(2L);
    }

    public static Empleado getEmpleadoRandomSampleGenerator() {
        return new Empleado()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .apellido(UUID.randomUUID().toString())
            .cargo(UUID.randomUUID().toString())
            .urlmg(UUID.randomUUID().toString())
            .establecimientoId(longCount.incrementAndGet());
    }
}
