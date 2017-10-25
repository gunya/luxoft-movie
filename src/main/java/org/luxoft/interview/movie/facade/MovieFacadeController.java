package org.luxoft.interview.movie.facade;

import java.util.ArrayList;
import java.util.List;

import org.luxoft.interview.movie.MovieService;
import org.luxoft.interview.movie.domain.ApiRequest;
import org.luxoft.interview.movie.domain.ApiResponse;
import org.luxoft.interview.movie.domain.MovieResponse;
import org.luxoft.interview.movie.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(value="/moviefacade")
public class MovieFacadeController {
	
	@Autowired
    private MovieFacadeService movieFacadeService;
	
	@RequestMapping(value = "/details", method = RequestMethod.POST)
    @ResponseBody
    public List<ApiResponse> facade(@RequestBody ApiRequest request) {
    	DeferredResult<ResponseEntity<MovieResponse>> result = new DeferredResult<>();
    	List<Integer> list = new ArrayList<>();
    	request.getMovieList().stream().forEach(e-> list.add(e));
        Task task = new Task(result, list);
        return movieFacadeService.commentAndDetails(task, request);
    }
}
