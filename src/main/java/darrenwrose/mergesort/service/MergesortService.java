package darrenwrose.mergesort.service;

import darrenwrose.mergesort.model.Execution;

import java.util.List;

/**
 * Created by darrenrose on 18/11/2017.
 */
public interface MergesortService {
    Execution mergesort(List<Integer> input);
    List<Execution> executions();

    Execution execution(int id);

    void deleteAll();
}
