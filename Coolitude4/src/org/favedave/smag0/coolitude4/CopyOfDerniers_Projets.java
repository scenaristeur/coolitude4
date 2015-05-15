package org.favedave.smag0.coolitude4;

//http://www.androidhive.info/2012/09/android-adding-search-functionality-to-listview/
//http://stackoverflow.com/questions/9605913/how-to-parse-json-in-android
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class CopyOfDerniers_Projets extends ActionBarActivity {
	private static final String TAG = "Derniers_Projets";
	// List view
	private ListView lv;

	// Listview Adapter
	ArrayAdapter<String> adapter;

	// Search EditText
	EditText inputSearch;
	TextWatcher textwatcher;

	// ArrayList for Listview
	ArrayList<HashMap<String, String>> productList;
	String products[] = { "Dell Inspiron", "HTC One X", "HTC Wildfire S",
			"HTC Sense", "HTC Sensation XE", "iPhone 4S",
			"Samsung Galaxy Note 800", "Samsung Galaxy S3", "MacBook Air",
			"Mac Mini", "MacBook Pro" };
	ArrayList<String> items;

	// private User user;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "DerniersProjets");
		setContentView(R.layout.activity_derniers__projets);
		recupereDerniersProjets();
		// Listview Data
		Log.v(TAG, "items : " + items);
		lv = (ListView) findViewById(R.id.derniersProjetslist_view);
		inputSearch = (EditText) findViewById(R.id.inputSearch);

		// Adding items to listview
		adapter = new ArrayAdapter<String>(this, R.layout.list_item,
				R.id.product_name, items);
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				Log.i(TAG, "Position=" + position);
				// Toast.makeText(getApplicationContext(),((EditText)
				// view).getText(), Toast.LENGTH_SHORT).show();
				Log.v(TAG, "DerniersProjets");
			}
		});

		inputSearch.addTextChangedListener(textwatcher = new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence cs, int start,
					int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence cs, int start, int before,
					int count) {
				// When user changed the Text
				CopyOfDerniers_Projets.this.adapter.getFilter().filter(cs);

			}

			@Override
			public void afterTextChanged(Editable cs) {
				// TODO Auto-generated method stub

			}

		});
	}

	private void recupereDerniersProjets() {
		Log.v(TAG, "recupereDerniersProjets");
		String str = "http://fuseki-smag0.rhcloud.com/ds/query?query=PREFIX+geo%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2003%2F01%2Fgeo%2Fwgs84_pos%23%3E%0D%0APREFIX+smag%3A+++%3Chttp%3A%2F%2Fsmag0.blogspot.fr%2Fns%2Fsmag0%23%3E%0D%0ASELECT%3Fprojet+%3Ftitre+%3Fdescription+%3Flat+%3Flon+WHERE%7B%0D%0A%3Fprojet+%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23type%3E+smag%3AProjet+.%0D%0AOPTIONAL+%7B%3Fprojet+%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Felements%2F1.1%2Ftitle%3E+%3Ftitre+%7D%0D%0A+OPTIONAL+%7B%3Fprojet+%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Felements%2F1.1%2Fdescription%3E+%3Fdescription+%7D%0D%0AOPTIONAL+%7B%3Fprojet+smag%3Adescription+%3Fdescription+%7D%0D%0AOPTIONAL+%7B+%3Fprojet+geo%3Alat+%3Flat+%7D%0D%0A+OPTIONAL+%7B+%3Fprojet+geo%3Along+%3Flon+%7D%0D%0A+OPTIONAL+%7B+%3Fprojet+smag%3Alatitude+%3Flat+%7D++%0D%0A+OPTIONAL+%7B+%3Fprojet+smag%3Alongitude+%3Flon+%7D++++%0D%0A%7D++++%0D%0AORDER+BY+DESC%28%3Fprojet%29%0D%0ALIMIT+20&output=json";
		// Log.v(TAG, str);

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
				/*
				 * JSONArray jsa = new JSONArray(line); for (int i = 0; i <
				 * jsa.length(); i++) { JSONObject jo = (JSONObject) jsa.get(i);
				 * Log.v(TAG, jo.toString()); String title =
				 * jo.getString("titre"); String des =
				 * jo.getString("description"); inputSearch.setText(title); }
				 */
			}
			result = sb.toString();
			JSONObject jObject = new JSONObject(result);
			JSONObject jObjectresults = jObject.getJSONObject("results");
			JSONArray jObjectBindings = jObjectresults.getJSONArray("bindings");
			Log.v(TAG, "json1 " + jObjectBindings.length());

			items = new ArrayList<String>();
			for (int i = 0; i < jObjectBindings.length(); i++) {
				JSONObject json_data = jObjectBindings.getJSONObject(i);
				// int id=json_data.getInt("id");
				String titre = null;
				try {
					titre = json_data.getJSONObject("titre").getString("value");
				} catch (Exception e) {
					Log.w(TAG, e.getMessage());
				}
				if (titre != null) {
					items.add(titre);
					Log.d(titre, "Output");
				} else {
					Log.d(TAG, "pas de titre");
				}
			}

			/*
			 * ArrayAdapter<String> mArrayAdapter = new
			 * ArrayAdapter<String>(this,
			 * android.R.layout.simple_expandable_list_item_1, items);
			 * setListAdapter(mArrayAdapter)
			 */
			// Log.v(TAG, "json2 " +
			// jObject.getJSONArray("bindings").toString());
			// Log.v(TAG, "json2 " + jObject.getJSONObject("results"));
			// Log.v(TAG, "jsonLength " + jObject.toString());
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
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
