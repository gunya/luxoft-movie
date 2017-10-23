package org.luxoft.interview.movie.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class ApiResponse implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String body;
    private int status;
    private int duration;

    @JsonRawValue
    public String getBody() {
        if(body != null && body.trim().isEmpty()) {
            return null;
        }
        return body;
    }
}
