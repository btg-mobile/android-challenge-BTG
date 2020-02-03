package com.app;

import btg.*;
import android.app.Activity;
import android.widget.Button;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;
import android.content.Intent;


public class Detail extends Activity
{
	public static final String TAG = "HelloADetailLogHere";

	private Asset asset = new Asset(TAG, this);

	@Override
	public void onCreate(Bundle save_instance_state)
	{
        super.onCreate(save_instance_state);
		asset.log("Details activity called");
		try
		{
		    setContentView( R.layout.details );
			ImageView imageview = ( ImageView ) findViewById( R.id.poster );
			TextView titleview = findViewById( R.id.title );
			TextView reviewview = findViewById( R.id.review );
			TextView scoreview = findViewById( R.id.score );

			Intent intent = getIntent();
			boolean should_display_favourite_button = intent.getBooleanExtra( "add_favourites_button", true );
			titleview.setText( intent.getStringExtra("title") );
			reviewview.setText( intent.getStringExtra("review") );
			scoreview.setText( String.format( "Vote average score: %s", intent.getStringExtra("score") ) );
			final int id = intent.getIntExtra( "id", 1 );

			TabContent.getSetImage( intent.getStringExtra("poster_path"), imageview );

			final Button make_favourite = ( Button ) findViewById( R.id.make_favourite );

			if ( should_display_favourite_button == false )
			{
				make_favourite.setVisibility( View.GONE );
			}

			make_favourite.setOnClickListener
			(
				new View.OnClickListener()
				{
					public void onClick( View view )
					{
						Intent intent = new Intent();
						intent.putExtra("id", id );
						Detail.this.setResult( Activity.RESULT_OK, intent );
						Detail.this.finish();
					}
				}
			);

		}
		catch( Exception e )
		{
			asset.log( "InitialException", e );
		}


	}
}
