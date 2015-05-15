package org.favedave.smag0.coolitude4.projet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import android.util.Log;

/* a voir pour update à partir d'un graphe : http://linuxsoftwareblog.com/index_p_843.html */

public class Projet {
	private static final String TAG = "Classe Projet";
	// private String id;
	private String titre;
	private String description;
	private long timestamp = System.currentTimeMillis() / 1000;
	private float longitude = 0;
	private float latitude = 0;
	private float altitude = 0;
	private int iconID;
	private String user = "David";
	private String endpoint_update = "http://fuseki-smag0.rhcloud.com/ds/update";
	private String requete = "la requete n'est pas valide";
	private String prefixSmag = "PREFIX smag: <http://smag0.blogspot.fr/ns/smag0#>";
	private String prefixRdf = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
	private String prefixDc = "PREFIX dc: <http://purl.org/dc/elements/1.1/>";
	private String prefixRdfs = " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";
	private String prefixGeo = " PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>";
	private String debutRequete = "INSERT DATA { GRAPH <http://example/bookStore> {";
	private String finRequete = "} }";
	private String data;
	private String uri;
	private String link;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Projet(String uri, String titre, String description, float latitude,
			float longitude, int iconID) {
		super();
		this.uri = uri;
		this.longitude = longitude;
		this.latitude = latitude;
		this.titre = titre;
		this.description = description;
	}

	public Projet() {
		Log.d(TAG, "Nouveau Projet");
	}

	public String constructionRequete() {

		uri = "P" + timestamp;

		data = "smag:" + uri + " smag:user " + "\"" + user + "\"" + " . ";
		data += "smag:" + uri + " rdf:type " + " smag:Projet " + " . ";
		data += "smag:" + uri + " dc:title " + "\"" + titre + "\"" + " . ";
		if (latitude != 0) {
			data += "smag:" + uri + " geo:lat " + "\"" + latitude + "\""
					+ " . ";
		}
		if (longitude != 0) {
			data += "smag:" + uri + " geo:long " + "\"" + longitude + "\""
					+ " . ";
		}
		data += "smag:" + uri + " dc:description " + "\"" + description + "\""
				+ " . ";
		System.out.println(data);
		requete = prefixSmag;
		requete += prefixRdf;
		requete += prefixDc;
		requete += prefixRdfs;
		requete += prefixGeo;
		requete += debutRequete;
		requete += data;
		requete += finRequete;
		return requete;
	}

	public void executionRequete() {

	}

	public String getURI() {
		return uri;
	}

	public void setURI(String uri) {
		this.uri = uri;
	}

	public float getAltitude() {
		return altitude;
	}

