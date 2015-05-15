package org.favedave.smag0.coolitude4;

//http://www.androidhive.info/2012/01/android-json-parsing-tutorial/
//http://www.w3.org/wiki/SparqlEndpoints
//http://linkedgeodata.org/
import java.util.ArrayList;
import java.util.HashMap;

import org.favedave.smag0.coolitude4.outils.L;
import org.favedave.smag0.coolitude4.outils.ServiceHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AjouteClassePropriete extends ListActivity {
	private ProgressDialog pDialog;

	// URL to get contacts JSON
	String url = "http://dbpedia.org/sparql/?output=json&query=PREFIX+foaf%3A%3Chttp%3A%2F%2Fxmlns.com%2Ffoaf%2F0.1%2F%3E%0APREFIX+rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E%0A+%0A%23%23%23+Vocabularies+contained+in+LOV+and+their+prefix%0ASELECT+DISTINCT+%3FsousClasse+%7B%0A+%09GRAPH+%3Chttp%3A%2F%2Fxmlns.com%2Ffoaf%2F0.1%2F%3E%7B%0A+%09++%3FsousClasse%09rdfs%3AsubClassOf+foaf%3AAgent%0A+%09+%0A%7D%7D+ORDER+BY+%3FsousClasse";

	// JSON Node names
	private static final String TAG_ID = "id";
	private static final String TAG_PROJETS = "projets";
	/*
	 * private static final String TAG_TITRE = "titre"; private static final
	 * String TAG_DESCRIPTION = "description"; private static final String
	 * TAG_LATITUDE = "lat"; private static final String TAG_LONGITUDE = "lon";
	 */
	private static final String TAG_RESULTS = "results";
	private static final String TAG_BINDINGS = "bindings";
	private static final String TAG_VALUE = "value";
	private static final String TAG_TYPE = "type";
	private static final String TAG_SOUSCLASSE = "sousClasse";
	private static final String FOAF = "http://xmlns.com/foaf/0.1/";
	private static final String TAG_NAMESPACE = "NS";
	private String Namespace = "test";
	TextView textViewClasse;
	EditText editTextResourceName;
	Button buttonAjouteRessource;
	String classeValue = null;
	String classe = null;
	String projetID = null;
	String prefix = null;
	// projets JSONArray
	// JSONArray projets = null;

	// Hashmap for ListView
	ArrayList<HashMap<String, String>> projetList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ajoute_classe_propriete);
		textViewClasse = (TextView) findViewById(R.id.textViewClasse);
		editTextResourceName = (EditText) findViewById(R.id.editTextResourceName);
		buttonAjouteRessource = (Button) findViewById(R.id.buttonAjouteRessource);

		if (this.getIntent().getExtras() != null) {
			// recuperer le nom grace à l'identifiant cleNom
			classe = this.getIntent().getExtras().getString("cleClasse");
			prefix = this.getIntent().getExtras().getString("clePrefix");
			projetID = this.getIntent().getExtras().getString("cleProjetId");
			// Construire la requete plus proprement !!! c'est n'importe NAWAK
			// !!!
			url = "http://dbpedia.org/sparql/?output=json&query=PREFIX+"
					+ "foaf%3A%3Chttp%3A%2F%2Fxmlns.com%2Ffoaf%2F0.1%2F%3E%0A"
					+ "PREFIX+rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E%0A+%0A%23%23%23+"
					+ "Vocabularies+contained+in+LOV+and+their+prefix%0A"
					+ "SELECT+DISTINCT+%3FsousClasse+%7B%0A+%09GRAPH+%3Chttp%3A%2F%2Fxmlns.com%2F"
					+ "foaf%2F0.1%2F%3E%7B%0A+%09++%3FsousClasse%09rdfs%3AsubClassOf+foaf%3A"
					+ classe + "%0A+%09+%0A%7D%7D+" + "ORDER+BY+%3FsousClasse";
			Log.d("Requete >", url);
		}
		textViewClasse.setText(classe);

		projetList = new ArrayList<HashMap<String, String>>();

		ListView lv = getListView();

		// Listview on item click listener
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				L.s(AjouteClassePropriete.this, "position :" + id);
				classeValue = projetList.get(position).get(TAG_VALUE);
				Log.d("clic on >", classeValue);
				String TextViewValue = ((TextView) findViewById(R.id.TextViewValue))
						.getText().toString();
				String TextViewType = ((TextView) findViewById(R.id.TextViewType))
						.getText().toString();
				String TextViewNS = ((TextView) findViewById(R.id.TextViewNS))
						.getText().toString();
				Log.d("clic on >", TextViewValue);
				// Starting single contact activity
				Intent in = new Intent(getApplicationContext(),
						AjouteClassePropriete.class);
				in.putExtra("clePrefix", TextViewNS);
				in.putExtra("cleClasse", classeValue);
				in.putExtra("cleProjetId", projetID);
				startActivity(in);

			}
		});

		new GetClasses().execute();
	}

	public void ajouterLaResource(View v) {
		String resourceName = ((TextView) findViewById(R.id.editTextResourceName))
				.getText().toString();
		L.s(AjouteClassePropriete.this, "Ajoute la Ressource cliqué" + classe
				+ " " + resourceName);

		Intent in = new Intent(getApplicationContext(),
				WebViewPostNewResourceActivity.class);
		in.putExtra("cleResourceName", resourceName);
		in.putExtra("cleClasse", classe);
		// in.putExtra("clePrefix", TextViewNS);
		in.putExtra("clePrefix", prefix);
		in.putExtra("cleProjetId", projetID);
		startActivity(in);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.projets_derniers100, menu);
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

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class GetClasses extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(AjouteClassePropriete.this);
			// affichage d'une pub pendant le chargement ???
			pDialog.setMessage("Chargement des Classes...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			// Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);

					// Getting JSON Array node
					JSONObject results = jsonObj.getJSONObject(TAG_RESULTS);
					JSONArray classes = results.getJSONArray(TAG_BINDINGS);
					// looping through All Contacts
					for (int i = 0; i < classes.length(); i++) {
						JSONObject c = classes.getJSONObject(i);
						Log.d("Classe: ", "> " + c);
						String type = null;
						String value = null;
						String namespace = null;
						try {
							type = c.getJSONObject(TAG_SOUSCLASSE).getString(
									TAG_TYPE);
						} catch (Exception e) {
							type = null;
						}
						try {
							String sousClasseResource = c.getJSONObject(
									TAG_SOUSCLASSE).getString(TAG_VALUE);
							// suppresion du prefix
							String[] parts = sousClasseResource.split(FOAF);
							namespace = parts[0]; // NS
							String SousClasseNom = parts[1]; // ProjetID
							Log.d("Classe Value: ", "> " + SousClasseNom);
							value = SousClasseNom;
						} catch (Exception e) {
							value = null;
						}

						// tmp hashmap for single contact
						HashMap<String, String> classe = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						classe.put(TAG_TYPE, type);
						classe.put(TAG_VALUE, value);
						classe.put(TAG_NAMESPACE, namespace);

						// adding contact to contact list
						projetList.add(classe);
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
			ListAdapter adapter = new SimpleAdapter(AjouteClassePropriete.this,
					projetList, R.layout.sousclasse_item, new String[] {
							TAG_TYPE, TAG_VALUE, TAG_NAMESPACE }, new int[] {
							R.id.TextViewType, R.id.TextViewValue,
							R.id.TextViewNS });

			setListAdapter(adapter);
		}
	}

}
