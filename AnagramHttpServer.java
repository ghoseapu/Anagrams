import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class AnagramHttpServer {
	
	static boolean isValidString(String str){
		Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(str);
        boolean b = m.matches();
		return b;
	}
	
	public static void main(String[] args) throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/anagrams", new AnagramHandler());
		// creates a default executor
		server.setExecutor(null);
		server.start();
		System.out.println("The server is running");
	}
	
	static class AnagramHandler implements HttpHandler {
		public void handle(HttpExchange httpExchange) throws IOException {
			StringBuilder response = new StringBuilder();
			// get the path from the URL
			HashMap<String,String> parms = AnagramHttpServer.queryToMap(httpExchange.getRequestURI().getPath());
			if(parms.get("string2").length() > 0){
				if(!isValidString(parms.get("string1")) || !isValidString(parms.get("string2"))){
					response.append("<html><body>Invalid String</body></html>");
					AnagramHttpServer.errorResponse(httpExchange, response.toString());
				}else{
					ApuAnagram testAnagram = new ApuAnagram();
					if(testAnagram.areAnagrams(parms.get("string1"), parms.get("string2"))){
						response.append("<html><body>{\"areAnagrams\" : \"true\"}</body></html>");
					}else{
						response.append("<html><body>{\"areAnagrams\" : \"false\"}</body></html>");
					}
					AnagramHttpServer.successResponse(httpExchange, response.toString());
				}
			} else {
				if(!isValidString(parms.get("string1"))){
					response.append("<html><body>Invalid String</body></html>");
					AnagramHttpServer.errorResponse(httpExchange, response.toString());
				} else {
					ArrayList<String> possibleAnagrams = new ArrayList<String>();
					ApuAnagram testAnagram = new ApuAnagram();
					try {
						File myObj = new File("wordlist.txt");
						Scanner myReader = new Scanner(myObj);
						while (myReader.hasNextLine()) {
							String data = myReader.nextLine();
							if(testAnagram.areAnagrams(parms.get("string1"), data)){
								if(!parms.get("string1").equals(data)){
									possibleAnagrams.add(data);
								}
							}
						}
						myReader.close();
					} catch (FileNotFoundException e) {
						System.out.println("An error occurred.");
						e.printStackTrace();
					}
					
					if(possibleAnagrams.size() > 0){
						response.append("<html><body>{\"anagrams\" : ");
						for (String i : possibleAnagrams) {
							response.append("<br>\t" + i);
						}
						response.append("}</body></html>");
					}else{
						response.append("<html><body>No anagrams found</body></html>");
					}
					AnagramHttpServer.successResponse(httpExchange, response.toString());
				}
			}
			
		}
	}

	public static void successResponse(HttpExchange httpExchange, String response) throws IOException {
		httpExchange.sendResponseHeaders(200, response.length());
		OutputStream os = httpExchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
	
	public static void errorResponse(HttpExchange httpExchange, String response) throws IOException {
		httpExchange.sendResponseHeaders(400, response.length());
		OutputStream os = httpExchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
	
	public static HashMap<String, String> queryToMap(String query){
		HashMap<String, String> result = new HashMap<String, String>();
		System.out.println(query);
		String[] pair = query.split("/");
		if(pair.length > 3){
			result.put("string1", pair[2]);
			result.put("string2", pair[3]);
		}else if(pair.length > 2){
			result.put("string1", pair[2]);
			result.put("string2", "");
		}
		return result;
	}
}
// http://localhost:8000/anagrams/cinema/iceman