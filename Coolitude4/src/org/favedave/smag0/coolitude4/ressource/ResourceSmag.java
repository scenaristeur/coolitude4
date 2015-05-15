package org.favedave.smag0.coolitude4.ressource;

import org.favedave.smag0.coolitude4.outils.L;

public class ResourceSmag {
	String resourceName = null;
	String classe = null;
	Boolean literal = true; // Ressorce detype literal par défaut
	String projetId = null;

	private long timestamp = System.currentTimeMillis() / 1000;
	private String user = "David";
	private String endpoint_update = "http://fuseki-smag0.rhcloud.com/ds/update";
	private String requete = "la requete n'est pas valide";
	private String prefixSmag = "PREFIX smag: <http://smag0.blogspot.fr/ns/smag0#>";
	private String prefixRdf = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
	private String prefixDc = "PREFIX dc: <http://purl.org/dc/elements/1.1/>";
	private String prefixRdfs = " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";
	private String prefixGeo = " PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>";
	private String prefixFoaf = " PREFIX foaf: <http://xmlns.com/foaf/0.1/>";
	private String debutRequete = "INSERT DATA { GRAPH <http://example/bookStore> {";
	private String finRequete = "} }";
	private String data;
	private String uri;
	private String link;
	private String prefix;

	public ResourceSmag() {
		L.m("Creation ResourceSmag");
	}

	public void setResourceName(String resourceName) {
		L.m("reçu " + resourceName);
		this.resourceName = resourceName;

	}

	public void setClasse(String classe) {
		// TODO Auto-generated method stub
		L.m("reçu " + classe);
		this.classe = classe;
	}

	public void setProjetId(String projetId) {
		L.m("reçu " + projetId);
		this.projetId = projetId;

	}

	public String constructionRequete() {
		L.m("construction Requete  " + classe + projetId + resourceName);

		uri = classe + timestamp;

		data = "smag:" + uri + " smag:user " + "\"" + user + "\"" + " . ";
		data += "smag:" + uri + " rdf:type " + " <" + prefix + classe + "> . ";
		data += "<" + projetId + "> smag:has" + classe + " " + "smag:" + uri
				+ " . ";
		data += "smag:" + uri + " rdf:label " + "\"" + resourceName + "\""
				+ " . ";

		System.out.println(data);
		requete = prefixSmag;
		requete += prefixRdf;
		requete += prefixDc;
		requete += prefixRdfs;
		requete += prefixGeo;
		requete += prefixFoaf;
		requete += debutRequete;
		requete += data;
		requete += finRequete;

		return requete;

	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
		L.m("reçu " + prefix);
	}

}
