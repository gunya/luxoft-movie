package org.luxoft.interview.movie.facade;

import java.util.List;

import org.luxoft.interview.movie.domain.ApiRequest;
import org.luxoft.interview.movie.domain.ApiResponse;
import org.luxoft.interview.movie.domain.Task;

public interface MovieFacadeService {
	public List<ApiResponse> commentAndDetails(Task task, ApiRequest request);
}
