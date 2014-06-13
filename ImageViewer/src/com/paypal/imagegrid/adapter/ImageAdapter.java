package com.paypal.imagegrid.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.paypal.imagegrid.R;
import com.paypal.imagegrid.utils.DisplayImage;
import com.paypal.imagegrid.utils.Utility;
/**
 * Adapter for displaying the images on grid view.
 * @author sukesh
 *
 */
public class ImageAdapter extends BaseAdapter {

	private Context context = null;
	private ArrayList<String> listUrl = null;

	private int count = 0;
	private boolean isLocal = false;

	public ImageAdapter(Context context, boolean isLocal) {
		this.isLocal = isLocal;
		this.context = context;
	}

	public ImageAdapter(Context context, ArrayList<String> listUrl, boolean social) {
		this.context = context;
		this.listUrl = listUrl;
	}

	@Override
	public int getCount() {
		if(this.isLocal)
			count = Utility.getCount(context);
		else
			count = this.listUrl.size();
		return count;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {

		ImageView imgView = null;
		View gridView = null;
		Drawable drawable = null;
		String imageThumb = null;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if(this.isLocal)
			imageThumb = Utility.getActualPath(context, position);

		if (convertView == null) {
			gridView = new View(context);
			gridView = inflater.inflate(R.layout.grid_adapter, null);
			imgView = (ImageView) gridView.findViewById(R.id.picture);
			
			if(this.isLocal) {
				drawable = Drawable.createFromPath(imageThumb);
				imgView.setImageDrawable(drawable);
			}else{
				new DisplayImage(listUrl.get(position), imgView).execute();
			}
		} else {
			gridView = (View) convertView;
		}
		return gridView;
	}
}