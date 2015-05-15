package org.favedave.smag0.coolitude4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SingleProjectActivity extends ActionBarActivity {
	private static final String TAG_ID = "projet";
	private static final String TAG_PROJETS = "projets";
	private static final String TAG_TITRE = "titre";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_LATITUDE = "lat";
	private static final String TAG_LONGITUDE = "lon";
	private static final String TAG_RESULTS = "results";
	private static final String TAG_BINDINGS = "bindings";
	private static final String TAG_VALUE = "value";
	private TextView titreTextView;
	private TextView idProjetTextView;
	private TextView descriptionTextView;
	private TextView latitudeTextView;
	private TextView longitudeTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_project);

		String id = null;
		String titre = null;
		String description = null;
		String latitude = null;
		String longitude = null;
		if (this.getIntent().getExtras() != null) {
			// recuperer le nom grace à l'identifiant cleNom
			id = this.getIntent().getExtras().getString(TAG_ID);
			titre = this.getIntent().getExtras().getString(TAG_TITRE);
			description = this.getIntent().getExtras()
					.getString(TAG_DESCRIPTION);
			latitude = this.getIntent().getExtras().getString(TAG_LATITUDE);
			longitude = this.getIntent().getExtras().getString(TAG_LONGITUDE);
		}
		idProjetTextView = (TextView) findViewById(R.id.idProjetTextView);
		titreTextView = (TextView) findViewById(R.id.titreTextView);
		descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
		latitudeTextView = (TextView) findViewById(R.id.latitudeTextView);
		longitudeTextView = (TextView) findViewById(R.id.longitudeTextView);
		idProjetTextView.setText(id);
		titreTextView.setText(titre);
		descriptionTextView.setText(description);
		latitudeTextView.setText(latitude);
		longitudeTextView.setText(longitude);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_project, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		String projetID = idProjetTextView.getText().toString();
		Intent i = null;
		int id = item.getItemId();
		switch (id) {
		// selon le bouton :
		case R.id.action_foaf_Agent:
			// RECHERCHER UNE ONTOLOGIE : http://lov.okfn.org/dataset/lov/terms
			i = new Intent(this, AjouteClassePropriete.class); // ou
			// ConnaissanceProjetActivity
			// pour les
			// boutons
			i.putExtra("clePrefix", "foaf");
			i.putExtra("cleClasse", "Agent");
			i.putExtra("cleProjetId", projetID);

			break;
		// homeActivity:Context
		// prissma:Context
		case R.id.action_dbpedia_Event:
			// RECHERCHER UNE ONTOLOGIE : http://lov.okfn.org/dataset/lov/terms
			i = new Intent(this, Action.class);
			i.putExtra("clePrefix", "http://dbpedia.org/ontology/");
			i.putExtra("cleClasse", "Event");
			i.putExtra("cleProjetId", projetID);
			i.putExtra("cleAction", "getSousClasses");
			// i.putExtra("cleEndpoint", "http://lod.openlinksw.com/sparql/");
			i.putExtra("cleEndpoint", "http://dbpedia.org/sparql/");
			break;
		case R.id.action_rdfs_Comment:
			// RECHERCHER UNE ONTOLOGIE : http://lov.okfn.org/dataset/lov/terms
			i = new Intent(this, Action.class);
			i.putExtra("clePrefix", "http://smag0.blogspot.fr/ns/smag0#");
			i.putExtra("cleClasse", "Comment");
			i.putExtra("cleProjetId", projetID);
			i.putExtra("cleAction", "simple");
			// i.putExtra("cleEndpoint", "http://lod.openlinksw.com/sparql/");
			// i.putExtra("cleEndpoint", "http://dbpedia.org/sparql/");
			break;

		case R.id.button2:
			// bouton annuler
			// fermer l'app
			finish();
			break;
		}
		this.startActivityForResult(i, 1000);
		return super.onOptionsItemSelected(item);
	}
}
