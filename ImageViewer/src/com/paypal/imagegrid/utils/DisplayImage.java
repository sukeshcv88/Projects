package com.paypal.imagegrid.utils;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

/**
 * Background process for displaying the image.
 * @author sukesh
 *
 */
public class DisplayImage extends AsyncTask<String, String, Drawable> {

		ImageView imgView = null;
		String url = null;
		Drawable drawable = null;
		
		public DisplayImage(String url, ImageView imgView) {
			
			this.url = url;
			this.imgView = imgView;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.i("SUKESH", "WAIT");
		}
		@Override
		protected Drawable doInBackground(String... params) {
			drawable = Utility.imagOperations(this.url);
			return drawable;
		}
		
		@Override
		protected void onPostExecute(Drawable result) {
			super.onPostExecute(result);
			this.imgView.setImageDrawable(result);
		}
	}