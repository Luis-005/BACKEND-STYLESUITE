package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CategoriaProductoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CategoriaProducto getCategoriaProductoSample1() {
        return new CategoriaProducto().id(1L).nombre("nombre1").establecimientoId(1L);
    }

    public static CategoriaProducto getCategoriaProductoSample2() {
        return new CategoriaProducto().id(2L).nombre("nombre2").establecimientoId(2L);
    }

    public static CategoriaProducto getCategoriaProductoRandomSampleGenerator() {
        return new CategoriaProducto()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .establecimientoId(longCount.incrementAndGet());
    }
}
