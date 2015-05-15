package org.favedave.smag0.coolitude4;

import org.apache.http.util.EncodingUtils;
import org.favedave.smag0.coolitude4.ressource.ResourceSmag;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewPostNewResourceActivity extends ActionBarActivity {
	private WebView webView;
	String endpoint_update = "http://fuseki-smag0.rhcloud.com/ds/update";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view_post_new_resource);
		String resourceName = null;
		String classe = null;
		String projetId = null;
		String prefix = null;

		if (this.getIntent().getExtras() != null) {
			// recuperer le nom grace à l'identifiant cleNom
			resourceName = this.getIntent().getExtras()
					.getString("cleResourceName");
			classe = this.getIntent().getExtras().getString("cleClasse");
			projetId = this.getIntent().getExtras().getString("cleProjetId");
			prefix = this.getIntent().getExtras().getString("clePrefix");
		}

		Toast msg = Toast.makeText(
				WebViewPostNewResourceActivity.this.getApplicationContext(),
				"ResourceName + projetId :" + resourceName + " " + projetId
						+ " " + prefix, Toast.LENGTH_LONG);
		// hello.setText(hello.getText() + titre);

		Toast msg1 = Toast.makeText(
				WebViewPostNewResourceActivity.this.getApplicationContext(),
				"classe : " + classe, Toast.LENGTH_LONG);

		msg.show();
		msg1.show();
		/*
		 * Projet projetAjoute = new Projet(null, titre, descriptionCourte,
		 * longitude, longitude, 0); projetAjoute.setTitre(titre);
		 * projetAjoute.setDescriptionCourte(descriptionCourte);
		 * projetAjoute.setLatitude(latitude);
		 * projetAjoute.setLongitude(longitude); projetAjoute.setEndpoint(); //
		 * projetAjoute.verifieEndpoint(); String requete =
		 * projetAjoute.constructionRequete();
		 */
		ResourceSmag resourceAjoute = new ResourceSmag();
		resourceAjoute.setResourceName(resourceName);
		resourceAjoute.setClasse(classe);
		resourceAjoute.setProjetId(projetId);
		resourceAjoute.setPrefix(prefix);
		String requete = resourceAjoute.constructionRequete();

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
