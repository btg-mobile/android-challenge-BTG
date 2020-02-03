package btg;

import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MakeHttp extends AsyncTask<JSONObject, Void, JSONObject>
{

	private HttpsURLConnection http_url_connection;
	public byte[] stream_byte_array;
	private Callback callback;
	private Asset asset = new Asset("HelloAHttpLogHere", null );
	//Get the default to request from movies
	public String method = "GET";
	public String url_string;
	private boolean post_json_parse = true;



	public MakeHttp( String url_string, Callback callback )
	{
		this.callback = callback;
		this.url_string = url_string;
		asset.log(url_string, "constructor Http");
	};

	public void postJSONParse( boolean parse )
	{
		this.post_json_parse = parse;
	}
	
	protected JSONObject doInBackground(JSONObject... args)
	{
		asset.log( "doInBackgroundHere");
		try
		{
			return actionCore( args[ 0 ] );
		}
		catch(Exception e)
		{
			asset.log("Something happened in doInBackground", e);
			return null;
		}
	}

	protected void onPostExecute( JSONObject result )
	{
		try
		{
			this.callback.callback( result );
			this.callback.callback( this );
		}
		catch( Exception exception )
		{
			asset.log( "Error calling callback", exception  );
		}
	}

	private void makeHttpsURLConnectionReady( URL url ) 
		throws Exception
	{
		asset.log( String.format("(%s) Preparing connection...)", url.toString() ));
		this.http_url_connection = (HttpsURLConnection) url.openConnection();
		this.http_url_connection.setAllowUserInteraction( true );
		http_url_connection.setRequestMethod( this.method );
		http_url_connection.setDoInput( true );
		http_url_connection.setDoOutput( true );
	}

	//\addToQuery
	private void addToQuery(StringBuilder s, String key, String value) throws Exception
	{
		String uppersand = "&";

		if (s.length() == 0)
		{
			uppersand = "";
		}

		s.append
		( 
			String.format
			(
				"%s%s=%s", uppersand, key, android.net.Uri.encode( value )
			)
		);
	}

	//\actionCore
	public JSONObject actionCore( JSONObject args ) 
		throws Exception
	{
		JSONObject jo_response = new JSONObject();
		jo_response.put("success", false);
		StringBuilder post = new StringBuilder();

		makeHttpsURLConnectionReady
		(
			new URL
			(
				this.url_string
			)
		);

		try
		{
			Iterator<String> iterator = args.keys();
			while( iterator.hasNext() )
			{
				String key = iterator.next();
				addToQuery( post, key, args.getString(key) );
			}

		}
		catch(Exception e)
		{
			buildResponse( jo_response, "building pack to send", e );
			return jo_response;
		}

		String response;

		try
		{
			response = send( post );
		}
		catch(Exception e)
		{
			buildResponse( jo_response, "sending pack", e );
			return jo_response;
		}
	

		jo_response.put( "pure_response", response);

		if ( post_json_parse == false )
		{
			jo_response.put("success", true);
			asset.log( String.format("Imediate response, not a JSONString: %s", this.url_string ) );
			return jo_response;
		}
		else
		{
			asset.log( String.format("Imediate response: %s", response) );
		}

		try
		{
			jo_response.put
			(
				"parsed", 
				new JSONObject
				(
					response
				)
			);
			jo_response.put("success", true);
		}
		catch(Exception e)
		{
			buildResponse( jo_response, "response parse", e );
			return jo_response;
		}

		return jo_response;
	}

	private void buildResponse( JSONObject jo_response, String comment, Exception e )
		throws Exception
	{
		jo_response.put
		(
			this.asset.REQUEST_STATUS_DESCRIPTION,
			String.format("Request error, %s", comment )
		);
		this.asset.log( comment, e );
	}


	private String send(StringBuilder post) throws Exception
	{
		String string_post_from_string_builder =  post.toString();

		asset.log(String.format("ThePost(%d length): %s", post.length(), string_post_from_string_builder ));
		asset.log("Flushing data...");
		byte[] bytes = string_post_from_string_builder.getBytes();
		string_post_from_string_builder = null;
		this.http_url_connection.setFixedLengthStreamingMode(bytes.length);
		OutputStream out = new BufferedOutputStream(http_url_connection.getOutputStream());
		out.write(bytes, 0, bytes.length);
		out.flush();
		InputStream in = new BufferedInputStream(http_url_connection.getInputStream());
		ByteArrayOutputStream response = new ByteArrayOutputStream();
		int b;

		while((b = in.read()) != -1)
		{
			response.write(b);
		}
		if (response != null)
		{
			response.close();
		}
		if (out != null)
		{
			out.close();
		}
		if (in != null)
		{
			in.close();
		}

		this.stream_byte_array = response.toByteArray();
		return response.toString();
	}

	public interface Callback
	{
		default void callback( JSONObject result ) 
			throws Exception
		{
			//
		};
		default void callback( MakeHttp make_http )
			throws Exception
		{
			//
		}
	}




}
