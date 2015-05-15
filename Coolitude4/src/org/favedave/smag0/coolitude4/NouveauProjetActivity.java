package org.favedave.smag0.coolitude4;

import org.favedave.smag0.coolitude4.location.GPSTracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NouveauProjetActivity extends ActionBarActivity implements
		OnClickListener {

	EditText titre;
	EditText descriptionCourte;
	Spinner prive;
	CheckBox type;
	EditText latitude;
	EditText longitude;
	float latitudeProvider;
	float longitudeProvider;
	Button enreg;
	Button annuler;

	// GPSTracker class
	Button btnShowLocation;
	GPSTracker gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nouveau_projet);
		// récupération des composants grâce aux ID
		titre = (EditText) findViewById(R.id.editTextTitre);
		descriptionCourte = (EditText) findViewById(R.id.editTextDescriptionCourte);
		// mail=(EditText)findViewById(R.id.editTextMail);
		// type = (CheckBox) findViewById(R.id.CheckBoxPrive);
		// prive = (Spinner) findViewById(R.id.SpinnerFeedbackType);
		latitude = (EditText) findViewById(R.id.editTextLatitude);
		longitude = (EditText) findViewById(R.id.editTextLongitude);
		enreg = (Button) findViewById(R.id.enregistrer_Bouton);
		annuler = (Button) findViewById(R.id.button2);
		// on applique un ecouteur d'évenemnt au clique sur les 2 boutons
		annuler.setOnClickListener(this);
		enreg.setOnClickListener(this);
		btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
		// show location button click event
		btnShowLocation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// create class object
				gps = new GPSTracker(NouveauProjetActivity.this);

				// check if GPS enabled
				if (gps.canGetLocation()) {

					double latitudeGps = gps.getLatitude();
					double longitudeGps = gps.getLongitude();

					latitudeProvider = (float) latitudeGps;
					longitudeProvider = (float) longitudeGps;
					latitude.setText(String.valueOf(latitudeGps));
					longitude.setText(String.valueOf(longitudeGps));
					// \n is for new line
					Toast.makeText(
							getApplicationContext(),
							"Your Location is - \nLat: " + latitude
									+ "\nLong: " + longitude, Toast.LENGTH_LONG)
							.show();
				} else {
					// can't get location
					// GPS or Network is not enabled
					// Ask user to enable GPS/network in settings
					gps.showSettingsAlert();
				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nouveau_projet, menu);
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

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		// selon le bouton :
		case R.id.enregistrer_Bouton:
			// bouton Enregister
			// On crée un nouveau Intent
			Intent i = new Intent(this, WebViewPostNewProject.class);
			// envoyer les données vers l'autre activité
			i.putExtra("cleTitre", titre.getText().toString());
			i.putExtra("cleDescriptionCourte", descriptionCourte.getText()
					.toString());
			i.putExtra("cleLatitude", latitudeProvider);
			i.putExtra("cleLongitude", longitudeProvider);
			// i.putExtra("cleMail", mail.getText().toString());

			this.startActivityForResult(i, 1000);
			break;

		case R.id.button2:
			// bouton annuler
			// fermer l'app
			finish();
			break;
		}

	}
}
