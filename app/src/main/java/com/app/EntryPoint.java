package com.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RelativeLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import btg.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import androidx.viewpager.widget.ViewPager;


public class EntryPoint extends FragmentActivity
{
	public static final String TAG = "HelloAnEntryPointLogHere";

	private Asset asset = new Asset(TAG, this);
	public SharedPreferences shared_preferences;
	Bootstrap bootstrap;

	@Override
	public void onCreate(Bundle save_instance_state)
	{
        super.onCreate(save_instance_state);
		asset.log("On create of MainActivity started");

		try
		{

		    setContentView( R.layout.main );
			this.bootstrap = new Bootstrap( this );
			this.shared_preferences = this.getPreferences( Context.MODE_PRIVATE );
			String string_favourites = this.asset.getSharedPreferencesString( this, "favourites" );

			if ( string_favourites != null )
			{
				this.getIntent().putExtra( "favourites", string_favourites );
			}
		}
		catch( Exception e )
		{
			asset.log( "InitialException", e );
		}


	}

	@Override
	public void onActivityResult( int request_code, int result_code, Intent data)
	{
		asset.log( result_code, "onActivityResult just arrived" );

		if 
		( 
			result_code == Activity.RESULT_OK &&
			data != null
		)
		{
			try
			{
				JSONArray favourites = new JSONArray( this.getIntent().getStringExtra("favourites") );

				int id = data.getIntExtra( "id", 0 );

				asset.log( id , "onActivityResult id received" );
				asset.log( favourites.toString() , "onActivityResult already favourited" );

				for ( int i = 0 ; i < favourites.length() ; i++ )
				{
					if ( id == favourites.getInt( i ) )
					{
						//Movie has already been favourited
						if ( data.getBooleanExtra( "unfavourite", false ) == true )
						{
							favourites.remove( i );
							this.getIntent().putExtra( "favourites", favourites.toString() );
							bootstrap.viewpager.setCurrentItem(0);
						}
						return;
					}
				}

				favourites.put( id );
				String string_favourites = favourites.toString();
				this.getIntent().putExtra( "favourites", string_favourites );
				this.asset.putStringToSharedPreference(this, "favourites", string_favourites );
			}
			catch( Exception exception )
			{
				asset.log( "onActivityResult", exception );
			}

		}
	}

}
