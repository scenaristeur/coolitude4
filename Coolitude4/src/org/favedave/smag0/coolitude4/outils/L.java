package org.favedave.smag0.coolitude4.outils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class L {
	public static void m(String message) {
		Log.d("Coolitude4", message);
	}

	public static void s(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

}
