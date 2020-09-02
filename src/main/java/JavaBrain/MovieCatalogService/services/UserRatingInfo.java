package JavaBrain.MovieCatalogService.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import JavaBrain.MovieCatalogService.Model.Rating;
import JavaBrain.MovieCatalogService.Model.UserRating;

@Service
public class UserRatingInfo {

	
	@Autowired
	private RestTemplate restTemplate;  
	
	@HystrixCommand(fallbackMethod="getFallBackUserRating")
	public UserRating getUserRating(@PathVariable("userId") String userId){
		return restTemplate.getForObject("http://Rating-Data-Service/rating/users/"+userId, UserRating.class);
	}
	
	public UserRating getFallBackUserRating(@PathVariable("UserId") String userId){
		UserRating userRating = new UserRating();
		userRating.setUserId(userId);
		userRating.setUserRating(Arrays.asList(
				new Rating("0",0)
				));
		return userRating;
	}
	
}
