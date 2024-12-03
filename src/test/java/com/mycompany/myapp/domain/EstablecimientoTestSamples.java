package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EstablecimientoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Establecimiento getEstablecimientoSample1() {
        return new Establecimiento()
            .id(1L)
            .nombre("nombre1")
            .direccion("direccion1")
            .telefono("telefono1")
            .correoElectronico("correoElectronico1")
            .urlImg("urlImg1")
            .horaApertura("horaApertura1")
            .horaCierre("horaCierre1")
            .userId(1L);
    }

    public static Establecimiento getEstablecimientoSample2() {
        return new Establecimiento()
            .id(2L)
            .nombre("nombre2")
            .direccion("direccion2")
            .telefono("telefono2")
            .correoElectronico("correoElectronico2")
            .urlImg("urlImg2")
            .horaApertura("horaApertura2")
            .horaCierre("horaCierre2")
            .userId(2L);
    }

    public static Establecimiento getEstablecimientoRandomSampleGenerator() {
        return new Establecimiento()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .direccion(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .correoElectronico(UUID.randomUUID().toString())
            .urlImg(UUID.randomUUID().toString())
            .horaApertura(UUID.randomUUID().toString())
            .horaCierre(UUID.randomUUID().toString())
            .userId(longCount.incrementAndGet());
    }
}
