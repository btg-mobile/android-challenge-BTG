
package btg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.view.View;
import com.app.R;
import java.lang.Exception;
import java.util.Collection;
import java.util.Date;
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
import android.content.SharedPreferences;
import com.app.*;


public class Asset
{
	private Activity activity;
	private Context context;
	private String TAG;
	public static final String REQUEST_STATUS_DESCRIPTION = "request_status_description";

	public Asset(String tag, Activity activity )
	{
		this.log(String.format("Asset constructor tag: %s", tag));
		this.setTag(tag);
		this.activity = activity;
		this.context = (Context) activity;
	}

	public String join(String[] pieces, String delimiter)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0 ; i < pieces.length ; i++)
		{
			sb.append(pieces[i] + (delimiter));
		}

		return sb.toString().replaceAll(".$", "");
	}

	public String join(String[] pieces)
	{
		return join(pieces, " ");
	}


	public void loader( boolean turn_on )
	{
		this.activity.runOnUiThread
		(
			new Runnable()
			{
				@Override
				public void run()
				{
					if (turn_on == true)
					{
						((View) activity.findViewById(R.id.progressbar)).setVisibility(View.VISIBLE);
					}
					else
					{
						((View) activity.findViewById(R.id.progressbar)).setVisibility(View.GONE);
					}
				}
			}
		);
	}

	//\RunONUIThread
	public void runOnUIThread( final Activity activity, final Misc callback )
	{
		activity.runOnUiThread
		(
			new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						callback.callback();					
					}
					catch(Exception e)
					{
						Asset.this.log("runOnUiThread", e);
					}
				}
			}
		);
	}

	public boolean booleanCoercion( JSONObject jo, String key, boolean case_no_coercion )
	{
		if ( jo.has( key ) == false )
		{
			return case_no_coercion;
		}

		try
		{
			return jo.getBoolean( key );

		} catch( Exception e ) { /**/ }

		try
		{
			int can_int = jo.getInt( key );
			if ( can_int == 0 )
			{
				return false;
			}
			return true;
		} catch( Exception e ) { /**/ }

		try
		{
			String can_string = jo.getString( key );
			if ( can_string.equals("0") )
			{
				return false;
			}
			return true;
		} catch( Exception e ) { /**/ }

		this.log( "Could not coerce!" );
		return case_no_coercion;
	}

	public void putStringToSharedPreference( EntryPoint activity, String key, String value )
	{
		SharedPreferences.Editor editor = activity.shared_preferences.edit();
		editor.putString( key, value );
		editor.commit();
	}

	public String getSharedPreferencesString( EntryPoint activity, String key )  
	{
		return activity.shared_preferences.getString( key, null );
	}
		
	public String matched( String regex, String against_to )
	{
		Pattern pattern = Pattern.compile( regex, Pattern.CASE_INSENSITIVE );
		Matcher match = pattern.matcher( against_to );

		if ( match.find() )
		{
			return match.group();
		}

		return null;
	}

	public void toastIt( Activity act, String msg)
	{
		Toast.makeText(act, msg, Toast.LENGTH_LONG)
			.show();
	}

	public void toastIt( Activity act, int resource_string, Object... fill )
	{
		if ( this.activity == null)
		{		
			return;
		}	
		Toast.makeText
		( 
			activity, 
			String.format( activity.getString( resource_string ), fill ), 
			Toast.LENGTH_LONG
		)
		.show();
	}

	//\Toast
	public void toastIt( Activity activity, int resource_string )
	{
		Toast.makeText(activity, activity.getString( resource_string ), Toast.LENGTH_LONG)
			.show();
	}
	
	public void log(String matter)
	{
		Log.i(this.TAG, matter);
	}

	public void log(float matter)
	{
		Log.i(this.TAG, String.valueOf(matter));
	}

	public void log(String matter, Exception e)
	{
		Log.i(this.TAG, matter, e);
	}

	public void log(float matter, String label)
	{
		Log.i
		(
		 	this.TAG, 
			String.format("%s: %f", label, matter)
		);
	}

	public void log(double matter, String label)
	{
		Log.i
		(
		 	this.TAG, 
			String.format("%s: %f", label, matter)
		);
	}

	public void log(boolean matter, String label)
	{
		Log.i
		(
		 	this.TAG, 
			String.format("%s: %b", label, matter)
		);
	}

	public void log(String matter, String label)
	{
		Log.i
		(
		 	this.TAG, 
			String.format("%s: %s", label, matter)
		);
	}

	public <T> void log(T[] collection)
	{
		log("Houston we have a collection <T> like");
		for (T item : collection)
		{
			log(item.toString());
		}
	}

	public <T> void log(Map<String, T> collection, String label)
	{
		log(String.format ("%s>>>>>>>>>>>>>>>>>", label) );
		Set<Map.Entry<String, T>> set = collection.entrySet();
		Iterator<Map.Entry<String, T>> iterator = set.iterator();
		while( iterator.hasNext() )
		{
			Map.Entry<String, T> entry = iterator.next();
			if ( entry.getValue() instanceof java.util.List )
			{
				List list = ( List ) entry.getValue();
				Iterator layered_iterator = list.iterator();
				while( layered_iterator.hasNext() == true )
				{
					this.log
					( 
						String.format("%s: %s", entry.getKey(), layered_iterator.next().toString() ) 
					);
				}
			}
			else
			{
				this.log
				( 
					String.format("%s: %s", entry.getKey(), entry.getValue().toString()) 
				);
			}
		}
	}

	public <T> void log(T[] collection, String label)
	{
		log(label);
		log(collection);
	}

	public void log(int[] collection)
	{
		for (int a : collection)
		{
			log(String.valueOf(a));
		}
	}

	public void log( Bundle bundle, String label )
	{
		log( label );
		Set<String> set = bundle.keySet();
		Iterator<String> iterator = set.iterator();
		while( iterator.hasNext() == true )
		{
			log( iterator.next() );
		}
	}

	public void log(List<?> list)
	{
		Iterator iter = list.iterator();
		while(iter.hasNext())
		{
			log( iter.next().toString() );
		}
	}

	public void log(List<?> list, String label)
	{
		log( label );
		Iterator iter = list.iterator();
		while(iter.hasNext())
		{
			log( iter.next().toString() );
		}
	}

	public void setTag(String tag)
	{
		this.TAG = tag;
	}

}