	public void setAltitude(float altitude) {
		this.altitude = altitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getLongitude() {
		return longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public int getIconId() {
		return iconID;
	}

	public void setId(int _iconID) {
		this.iconID = _iconID;
	}

	public void setTitre(String _titre) {
		this.titre = _titre;

	}

	public void setDescriptionCourte(String _description) {
		this.description = _description;

	}

	public void setEndpoint() {
		// TODO Auto-generated method stub

	}

	public void verifieEndpoint() {
		// TODO Auto-generated method stub

	}

	/*
	 * public void insert() { // DefaultHttpClient httpclient = new
	 * DefaultHttpClient(); // HttpPost httpPost=new //
	 * HttpPost("http://fuseki-smag0.rhcloud.com/ds/update?"); URL url;
	 * HttpsURLConnection conn = null; try { url = new
	 * URL("http://fuseki-smag0.rhcloud.com/ds/update?"); conn =
	 * (HttpsURLConnection) url.openConnection(); conn.setReadTimeout(10000);
	 * conn.setConnectTimeout(15000); conn.setRequestMethod("POST");
	 * conn.setDoInput(true); conn.setDoOutput(true); } catch
	 * (MalformedURLException e1) { // TODO Auto-generated catch block
	 * e1.printStackTrace(); } catch (ProtocolException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * List<NameValuePair> params = new ArrayList<NameValuePair>(); String
	 * paramValue1 = id; params.add(new BasicNameValuePair("firstParam",
	 * paramValue1)); String paramValue2 = "testparam2"; params.add(new
	 * BasicNameValuePair("secondParam", paramValue2)); String paramValue3 =
	 * "testparam3"; params.add(new BasicNameValuePair("thirdParam",
	 * paramValue3));
	 * 
	 * OutputStream os; try { os = conn.getOutputStream(); BufferedWriter writer
	 * = new BufferedWriter(new OutputStreamWriter( os, "UTF-8"));
	 * writer.write(getQuery(params)); writer.flush(); writer.close();
	 * os.close();
	 * 
	 * conn.connect(); } catch (IOException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); }
	 * 
	 * }
	 */

	/*
	 * private String getQuery(List<NameValuePair> params) throws
	 * UnsupportedEncodingException { StringBuilder result = new
	 * StringBuilder(); boolean first = true;
	 * 
	 * for (NameValuePair pair : params) { if (first) first = false; else
	 * result.append("&");
	 * 
	 * result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
	 * result.append("="); result.append(URLEncoder.encode(pair.getValue(),
	 * "UTF-8")); }
	 * 
	 * return result.toString(); }
	 */

	/*
	 * public String insert2() { String message; String update = requete; /*
	 * String update =
	 * "PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "; update +=
	 * "PREFIX rdfs:   <http://www.w3.org/2000/01/rdf-schema#>"; update +=
	 * "PREFIX smag:   <http://smag0.blogspot.fr/NS/>"; update +=
	 * "PREFIX zoo:   <http://example.org/zoo/>"; update +=
	 * "PREFIX owl: <http://www.w3.org/2002/07/owl#>"; update +=
	 * "PREFIX ex: <http://example.org/>";
	 * 
	 * update += "INSERT DATA {"; update +=
	 * "GRAPH <http://smag0.blogspot.fr/GraphTest>{";
	 * 
	 * update += "smag:RobokIze    rdf:type         smag:Site ."; update +=
	 * "smag:RobokIze   smag:objectif         'un site de vente revente piece detachees , pour robots, et objets connectes' ."
	 * ; update += "ex:cat     rdfs:subClassOf  ex:animal ."; update +=
	 * "zoo:host   rdfs:range       ex:animal ."; update +=
	 * "ex:zoo1    zoo:host         ex:cat2 ."; update +=
	 * "ex:cat3    owl:sameAs       ex:cat2 .";
	 * 
	 * update += "}}";
	 */
	/*
	 * HttpClient httpclient = new DefaultHttpClient();
	 * 
	 * message = new String(
	 * "Votre ecran peut vous apparaitre tout noir,\n a la premiere connexion au serveur, \n merci de patienter quelques dizaines de secondes au plus \n"
	 * );
	 * 
	 * try {
	 * 
	 * HttpPost httpPost = new HttpPost(
	 * "http://fuseki-smag0.rhcloud.com/ds/update?"); // HttpPost httpPost = new
	 * // HttpPost("http://192.168.1.52:3030/ds/update?"); List<NameValuePair>
	 * nameValuePairs = new ArrayList<NameValuePair>(2); //
	 * nameValuePairs.add(new BasicNameValuePair("update", //
	 * "INSERT {<bola> <bolb> <bolc>} WHERE {?s ?p ?o}" ));
	 * nameValuePairs.add(new BasicNameValuePair("update", update));
	 * httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs)); System.out
	 * .println("executing request " + httpPost.getRequestLine()); HttpResponse
	 * response = httpclient.execute(httpPost); HttpEntity resEntity =
	 * response.getEntity();
	 * System.out.println("----------------------------------------");
	 * System.out.println(response.getStatusLine()); if (resEntity != null) {
	 * System.out.println("Response content length: " +
	 * resEntity.getContentLength()); System.out.println("Chunked?: " +
	 * resEntity.isChunked()); String responseBody =
	 * EntityUtils.toString(resEntity); System.out.println("Data: " +
	 * responseBody); message += "Response content length: " +
	 * resEntity.getContentLength() + "\n"; message += "Chunked?: " +
	 * resEntity.isChunked() + "\n"; // String responseBody =
	 * EntityUtils.toString(resEntity); message += "Data: " + responseBody +
	 * "\n"; // message="45" ; } // EntityUtils.consume(resEntity); } catch
	 * (Exception e) { System.out.println(e); message += e; } finally { // When
	 * HttpClient instance is no longer needed, // shut down the connection
	 * manager to ensure // immediate deallocation of all system resources
	 * httpclient.getConnectionManager().shutdown(); } return message;
	 * 
	 * }
	 */

	public String insert1() {

		String message = "fonction insert1 ";
		String update = requete;

		URL url = null;
		try {
			url = new URL("endpoint_update");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message += e;
		}
		Map<String, Object> params = new LinkedHashMap<>();
		// params.put("name", "Freddie the Fish");
		// params.put("email", "fishie@seamail.com");
		// params.put("reply_to_thread", 10394);
		// params.put("message",
		// "Shark attacks in Botany Bay have gotten out of control. We need more defensive dolphins to protect the schools here, but Mayor Porpoise is too busy stuffing his snout with lobsters. He's so shellfish.");
		params.put("update", update);

		StringBuilder postData = new StringBuilder();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (postData.length() != 0)
				postData.append('&');
			try {
				postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				message += e;
			}
			postData.append('=');
			try {
				postData.append(URLEncoder.encode(
						String.valueOf(param.getValue()), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				message += e;
			}
		}
		byte[] postDataBytes = null;
		try {
			postDataBytes = postData.toString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message += e;
		}

		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message += e;
		}
		try {
			conn.setRequestMethod("POST");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message += e;
		}
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length",
				String.valueOf(postDataBytes.length));
		conn.setDoOutput(true);
		try {
			conn.getOutputStream().write(postDataBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message += e;
		}

		Reader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message += e;
		}
		try {
			for (int c; (c = in.read()) >= 0; System.out.print((char) c))
				;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message += e;
		}
		message += "Terminée";
		return message;

	}

	public void setLatitude(float _latitude) {
		// TODO Auto-generated method stub
		this.latitude = _latitude;
	}

	public void setLongitude(float _longitude) {
		this.longitude = _longitude;
		// TODO Auto-generated method stub

	}

	public String getTitre() {
		// TODO Auto-generated method stub
		return this.titre;
	}

}
