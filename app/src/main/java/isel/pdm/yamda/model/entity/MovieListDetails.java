package isel.pdm.yamda.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * This class is used for representing a Movie details obtained from the data layer
 * by a movies list. It contains less data than a MovieDetails
 *
 * Instances can also be used to transport information between components of the
 * application (e.g. Activities, Services). Because those interactions may cross process
 * boundaries, the class supports the {@link android.os.Parcelable} contract and conventions.
 *
 * Instances are designed to be immutable, and are therefore thread-safe.
 */
public final class MovieListDetails implements Parcelable {

    /** The movie id  **/
    private final int id;
    /** The movie title **/
    private final String title;
    /** The movie release date **/
    private final String release_date;
    /** The movie rating **/
    private final float rating;
    /** The movie overview description **/
    private final String overview;
    /** The movie poster image **/
    private final String poster;
    /** The movie genres **/
    private final Genre[] genres;

    public MovieListDetails(int id, String title, String release_date,
                            String overview, String poster, Genre[] genres, float rating) {
        this.id = id;
        this.title = title;
        this.release_date = release_date;
        this.overview = overview;
        this.poster = poster;
        this.genres = genres;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public float getRating() {
        return rating;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster() {
        return poster;
    }

    public Genre[] getGenres() {
        return genres;
    }

    public String getGenresTogether() {
        if(this.genres.length == 0) return "";

        StringBuilder res = new StringBuilder();

        for (Genre genre : genres) {
            res.append(genre.getName()).append(", ");
        }

        return res.substring(0, res.length() - 2);
    }

    /*
    |--------------------------------------------------------------------------
    | Parcelable options
    |--------------------------------------------------------------------------
    */
    protected MovieListDetails(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.release_date = in.readString();
        this.rating = in.readFloat();
        this.overview = in.readString();
        this.poster = in.readString();
        this.genres = in.createTypedArray(Genre.CREATOR);
    }

    public static final Creator<MovieListDetails> CREATOR = new Creator<MovieListDetails>() {
        @Override
        public MovieListDetails createFromParcel(Parcel in) {
            return new MovieListDetails(in);
        }

        @Override
        public MovieListDetails[] newArray(int size) {
            return new MovieListDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.release_date);
        dest.writeFloat(this.rating);
        dest.writeString(this.overview);
        dest.writeString(this.poster);
        dest.writeTypedArray(this.genres, 0);
    }
}