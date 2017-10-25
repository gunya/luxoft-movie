package org.luxoft.interview.movie.facade;

import java.util.ArrayList;
import java.util.List;

import org.luxoft.interview.movie.MovieService;
import org.luxoft.interview.movie.domain.ApiRequest;
import org.luxoft.interview.movie.domain.ApiResponse;
import org.luxoft.interview.movie.domain.MovieResponse;
import org.luxoft.interview.movie.domain.Task;
import org.luxoft.interview.util.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("movieFacadeService")
public class MovieFacadeServiceImpl implements MovieFacadeService {
	
	@Autowired
	private MovieService movieService;
	
	@Cacheable(value="movieFacadeCache", key="#request")
	@Override
	public List<ApiResponse> commentAndDetails(Task task, ApiRequest request) {
		log.info("Started task with {} movieId", task.getMovieList().size());
	    task.start();
	    
	    Integer movieId = request.getMovieId();
	    
	    if(Data.data.containsKey(movieId)) {
	    	DeferredResult<ResponseEntity<MovieResponse>> result = new DeferredResult<>();
	    	List<Integer> list = new ArrayList<>();
	    	list.add(request.getMovieId());
	        Task commentTask = new Task(result, list);
	        
	    	List<ApiResponse> apiResp = movieService.commentOnMovie(commentTask, request);
	    	if(HttpStatus.OK.value() == apiResp.get(0).getStatus()) {
	    		return movieService.getMovieDescription(request.getMovieList(), task);
	    	} else {
	    		return apiResp;
	    	}
	    }
	    
		return task.getResponses();
	}
}
