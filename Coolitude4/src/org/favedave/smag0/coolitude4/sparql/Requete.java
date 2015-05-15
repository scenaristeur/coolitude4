package org.favedave.smag0.coolitude4.sparql;

import android.util.Log;

public class Requete {

	private String classe;
	private String prefix;
	private String action;
	private String requete = null;
	private final String TAG_DBPEDIA_JSON = "?output=json&query=";

	private final String prefixSmag = " PREFIX smag: <http://smag0.blogspot.fr/ns/smag0#> ";
	private final String prefixRdf = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ";
	private final String prefixDc = " PREFIX dc: <http://purl.org/dc/elements/1.1/> ";
	private final String prefixRdfs = " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ";
	private final String prefixSchema = " PREFIX schema: <http://schema.org/> ";
	private final String debutRequete = " SELECT DISTINCT ";
	private final String where = " WHERE {";
	private final String finRequete = " }";
	private String champsRecherche;
	private String requeteLimite = " LIMIT 100 ";
	private String requeteOrdre = null;

	private enum Actions {
		getSousClasses, carrot, mango, orange;
	}

	public void setClasse(String classe) {
		this.classe = classe;

	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;

	}

	public void addAction(String action) {
		this.action = action;

	}

	public String getRequeteConstruite() {
		String data = null;
		champsRecherche = " ?" + classe + " "; // %3F = encodage du point
		Log.d("Action dans get RequeteConstruite >", action); // d'interrogation
		if (action.equals("getSousClasses")) {
			data = "?" + classe + " ";
			data += " rdfs:subClassOf";
			data += " <" + prefix + classe + "> .";
			Log.d("Data >", data);
		}
		// String requeteConstruite =
		// "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
		// + "select distinct ?"
		// + classe
		// + " where {?"
		// + classe
		// + " rdfs:subClassOf <http://dbpedia.org/ontology/"
		// + classe
		// + ">} LIMIT 100";

		// requete = TAG_DBPEDIA_JSON;
		requete = prefixRdfs;
		// requete += prefixSchema;
		requete += debutRequete;
		requete += champsRecherche;
		requete += where;
		requete += data;
		requete += finRequete;
		if (requeteLimite != null) {
			requete += requeteLimite;
		}
		if (requeteOrdre != null) {
			requete += requeteOrdre;
		}
		Log.d("REQUETE>>>>", requete);
		return this.requete;
	}
}
