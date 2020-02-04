package btg;

import com.app.R;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import android.widget.ScrollView;
import android.graphics.BitmapFactory;
import android.content.Intent;
import android.os.AsyncTask;


public class TabContent extends Fragment
{
	private LayoutInflater inflater;
	private Asset asset = new Asset("HelloATabContentLogHere", null);
	public static final String images_base_secure_url = "https://image.tmdb.org/t/p/w500/";

	public LinearLayout container;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle saved_instance_state )
	{
		asset.log( container.toString() );
		this.inflater = inflater;
		return inflater.inflate( R.layout.scroll, container, false );	
	}

	@Override
	public void onViewCreated( View view, Bundle saved_instance_state )
	{
		Bundle args = getArguments();
		LinearLayout container = ( LinearLayout ) ((ScrollView) view).getChildAt(0);
		int tab = args.getInt("key");

		this.container = container;

		try
		{
			if ( tab == 0 )
			{
				buildPopMovies( );
			}
			else
			{
				buildFavourites( );
			}
		}
		catch( Exception exception )
		{
			asset.log("buildPopMovies", exception);
		}
		
	}

	private void buildPopMovies(  )
		throws Exception
	{
		LinearLayout container = this.container;
		String movies = getActivity().getIntent().getStringExtra("movies");
		this.drawMovies( new JSONArray( movies ), true );

	}

	public void buildFavourites(  )
		throws Exception
	{
		LinearLayout container = this.container;
		this.container.removeAllViews();

		String movies_string = getActivity().getIntent().getStringExtra("movies");
		String favourites_string = getActivity().getIntent().getStringExtra("favourites");

		JSONArray favourites = new JSONArray( favourites_string );
		JSONArray movies = new JSONArray( movies_string );
		JSONArray selected = new JSONArray();

		for ( int i = 0 ; i < favourites.length() ; i ++ )
		{
			int get_this = favourites.getInt( i );
			for ( int k = 0 ; k < movies.length() ; k++ )
			{
				JSONObject this_movie = movies.getJSONObject( k );
				if ( get_this == this_movie.getInt("id") )
				{
					selected.put( this_movie );
				}
			}
		}

		drawMovies( selected, false );
	}

	private void drawMovies( JSONArray ja_movies, boolean add_favourites_button )
		throws Exception
	{

		for ( int i = 0 ; i < ja_movies.length() ; i++ )
		{
			final JSONObject jo_each = ja_movies.getJSONObject( i );
			LinearLayout linear_layout = ( LinearLayout ) inflater.inflate( R.layout.each_movie, null );
			ImageView imageview = (ImageView) linear_layout.getChildAt( 1 );
			TextView textview = (TextView) linear_layout.getChildAt( 0 );
			textview.setText
			( 
				String.format
				(
					"%s, %s",
					jo_each.getString("title"),  
					asset.matched("^....", jo_each.getString("release_date"))
				) 
			);

			imageview.setOnClickListener
			(
				new View.OnClickListener()
				{
					public void onClick( View view )
					{
						try
						{
							Intent intent = new Intent( TabContent.this.getActivity(), com.app.Detail.class);
							intent.putExtra("title", jo_each.getString("title") );
							intent.putExtra("review", jo_each.getString("overview") );
							intent.putExtra("score", jo_each.getString("vote_average") );
							intent.putExtra("poster_path", jo_each.getString("poster_path") );
							intent.putExtra("id", jo_each.getInt("id") );
							intent.putExtra("add_favourites_button",  add_favourites_button );
							TabContent.this.getActivity().startActivityForResult( intent, 1 );
						}
						catch( Exception exception )
						{
							asset.log( "imageTouch", exception );
						}
					}
				}
			);

			getSetImage( jo_each.getString("poster_path"), imageview );
			container.addView( linear_layout );
			asset.log( jo_each.getString("title") );
		}
	}

	public static void getSetImage( String poster_path, ImageView imageview )
	{
		MakeHttp http = 
			new MakeHttp
			(
				TabContent.buildImageUrl( poster_path ),
				new MakeHttp.Callback()
				{
					public void callback( MakeHttp make_http )
						throws Exception
					{
						int length = make_http.stream_byte_array.length;
						imageview.setImageBitmap
						(
							BitmapFactory.decodeByteArray( make_http.stream_byte_array, 0, length )
						);

					}
				}
			);

		http.postJSONParse( false );

		http.executeOnExecutor( AsyncTask.THREAD_POOL_EXECUTOR,  new JSONObject() );
	}

	public static String buildImageUrl( String name )
	{
		return String.format
		(
			"%s%s",
			TabContent.images_base_secure_url,
			name
		);
	}



}
