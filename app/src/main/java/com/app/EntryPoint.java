package com.app;

import btg.*;
import android.app.Activity;
import android.widget.RelativeLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.content.Intent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class EntryPoint extends FragmentActivity
{
	public static final String TAG = "HelloAnEntryPointLogHere";

	private Asset asset = new Asset(TAG, this);
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
						return;
					}
				}

				favourites.put( id );

				this.getIntent().putExtra( "favourites", favourites.toString() );
			}
			catch( Exception exception )
			{
				asset.log( "onActivityResult", exception );
			}

		}
	}

}
