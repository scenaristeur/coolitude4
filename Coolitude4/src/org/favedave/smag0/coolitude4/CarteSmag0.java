package org.favedave.smag0.coolitude4;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CarteSmag0 extends ActionBarActivity {
	/**
	 * WebViewClient subclass loads all hyperlinks in the existing WebView
	 */
	public class GeoWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// When user clicks a hyperlink, load in the existing WebView
			view.loadUrl(url);
			return true;
		}
	}

	/**
	 * WebChromeClient subclass handles UI-related calls Note: think chrome as
	 * in decoration, not the Chrome browser
	 */
	public class GeoWebChromeClient extends WebChromeClient {
		@Override
		public void onGeolocationPermissionsShowPrompt(String origin,
				GeolocationPermissions.Callback callback) {
			// Always grant permission since the app itself requires location
			// permission and the user has therefore already granted it
			callback.invoke(origin, true, false);
		}
	}

	WebView mWebView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_carte_smag0);
		mWebView = (WebView) findViewById(R.id.webView1);
		// Brower niceties -- pinch / zoom, follow links in place
		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.setWebViewClient(new GeoWebViewClient());
		// Below required for geolocation
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setGeolocationEnabled(true);
		mWebView.setWebChromeClient(new GeoWebChromeClient());
		// Load maps
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		int width = display.getWidth();
		int height = display.getHeight();
		mWebView.loadUrl("http://smag-smag0.rhcloud.com/maps/smag0-maps.jsp?width="
				+ width + "&height=" + height);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		int width = mWebView.getWidth();
		int height = mWebView.getHeight();
		width -= 10;
		height -= 10;
		mWebView.loadUrl("http://smag-smag0.rhcloud.com/maps/smag0-maps.jsp?width="
				+ width + "&height=" + height);
	}

	@Override
	public void onBackPressed() {
		// Pop the browser back stack or exit the activity
		if (mWebView.canGoBack()) {
			mWebView.goBack();
		} else {
			super.onBackPressed();
		}
	}

	/*
	 * @Override protected void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState);
	 * setContentView(R.layout.activity_carte_smag0); }
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.carte_smag0, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
