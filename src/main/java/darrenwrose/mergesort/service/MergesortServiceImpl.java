package darrenwrose.mergesort.service;

import darrenwrose.mergesort.model.Execution;
import darrenwrose.mergesort.model.ExecutionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * Created by darrenrose on 18/11/2017.
 */

@Service
public class MergesortServiceImpl implements MergesortService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MergesortServiceImpl.class);

    private final MergesortProcessor mergesortProcessor;
    private int nextId = 0;

    private ConcurrentMap<Integer, Execution> results = new ConcurrentHashMap();

    public MergesortServiceImpl(MergesortProcessor mergesortProcessor) {
        this.mergesortProcessor = mergesortProcessor;
    }

    @Override
    public Execution mergesort(List<Integer> input) {
        nextId = nextId + 1;
        Execution execution = new Execution();
        execution.setId(nextId);
        execution.setInput(input);
        execution.setStatus(ExecutionStatus.pending);
        mergesortProcessor.mergesort(execution, results);
        return execution;
    }

    @Override
    public List<Execution> executions() {
        return results.entrySet().stream().map(x -> x.getValue()).collect(Collectors.toList());
    }

    @Override
    public Execution execution(int id) {
        if (results.containsKey(id)) return results.get(id);
        LOGGER.warn("Invalid id: {}", id);
        throw new IllegalArgumentException("invalid id: " + id);
    }

}
