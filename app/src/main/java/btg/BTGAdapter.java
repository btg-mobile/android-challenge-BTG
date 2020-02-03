package btg;

import android.app.Activity;
import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.os.Bundle;
import java.lang.CharSequence;


public class BTGAdapter extends FragmentPagerAdapter
{
	Asset asset = new Asset("HelloAnAdapterLogHere", null);
	public TabContent[] tabcontents = new TabContent[ 2 ];
	MovieFetcher movie_fetcher;

	public BTGAdapter( FragmentManager fragment_manager )
	{
		super( fragment_manager );
		this.movie_fetcher = movie_fetcher;
		asset.log( fragment_manager.toString(), "fragment_manager" );
	}

	@Override
	public Fragment getItem( int tab_index )
	{
		asset.log( tab_index, "getItem" );
		TabContent tabcontent = new TabContent(  );
		tabcontents[ tab_index ] = tabcontent;
		Fragment fragment = tabcontent;
		Bundle args = new Bundle();
		args.putInt( "key", tab_index );
		fragment.setArguments( args );
		return fragment;
	}

	@Override
	public int getCount()
	{
		return 2;
	}

	@Override
	public CharSequence getPageTitle( int position )
	{
		if ( position == 0 )
		{
			return "Movies";
		}
		return "Favourites";
	}

}



