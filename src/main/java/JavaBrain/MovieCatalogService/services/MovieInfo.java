package JavaBrain.MovieCatalogService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import JavaBrain.MovieCatalogService.Model.CatalogItem;
import JavaBrain.MovieCatalogService.Model.Movie;
import JavaBrain.MovieCatalogService.Model.Rating;

@Service
public class MovieInfo {

	@Autowired
	private RestTemplate restTemplate;  
	
	
	@HystrixCommand(fallbackMethod="getFallBackCatalogItem")
	public CatalogItem getCatalogItem(Rating rating)
	{
		Movie movie = restTemplate.getForObject("http://Movie-info-Service/movieInfo/"+rating.getMovieId(), Movie.class);
		return new CatalogItem(movie.getMoviename(),"Action",rating.getRating());
	}
	
	public CatalogItem getFallBackCatalogItem(Rating rating ){
		return new CatalogItem("Movie name not found ","Action",rating.getRating());
	}
}
