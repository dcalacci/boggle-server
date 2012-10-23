package src;
import java.util.ArrayList;

public class TestServerAccessor {
  private final ServerAccessor sa;
  private final ArrayList<String> emptyArrayList = new ArrayList<String>();

  public TestServerAccessor() {
    emptyArrayList.add("");
    this.sa = new ServerAccessor();
    sa.clear();
  }

  public static void main(String[] args) {
    TestServerAccessor test = new TestServerAccessor();
    test.requests();
    test.received();
    test.login();

    test.summarize();

  }

  private void summarize() {
    System.out.println();
    System.out.println (totalErrors + " errors found in " + totalTests +
        " tests.");
  }

  private void requests() {
    final ArrayList<String> userlist1 = new ArrayList<String>();
    userlist1.add("user1");
    userlist1.add("user2");
    userlist1.add("user3");
    userlist1.add("user4");

    try {
      assertTrue("emptyReq", sa.getRequests("user1").equals(emptyArrayList));
      sa.addRequest("user1", "user2");
      assertTrue("getReqsUser1", sa.getRequests("user1").contains("user2"));
      assertTrue("arrayListToString",
          sa.arrayListToString(sa.getRequests("user1")).equals("user2,"));
      sa.addRequest("user1", "user3");
      assertTrue("getReqsUser1", sa.getRequests("user1").contains("user2")
          && sa.getRequests("user1").contains("user3"));
      assertTrue("arrayListToString2",
          sa.arrayListToString(sa.getRequests("user1")).equals("user2,user3,"));
      ArrayList<String> toAdd = new ArrayList<String>();
      toAdd.add("user4");
      toAdd.add("user5");
      sa.addRequestList("user1", toAdd);
      // testing adding and getting multiple requests
      assertTrue("arrayListAddMultiple",
          sa.arrayListToString(sa.getRequests("user1")).equals(
              "user2,user3,user4,user5,"));
      
      sa.removeRequest("user1", "user2");
      assertFalse("arrayListRemove1",
          sa.getRequests("user1").contains("user2"));
      sa.removeRequest("user1", "user3");
      assertFalse("arrayListRemove2",
          sa.getRequests("user1").contains("user3"));

    }
    catch(Exception e) {
      System.out.println("Exception thrown during requests tests:");
      System.out.println(e);
      sa.clear(); //clear the keys and values
      assertTrue("setters", false);
    }
  }
  
  private void received() {
    final ArrayList<String> userlist2 = new ArrayList<String>();
    userlist2.add("user2");
    userlist2.add("user3");
    userlist2.add("user4");

    try {
      sa.addReceived("user1", "user2");
      assertTrue("addReceived1",
          sa.getReceived("user1").contains("user2"));
      
      sa.addReceived("user1", "user3");
      assertTrue("addReceived2",
          sa.getReceived("user1").contains("user2") &&
          sa.getReceived("user1").contains("user3"));
      sa.clear();
      
      sa.addReceivedList("user1", userlist2);
      ArrayList<String> user1Recs = sa.getReceived("user1");
      int count = 0;
      for (String user : userlist2) {
        assertTrue("addAllReceived1" + count,
            user1Recs.contains(user));
        count++;
      }
      sa.removeReceived("user1", "user2");
      assertFalse("removeReceived1",
          sa.getReceived("user1").contains("user2"));
      //ArrayList<String> curRecs

    }
    catch(Exception e) {
      System.out.println("Exception thrown during received tests:");
      System.out.println(e);
      assertTrue("setters", false);
    }
  }

  private void login() {
    String user1 = "user1";
    String pass1 = "suspension1";
    String user2 = "user2";
    String pass2 = "suspension2";
    String user3 = "user3";
    String pass3 = "suspension,3";

    sa.clear();
    assertTrue("canRegister1",
        sa.canRegister(user1, pass1));
    sa.register(user1, pass1);
    assertTrue("canRegister2",
        sa.canRegister(user2, pass2));
    sa.register(user2, pass2);
    assertFalse("canRegister3",
        sa.canRegister(user1, pass2));
    assertFalse("canRegister4",
        sa.canRegister(user3, pass3));
    assertTrue("alreadyRegistered1",
        sa.alreadyRegistered(user1));
    assertFalse("alreadyRegistered2",
        sa.alreadyRegistered(user3));
  }


  //////////////////////////////////////////////////////
  private int totalTests = 0;       // tests run so far
  private int totalErrors = 0;      // errors so far

  private void assertTrue (boolean result) {
    assertTrue ("anonymous", result);
  }

  // Prints failure report if the result is not true.

  private void assertTrue (String name, boolean result) {
    if (! result) {
      System.out.println ();
      System.out.println ("***** Test failed ***** "
          + name + ": " +totalTests);
      totalErrors = totalErrors + 1;
    }
    totalTests = totalTests + 1;
  }

  // For anonymous tests.  Deprecated.

  private void assertFalse (boolean result) {
    assertTrue (! result);
  }

  // Prints failure report if the result is not false.

  private void assertFalse (String name, boolean result) {
    assertTrue (name, ! result);
  }
}
