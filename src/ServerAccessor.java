import java.util.*;
//go_ogle_me
//googleme

import edu.neu.mobileclass.apis.KeyValueAPI;


public class ServerAccessor {
  private static final String TEAM_NAME = "go_ogle_me";
  private static final String PASSWORD  = "googleme";

  public ServerAccessor() {}

  /**
   * Gets the value associated with the given key
   * @param key    the key associated with the value to get
   */
  public String get(String key) {
    return KeyValueAPI.get(TEAM_NAME, PASSWORD, key);
  }

  /**
   * Stores given key/value pair on the server
   * @param key    the key of the key/value pair
   * @param value  the value of the key/value pair.
   */
  public void put(String key, String value) {
    KeyValueAPI.put(TEAM_NAME, PASSWORD, key, value);
  }

  /**
   * Clears all key/value pairs from the server.
   */
  public void clear() {
    KeyValueAPI.clear(TEAM_NAME, PASSWORD);
  }

  /**
   * Clears a specific key/value pairing
   * @param key the key of the key/value pair to delete.
   */
  public void clearKey(String key) {
    KeyValueAPI.clearKey(TEAM_NAME, PASSWORD, key);
  }

  /**
   * Returns true if the server is available.
   */
  public boolean isAvailable() {
    return KeyValueAPI.isServerAvailable();
  }

  /**
   * Converts a list of Users to a String
   * @param users The list of users to convert
   */
  public String arrayListToString(ArrayList<String> users) {
    StringBuilder builder = new StringBuilder();
    for (String user : users) {
    	if (!user.equals("")) {
    		builder.append(user + ",");
    	}
    }
    return builder.toString();
  }

  public ArrayList<String> stringToArrayList(String strUsers) {
	ArrayList<String> users = new ArrayList<String>(Arrays.asList(strUsers.split(",")));
    return users;
  }

  
  // Requests
  
  /**
   * Adds a game request for the given user
   * @param user the user for whom to add the request
   * @param req  the user to add to users' request list
   */
  public void addRequest(String user, String req) {
    String key = "req_" + user;
    ArrayList<String> reqs = this.stringToArrayList(this.get(key));
    reqs.add(req);
    String val = this.arrayListToString(reqs);
    this.put(key, val);
  }

  /**
   * Returns a list of the given users' requests
   * @param user the user whose requests we're getting
   * @return an ArrayList<String> of all the given users' requests
   */
  public ArrayList<String> getRequests(String user) {
    String key = "req_" + user;
    ArrayList<String> reqs = this.stringToArrayList(this.get(key));
    return reqs;
  }

  /**
   * Adds all the given requests to the users' requests list
   * @param user The user to add requests to
   * @param reqsToAdd The list of Strings that represents all the requests to add
   */
  public void addRequests(String user, ArrayList<String> reqsToAdd) {
    ArrayList<String> reqs = this.getRequests(user);
    reqs.addAll(reqsToAdd);
    String key = "req_" + user;
    String val = this.arrayListToString(reqs);
    this.put(key, val);
  }

  // Received


}
