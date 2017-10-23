package org.luxoft.interview.movie.domain;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Task implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final AtomicInteger counter;
	
	private final transient DeferredResult<ResponseEntity<MovieResponse>> result;
    private final List<Integer> movieList;
    private final List<ApiResponse> responses;
    private long startTime;
    
    public Task(final DeferredResult<ResponseEntity<MovieResponse>> result, final List<Integer> movieList) {
        this.counter = new AtomicInteger(movieList.size());
        this.movieList = movieList;
        this.result = result;
        this.responses = movieList.stream().map(s -> new ApiResponse()).collect(toList());
    }

    public List<Integer> getMovieList() {
        return unmodifiableList(movieList);
    }
    
    public List<ApiResponse> getResponses() {
    	return unmodifiableList(responses);
    }

    public void fail(int index, long time, String message) {
        responses.get(index).setStatus(502);
        responses.get(index).setBody("Failed: " + message);
        responses.get(index).setDuration((int)(System.currentTimeMillis() - time));

        checkDone();
    }

    public void success(int index, long time, int status, String body) {
        responses.get(index).setStatus(status);
        responses.get(index).setBody(body);
        responses.get(index).setDuration((int)(System.currentTimeMillis() - time));

        checkDone();
    }

    public void start() {
        startTime = System.currentTimeMillis();
    }

    private void checkDone() {
        synchronized (counter) {
            if (counter.decrementAndGet() == 0) {
                MovieResponse response = new MovieResponse(responses, (int)(System.currentTimeMillis() - startTime));
                result.setResult(ResponseEntity.ok(response));
                log.info("Finished task in {} ms", response.getDuration());
            }
        }
    }
}
