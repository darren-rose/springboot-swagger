package darrenwrose.mergesort.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by darrenrose on 18/11/2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Execution {
    private int id;
    private Long timestamp;
    private Long duration;
    private ExecutionStatus status;
    private List<Integer> input;
    private List<Integer> output;
}
