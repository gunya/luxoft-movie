package org.luxoft.interview.movie;

import java.util.ArrayList;
import java.util.List;

import org.luxoft.interview.movie.domain.ApiRequest;
import org.luxoft.interview.movie.domain.ApiResponse;
import org.luxoft.interview.movie.domain.MovieResponse;
import org.luxoft.interview.movie.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;


@RestController
@RequestMapping(value="/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "/movieDetails/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<ApiResponse> getMovieDetails(@PathVariable("id") List<Integer> ids) {
    	DeferredResult<ResponseEntity<MovieResponse>> result = new DeferredResult<>();
        Task task = new Task(result, ids);
        return movieService.getMovieDescription(ids, task);
    }
    
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    @ResponseBody
    public List<ApiResponse> addComment(@RequestBody ApiRequest request) {
    	DeferredResult<ResponseEntity<MovieResponse>> result = new DeferredResult<>();
    	List<Integer> list = new ArrayList<>();
    	list.add(request.getMovieId());
        Task task = new Task(result, list);
        return movieService.commentOnMovie(task, request);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/movieAllDetails", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse movieDetailsFromAdmin() {
    	ApiResponse response = new ApiResponse();
    	response.setStatus(200);
    	response.setBody("{\"body\": \"Success\"}");
    	
        return response;
    }
    
    
    @RequestMapping(value = "/commentByUser", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ApiResponse commentOnMovieByUser(@RequestBody ApiRequest request) {
    	ApiResponse response = new ApiResponse();
    	response.setStatus(200);
    	response.setBody("{\"body\": \"Comment "+request.getComment()+" added.\"}");
    	
        return response;
    }
    
}
