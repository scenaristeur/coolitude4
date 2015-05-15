package org.favedave.smag0.coolitude4;

import java.util.ArrayList;
import java.util.HashMap;

import org.favedave.smag0.coolitude4.outils.ServiceHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ConnaissanceProjetActivity extends ListActivity {
	private ProgressDialog pDialog;

	private static final String TAG_SOUS_CLASSES = "results";
	private static final String TAG_RESULTS = "results";
	private static final String TAG_BINDINGS = "bindings";
	private static final String TAG_TYPE = "type";
	private static final String TAG_VALUE = "value";
	private static final String FOAF = "http://xmlns.com/foaf/0.1/";

	String url = "http://dbpedia.org/sparql/?output=json&query=PREFIX+foaf%3A%3Chttp%3A%2F%2Fxmlns.com%2Ffoaf%2F0.1%2F%3E%0APREFIX+rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E%0A+%0A%23%23%23+Vocabularies+contained+in+LOV+and+their+prefix%0ASELECT+DISTINCT+%3FsousClasse+%7B%0A+%09GRAPH+%3Chttp%3A%2F%2Fxmlns.com%2Ffoaf%2F0.1%2F%3E%7B%0A+%09++%3FsousClasse%09rdfs%3AsubClassOf+foaf%3AAgent%0A+%09+%0A%7D%7D+ORDER+BY+%3FsousClasse";
	// String
	// urlProprieteDesSousClasses="http://dbpedia.org/sparql/?default-graph-uri=http%3A%2F%2Fxmlns.com%2Ffoaf%2F0.1%2F&query=PREFIX+foaf%3A%3Chttp%3A%2F%2Fxmlns.com%2Ffoaf%2F0.1%2F%3E%0D%0APREFIX+rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E%0D%0A+%0D%0ASELECT+DISTINCT+%3FsousClasse+%3Fpropriete+%3Fobjet+%7B%0D%0A+%09%0D%0A+%09++%3FsousClasse%09rdfs%3AsubClassOf+foaf%3AAgent.%0D%0A%3FsousClasse+%3Fpropriete+%3Fobjet.%0D%0A%0D%0A+%09+%0D%0A%7D+ORDER+BY+%3FsousClasse&format=application%2Fsparql-results%2Bjson";
	// projets JSONArray
	// JSONArray sousClasses = null;
	TextView textViewClasse;
	TextView textViewPrefix;

	// Hashmap for ListView
	ArrayList<HashMap<String, String>> sousClasseList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_connaissance_projet);
		textViewClasse = (TextView) findViewById(R.id.textViewClasse);
		textViewPrefix = (TextView) findViewById(R.id.textViewPrefix);

		// setContentView(R.layout.activity_projets_derniers100);
		ListView lv = getListView();
		String classe = null;
		String prefix = null;
		if (this.getIntent().getExtras() != null) {
			// recuperer le nom grace à l'identifiant cleNom
			classe = this.getIntent().getExtras().getString("cleClasse");
			prefix = this.getIntent().getExtras().getString("clePrefix");
		}
		textViewClasse.setText(classe);
		textViewPrefix.setText(prefix);

		// Listview on item click listener
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				/*
				 * String idProjet = ((TextView) view
				 * .findViewById(R.id.idProjetTextView)).getText() .toString();
				 * String titre = ((TextView) view.findViewById(R.id.titre))
				 * .getText().toString(); String description = ((TextView) view
				 * .findViewById(R.id.descriptionTextView)).getText()
				 * .toString(); String latitude = ((TextView) view
				 * .findViewById(R.id.latitudeTextView)).getText() .toString();
				 * String longitude = ((TextView) view
				 * .findViewById(R.id.longitudeTextView)).getText() .toString();
				 */
				// Starting single contact activity
				/*
				 * Intent in = new Intent(getApplicationContext(),
				 * SingleProjectActivity.class); in.putExtra(TAG_ID, idProjet);
				 * in.putExtra(TAG_TITRE, titre); in.putExtra(TAG_DESCRIPTION,
				 * description); in.putExtra(TAG_LATITUDE, latitude);
				 * in.putExtra(TAG_LONGITUDE, longitude); startActivity(in);
				 */
			}
		});
		new GetSousClasses().execute();
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class GetSousClasses extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(ConnaissanceProjetActivity.this);
			// affichage d'une pub pendant le chargement ???
			pDialog.setMessage("Recherche des sous classes d'un foaf:Agent...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);

					// Getting JSON Array node
					JSONObject results = jsonObj.getJSONObject(TAG_RESULTS);
					JSONArray sousClasses = results.getJSONArray(TAG_BINDINGS);
					// looping through All Contacts
					for (int i = 0; i < sousClasses.length(); i++) {
						JSONObject classe = sousClasses.getJSONObject(i);
						String type = null;
						String value = null;

						try {
							type = classe.getString(TAG_TYPE);
						} catch (Exception e) {
							type = null;
						}
						try {
							value = classe.getString(TAG_VALUE);
						} catch (Exception e) {
							value = null;
						}

						// tmp hashmap for single contact
						HashMap<String, String> sousClasse = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						sousClasse.put(TAG_TYPE, type);
						sousClasse.put(TAG_VALUE, value);

						// adding contact to contact list
						sousClasseList.add(sousClasse);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			/**
			 * Updating parsed JSON data into ListView
			 * */

			ListAdapter adapter = new SimpleAdapter(
					ConnaissanceProjetActivity.this, sousClasseList,
					R.layout.sousclasse_item, new String[] { TAG_TYPE,
							TAG_VALUE }, new int[] { R.id.TextViewType,
							R.id.TextViewValue });

			setListAdapter(adapter);

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.connaissance_projet, menu);
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
