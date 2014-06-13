package com.paypal.imagegrid;

import com.paypal.imagegrid.utils.Constatnts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity implements OnClickListener {

	private ImageButton imgBtnLocal = null;
	private ImageButton imgBtnSocial = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		imgBtnLocal = (ImageButton) findViewById(R.id.imgbtnLocal);
		imgBtnSocial = (ImageButton) findViewById(R.id.imgBtnSocail);
		imgBtnLocal.setOnClickListener(this);
		imgBtnSocial.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {

		String pref = null;
		if (view.getId() == imgBtnLocal.getId()) {
			pref = Constatnts.LOCAL_PREF;
		}else if (view.getId() == imgBtnSocial.getId()) {
			pref = Constatnts.SOCIAL_PREF;
		}

		Intent intent = new Intent(this, GridActivity.class);
		intent.putExtra("value", pref);
		startActivity(intent);
	}
}
