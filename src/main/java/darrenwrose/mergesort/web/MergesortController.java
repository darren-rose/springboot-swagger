package darrenwrose.mergesort.web;

import darrenwrose.mergesort.model.Execution;
import darrenwrose.mergesort.service.MergesortService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by darrenrose on 18/11/2017.
 */
@RestController()
public class MergesortController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MergesortController.class);

    private final MergesortService mergesortService;

    public MergesortController(MergesortService mergesortService) {
        this.mergesortService = mergesortService;
    }

    @PostMapping(value = {"/mergesort", "/mergesort/executions"})
    public Execution mergesort(@RequestBody List<Integer> input){
        LOGGER.info("mergesort: {}", input);
        return mergesortService.mergesort(input);
    }

    @GetMapping("/mergesort/executions")
    public List<Execution> executions(){
        LOGGER.info("executions");
        return mergesortService.executions();
    }

    @GetMapping("/mergesort/executions/{id}")
    public Execution execution(@PathVariable("id") final int id){
        LOGGER.info("execution: {}", id);
        return mergesortService.execution(id);
    }

    @DeleteMapping("/mergesort/executions")
    public void deleteAll(){
        LOGGER.info("delete all");
        mergesortService.deleteAll();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public void illegalArgumentException(IllegalArgumentException e){
        LOGGER.warn(e.toString());
    }

}
