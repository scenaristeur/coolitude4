package org.favedave.smag0.coolitude4;

//http://www.androidhive.info/2012/01/android-json-parsing-tutorial/
import java.util.ArrayList;
import java.util.HashMap;

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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ProjetsDerniers100Activity extends ListActivity {
	private ProgressDialog pDialog;

	// URL to get contacts JSON
	private static String url = "http://fuseki-smag0.rhcloud.com/ds/query?query=PREFIX+geo%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2003%2F01%2Fgeo%2Fwgs84_pos%23%3E%0D%0A%0D%0APREFIX+smag%3A+++%3Chttp%3A%2F%2Fsmag0.blogspot.fr%2Fns%2Fsmag0%23%3E+%0D%0A%0D%0Aselect+%3Fprojet+%3Ftitre+%3Fdescription+%3Flat+%3Flon+where+%7B%0D%0A%0D%0A%3Fprojet+%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23type%3E+smag%3AProjet+.+%0D%0A%0D%0AOPTIONAL+%7B%3Fprojet+%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Felements%2F1.1%2Ftitle%3E+%3Ftitre+%7D+%0D%0A%0D%0A+OPTIONAL+%7B%3Fprojet+%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Felements%2F1.1%2Fdescription%3E+%3Fdescription+%7D+%0D%0A%0D%0AOPTIONAL+%7B%3Fprojet+smag%3Adescription+%3Fdescription+%7D+%0D%0A%0D%0AOPTIONAL+%7B+%3Fprojet+geo%3Alat+%3Flat+%7D+%0D%0A%0D%0A+OPTIONAL+%7B+%3Fprojet+geo%3Along+%3Flon+%7D+%0D%0A%0D%0A+OPTIONAL+%7B+%3Fprojet+smag%3Alatitude+%3Flat+%7D+%0D%0A%09%09%09%09%0D%0A+OPTIONAL+%7B+%3Fprojet+smag%3Alongitude+%3Flon+%7D+%0D%0A%09%09%09%09%0D%0A%7D%0D%0A%09%09%09%09%09%0D%0AORDER+BY+DESC%28%3Fprojet%29&output=json";

	// JSON Node names
	private static final String TAG_ID = "projet";
	private static final String TAG_PROJETS = "projets";
	private static final String TAG_TITRE = "titre";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_LATITUDE = "lat";
	private static final String TAG_LONGITUDE = "lon";
	private static final String TAG_RESULTS = "results";
	private static final String TAG_BINDINGS = "bindings";
	private static final String TAG_VALUE = "value";

	// projets JSONArray
	// JSONArray projets = null;

	// Hashmap for ListView
	ArrayList<HashMap<String, String>> projetList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_projets_derniers100);

		projetList = new ArrayList<HashMap<String, String>>();

		ListView lv = getListView();

		// Listview on item click listener
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String idProjet = ((TextView) view
						.findViewById(R.id.idProjetTextView)).getText()
						.toString();
				String titre = ((TextView) view.findViewById(R.id.titre))
						.getText().toString();
				String description = ((TextView) view
						.findViewById(R.id.descriptionTextView)).getText()
						.toString();
				String latitude = ((TextView) view
						.findViewById(R.id.latitudeTextView)).getText()
						.toString();
				String longitude = ((TextView) view
						.findViewById(R.id.longitudeTextView)).getText()
						.toString();
				// Starting single contact activity
				Intent in = new Intent(getApplicationContext(),
						SingleProjectActivity.class);
				in.putExtra(TAG_ID, idProjet);
				in.putExtra(TAG_TITRE, titre);
				in.putExtra(TAG_DESCRIPTION, description);
				in.putExtra(TAG_LATITUDE, latitude);
				in.putExtra(TAG_LONGITUDE, longitude);
				startActivity(in);

			}
		});
		new GetProjets().execute();
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
	private class GetProjets extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(ProjetsDerniers100Activity.this);
			// affichage d'une pub pendant le chargement ???
			pDialog.setMessage("Chargement des projets, merci de patienter quelques instants, de toute façon, tu n'as pas le choix ;-)...");
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
					JSONArray projets = results.getJSONArray(TAG_BINDINGS);
					// looping through All Contacts
					for (int i = 0; i < projets.length(); i++) {
						JSONObject p = projets.getJSONObject(i);
						String id = null;
						String titre = null;
						String description = null;
						float latitude = 0.0000000f;
						float longitude = 0.0000000f;
						try {
							id = p.getJSONObject(TAG_ID).getString(TAG_VALUE);
						} catch (Exception e) {
							id = null;
						}
						try {
							titre = p.getJSONObject(TAG_TITRE).getString(
									TAG_VALUE);
						} catch (Exception e) {
							titre = null;
						}
						try {
							description = p.getJSONObject(TAG_DESCRIPTION)
									.getString(TAG_VALUE);
						} catch (Exception e) {
							description = null;
						}
						try {
							latitude = p.getJSONObject(TAG_LATITUDE).getLong(
									TAG_VALUE);
							Log.v(TAG_LATITUDE, String.valueOf(latitude));
						} catch (Exception e) {
							latitude = 0;
						}
						try {
							longitude = p.getJSONObject(TAG_LONGITUDE).getLong(
									TAG_VALUE);
						} catch (Exception e) {
							longitude = 0;
						}

						// tmp hashmap for single contact
						HashMap<String, String> projet = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						projet.put(TAG_ID, id);
						projet.put(TAG_TITRE, titre);
						projet.put(TAG_DESCRIPTION, description);
						projet.put(TAG_LATITUDE, String.valueOf(latitude));
						projet.put(TAG_LONGITUDE, String.valueOf(longitude));

						// adding contact to contact list
						projetList.add(projet);
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
					ProjetsDerniers100Activity.this, projetList,
					R.layout.projet_item, new String[] { TAG_ID, TAG_TITRE,
							TAG_DESCRIPTION, TAG_LATITUDE, TAG_LONGITUDE },
					new int[] { R.id.idProjetTextView, R.id.titre,
							R.id.descriptionTextView, R.id.latitudeTextView,
							R.id.longitudeTextView });

			setListAdapter(adapter);
		}
	}

}
