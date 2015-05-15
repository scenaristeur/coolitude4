package org.favedave.smag0.coolitude4;

/* changements de vue http://www.enis-android-club.com/2013/10/activite-changement-de-vues-sous.html
 * 
 *
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Coolitude extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coolitude);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.coolitude, menu);
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
		} /*
		 * else if (id == R.id.mon_compte) { Intent i = new Intent(this,
		 * MonCompte.class); this.startActivityForResult(i, 1000); return true;
		 * }
		 */else if (id == R.id.ajoute_projet) {
			Intent i = new Intent(this, NouveauProjetActivity.class);
			this.startActivityForResult(i, 1000);
			return true;
		}/*
		 * else if (id == R.id.mes_projets) { Intent i = new Intent(this,
		 * ProjetListActivity.class); this.startActivityForResult(i, 1000);
		 * return true; }
		 */else if (id == R.id.derniers_projets) {
			Intent i = new Intent(this, Derniers_Projets.class);
			this.startActivityForResult(i, 1000);
			return true;
		} else if (id == R.id.derniers_projets100) {
			Intent i = new Intent(this, ProjetsDerniers100Activity.class);
			this.startActivityForResult(i, 1000);
			return true;
		}/*
		 * else if (id == R.id.derniers_projets) { Intent i = new Intent(this,
		 * DerniersProjetsListeActivity.class); this.startActivityForResult(i,
		 * 1000); return true; }
		 */
		else if (id == R.id.carte_menu) {
			Intent i = new Intent(this, CarteSmag0.class);
			this.startActivityForResult(i, 1000);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	// SAise d'un nouveau projet
	public void sendFeedback(View button) {
		Intent i = new Intent(this, NouveauProjetActivity.class);
		this.startActivityForResult(i, 1000);
	}

	public void ButtonCarteProjets(View button) {
		Intent i = new Intent(this, CarteSmag0.class);
		this.startActivityForResult(i, 1000);
	}

	public void ButtonDerniersProjets(View button) {
		Intent i = new Intent(this, Derniers_Projets.class);
		this.startActivityForResult(i, 1000);
	}

	public void ButtonUrgenceLocale(View button) {
		Intent i = new Intent(this, NouveauProjetActivity.class);
		this.startActivityForResult(i, 1000);
	}
}
