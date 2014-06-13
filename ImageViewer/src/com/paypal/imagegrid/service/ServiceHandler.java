package com.paypal.imagegrid.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

import com.paypal.imagegrid.data.InstagramData;
import com.paypal.imagegrid.utils.Constatnts;
/**
 * Class retrieves the data from the Instagram API.
 * @author sukesh
 *
 */
public class ServiceHandler {

	public static final String LINK_ACCESSCODE = "client_id="+ Constatnts.CLIENT_ID +"&client_secret="+ Constatnts.CLIENT_SECRET + 
			"&grant_type=authorization_code" + "&redirect_uri="+ Constatnts.CALLBACKURL + "&code=" + InstagramData.getInstance().getToken();

	private static ServiceHandler mServiceHandler = null;

	public static ServiceHandler getInstance() {

		if(mServiceHandler == null)
			mServiceHandler = new ServiceHandler();

		return mServiceHandler;
	}

	/**
	 * Retrieves the access token and store it into a data class.
	 */
	public void createAccessToken() {

		String accessToken = null;
		try {
			URL url = new URL(Constatnts.TOKEN_URL_STRING);

			HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
			httpsURLConnection.setRequestMethod("POST");
			httpsURLConnection.setDoInput(true);
			httpsURLConnection.setDoOutput(true);

			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpsURLConnection.getOutputStream());
			outputStreamWriter.write(LINK_ACCESSCODE);
			outputStreamWriter.flush();

			String response = streamToString(httpsURLConnection.getInputStream());
			Log.e("SUKESH", "Response is: " + response);
			JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();

			accessToken = jsonObject.getString("access_token"); //Here is your ACCESS TOKEN

			InstagramData.getInstance().setAccessToken(accessToken);
			InstagramData.getInstance().setUserId(jsonObject.getJSONObject("user").getString("id"));
			InstagramData.getInstance().setUserName(jsonObject.getJSONObject("user").getString("username"));
		}catch (UnknownHostException e) {
			Log.i("SUKESH", "Exception is :" +e);
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Retrieving the image url.
	 */
	public void createImages() {

		ArrayList<String> list = new ArrayList<String>();

		Log.i("SUKESH", "Image Link is: " +  Constatnts.APIURL + "/users/" + InstagramData.getInstance().getUserId() + 
				"/media/recent/?access_token=" + InstagramData.getInstance().getAccessToken());
		try {
			URL url = new URL( Constatnts.APIURL + "/users/" + InstagramData.getInstance().getUserId() + 
					"/media/recent/?access_token=" + InstagramData.getInstance().getAccessToken());

			HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

			String response = streamToString(httpsURLConnection.getInputStream());
			Log.i("SUKESH", "Response is: " + response);

			JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
			JSONArray jsonArray = jsonObject.getJSONArray("data");

			for (int i = 0; i < jsonArray.length(); i++) {

				Log.i("SUKESH", "The length is: " + jsonArray.get(i));
				list.add(jsonArray.getJSONObject(i).getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
			}

			InstagramData.getInstance().setThumbnailList(list);
			
			for (int i = 0; i < list.size(); i++) {
				
				Log.i("SUKESH", "the uri is: " + list.get(i));
			}

		}catch (UnknownHostException e) {
			Log.i("", "Exception is :" +e);
			e.printStackTrace();
		}catch (Exception e) {

			Log.i("SUKESH", "Exception is " +e);
		}

	}

	/**
	 * Handles the response and stores into an string buffer.
	 * @param p_is
	 * @return
	 */
	public String streamToString(InputStream p_is) {
		try
		{
			BufferedReader m_br;
			StringBuffer m_outString = new StringBuffer();
			m_br = new BufferedReader(new InputStreamReader(p_is));
			String m_read = m_br.readLine();
			while(m_read != null)
			{
				m_outString.append(m_read);
				m_read =m_br.readLine();
			}
			return m_outString.toString();
		}
		catch (Exception p_ex)
		{
			p_ex.printStackTrace();
			return "";
		}
	}
}
