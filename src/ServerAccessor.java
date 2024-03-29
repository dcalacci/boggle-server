package src;
import java.util.*;
//go_ogle_me
//googleme

import edu.neu.mobileclass.apis.KeyValueAPI;


public class ServerAccessor {
  private static final String TEAM_NAME = "go_ogle_me";
  private static final String PASSWORD  = "googleme";
  private static String USER_NAME = "";
  private static String USER_PASS = "";

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
  public void addRequestList(String user, ArrayList<String> reqsToAdd) {
    ArrayList<String> reqs = this.getRequests(user);
    reqs.addAll(reqsToAdd);
    String key = "req_" + user;
    String val = this.arrayListToString(reqs);
    this.put(key, val);
  }
  
  /**
   * Removes the given request from the given users' request list
   * @param user  The user whose request list we're removing stuff from
   * @param req   The user request we're removing
   */
  public void removeRequest(String user, String req) {
    String key = "req_" + user;
    ArrayList<String> curReqs = this.getRequests(key);
    curReqs.remove(req);
    String val = this.arrayListToString(curReqs);
    this.put(key, val);
  }

  // Received

  /**
   * Adds user rec to user1's recieved list
   * @param user  The user whose recieved list we're editing
   * @param rec   The user to add to users's recieved list
   */
  public void addReceived(String user, String rec) {
    String key = "rec_" + user;
    ArrayList<String> recs = this.stringToArrayList(this.get(key));
    recs.add(rec);
    String val = this.arrayListToString(recs);
    this.put(key, val);
  }
  
  /**
   * Returns a list of the given users' received requests
   * @param user  The user whose requests we're returning
   * @return    The list of users' received requests 
   */
  public ArrayList<String> getReceived(String user) {
    String key = "rec_" + user;
    ArrayList<String> recs = this.stringToArrayList(this.get(key));
    return recs;
  }
  
  /**
   * Adds all users in the list to the given users' received list
   * @param user  The user whose received list we're editing
   * @param recs  The list of requests to add
   */
  public void addReceivedList(String user, ArrayList<String> recs) {
    String key = "rec_" + user;
    ArrayList<String> curRecs = this.stringToArrayList(this.get(key));
    curRecs.addAll(recs);
    String val = this.arrayListToString(curRecs);
    this.put(key, val);
  }

  /**
   * Removes rec from the given users' list of received requests
   * @param user  The user whose received list we're editing
   * @param rec   The received request we're removing
   */
  public void removeReceived(String user, String rec) {
    String key = "rec_" + user;
    ArrayList<String> curRecs = this.getReceived(user);
    curRecs.remove(rec);
    String val = this.arrayListToString(curRecs);
    this.put(key, val);
  }

  /**
   * Returns true if the given user/pass combination exists
   * @param user  The given username
   * @param pass  The given password
   */
  public boolean login(String user, String pass) {
    String usersKey = "users";
    String passKey = "pass_" + user;

    String passVal = this.get(passKey);
    ArrayList<String> users = stringToArrayList(this.get(usersKey));

    if (users.contains(user) && pass.equals(passVal)) {
      this.USER_NAME  = user;
      this.PASSWORD   = pass;
      return true;
    } else {
      return false;
    }
  }

  /**
   * Registers a new user
   * This will only be called if the given user/pass is valid
   * @param user  The new username to register
   * @param pass  The password for the new username
   */
  public void register(String user, String pass) {
    String usersKey = "users";
    String passKey = "pass_" + user;

    String passVal = pass;
    this.put(passKey, passVal); // enters the new user/pass combo into the server

    ArrayList<String> users = stringToArrayList(this.get(usersKey));
    users.add(user); // adds the given username to the list of users
    this.put(usersKey, this.arrayListToString(users)); // updates the list of users on the server
  }

