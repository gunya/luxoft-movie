package org.luxoft.interview.movie;

import java.util.List;

import org.luxoft.interview.movie.domain.ApiRequest;
import org.luxoft.interview.movie.domain.ApiResponse;
import org.luxoft.interview.movie.domain.Task;
import org.luxoft.interview.util.Data;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("movieService")
public class MovieServiceImpl implements MovieService {

	@Cacheable(value="movieFindCache", key="#movieList")
	@Override
	public List<ApiResponse> getMovieDescription(List<Integer> movieList, Task task) {
		
		log.info("Started task with {} movieId", task.getMovieList().size());
        task.start();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        
        for(int i = 0; i < movieList.size(); i++) {
            final int index = i;
            final long time = System.currentTimeMillis();
            Integer movieId = movieList.get(i);
            
            if(Data.data.containsKey(movieId)) {
            	String json = "";
				try {
					json = ow.writeValueAsString(Data.data.get(movieId));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
    			task.message(index, time, HttpStatus.OK.value(), json);
    		} else {
    			task.message(index, time, HttpStatus.NOT_FOUND.value(), "\"Sorry, couldn't find any movie with the given details.\"");
    		}    
        }
        return task.getResponses();
	}

	@Cacheable(value="movieUpdateCommentCache", key="#request")
	@Override
	public List<ApiResponse> commentOnMovie(Task task, ApiRequest request) {
		log.info("Started task with {} movieId", task.getMovieList().size());
        task.start();
        
        final long time = System.currentTimeMillis();
        Integer movieId = request.getMovieId();
        
        if(Data.data.containsKey(movieId)) {
			Data.data.get(movieId).getCommentMap().put(new Integer(request.getUser().getId()), request.getComment());
			task.message(0, time, HttpStatus.OK.value(), "\"Successfull, added the comment.\"");
		} else {
			task.message(0, time, HttpStatus.NOT_FOUND.value(), "\"Sorry, couldn't find any movie with the given details.\"");
		} 
        
		return task.getResponses();
	}

}
