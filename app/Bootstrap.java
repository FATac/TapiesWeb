import play.*;
import play.jobs.*;
import play.test.*;

import models.*;

@OnApplicationStart
public class Bootstrap extends Job {

	public void doJob() {
		if (User.count() == 0) {
			System.out.println("Loading default-admin user");
			if (User.count() == 0)
				Fixtures.loadModels("data.yml");
		}
	}
}

