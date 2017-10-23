package org.luxoft.interview.movie.domain;

import java.io.Serializable;
import java.util.List;

import org.luxoft.interview.movie.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequest implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Integer> movieList;
    private Integer movieId;
    private String comment;
    private User user;
}
