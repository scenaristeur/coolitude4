package org.favedave.smag0.coolitude4;

/* insertion de la librairie JENA http://www.iandickinson.me.uk/articles/jena-eclipse-helloworld
 * voir aussi avec Maven
 * */
import org.favedave.smag0.coolitude4.projet.Projet;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class EnvoiProjetServeurActivity extends ActionBarActivity {
	EditText hello;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_envoi_projet_serveur);
		hello = (EditText) findViewById(R.id.editHello);
		String titre = null;
		String descriptionCourte = null;
		if (this.getIntent().getExtras() != null) {
			// recuperer le nom grace à l'identifiant cleNom
			titre = this.getIntent().getExtras().getString("cleTitre");
			// recuperer le nom grace à l'identifiant cleNom
			descriptionCourte = this.getIntent().getExtras()
					.getString("cleDescriptionCourte");
		}
		Toast msg = Toast.makeText(
				EnvoiProjetServeurActivity.this.getApplicationContext(),
				"Titre :" + titre, Toast.LENGTH_LONG);
		hello.setText(hello.getText() + titre);

		Toast msg1 = Toast.makeText(
				EnvoiProjetServeurActivity.this.getApplicationContext(),
				"description : " + descriptionCourte, Toast.LENGTH_LONG);
		hello.setText(hello.getText() + descriptionCourte);
		msg.show();
		msg1.show();
		Projet projetAjoute = new Projet(null, titre, descriptionCourte, 0, 0,
				0);
		// projetAjoute.setTitre(titre);
		// projetAjoute.setDescriptionCourte(descriptionCourte);
		projetAjoute.setEndpoint();
		projetAjoute.verifieEndpoint();
		String requete = projetAjoute.constructionRequete();
		Toast msg2 = Toast.makeText(
				EnvoiProjetServeurActivity.this.getApplicationContext(),
				"Requete : " + requete, Toast.LENGTH_LONG);
		msg2.show();
		// String result = projetAjoute.insert3();
		// Toast msg3 = Toast.makeText(
		// EnvoiProjetServeurActivity.this.getApplicationContext(), "result : "
		// + result, Toast.LENGTH_LONG);
		msg.show();
		hello.setText(hello.getText() + requete);
		// hello.setText(hello.getText() + result);
		hello.setText(hello.getText() + "fin");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.envoi_projet_serveur, menu);
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
