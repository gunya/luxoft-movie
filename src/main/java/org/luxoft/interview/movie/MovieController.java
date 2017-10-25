package org.luxoft.interview.movie;

import java.util.ArrayList;
import java.util.List;

import org.luxoft.interview.movie.domain.ApiRequest;
import org.luxoft.interview.movie.domain.ApiResponse;
import org.luxoft.interview.movie.domain.MovieResponse;
import org.luxoft.interview.movie.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(value="/movie")
@Slf4j
public class MovieController {
    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "/movieDetails/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<ApiResponse> getMovieDetails(@PathVariable("id") List<Integer> ids) {
    	log.info("getMovieDetails started with {}", ids);
    	DeferredResult<ResponseEntity<MovieResponse>> result = new DeferredResult<>();
        Task task = new Task(result, ids);
        log.info("getMovieDetails completed");
        return movieService.getMovieDescription(ids, task);
    }
    
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    @ResponseBody
    public List<ApiResponse> addComment(@RequestBody ApiRequest request) {
    	log.info("addComment started with {}", request);
    	DeferredResult<ResponseEntity<MovieResponse>> result = new DeferredResult<>();
    	List<Integer> list = new ArrayList<>();
    	list.add(request.getMovieId());
        Task task = new Task(result, list);
        log.info("addComment ended");
        return movieService.commentOnMovie(task, request);
    }
    
    @PreAuthorize("hasRole('ADMIN')")    
    @RequestMapping(value = "/movieAllDetails", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse movieDetailsFromAdmin() {
    	log.info("movieDetailsFromAdmin started");
    	ApiResponse response = new ApiResponse();
    	response.setStatus(HttpStatus.OK.value());
    	response.setBody("{\"body\": \"Success\"}");
    	log.info("movieDetailsFromAdmin ended");
        return response;
    }
    
    
    @RequestMapping(value = "/commentByUser", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ApiResponse commentOnMovieByUser(@RequestBody ApiRequest request) {
    	log.info("commentOnMovieByUser started");
    	ApiResponse response = new ApiResponse();
    	response.setStatus(HttpStatus.OK.value());
    	response.setBody("{\"body\": \"Comment "+request.getComment()+" added.\"}");
    	log.info("commentOnMovieByUser ended");
        return response;
    }
    
}
