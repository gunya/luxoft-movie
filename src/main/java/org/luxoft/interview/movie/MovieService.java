package org.luxoft.interview.movie;

import java.util.List;

import org.luxoft.interview.movie.domain.ApiRequest;
import org.luxoft.interview.movie.domain.ApiResponse;
import org.luxoft.interview.movie.domain.Task;

public interface MovieService {
	public List<ApiResponse> getMovieDescription(List<Integer> movieList, Task task);
	public List<ApiResponse> commentOnMovie(Task task, ApiRequest request);
}
