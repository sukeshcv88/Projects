package com.paypal.imagegrid.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;

public class Utility {

	/**
	 * Query the images.
	 * @param context
	 * @param uri
	 * @param projection
	 * @param selection
	 * @param selectionArgs
	 * @param sortOrder
	 * @return
	 */
	public static Cursor getCursor(Context context, Uri uri, String[] projection, String selection, 
			String[] selectionArgs, String sortOrder) {

		Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
		return cursor;
	}

	/**
	 * Get the count of the images.
	 * @param context
	 * @return
	 */
	public static int getCount(Context context) {
		int count = 0;
		Cursor cursor = null;
		try {
			String[] images = {MediaStore.Images.Thumbnails.DATA};
			cursor = Utility.getCursor(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, images, null, null, null);
			count = cursor.getCount();
		} catch (Exception e) {
		} finally {
			cursor.close();
		}

		return count;
	}

	/**
	 * Getting the path of the image
	 * @param context
	 * @param position
	 * @return
	 */
	public static String getActualPath(Context context, int position) {

		Cursor cursor = null;
		String imageThumb = null;
		try {
			String[] proj = {MediaStore.Images.Thumbnails.DATA};
			cursor = Utility.getCursor(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj, null, null, null);
			int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA);
			cursor.moveToPosition(position);                       					
			imageThumb = cursor.getString(colIndex);    					
		} catch (Exception e) {
		} finally {
			cursor.close();
		}
		return imageThumb;
	}
	
	/**
	 * For displaying the image URI in grid.
	 * @param ctx
	 * @param url
	 * @return
	 */
	public static Drawable imagOperations(String url) {
		try {
			URL imageUrl = new URL(url);
			InputStream is = (InputStream) imageUrl.getContent();
			Drawable d = Drawable.createFromStream(is, "");
			return d;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Retrieves the selected image path from the local.
	 * @param context
	 * @param position
	 * @return
	 */
	public static String getSelectedImagePath(Context context, int position) {
		Cursor cursor = null;
		String imagPath = null;
		try {
			String[] projection = new String[] {MediaStore.Video.Media.DATA};
			cursor = Utility.getCursor(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
			int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToPosition(position);
			imagPath = cursor.getString(colIndex);

		} catch (Exception e) {
		} finally {
			cursor.close();
		}
		return imagPath;
	}

}