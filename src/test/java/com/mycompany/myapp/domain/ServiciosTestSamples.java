package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ServiciosTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Servicios getServiciosSample1() {
        return new Servicios().id(1L).tipoServicio("tipoServicio1").descripcion("descripcion1").establecimientoId(1L);
    }

    public static Servicios getServiciosSample2() {
        return new Servicios().id(2L).tipoServicio("tipoServicio2").descripcion("descripcion2").establecimientoId(2L);
    }

    public static Servicios getServiciosRandomSampleGenerator() {
        return new Servicios()
            .id(longCount.incrementAndGet())
            .tipoServicio(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString())
            .establecimientoId(longCount.incrementAndGet());
    }
}
