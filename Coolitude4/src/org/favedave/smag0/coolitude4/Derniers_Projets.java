package org.favedave.smag0.coolitude4;

//https://www.youtube.com/watch?v=dVwR5Gpw1_E
//https://www.youtube.com/watch?v=WRANgDgM2Zg
//http://www.androidhive.info/2012/09/android-adding-search-functionality-to-listview/
//http://stackoverflow.com/questions/9605913/how-to-parse-json-in-android
//http://stackoverflow.com/questions/4538338/progressdialog-in-asynctask

//Fonctionne avec l'émulateur mais pas avec le telephone--> utiliser asynctask, doinbackground

// a virer ListView lv (simple) 

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.favedave.smag0.coolitude4.outils.L;
import org.favedave.smag0.coolitude4.projet.Projet;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Derniers_Projets extends ActionBarActivity {
	private static final String TAG = "Derniers_Projets";
	private ListView lv;
	ArrayAdapter<String> adapter;
	EditText inputSearch;
	TextWatcher textwatcher;
	private List<Projet> projets = new ArrayList<Projet>();

	// private User user;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_derniers__projets);
		setProgressBarIndeterminateVisibility(true);
		setProgressBarVisibility(true);
		lv = (ListView) findViewById(R.id.derniersProjetslist_view);
		adapter = new ArrayAdapter<String>(Derniers_Projets.this,
				R.layout.list_item, R.id.product_name, new ArrayList<String>());
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {
			private String racine = "http://smag-smag0.rhcloud.com/projet.jsp?projet=";

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				L.s(Derniers_Projets.this, "position :" + id);
				String url = projets.get(position).getURI();
				String[] parts = url.split("#");
				String Namespace = parts[0]; // NS
				String projectID = parts[1]; // ProjetID
				String link = racine + projectID;
				// Toast.makeText(Derniers_Projets.this, link,
				// Toast.LENGTH_SHORT) .show();
				try {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
							.parse(link));
					startActivity(browserIntent);
				} catch (Exception e) {
					e.printStackTrace();
					L.s(Derniers_Projets.this, e.toString());
				}
			}
		});

		inputSearch = (EditText) findViewById(R.id.inputSearch);
		inputSearch.addTextChangedListener(textwatcher = new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence cs, int start,
					int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence cs, int start, int before,
					int count) {
				Derniers_Projets.this.adapter.getFilter().filter(cs);
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		new RemplissageListe().execute();
	}

	private class ProjetListAdapter extends ArrayAdapter<Projet> implements
			Filterable {
		public ProjetListAdapter() {
			super(Derniers_Projets.this, R.layout.projet_view, projets);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// Make sure we have a view to work with
			View itemView = convertView;
			if (itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.projet_view,
						parent, false);
			}
			// find the projet to work with
			Projet currentProjet = projets.get(position);
			// fill the View
			ImageView imageView = (ImageView) itemView
					.findViewById(R.id.projet_icon);
			imageView.setImageResource(currentProjet.getIconId());
			TextView titreProjetText = (TextView) itemView
					.findViewById(R.id.projet_textTitreProjet);
			titreProjetText.setText(currentProjet.getTitre());
			TextView descriptionProjetText = (TextView) itemView
					.findViewById(R.id.projet_textDescription);
			descriptionProjetText.setText(currentProjet.getDescription());
			TextView IDProjetText = (TextView) itemView
					.findViewById(R.id.projet_textDistance);
			IDProjetText.setText(currentProjet.getLink());
			return itemView;

		}
	}

	private void populateListView() {
		L.s(Derniers_Projets.this, "populateListView");
		ArrayAdapter<Projet> adapter = new ProjetListAdapter();
		ListView list = (ListView) findViewById(R.id.derniersProjetslist_view);
		list.setAdapter(adapter);
	}

	class RemplissageListe extends AsyncTask<Void, String, Void> {
		// private ArrayAdapter<String> adapter1;
		private int count = 0;

		@Override
		protected void onPreExecute() {
			L.s(Derniers_Projets.this, "Chargement des projets");
			// adapter1 = (ArrayAdapter<String>) lv.getAdapter();
			// setProgressBarIndeterminate(false);
			// setProgressBarVisibility(true);
			// utiliser pour la liste paramétrée
			// populateProjeList();
		}

		@Override
		protected Void doInBackground(Void... params) {
			Log.v(TAG, "doinbackground");
			String str = "http://fuseki-smag0.rhcloud.com/ds/query?query=PREFIX+geo%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2003%2F01%2Fgeo%2Fwgs84_pos%23%3E%0D%0A%0D%0APREFIX+smag%3A+++%3Chttp%3A%2F%2Fsmag0.blogspot.fr%2Fns%2Fsmag0%23%3E+%0D%0A%0D%0Aselect+%3Fprojet+%3Ftitre+%3Fdescription+%3Flat+%3Flon+where+%7B%0D%0A%0D%0A%3Fprojet+%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23type%3E+smag%3AProjet+.+%0D%0A%0D%0AOPTIONAL+%7B%3Fprojet+%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Felements%2F1.1%2Ftitle%3E+%3Ftitre+%7D+%0D%0A%0D%0A+OPTIONAL+%7B%3Fprojet+%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Felements%2F1.1%2Fdescription%3E+%3Fdescription+%7D+%0D%0A%0D%0AOPTIONAL+%7B%3Fprojet+smag%3Adescription+%3Fdescription+%7D+%0D%0A%0D%0AOPTIONAL+%7B+%3Fprojet+geo%3Alat+%3Flat+%7D+%0D%0A%0D%0A+OPTIONAL+%7B+%3Fprojet+geo%3Along+%3Flon+%7D+%0D%0A%0D%0A+OPTIONAL+%7B+%3Fprojet+smag%3Alatitude+%3Flat+%7D+%0D%0A%09%09%09%09%0D%0A+OPTIONAL+%7B+%3Fprojet+smag%3Alongitude+%3Flon+%7D+%0D%0A%09%09%09%09%0D%0A%7D%0D%0A%09%09%09%09%09%0D%0AORDER+BY+DESC%28%3Fprojet%29&output=json";
			Log.v(TAG, str);

			try {
				URL url = new URL(str);
				URLConnection urlc = url.openConnection();
				BufferedReader bfr = new BufferedReader(new InputStreamReader(
						urlc.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				String result = null;
				while ((line = bfr.readLine()) != null) {
					// Log.v(TAG, "line " + line);
					sb.append(line + "\n");
				}
				result = sb.toString();
				JSONObject jObject = new JSONObject(result);
				JSONObject jObjectresults = jObject.getJSONObject("results");
				JSONArray jObjectBindings = jObjectresults
						.getJSONArray("bindings");
				Log.v(TAG, "json1 " + jObjectBindings.length());

				for (int i = 0; i < jObjectBindings.length(); i++) {
					JSONObject json_data = jObjectBindings.getJSONObject(i);
					Projet projet = new Projet();
					projets.add(projet);
					String uriProject = null;
					String titre = null;
					String description = null;
					float lat = 0.f;
					float lon = 0.f;
					// ArrayMap<String, Comparable> projetsList = new
					// ArrayMap<String, Comparable>();

					try {
						uriProject = json_data.getJSONObject("projet")
								.getString("value");
					} catch (Exception e) {
						uriProject = "smag0:--vide--";
					}
					try {
						titre = json_data.getJSONObject("titre").getString(
								"value");
					} catch (Exception e) {
						titre = "smag0:--vide--";
					}
					try {
						description = json_data.getJSONObject("description")
								.getString("value");
					} catch (Exception e) {
						description = "smag0:--vide--";
					}
					try {
						lat = json_data.getJSONObject("lat").getLong("value");
					} catch (Exception e) {
						lat = 0;
					}
					try {
						lon = json_data.getJSONObject("lon").getLong("value");
					} catch (Exception e) {
						lon = 0;
					}
					projet.setURI(uriProject);
					projet.setTitre(titre);
					projet.setDescriptionCourte(description);
					projet.setLatitude(lat);
					projet.setLongitude(lon);

					/*
					 * projetsList.put("projet", projet);
					 * projetsList.put("titre", titre);
					 * projetsList.put("description", description);
					 * projetsList.put("lat", lat); projetsList.put("lon", lon);
					 */
					/*
					 * } catch (Exception e) { Log.w(TAG, e.getMessage()); }
					 */
					// URI projetURI = new URI(projet);
					// projets.add(new Projet(projetURI, titre, description,
					// lat, lon, 0));

					/*
					 * if (titre != null) { itemsJSON.add(titre);
					 * 
					 * // Log.d(titre, "Output"); } else { Log.d(TAG,
					 * "pas de titre"); }
					 */

				}
				Log.d(TAG, "taille projets : " + projets.size());
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
			// Log.v(TAG, "items : " + itemsJSON);
			/*
			 * for (String item : itemsJSON) { publishProgress(item); }
			 */
			for (Projet projet : projets) {
				publishProgress(projet.getTitre());
			}
			return null;
		}

		protected void onProgressUpdate(String... values) {
			adapter.add(values[0]);

			count++;
			setProgress((int) (((double) count / projets.size()) * 10000));
		}

		@Override
		protected void onPostExecute(Void result) {
			L.s(Derniers_Projets.this, "tous les projets sont ajoutés");

			setProgressBarVisibility(false);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.derniers__projets, menu);
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
