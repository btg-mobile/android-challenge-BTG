package btg;


import android.app.Activity;
import android.widget.ImageView;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.*;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class MovieFetcher
{
	private Activity activity;

	private String the_moviedb_API = "44f26579cd8b9daed1a68ea7b4775e1e";
	private String base_secure_url = "https://api.themoviedb.org/3/";

	private Asset asset;

//	public Map<Integer, JSONObject> movie_pack = new HashMap<>();
	//

	Map<String, String> targets = new HashMap<>();

	public MovieFetcher( Activity activity )
	{
		this.activity = activity;
		this.asset = new Asset( "HelloAMovieFetcherLogHere", activity );
		this.activity.getIntent().putExtra("favourites", "[]");
		fillTargets();
	}


	public void getPopMovies( MakeHttp.Callback callback )
	{
		new MakeHttp
		(
			this.buildUrl(this.base_secure_url, "pop"),
			callback
		).execute( new JSONObject() );
	}

	private void fillTargets()
	{
		this.targets.put("pop", "movie/popular");
	}
	
	public void save( JSONObject jo_pop_movies )
		throws Exception
	{

		if ( jo_pop_movies.has("results") == false )
		{
			//Messages could have been put in a xml in values, saving time for now
			asset.toastIt( activity, "Could not fetch from TheMovieDB" );
			return;
		}

		JSONArray ja_movies = jo_pop_movies.getJSONArray("results");

		this.activity.getIntent().putExtra("movies", ja_movies.toString() );
	}

	private String buildUrl( String base, String which )
	{
		String query = 
			String.format
			(
				"%s%s?api_key=%s",
				base,
				this.targets.get( which ),
				this.the_moviedb_API
			);
		this.asset.log( query, "buildUrl" );

		return query;
	}

}



