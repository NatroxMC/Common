/*
 * Copyright 2020-2022 NatroxMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.natrox.common.counter;

import org.junit.jupiter.api.Test;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class CounterTest {

    @Test
    void isRunningTest() {
        Counter counter = Counter.builder()
            .tick(25, ChronoUnit.MILLIS)
            .startCount(1)
            .stopCount(5)
            .build();
        assertFalse(counter.isRunning());
        counter.start();
        assertTrue(counter.isRunning());
        counter.pause();
        assertFalse(counter.isRunning());
        counter.resume();
        assertTrue(counter.isRunning());
        counter.stop();
        assertFalse(counter.isRunning());
    }

    @Test
    void isPausedTest() {
        Counter counter = Counter.builder()
            .tick(25, ChronoUnit.MILLIS)
            .startCount(1)
            .stopCount(5)
            .build();
        assertFalse(counter.isPaused());
        counter.start();
        assertFalse(counter.isPaused());
        counter.pause();
        assertTrue(counter.isPaused());
        counter.resume();
        assertFalse(counter.isPaused());
        counter.stop();
        assertFalse(counter.isPaused());
    }

    @Test
    void stateTest() {
        Counter counter = Counter.builder()
            .tick(25, ChronoUnit.MILLIS)
            .startCount(1)
            .stopCount(5)
            .build();
        assertEquals(CounterStatus.IDLING, counter.status());
        counter.start();
        assertEquals(CounterStatus.RUNNING, counter.status());
        counter.pause();
        assertEquals(CounterStatus.PAUSED, counter.status());
        counter.resume();
        assertEquals(CounterStatus.RUNNING, counter.status());
        counter.stop();
        assertEquals(CounterStatus.IDLING, counter.status());
    }

    @Test
    void countTest() {
        Counter slowCounter = Counter.builder()
            .tick(1, ChronoUnit.HOURS)
            .startCount(2)
            .stopCount(6)
            .build();
        slowCounter.start();
        assertEquals(2, slowCounter.currentCount());
        assertEquals(0, slowCounter.tickedCount());
        slowCounter.currentCount(4);
        assertEquals(4, slowCounter.currentCount());
        assertEquals(2, slowCounter.tickedCount());
    }

    @Test
    void presetVariablesTest() {
        Counter counter = Counter.builder()
            .tick(1, ChronoUnit.SECONDS)
            .startCount(2)
            .stopCount(-3)
            .build();
        assertEquals(1, counter.tickValue());
        assertEquals(TimeUnit.SECONDS, counter.tickUnit());
        assertEquals(2, counter.startCount());
        assertEquals(-3, counter.stopCount());
    }

    @Test
    void handlerTest() throws InterruptedException {
        AtomicInteger timesStarted = new AtomicInteger();
        AtomicInteger timesTicked = new AtomicInteger();
        AtomicInteger timesFinished = new AtomicInteger();
        AtomicInteger timesCanceled = new AtomicInteger();
        Counter counter = Counter.builder()
            .tick(50, ChronoUnit.MILLIS)
            .startCount(1)
            .stopCount(10)
            .startHandler(c -> timesStarted.incrementAndGet())
            .tickHandler(c -> timesTicked.incrementAndGet())
            .finishHandler(c -> timesFinished.incrementAndGet())
            .cancelHandler(c -> timesCanceled.incrementAndGet())
            .build();

        assertEquals(0, timesStarted.get());
        assertEquals(0, timesTicked.get());
        assertEquals(0, timesFinished.get());
        assertEquals(0, timesCanceled.get());

        counter.start();
        assertEquals(1, timesStarted.get());
        Thread.sleep(525);
        assertEquals(11, timesTicked.get());
        assertEquals(1, timesFinished.get());
        assertEquals(0, timesCanceled.get());

        counter.stop();
        assertEquals(0, timesCanceled.get());

        counter.start();
        Thread.sleep(50);
        assertEquals(1, timesFinished.get());
        counter.stop();
        assertEquals(1, timesCanceled.get());
    }

    @Test
    void tickTest() throws InterruptedException {
        AtomicInteger upTicked = new AtomicInteger();
        AtomicInteger downTicked = new AtomicInteger();
        Counter upTicker = Counter.builder()
            .tick(10, ChronoUnit.MILLIS)
            .startCount(50)
            .stopCount(1)
            .tickHandler(counter -> upTicked.incrementAndGet())
            .build();
        Counter downTicker = Counter.builder()
            .tick(10, ChronoUnit.MILLIS)
            .startCount(1)
            .stopCount(50)
            .tickHandler(counter -> downTicked.incrementAndGet())
            .build();
        upTicker.start();
        downTicker.start();
        Thread.sleep(550);
        assertEquals(51, upTicked.get());
        assertEquals(51, downTicked.get());
    }
}