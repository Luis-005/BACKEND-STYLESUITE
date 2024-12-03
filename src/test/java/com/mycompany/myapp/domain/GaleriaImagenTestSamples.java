package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GaleriaImagenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static GaleriaImagen getGaleriaImagenSample1() {
        return new GaleriaImagen().id(1L).nombre("nombre1").descripcion("descripcion1").urlImagen("urlImagen1").establecimientoId(1L);
    }

    public static GaleriaImagen getGaleriaImagenSample2() {
        return new GaleriaImagen().id(2L).nombre("nombre2").descripcion("descripcion2").urlImagen("urlImagen2").establecimientoId(2L);
    }

    public static GaleriaImagen getGaleriaImagenRandomSampleGenerator() {
        return new GaleriaImagen()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString())
            .urlImagen(UUID.randomUUID().toString())
            .establecimientoId(longCount.incrementAndGet());
    }
}
