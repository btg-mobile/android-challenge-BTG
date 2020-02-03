package btg;

import android.app.Activity;
import androidx.fragment.app.FragmentActivity;
import com.app.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import android.widget.EditText;
import android.text.TextWatcher;
import android.text.Editable;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;

public class Bootstrap
{
	private Asset asset;
	private Activity activity;
	private MovieFetcher movie_fetcher;

	EditText filter;

	public Bootstrap( FragmentActivity fragment_activity )
	{
		this.activity = fragment_activity;
		filter = this.activity.findViewById( R.id.filter );

		asset = new Asset( "HelloABootStrapLogHere", activity );


		asset.loader( true );

		MovieFetcher movie_fetcher = new MovieFetcher( activity );

		movie_fetcher.getPopMovies
		(
			new MakeHttp.Callback()
			{
				public void callback( JSONObject jo_pop_movies )
					throws Exception
				{
					Bootstrap.this.asset.loader( false );

					if ( jo_pop_movies.getBoolean("success") == false )
					{
						Bootstrap.this.asset.toastIt
						( 
							activity, 
							jo_pop_movies.getString( Asset.REQUEST_STATUS_DESCRIPTION ) 
						);
					}				
					movie_fetcher.save( jo_pop_movies.getJSONObject( "parsed" ) );
					buildLayout( fragment_activity );
				}
			}
		);

		takeCareOfFilter();

	}

	private void buildLayout( FragmentActivity fragment_activity )
	{
		TabLayout tablayout = this.activity.findViewById( R.id.tablayout );

		TabLayout.Tab movies = tablayout.newTab();
		TabLayout.Tab favourites = tablayout.newTab();
		tablayout.addTab( movies );
		tablayout.addTab( favourites );

		ViewPager viewpager = this.activity.findViewById( R.id.viewpager );

		BTGAdapter btg_adapter = 
			new BTGAdapter
			( 
				fragment_activity.getSupportFragmentManager() 
			);

		viewpager.setAdapter( btg_adapter );
		tablayout.setupWithViewPager( viewpager );
		viewpager.addOnPageChangeListener
		(
			new TabLayout.TabLayoutOnPageChangeListener( tablayout )
		);

		final FragmentManager fragment_manager = fragment_activity.getSupportFragmentManager();
//		TabContent tabcontent = fragment_manager.find		

		tablayout.addOnTabSelectedListener
		(
			new TabLayout.OnTabSelectedListener()
			{
				@Override
				public void onTabSelected( TabLayout.Tab tab )
				{
					int tab_position = tab.getPosition();
					viewpager.setCurrentItem( tab_position );
					asset.log( tab_position, "TabSelected");
					try
					{
						if ( tab_position == 1 )
						{
							btg_adapter.tabcontents[ tab_position ]
								.buildFavourites();
							Bootstrap.this.filter.setHint("Filter by name or year");
						}
						else
						{
							Bootstrap.this.filter.setHint("Filter by name");
						}
					}
					catch( Exception exception )
					{
						asset.log("TabChangeBuildFavourites", exception );
					}

				}

				@Override
				public void onTabReselected( TabLayout.Tab tab )
				{
					//
				}
				
				@Override
				public void onTabUnselected( TabLayout.Tab tab )
				{
					//
				}
			}
		);

	}

	private void takeCareOfFilter( )
	{

		this.filter.addTextChangedListener
		(
			new TextWatcher()
			{
				@Override
				public void afterTextChanged( Editable editable )
				{
					asset.log(editable.toString(), "afterTextChanged");
				}

				@Override
				public void beforeTextChanged( CharSequence char_sequence, int start, int count, int after )
				{
					asset.log(char_sequence.toString(), "beforeTextChanged");
				}

				@Override
				public void onTextChanged( CharSequence char_sequence, int start, int before, int count )
				{
					asset.log(char_sequence.toString(), "onTextChanged");
				}
			}
		);

	}

}

