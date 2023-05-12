package com.dragosh.branchprediction;

import org.openjdk.jmh.annotations.*;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@BenchmarkMode(Mode.Throughput)
@Fork(1)
@Warmup(iterations = 4, time = 1000, timeUnit =  TimeUnit.MILLISECONDS)
@Measurement(iterations = 50, time = 1000, timeUnit =  TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Timeout(time = 5, timeUnit = TimeUnit.MINUTES)
public class BranchPredictionBenchmark {
    private static int ARRAY_SIZE;
    private static long[] SORTED_ARRAY ;
    private static long[] SHUFFLED_ARRAY;

    @Setup
    public void setup() {
        ARRAY_SIZE = 10_000_000;
        SORTED_ARRAY = generateSortedArray(ARRAY_SIZE);
        SHUFFLED_ARRAY = generateShuffledArray(ARRAY_SIZE);
    }

    @Benchmark
    public void sortedArrayTest() {
        final long cutoff = ARRAY_SIZE / 2;
        long count = 0;
        for (long number : SORTED_ARRAY) {
            if (number < cutoff) {
                ++count;
            }
        }
    }

    @Benchmark
    public void shuffledArrayTest() {
        final long cutoff = ARRAY_SIZE / 2;
        long count = 0;

        for (long number : SHUFFLED_ARRAY) {
            if (number < cutoff) {
                ++count;
            }
        }
    }

    private static long[] generateSortedArray(final int size) {
        return LongStream.range(0, size).toArray();
    }

    private static long[] generateShuffledArray(final int size) {
        final List<Long> numbers = LongStream.range(0, size)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(numbers);
        return numbers
                .stream()
                .mapToLong(Long::valueOf)
                .toArray();
    }
}
