package org.favedave.smag0.coolitude4;

import org.apache.http.util.EncodingUtils;
import org.favedave.smag0.coolitude4.projet.Projet;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewPostNewProject extends ActionBarActivity {
	private WebView webView;
	String endpoint_update = "http://fuseki-smag0.rhcloud.com/ds/update";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view_post_new_project);
		String titre = null;
		String descriptionCourte = null;
		float latitude = 0;
		float longitude = 0;
		if (this.getIntent().getExtras() != null) {
			// recuperer le nom grace à l'identifiant cleNom
			titre = this.getIntent().getExtras().getString("cleTitre");
			descriptionCourte = this.getIntent().getExtras()
					.getString("cleDescriptionCourte");
			latitude = this.getIntent().getExtras().getFloat("cleLatitude");
			longitude = this.getIntent().getExtras().getFloat("cleLongitude");
		}

		Toast msg = Toast.makeText(
				WebViewPostNewProject.this.getApplicationContext(), "Titre :"
						+ titre, Toast.LENGTH_LONG);
		// hello.setText(hello.getText() + titre);

		Toast msg1 = Toast.makeText(
				WebViewPostNewProject.this.getApplicationContext(),
				"description : " + descriptionCourte, Toast.LENGTH_LONG);
		Toast msg2 = Toast.makeText(
				WebViewPostNewProject.this.getApplicationContext(),
				"latitude : " + latitude, Toast.LENGTH_LONG);
		Toast msg3 = Toast.makeText(
				WebViewPostNewProject.this.getApplicationContext(),
				"longitude : " + longitude, Toast.LENGTH_LONG);
		// hello.setText(hello.getText() + descriptionCourte);
		msg.show();
		msg1.show();
		msg2.show();
		msg3.show();
		Projet projetAjoute = new Projet(null, titre, descriptionCourte,
				longitude, longitude, 0);
		projetAjoute.setTitre(titre);
		projetAjoute.setDescriptionCourte(descriptionCourte);
		projetAjoute.setLatitude(latitude);
		projetAjoute.setLongitude(longitude);
		projetAjoute.setEndpoint();
		// projetAjoute.verifieEndpoint();
		String requete = projetAjoute.constructionRequete();
		// webview
		// instantiate the webview
		this.webView = new WebView(this);
		setContentView(this.webView);

		String postData = "update=" + requete;

		// This is the section of code that fixes redirects to external apps
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}
		});

		webView.postUrl(endpoint_update,
				EncodingUtils.getBytes(postData, "BASE64"));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.web_view_post_new_project, menu);
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
