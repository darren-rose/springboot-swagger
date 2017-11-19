package darrenwrose.mergesort;

import darrenwrose.mergesort.model.Execution;
import darrenwrose.mergesort.model.ExecutionStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Before
    public void setUp(){
        String endpoint = "http://localhost:" + port + "/mergesort/executions";
        restTemplate.delete(endpoint);
    }

    @Test
    public void should_return_pending_execution() throws Exception {
        // Given
        String endpoint = "http://localhost:" + port + "/mergesort";
        List<Integer> input = Stream.of(1,7,3,7,4,6).collect(Collectors.toList());
        Execution expected = new Execution();
        expected.setId(1);
        expected.setStatus(ExecutionStatus.pending);
        expected.setInput(input);

        // When
        ResponseEntity<Execution> responseEntity = restTemplate.postForEntity(endpoint, input, Execution.class);

        // Then
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(responseEntity.getBody(), is(expected));
    }

    @Test
    public void should_return_empty_list_of_executions() throws Exception {
        // Given
        String endpoint = "http://localhost:" + port + "/mergesort/executions";

        // When
        List<Execution> actual = restTemplate.getForObject(endpoint, new ArrayList<Execution>().getClass());

        // Then
        assertThat(actual, is(notNullValue()));
        assertThat(actual.size(), is(0));
    }


    @Test
    public void should_delete_all_executions() throws Exception {
        // Given
        String postEndpoint = "http://localhost:" + port + "/mergesort";
        String getEndpoint = "http://localhost:" + port + "/mergesort/executions";
        List<Integer> input = Stream.of(1,7,3,7,4,6).collect(Collectors.toList());

        // When
        restTemplate.postForObject(postEndpoint, input, Execution.class);
        restTemplate.postForObject(postEndpoint, input, Execution.class);
        restTemplate.postForObject(postEndpoint, input, Execution.class);
        restTemplate.postForObject(postEndpoint, input, Execution.class);
        restTemplate.postForObject(postEndpoint, input, Execution.class);

        // Then
        List<Execution> executions = restTemplate.getForObject(getEndpoint, new ArrayList<Execution>().getClass());
        assertThat(executions.size(), is(5));

        // When
        restTemplate.delete(getEndpoint);
        executions = restTemplate.getForObject(getEndpoint, new ArrayList<Execution>().getClass());

        // then
        assertThat(executions.size(), is(0));
    }

    @Test
    public void should_return_bad_request_when_invalid_id() throws Exception {
        // Given
        String endpoint = "http://localhost:" + port + "/mergesort/executions/666";

        // When
        ResponseEntity<Execution> actual = restTemplate.getForEntity(endpoint, Execution.class);

        // Then
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

}
