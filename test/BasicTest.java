import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;
import java.util.List;


public class BasicTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteAll();
	}
	
	@Test
	public void createAndRetrieveUser() {
		// Create a new user and save it
		new User("bob@gmail.com", "secret", "Bob").save();

		// Retrieve the user with e-mail address bob@gmail.com
		User bob = User.find("byEmail", "bob@gmail.com").first();

		// Test 
		assertNotNull(bob);
		assertEquals("Bob", bob.fullname);
	}
	
	@Test
	public void tryConnectAsUser() {
	
		new User("bob@gmail.com", "secret", "Bob").save();
		
		assertNotNull(User.connect("bob@gmail.com", "secret"));
		assertNull(User.connect("bob@gmail.com", "badpass"));
		assertNull(User.connect("pop@gmail.com", "secret"));
	}

	@Test
	public void createTranscodification() {

		User user = new User("bob@gmail.com", "secret", "Bob").save();
		
		Profile profile = new Profile("profileName", "container", 
				"videoCodec", 0, 0, 0, "audioCodec", 0, 0, 0).save();
		
		new Transcodification(user, "srcUrl", "dstUrl", 
				"description", profile).save();
		
		assertEquals(1, Transcodification.count());		

		List <Transcodification> bobTransco = Transcodification.find("byAuthor", user).fetch();

		assertEquals(1, bobTransco.size());

		Transcodification first = bobTransco.get(0);

		assertNotNull(first);
		assertEquals(user, first.author);
		assertEquals("srcUrl", first.srcUrl);
		assertEquals("description", first.description);
		assertNotNull(first.postedAt);
	}
	
	@Test 
	public void createProfile() {
		new Profile("profileName", "container", "videoCodec", 
				0, 0, 0, "audioCodec", 0, 0, 0).save();
		
		assertEquals(1, Profile.count());
	}
}
