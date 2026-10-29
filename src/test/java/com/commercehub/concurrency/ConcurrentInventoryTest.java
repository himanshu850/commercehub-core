
package com.commercehub.concurrency;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import com.commercehub.model.PhysicalProduct;
import com.commercehub.repository.InMemoryProductRepository;
import com.commercehub.service.InventoryService;
import com.commercehub.exception.InsufficientStockException;

class ConcurrentInventoryTest {

    @Test
    void shouldPreventOverselling()
            throws Exception {

        InMemoryProductRepository repository =
            new InMemoryProductRepository();

        repository.save(new PhysicalProduct(
            101, "Laptop",
            new BigDecimal("900"), 10, 2.0));

        InventoryService inventoryService =
            new InventoryService(repository);

        int customers = 20;

        ExecutorService executor =
            Executors.newFixedThreadPool(customers);

        CountDownLatch ready =
            new CountDownLatch(customers);

        CountDownLatch start =
            new CountDownLatch(1);

        List<Future<Boolean>> futures =
            new ArrayList<>();

        try {
            for (int i = 0; i < customers; i++) {
                Callable<Boolean> task = () -> {
                    ready.countDown();

                    if (!start.await(10, TimeUnit.SECONDS)) {
                        throw new IllegalStateException(
                            "Start signal timed out");
                    }

                    try {
                        inventoryService.purchaseProduct(
                            101, 1);
                        return true;
                    } catch (InsufficientStockException e) {
                        return false;
                    }
                };

                futures.add(executor.submit(task));
            }

            assertTrue(
                ready.await(10, TimeUnit.SECONDS),
                "All tasks should become ready");

            start.countDown();

            int successful = 0;

            for (Future<Boolean> future : futures) {
                if (future.get(10, TimeUnit.SECONDS)) {
                    successful++;
                }
            }

            int remaining = repository.findById(101)
                .orElseThrow()
                .getQuantity();
            final int successfulPurchases = successful;
            assertAll(
            	    () -> assertEquals(10, successfulPurchases),
            	    () -> assertEquals(0, remaining),
            	    () -> assertEquals(
            	        10, successfulPurchases + remaining)
            	);

        } finally {
            start.countDown();
            executor.shutdownNow();
        }
    }
}
