package JavaBrain.MovieCatalogService.resource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import JavaBrain.MovieCatalogService.Model.CatalogItem;
import JavaBrain.MovieCatalogService.Model.Movie;
import JavaBrain.MovieCatalogService.Model.Rating;
import JavaBrain.MovieCatalogService.Model.UserRating;
import JavaBrain.MovieCatalogService.services.MovieInfo;
import JavaBrain.MovieCatalogService.services.UserRatingInfo;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;  
	
	@Autowired
	MovieInfo movieInfo;
	
	@Autowired
	UserRatingInfo userRatingInfo;
	
    @Autowired
	private WebClient.Builder webClientBuilder;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		WebClient.Builder builder = WebClient.builder();
		 
		UserRating userRating = userRatingInfo.getUserRating(userId);	
				
		
		/*List<Rating> ratings=Arrays.asList(
				new Rating("1234",4),
				new Rating("5678",4)
				);*/
		
		return userRating.getUserRating().stream().map(rating -> movieInfo.getCatalogItem(rating))
				.collect(Collectors.toList());
			
		  /* Movie movie = webClientBuilder.build()
		   .get()
		   .uri("http://localhost:8082/movieInfo/"+rating.getMovieId())
		   .retrieve()
		   .bodyToMono(Movie.class)
		   .block();
		   */
			/*return getCatalogItem(rating);
		})
				.collect(Collectors.toList());*/
		
		/*return Collections.singletonList(
				new CatalogItem("Raj","romantic",3)
				);*/
		
	}
	
	
	
	
	/*public List<CatalogItem> getFallBackMethod(@PathVariable("userId") String userId){
		
		return Arrays.asList(new CatalogItem("No Movie "," ", 0));
	}*/

}