  /**
   * Checks to see if a new user/pass combo can be registered
   * @param user  The username to check
   * @param pass  The password to check
   * @return true if the username/pass can be registered, false otherwise
   */
  public boolean canRegister(String user, String pass) {
    // if the username or password contains commas, it can't be registered
    if (user.contains(",") || pass.contains(",")) {
      return false;
    } else if (alreadyRegistered(user)) {
      return false;
    } else {
      return true;
    }
  }
  /**
   * Checks to see if the given username is already registered
   * @param user  The username to check
   * @return true if the username is already registered
   */
  public boolean alreadyRegistered(String user) {
    String usersKey = "users";
    ArrayList<String> users = stringToArrayList(this.get(usersKey));
    return users.contains(user);
  }

  /**
   * Returns the key for the given two users - order unspecific
   * I recognize that this does not generate a very unique
   * key, but it needs to be an int and we have a very small use case.
   * @param user1   The first user
   * @param user2   The second user
   * @return        An integer value that is the key value for the two users
   */
  private String getUsersKey(String user1, String user2) {
    // String.hashCode has used the same algorithm since java 1.3.1,
    // so i'm comfortable using it here to generate a consistent "unique" identifier
    // for the two given users
    int hash1 = user1.hashCode();
    int hash2 = user2.hashCode();
    int key = hash1+hash2;
    return "" + key;
  }

  /**
   * Returns true if a game between the two users exists
   * @param user1   The first user
   * @param user2   The second user
   * @return        True if a game exists, false otherwise
   */
  public boolean doesGameExist(String user1, String user2) {
    String gameKey = "games";
    // get the unique identifier for these two users
    String thisGame = this.getUsersKey(user1, user2);
    ArrayList<String> games = this.stringToArrayList(this.get(gameKey));
    return games.contains(thisGame);
  }

  /**
   * Adds a game between the two given users to the server
   * @param user1   The first user
   * @param user2   The second user
   */
  public void addGame(String user1, String user2) {
    String gameKey = "games";
    String usersKey = this.getUsersKey(user1, user2);
    ArrayList<String> games = this.stringToArrayList(this.get(gameKey));
    games.add(usersKey);
    String gameVal = this.arrayListToString(games);
    this.put(gameKey, gameVal);
  }

  /**
   * Produces an arraylist of all the users that user has games with
   * @param user  The user who we are concerned with
   * @return      An arraylist that contains every user that user is in a game with
   */
  public ArrayList<String> getGames(String user) {
    ArrayList<String> serverGames = this.stringToArrayList(this.get("games"));
    ArrayList<String> serverUsers = this.stringToArrayList(this.get("users"));
    ArrayList<String> games = new ArrayList<String>();
    for (String user2 : serverUsers) {
      if (serverGames.contains(this.getUsersKey(user, user2))) {
        games.add(user2);
      }
    }
    return games;
  }
  // only called when a previous game doesn't exist
  public void createNewGame(String creator, String opponent, String board) {
    String userskey = this.getUsersKey(creator, opponent);

    // add game to game list
    this.addGame(creator, opponent);

    // add board to the server
    String boardKey = "board_" + userskey;
    this.put(boardKey, board); // create the board on the server

    // value of turn element will always be a username - initiates to the creator.
    String turnKey = "turn_" + userskey;
    this.put(turnKey, creator); // create the turn element on the server

    // create the entered words element on the server
    String enteredKey = "entered_" + userskey;
    this.put(enteredKey, "");

    // create the scores element on the server
    // score is a string like "user1|50,user2|44"
    String scoresKey = "scores_" + userskey;
    this.put(scoresKey, creator + "|0," + opponent + "|0");

    // create the number of turns element on the server
    String numTurnsKey = "numTurns_" + userskey;
    this.put(numTurnsKey, "0");
  }

// TODO: create a scores interpreter
// TODO: create a numTurns interpreter
// TODO: create a board interpreter
// TODO: create an enteredKey interpreter
// TODO: make a (makeTurn) method that takes in a users' name, a new board,
//        the user they're playing against, that users' updated score, and
//        the updated entered word list....or if we choose to store everything 
//        on the server, the word that the user just entered(might be cleaner).
//        turn_... should update itself.


  


  public void updateBoard(String user1, String user2, String board) {
    String boardKey = "board_" + this.getUsersKey(user1, user2);
    String boardVal = board;
    this.put(boardKey, boardVal);
  }

}
