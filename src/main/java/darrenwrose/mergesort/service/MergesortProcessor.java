package darrenwrose.mergesort.service;

import darrenwrose.mergesort.model.Execution;
import darrenwrose.mergesort.model.ExecutionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by darrenrose on 18/11/2017.
 */

@Component
public class MergesortProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(MergesortProcessor.class);

    @Async
    public void mergesort(Execution input, ConcurrentMap<Integer, Execution> results){
        LOGGER.info("sorting: {}", input.getInput());
        List<Integer> output = new ArrayList<>(input.getInput());
        long timestamp = System.currentTimeMillis();
        long start = System.nanoTime();
        sort(output, 0, output.size()-1);
        long duration = System.nanoTime() - start;
        LOGGER.debug("sorted: {} to {} in {}", input.getInput(), output, duration);

        Execution result = new Execution(input.getId(), timestamp, duration, ExecutionStatus.completed, input.getInput(), output);
        LOGGER.debug("output: {}", result);
        results.put(result.getId(), result);
    }

    // source: http://www.geeksforgeeks.org/merge-sort/
    public void sort(List<Integer> input, int l, int r) {
        if (l < r) {
            int middle = (l + r) / 2;
            sort(input, l, middle);
            sort(input, middle + 1, r);
            merge(input, l, middle, r);
        }
    }

    private void merge(List<Integer> input, int l, int m, int r){
        int n1 = m - l + 1;
        int n2 = r - m;

        int L[] = new int [n1];
        int R[] = new int [n2];

        for (int i=0; i<n1; ++i) {
            L[i] = input.get(l + i);
        }
        for (int j=0; j<n2; ++j) {
            R[j] = input.get(m + 1 + j);
        }

        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                input.set(k, L[i]);
                i++;
            } else {
                input.set(k, R[j]);
                j++;
            }
            k++;
        }

        while (i < n1) {
            input.set(k, L[i]);
            i++;
            k++;
        }

        while (j < n2) {
            input.set(k, R[j]);
            j++;
            k++;
        }

    }

}
