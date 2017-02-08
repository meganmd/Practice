
// Megan Daly
// 12-21-16
package apistuff;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HubSpot {

	
	public static void main(String[] args) {
		try {
			// get
			// countryDatePartners is organized as: {countryName -> { date -> [partnerEmailsWhoCanAttend]}
			HashMap<String,HashMap<String,ArrayList<String>>> countryDatePartners = getCountryDatePartners();
			
			//process
			
			JSONArray countries = new JSONArray();
		    
			for(String country : countryDatePartners.keySet()) {
				// canAttend is a map from a date to the partners who can attend on that date.
				HashMap<String, ArrayList<String>> canAttend = countryDatePartners.get(country);
				
				// bestDay is the earliest date the most number of partners from that country can attend.
				String bestDay = getBestDate(canAttend);
				
				// countryInfo is what needs to be returned for each country
				JSONObject countryInfo = new JSONObject();
				countryInfo.put("attendeeCount", bestDay != null ? canAttend.get(bestDay).size() : 0);
				countryInfo.put("name", country);
				countryInfo.put("startDate", bestDay);
				
				JSONArray attendees = new JSONArray();
				if(bestDay != null) {
					for(String partner : canAttend.get(bestDay)) {
						attendees.put(partner);
					}
				}
				countryInfo.put("attendees", attendees);
				countries.put(countryInfo);
			}
			
			JSONObject result = new JSONObject();
			result.put("countries", countries);
			
			//post
		    System.out.println(postOutput(result));
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	// given a map of dates to a list of people who can attend, this method returns the
	// earliest date where the most people can attend.
	private static String getBestDate(HashMap<String, ArrayList<String>> canAttend) {
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
		return bestDay;
	}

	// this method gets the partners and organizes them first by country and then by dates they can attend.
	private static HashMap<String, HashMap<String, ArrayList<String>>> getCountryDatePartners() {
		try {
			JSONObject json = getInput();
			JSONArray JSONPartners = (JSONArray) json.get("partners");
			
			//building a map: {Country : {date : [emails of partners available]}}
			HashMap<String,HashMap<String,ArrayList<String>>> countryDatePartners = new HashMap<>();			
			
			for(int i = 0; i < JSONPartners.length(); i++) {
				JSONObject JSONPartner = JSONPartners.getJSONObject(i);
				
				JSONArray JSONAvailableDates = JSONPartner.getJSONArray("availableDates");
				HashSet<String> availableDates = new HashSet<>();
				for(int j = 0; j < JSONAvailableDates.length(); j++) {
					availableDates.add(JSONAvailableDates.getString(j));
				}
				
				// if this partner's country isn't in the map yet, add it
				if(!countryDatePartners.containsKey(JSONPartner.get("country"))) {
					countryDatePartners.put((String) JSONPartner.get("country"), new HashMap<String,ArrayList<String>>());
				}
				
				HashMap<String,ArrayList<String>> datePartners = countryDatePartners.get(JSONPartner.get("country"));
				for(String date : availableDates) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Calendar cal = Calendar.getInstance();
						cal.setTime(sdf.parse(date) );
						cal.add( Calendar.DATE, 1 );
						String nextDate = sdf.format(cal.getTime());
						
						// Partner is only available if available for the given date and the next date
						if(availableDates.contains(nextDate)){					
							// if partner is available and the current date isn't listed for the country, add it
							if(!datePartners.containsKey(date)) {
								datePartners.put(date, new ArrayList<String>());
							}
							datePartners.get(date).add(JSONPartner.getString("email"));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			return countryDatePartners;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// This method gets a JSONObject from the server
	static JSONObject getInput() throws IOException, JSONException {
		String Surl = "https://candidate.hubteam.com/candidateTest/v1/partners?userKey=cd576129f98ef6cc1159011350ba";
		URL url = new URL(Surl);
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		String responseMessage = con.getResponseMessage();
		System.out.println(responseCode + ": " + responseMessage);
		InputStream is = con.getInputStream();
		
	    return convertStreamToJSONObject(is);
	}
	
	// This method posts a JSONObject to the server
	static String postOutput(JSONObject json) throws IOException, JSONException {
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
	
	// This is a helper method for getInput, which converts an inputStream to a JSONObject.
	static JSONObject convertStreamToJSONObject(InputStream is) throws JSONException, IOException {
	    Scanner s = new Scanner(is).useDelimiter("\\A");
	    String str = s.hasNext() ? s.next() : "";
	    is.close();
	    return new JSONObject(str);
	}

}
