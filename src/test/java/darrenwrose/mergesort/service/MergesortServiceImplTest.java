package darrenwrose.mergesort.service;

import darrenwrose.mergesort.model.Execution;
import darrenwrose.mergesort.model.ExecutionStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by darrenrose on 19/11/2017.
 */
public class MergesortServiceImplTest {

    private MergesortProcessor mergesortProcessorSpy;
    private MergesortServiceImpl sut;

    @Before
    public void setup(){
        mergesortProcessorSpy = Mockito.spy(MergesortProcessor.class);
        doNothing().when(mergesortProcessorSpy).mergesort(any(), any());
        sut = new MergesortServiceImpl(mergesortProcessorSpy);
    }

    @Test
    public void should_return_pending_execution() throws Exception {
        // Given
        List<Integer> expected = Stream.of(1, 8, 2, 7, 3, 6, 4).collect(Collectors.toList());

        // When
        Execution actual = sut.mergesort(expected);

        // Then
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(1));
        assertThat(actual.getInput(), is(expected));
        assertThat(actual.getOutput(), is(nullValue()));
        assertThat(actual.getTimestamp(), is(nullValue()));
        assertThat(actual.getDuration(), is(nullValue()));
        assertThat(actual.getStatus(), is(ExecutionStatus.pending));
        verify(mergesortProcessorSpy, times(1)).mergesort(any(), any());
    }

    @Test
    public void should_increment_execution_id() throws Exception {
        // Given
        List<Integer> expected = Stream.of(1, 8, 2, 7, 3, 6, 4).collect(Collectors.toList());

        // When
        Execution first = sut.mergesort(expected);
        Execution second = sut.mergesort(expected);
        Execution third = sut.mergesort(expected);

        // Then
        assertThat(first, is(notNullValue()));
        assertThat(second, is(notNullValue()));
        assertThat(third, is(notNullValue()));
        assertThat(first.getId(), is(1));
        assertThat(second.getId(), is(2));
        assertThat(third.getId(), is(3));
        verify(mergesortProcessorSpy, times(3)).mergesort(any(), any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_when_invalid_id() throws Exception {
        // When
        sut.execution(99);
        // Then
    }

}