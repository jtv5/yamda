package isel.pdm.yamda.data.repository.datasource;

import isel.pdm.yamda.data.entity.tmdb.ConfigurationDTO;
import isel.pdm.yamda.data.entity.tmdb.MovieDTO;
import isel.pdm.yamda.data.entity.tmdb.MovieListingDTO;

public interface IMovieDataStore {

    /**
     * Get the theaters movies entities list from storage
     * @return
     */
    MovieListingDTO theaterMovieListEntity(int page);

    /**
     * Get the soon movies entities list from storage
     * @return
     */
    MovieListingDTO soonMovieListEntity(int page);

    /**
     * Get the top movies entities list from storage
     * @return
     */
    MovieListingDTO topMovieListEntity(int page);

    /**
     * Get a movie entity from storage
     * @return
     */
    MovieDTO movieEntityDetails(int id);

    /**
     * Get the api configuration
     * @return
     */
    ConfigurationDTO apiConfiguration();
}
