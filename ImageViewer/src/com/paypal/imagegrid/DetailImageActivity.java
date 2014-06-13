package com.paypal.imagegrid;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.paypal.imagegrid.data.InstagramData;
import com.paypal.imagegrid.utils.Constatnts;
import com.paypal.imagegrid.utils.DisplayImage;

/**
 * Displaying full image in a screen.
 * @author sukesh
 *
 */
public class DetailImageActivity extends Activity {

	private ImageView imgView = null;
	private TextView txtView = null;
	
	private Bundle bundle = null;
	private String imagePath = null;
	private String pref = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		bundle = getIntent().getExtras();
		imagePath = bundle.getString("IMAGE_PATH");
		pref = bundle.getString("sccreen_preff");
		
		imgView = (ImageView) findViewById(R.id.imgDetail);
		txtView = (TextView) findViewById(R.id.textView1);
		if(pref.equals(Constatnts.LOCAL_PREF)) {
			imgView.setImageURI(Uri.parse(imagePath));
		}else{
			txtView.setText("Username: " +InstagramData.getInstance().getUserName());
			new DisplayImage(imagePath, imgView).execute();
		}
	}

}
