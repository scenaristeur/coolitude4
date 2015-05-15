package org.favedave.smag0.coolitude4;

// voir avec EVENT ontologie : http://motools.sourceforge.net/event/event.html#
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.favedave.smag0.coolitude4.outils.L;
import org.favedave.smag0.coolitude4.outils.ServiceHandler;
import org.favedave.smag0.coolitude4.sparql.Requete;
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

public class Action extends ListActivity {
	private ProgressDialog pDialog;
	String prefix = null;
	String classe = null;
	String projetId = null;
	String endpoint = null;
	String action = null;
	String url = null;
	String classeValue = null;
	TextView textViewClasse;
	EditText editTextResourceName;
	Button buttonAjouteRessource;
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	private static final String TAG_RESULTS = "results";
	private static final String TAG_BINDINGS = "bindings";
	private static final String TAG_VALUE = "value";
	private static final String TAG_TYPE = "type";
	private static final String TAG_NAMESPACE = null;

	ArrayList<HashMap<String, String>> sousClasseList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_action);

		textViewClasse = (TextView) findViewById(R.id.textViewClasse);
		editTextResourceName = (EditText) findViewById(R.id.editTextResourceName);
		buttonAjouteRessource = (Button) findViewById(R.id.buttonAjouteRessource);

		if (this.getIntent().getExtras() != null) {
			// recuperer le nom grace à l'identifiant cleNom
			classe = this.getIntent().getExtras().getString("cleClasse");
			prefix = this.getIntent().getExtras().getString("clePrefix");
			projetId = this.getIntent().getExtras().getString("cleProjetId");
			endpoint = this.getIntent().getExtras().getString("cleEndpoint");
			action = this.getIntent().getExtras().getString("cleAction");
			if (action.equals("simple")) {
				// pas besoin de creer la requete;
			} else {
				// Construire la requete plus proprement !!! c'est n'importe
				// NAWAK
				// !!!
				Requete requete = new Requete();
				// TESTER VOIR SI LE ENDPOINT EST DISPO , OU MEME LE TESTER
				// AVANT,
				// rechercher si un autre est disponible
				// requete.setEndpoint(endpoint);
				requete.setClasse(classe);
				requete.setPrefix(prefix);
				Log.d("Action >", action);
				requete.addAction(action);
				String requeteConstruite = requete.getRequeteConstruite();
				/*
				 * String requeteConstruite =
				 * "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
				 * "select distinct ?" + classe + " where {?" + classe +
				 * " rdfs:subClassOf <http://dbpedia.org/ontology/" + classe +
				 * ">} LIMIT 100";
				 */
				url = endpoint;
				// params.add(new BasicNameValuePair("output", "json"));
				params.add(new BasicNameValuePair("format", "json"));
				String requeteEncode = null;
				Log.d("requeteConstruite >", requeteConstruite);
				/*
				 * try { requeteEncode = URLEncoder.encode(requeteConstruite,
				 * "UTF-8"); } catch (UnsupportedEncodingException e1) {
				 * e1.printStackTrace(); Log.d("EncodeRequete >",
				 * "Pb pour Encoder la requete"); } Log.d("EncodeRequete >",
				 * requeteEncode);
				 */
				params.add(new BasicNameValuePair("query", requeteConstruite));
				Log.d("Requete >", url);
			}
		}
		textViewClasse.setText(classe);

		sousClasseList = new ArrayList<HashMap<String, String>>();

		ListView lv = getListView();
		// Listview on item click listener

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) { // getting values from selected
												// ListItem
				L.s(Action.this, "position :" + id);
				classeValue = sousClasseList.get(position).get(TAG_VALUE);
				prefix = sousClasseList.get(position).get(TAG_NAMESPACE);
				Log.d("clic on >", classeValue);
				/*
				 * String TextViewValue = ((TextView)
				 * findViewById(R.id.TextViewValue)) .getText().toString();
				 * String TextViewType = ((TextView)
				 * findViewById(R.id.TextViewType)) .getText().toString();
				 * String TextViewNS = ((TextView)
				 * findViewById(R.id.TextViewNS)) .getText().toString();
				 * Log.d("clic on >", TextViewValue);
				 */
				// Starting single contact activity
				Intent in = new Intent(getApplicationContext(), Action.class);
				// in.putExtra("clePrefix", TextViewNS);
				in.putExtra("cleClasse", classeValue);
				in.putExtra("cleProjetId", projetId);
				in.putExtra("clePrefix", prefix);
				in.putExtra("cleAction", "getSousClasses");
				in.putExtra("cleEndpoint", endpoint);
				startActivity(in);

			}
		});
		if (!action.equals("simple")) {
			new ExecuteRequete().execute();
		}
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class ExecuteRequete extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.d("PREEXECUTE Requete >", url);
			// Showing progress dialog
			pDialog = new ProgressDialog(Action.this);
			// affichage d'une pub pendant le chargement ???
			pDialog.setMessage("Chargement des Classes...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			Log.d("DOINBACKGROUND Requete >", url);

			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh
					.makeServiceCall(url, ServiceHandler.GET, params);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);

					// Getting JSON Array node
					JSONObject results = jsonObj.getJSONObject(TAG_RESULTS);
					JSONArray bindings = results.getJSONArray(TAG_BINDINGS);
					// looping through All Contacts
					for (int i = 0; i < bindings.length(); i++) {
						JSONObject c = bindings.getJSONObject(i);
						Log.d("Classe: ", "> " + c);
						String type = null;
						String value = null;
						// String namespace = null;
						try {
							type = c.getJSONObject(classe).getString(TAG_TYPE);
						} catch (Exception e) {
							type = null;
						}
						try {
							String sousClasseResource = c.getJSONObject(classe)
									.getString(TAG_VALUE);
							// suppresion du prefix
							String[] parts = sousClasseResource.split(prefix);
							// namespace = parts[0]; // NS
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
						classe.put(TAG_NAMESPACE, prefix);

						// adding contact to contact list
						sousClasseList.add(classe);
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
			Log.d("POSTEXECUTE Requete >", url);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			/**
			 * Updating parsed JSON data into ListView
			 * */
			ListAdapter adapter = new SimpleAdapter(Action.this,
					sousClasseList, R.layout.sousclasse_item, new String[] {
							TAG_TYPE, TAG_VALUE, TAG_NAMESPACE }, new int[] {
							R.id.TextViewType, R.id.TextViewValue,
							R.id.TextViewNS });

			setListAdapter(adapter);
		}
	}

	public void ajouterLaResource(View v) {
		String resourceName = ((TextView) findViewById(R.id.editTextResourceName))
				.getText().toString();
		L.s(Action.this, "Ajoute la Ressource cliqué" + classe + " "
				+ resourceName);

		Intent in = new Intent(getApplicationContext(),
				WebViewPostNewResourceActivity.class);
		in.putExtra("cleResourceName", resourceName);
		in.putExtra("cleClasse", classe);
		// in.putExtra("clePrefix", TextViewNS);
		in.putExtra("clePrefix", prefix);
		in.putExtra("cleProjetId", projetId);
		startActivity(in);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.action, menu);
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
