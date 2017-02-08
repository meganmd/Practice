package apistuff;

import org.json.*;

import com.google.gson.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.commons.io.IOUtils;


public class Practice {

	
	public static void main(String[] args) {
		try {
			//get
			ArrayList<Partner> partners = getPartners();
			HashMap<String, ArrayList<Partner>> partnersByCountry = toHashMap(partners);
			//process
			
			JSONArray countries = new JSONArray();
		
		    
			for(String country : partnersByCountry.keySet()) {
				ArrayList<Partner> countryPartners = partnersByCountry.get(country);
				//need best date, list of people
				HashMap<String, ArrayList<Partner>> canAttend = toCanAttendMap(countryPartners);
				String bestDay = null;
				for(String date : canAttend.keySet()) {
					if(bestDay == null || canAttend.get(date).size() >= canAttend.get(bestDay).size() ) {
						if(bestDay != null && canAttend.get(date).size() == canAttend.get(bestDay).size()) {
							try {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								if(sdf.parse(date).before(sdf.parse(bestDay))){
									bestDay = date;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							bestDay = date;
						}
					}
				}
				
				JSONObject countryInfo = new JSONObject();
				countryInfo.put("attendeeCount", bestDay != null ? canAttend.get(bestDay).size() : 0);
				countryInfo.put("name", country);
				countryInfo.put("startDate", bestDay);
				
				JSONArray attendees = new JSONArray();
				if(bestDay != null) {
					for(Partner partner : canAttend.get(bestDay)) {
						attendees.put(partner.email);
					}
				}
				countryInfo.put("attendees", attendees);
				countries.put(countryInfo);
			}
			
			JSONObject result = new JSONObject();
			result.put("countries", countries);
			//System.out.println(result.toString());
			
			//post
		    System.out.println(postInput(result));
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	private static HashMap<String, ArrayList<Partner>> toCanAttendMap(ArrayList<Partner> countryPartners) {
		HashMap<String, ArrayList<Partner>> canAttend = new HashMap<>();
		for(int i = 0; i < countryPartners.size(); i++) {
			Partner partner = countryPartners.get(i);
			for(String date : partner.availableDates) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Calendar cal = Calendar.getInstance();
					cal.setTime(sdf.parse(date) );
					cal.add( Calendar.DATE, 1 );
					String nextDate = sdf.format(cal.getTime());
					
					if(partner.availableDates.contains(nextDate)){										
						if(!canAttend.containsKey(date)) {
							canAttend.put(date, new ArrayList<Partner>());
						}
						canAttend.get(date).add(partner);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return canAttend;
	}

	private static HashMap<String, ArrayList<Partner>> toHashMap(ArrayList<Partner> partners) {
		HashMap<String, ArrayList<Partner>> partnersByCountry = new HashMap<>();
		for(int i = 0; i < partners.size(); i++) {
			Partner partner = partners.get(i);
			if(!partnersByCountry.containsKey(partner.country)) {
				partnersByCountry.put(partner.country, new ArrayList<Partner>());
			}
			ArrayList<Partner> people = partnersByCountry.get(partner.country);
			people.add(partner);
		}
		return partnersByCountry;
	}

	private static String postInputA(JSONObject json) {
		// TODO Auto-generated method stub
		return null;
	}

	private static JSONObject getInputA() {
		String Surl = "https://graph.facebook.com/me";
		String accessToken ="EAACEdEose0cBAKKJAMam7WSsDqfB8JBGSokaZAuAo4boDX6ZCCX4YgFq4omUQbuP5IPfwHX9wfNUf5W01tta4PJn0LZBIcy3OtaSaEIR5PdvZAlcVgZArNz6TdOCO2edq0lZAoIAfwCT1cg11lhGs6qz7akRz7AbUjNJW0T9XskAZDZD";
		HttpClient client = HttpClientBuilder.create().build();
		URIBuilder urib = new URIBuilder();
		urib.setScheme("https");
		urib.setHost("graph.facebook.com");
		urib.setPath("/me");
		urib.setParameter("access_token", accessToken);
		try{
			URI uri = urib.build();
			System.out.println("uri = " + uri.toString());
			HttpGet get = new HttpGet(uri);
			//get.setHeader("Authorization",  accessToken);
			HttpResponse response = client.execute(get);
			System.out.println(response.getStatusLine().getStatusCode());
			System.out.println(response.getStatusLine().getReasonPhrase());
			System.out.println(response.getEntity().toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	static ArrayList<Partner> getPartners() {
		try {
			JSONObject json = getInput();
			JSONArray JSONPartners = (JSONArray) json.get("partners");
			//System.out.println(JSONPartners.toString());
			
			ArrayList<Partner> partners = new ArrayList<>();
			
			
			for(int i = 0; i < JSONPartners.length(); i++) {
				//System.out.println("partners: " + partners.toString());
				Partner partner = new Partner();
				JSONObject JSONPartner = JSONPartners.getJSONObject(i);
				partner.firstName = JSONPartner.getString("firstName");
				partner.lastName = JSONPartner.getString("lastName");
				partner.country = JSONPartner.getString("country");
				partner.email = JSONPartner.getString("email");
				partner.availableDates = new ArrayList<String>();
				JSONArray JSONAvailableDates = JSONPartner.getJSONArray("availableDates");
				for(int j = 0; j < JSONAvailableDates.length(); j++) {
					partner.availableDates.add(JSONAvailableDates.getString(j));
				}
				partners.add(partner);
			}
			//for(int i = 0; i < partners.size(); i++) {
			//	System.out.println(partners.get(i).toString());
			//}
			
			return partners;
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	static JSONObject getInput() throws IOException, JSONException {
		//String Surl = "https://graph.facebook.com/me";
		String Surl = "https://candidate.hubteam.com/candidateTest/v1/partners?userKey=cd576129f98ef6cc1159011350ba";
		URL url = new URL(Surl);
		//String accessToken = "EAACEdEose0cBAKKJAMam7WSsDqfB8JBGSokaZAuAo4boDX6ZCCX4YgFq4omUQbuP5IPfwHX9wfNUf5W01tta4PJn0LZBIcy3OtaSaEIR5PdvZAlcVgZArNz6TdOCO2edq0lZAoIAfwCT1cg11lhGs6qz7akRz7AbUjNJW0T9XskAZDZD";
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("GET");
		//con.setRequestProperty("Authorization", "OAuth " + accessToken);

		int responseCode = con.getResponseCode();
		String responseMessage = con.getResponseMessage();
		System.out.println(responseCode + ": " + responseMessage);
		InputStream is = con.getInputStream();
		
	    //String result = convertStreamToString(is);
	    return convertStreamToJSONObject(is);
	}
	
	static String postInput(JSONObject json) throws IOException, JSONException {
		String psurl = "https://candidate.hubteam.com/candidateTest/v1/results?userKey=cd576129f98ef6cc1159011350ba";
	    URL purl = new URL(psurl);
	    
	    HttpURLConnection con = (HttpURLConnection) purl.openConnection();
	    
	    con.setRequestMethod("POST");
	    con.setRequestProperty("Content-Type", "application/json");
	    con.setDoOutput(true);
	    con.setRequestProperty("json", json.toString());
	    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	    wr.writeBytes(json.toString());
	    
	    InputStream is = con.getInputStream();
	    System.out.println(convertStreamToJSONObject(is));
	    
	    int responseCode = con.getResponseCode();
	    String responseMessage = con.getResponseMessage();
	    return responseCode + ": " + responseMessage;
	}
	
	
	static String convertStreamToString(InputStream is) throws IOException{
		Scanner s = new Scanner(is).useDelimiter("\\A");
		String str = s.hasNext() ? s.next() : "";
		is.close();
		return str;
	}
	
	static JSONObject convertStreamToJSONObject(InputStream is) throws JSONException, IOException {
	    Scanner s = new Scanner(is).useDelimiter("\\A");
	    String str = s.hasNext() ? s.next() : "";
	    is.close();
	    return new JSONObject(str);
	}
	
	static class Partner {
		String firstName;
		String lastName;
		String email;
		String country;
		ArrayList<String> availableDates;
		public String toString() {
			return "firstName: " + firstName + " lastName: " + lastName + " email: " + email + 
					" country: " + country + " availableDates: " + availableDates.toString();
		}
	}

}
