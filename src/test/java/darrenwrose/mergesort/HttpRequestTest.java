package darrenwrose.mergesort;

import darrenwrose.mergesort.model.Execution;
import darrenwrose.mergesort.model.ExecutionStatus;
import org.assertj.core.api.Condition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void should_return_empty_list_of_executions() throws Exception {
        // Given
        String endpoint = "http://localhost:" + port + "/mergesort/executions";

        // When
        List<Execution> actual = restTemplate.getForObject(endpoint, new ArrayList<Execution>().getClass());

        // Then
        assertThat(actual, is(notNullValue()));
    }

    @Test
    public void should_return_pending_execution() throws Exception {
        // Given
        List<Integer> input = Stream.of(1,7,3,7,4,6).collect(Collectors.toList());
        Execution expected = new Execution();
        expected.setId(1);
        expected.setStatus(ExecutionStatus.pending);
        expected.setInput(input);

        // When
        Execution actual = restTemplate.postForObject("http://localhost:" + port + "/mergesort", input, Execution.class);

        // Then
        assertThat(actual, is(expected));
    }

}
