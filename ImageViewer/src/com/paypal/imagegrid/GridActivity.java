package com.paypal.imagegrid;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.paypal.imagegrid.adapter.ImageAdapter;
import com.paypal.imagegrid.data.InstagramData;
import com.paypal.imagegrid.service.ServiceHandler;
import com.paypal.imagegrid.utils.Constatnts;
import com.paypal.imagegrid.utils.Utility;

/**
 * Class retrieves the data from the source and displays it.
 * @author sukesh
 *
 */
public class GridActivity extends Activity implements OnItemClickListener {

	private LinearLayout llSocial = null;
	private FrameLayout flGrid = null;

	private GridView mGridView = null;
	private ProgressBar mProgressBar = null;
	private WebView mWebView = null;
	private ImageAdapter mImageAdapter = null;
	
	private String scrPref = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid);

		flGrid = (FrameLayout) findViewById(R.id.flGrid);
		mGridView = (GridView) findViewById(R.id.gridView);
		mGridView.setOnItemClickListener(this);
		loadComponents();
	}

	/**
	 * set up the environment which source should launch.
	 */
	private void loadComponents() {
		Bundle bundle = getIntent().getExtras();
		scrPref = bundle.getString("value");
		if(scrPref.equals(Constatnts.LOCAL_PREF)) {
			launchLocal();
		}else{
			launchSocial();
		}
	}

	/**
	 * launch the social network instagram.
	 */
	private void launchSocial() {

		llSocial = (LinearLayout) findViewById(R.id.llSocial);
		llSocial.setVisibility(View.VISIBLE);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
		mWebView = (WebView) findViewById(R.id.webView1);
		setPropertiesWebView();
	}

	/**
	 * Gets the data from local device.
	 */
	private void launchLocal() {

		flGrid.setVisibility(View.VISIBLE);
		mImageAdapter = new ImageAdapter(this, true);
		mGridView.setAdapter(mImageAdapter);
	}
	
	/**
	 * sets the properties of webview to 
	 * authenticate with the instagram.
	 */
	private void setPropertiesWebView() {

		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(false);
		mWebView.setWebViewClient(new AuthWebViewClient()); 
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl(Constatnts.AUTH_URL_STRING);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {

		String imagePath = null;
		if(scrPref.equals(Constatnts.LOCAL_PREF)) {
			imagePath = Utility.getSelectedImagePath(this, position);
		}else{
			imagePath = InstagramData.getInstance().getThumbnailList().get(position);
		}

		Intent intent = new Intent(this, DetailImageActivity.class);
		intent.putExtra("IMAGE_PATH", imagePath);
		intent.putExtra("sccreen_preff", scrPref);
		startActivity(intent);
	}
	
	/**
	 * Creates a web client for displaying the instagram view.
	 * @author sukesh
	 *
	 */
	public class AuthWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (url.startsWith(Constatnts.CALLBACKURL)) {
				System.out.println(url);
				String parts[] = url.split("=");
				String request_token = parts[1];
				if(request_token != "" || request_token != null) {
					InstagramData.getInstance().setToken(request_token);
					mWebView.setVisibility(View.GONE);
					new NetworkLoader().execute(Constatnts.TOKEN_URL_STRING); 
				}
				return true;
			}
			return false;
		}
	}
	
	/**
	 * Class retrieves the tokens form th instagram API.
	 * @author sukesh
	 *
	 */
	public class NetworkLoader extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			ServiceHandler.getInstance().createAccessToken();
			ServiceHandler.getInstance().createImages();
			return null;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			mProgressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mProgressBar.setVisibility(View.GONE);
			flGrid.setVisibility(View.VISIBLE);
			mImageAdapter = new ImageAdapter(GridActivity.this, InstagramData.getInstance().getThumbnailList(), true);
			mGridView.setAdapter(mImageAdapter);
		}
	}
}
