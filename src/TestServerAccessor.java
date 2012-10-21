import java.util.ArrayList;

public class TestServerAccessor {
	private final ArrayList<String> userlist1 = new ArrayList<String>();
	private final ServerAccessor sa;
	private final ArrayList<String> emptyArrayList = new ArrayList<String>();

	public TestServerAccessor() {
		emptyArrayList.add("");
		this.sa = new ServerAccessor();
		sa.clear();
	}

	public static void main(String[] args) {
		TestServerAccessor test = new TestServerAccessor();
		test.setters();

		test.summarize();

	}

	private void summarize() {
		System.out.println();
		System.out.println (totalErrors + " errors found in " + totalTests +
				" tests.");
	}

	private void setters() {
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
			sa.addRequests("user1", toAdd);
			// testing adding and getting multiple requests
			assertTrue("arrayListAddMultiple",
					sa.arrayListToString(sa.getRequests("user1")).equals(
							"user2,user3,user4,user5,"));

		}
		catch(Exception e) {
			System.out.println("Exception thrown during setters tests:");
			System.out.println(e);
			assertTrue("setters", false);
		}
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
