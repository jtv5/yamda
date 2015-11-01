package isel.pdm.yamda.data.repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import isel.pdm.yamda.data.api.TheMovieAPI;
import isel.pdm.yamda.data.entity.ConfigurationDTO;
import isel.pdm.yamda.data.entity.MovieListDTO;
import isel.pdm.yamda.data.entity.MovieListingDTO;
import isel.pdm.yamda.data.entity.mapper.MovieDataMapper;
import isel.pdm.yamda.model.entity.Configuration;
import isel.pdm.yamda.model.entity.Movie;
import isel.pdm.yamda.model.repository.IMovieRepository;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Nuno on 30/10/2015.
 */
public class MovieRepository implements IMovieRepository {

    private TheMovieAPI api;

    private MovieDataMapper movieDataMapper;

    private ConfigurationDTO apiConfiguration;

    private HashMap<String, MovieListingDTO> listings;

    public MovieRepository(TheMovieAPI api, MovieDataMapper movieDataMapper) {
        this.api = api;
        this.movieDataMapper = movieDataMapper;
    }

    @Override
    public Configuration getApiConfiguration() throws IOException {
        if(apiConfiguration == null){
            apiConfiguration = api.getConfig(TheMovieAPI.API_KEY).execute().body();
        }
        return movieDataMapper.transform(apiConfiguration);
    }

    @Override
    public HashMap<String, List<Movie>> getListings() throws IOException {
        if (listings == null) {
            listings = new HashMap<>();
            if (listings.get(MovieListingDTO.NOW_PLAYING_TAG) == null) {
                listings.put(MovieListingDTO.NOW_PLAYING_TAG, api.getNowPlaying(TheMovieAPI.API_KEY, 1, "en").execute().body());
            }
            if (listings.get(MovieListingDTO.UPCOMING_TAG) == null) {
                listings.put(MovieListingDTO.UPCOMING_TAG, api.getUpcoming(TheMovieAPI.API_KEY, 1, "en").execute().body());
            }
            if (listings.get(MovieListingDTO.POPULAR_TAG) == null) {
                listings.put(MovieListingDTO.POPULAR_TAG, api.getMostPopular(TheMovieAPI.API_KEY, 1, "en").execute().body());
            }
        }
        return movieDataMapper.transform(listings);
    }

    @Override
    public List<Movie> getListing(String tag) throws IOException {
        if (listings == null) {
            listings = new HashMap<>();
        }
        if(listings.get(tag) == null) {
            MovieListingDTO listing;
            switch (tag) {
                case MovieListingDTO.NOW_PLAYING_TAG:
                    listing = api.getNowPlaying(TheMovieAPI.API_KEY, 1, "en").execute().body();
                    appendImageUrl(listing);
                    listings.put(tag, listing);
                    break;
                case MovieListingDTO.UPCOMING_TAG:
                    listing = api.getUpcoming(TheMovieAPI.API_KEY, 1, "en").execute().body();
                    appendImageUrl(listing);
                    listings.put(tag, listing);
                    break;
                case MovieListingDTO.POPULAR_TAG:
                    listing = api.getMostPopular(TheMovieAPI.API_KEY, 1, "en").execute().body();
                    appendImageUrl(listing);
                    listings.put(tag, listing);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        return movieDataMapper.transform(listings.get(tag));
    }

    private void appendImageUrl(MovieListingDTO movieListingDTO) throws IOException {
        if (apiConfiguration == null) {
            getApiConfiguration();
        }
        for (MovieListDTO dto : movieListingDTO.getResults()) {
            dto.setPoster_path(apiConfiguration.getBaseUrl() + apiConfiguration.getPosterSizes()[0] + dto.getPoster_path());
        }
    }

    @Override
    public Movie getMovie(int id) {
        return null;
    }

    public static MovieRepository create() {
        return new MovieRepository(new Retrofit.Builder()
                .baseUrl(TheMovieAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TheMovieAPI.class), new MovieDataMapper());
    }
}
