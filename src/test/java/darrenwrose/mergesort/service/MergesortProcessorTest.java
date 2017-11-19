package darrenwrose.mergesort.service;

import darrenwrose.mergesort.model.Execution;
import darrenwrose.mergesort.model.ExecutionStatus;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by darrenrose on 19/11/2017.
 */
public class MergesortProcessorTest {

    @Test
    public void should_sort_input_list() throws Exception {
        // Given
        MergesortProcessor sut = new MergesortProcessor();
        List<Integer> input = Stream.of(1,9,3,8,4,7,5).collect(Collectors.toList());
        List<Integer> expected = Stream.of(1,3,4,5,7,8,9).collect(Collectors.toList());

        // When
        sut.sort(input, 0, input.size() -1);

        // Then
        assertThat(input, is(expected));

    }

    @Test
    public void should_populate_results_map_with_execution_with_sorted_output() throws Exception {
        // Given
        MergesortProcessor sut = new MergesortProcessor();
        List<Integer> input = Stream.of(1, 9, 3, 8, 4, 7, 5).collect(Collectors.toList());
        List<Integer> sorted = Stream.of(1, 3, 4, 5, 7, 8, 9).collect(Collectors.toList());
        Execution execution = new Execution();
        execution.setId(1);
        execution.setInput(input);
        ConcurrentMap<Integer, Execution> results = new ConcurrentHashMap<>();

        // When
        sut.mergesort(execution, results);

        // Then
        assertThat(results.size(), is(1));
        assertThat(results.get(1).getId(), is(1));
        assertThat(results.get(1).getInput(), is(input));
        assertThat(results.get(1).getStatus(), is(ExecutionStatus.completed));
        assertThat(results.get(1).getOutput(), is(sorted));
        assertNotNull(results.get(1).getTimestamp());
        assertTrue(results.get(1).getTimestamp() > 0);
        assertNotNull(results.get(1).getDuration());
        assertTrue(results.get(1).getDuration() > 0);

    }

}