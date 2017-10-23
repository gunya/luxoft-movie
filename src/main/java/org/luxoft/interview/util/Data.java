package org.luxoft.interview.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.luxoft.interview.movie.model.Movie;
import org.luxoft.interview.movie.model.User;

public class Data {
	public static Map<Integer, Movie> data = new ConcurrentHashMap<>();
	public static Map<Integer, User> user = new ConcurrentHashMap<>();
	
	public static void prepareData() {
		Map<Integer, String> comment = new ConcurrentHashMap<>();
		List<String> roleList = new ArrayList<>();
		roleList.add("ADMIN");
		
		User u1 = new User(1, "John", "321", roleList);
		
		roleList = new ArrayList<>();
		roleList.add("USER");
		
		User u2 = new User(2, "Ben", "123", roleList);
		User u3 = new User(3, "Henry", "123", roleList);
		User u4 = new User(4, "Alisa", "123", roleList);
		user.put(1, u1);
		user.put(2, u2);
		user.put(3, u3);
		user.put(4, u4);
		
		comment.put(1, "Cool I like it");
		comment.put(2, "Nice");
		data.put(1, new Movie(1, "Brave Heart", "Full of action and thrill.", comment));
		
		comment = new ConcurrentHashMap<>();
		comment.put(1, "Amazing");
		comment.put(3, "super");
		data.put(2, new Movie(2, "Walle", "Emotions and a new story, love of a robot.", comment));
		
		comment = new ConcurrentHashMap<>();
		comment.put(1, "Pathatic");
		comment.put(4, "Wow...");
		data.put(3, new Movie(3, "Hidden Man", "Fight, love and suspense.", comment));
	}
}
