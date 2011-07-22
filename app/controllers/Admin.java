package controllers;
 
import play.*;
import play.mvc.*;
import play.data.validation.Error;
 
import java.util.*;

import org.i2cat.tapies.transcoder.configuration.GetXuggleSupport;
 
import models.*;
 
@With(Secure.class)
public class Admin extends Controller {
    
    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("byEmail", Security.connected()).first();
            renderArgs.put("user", user.fullname);
        }
    }
 
    public static void index() {
	List<Transcodification> myTranscos = Transcodification.find("author.email", Security.connected()).fetch();
	render(myTranscos);
    }
    
    private static List<String> getProfiles() {
		List<Profile> profiles = Profile.findAll();	
    	List<String> profileNames = new ArrayList<String>();
		
		// Empty item
		profileNames.add("");
		
		for (Profile profile : profiles) {
			profileNames.add(profile.profileName);
		}
		return profileNames;
    }
    
    private static List<String> getContainers() {
    	// TODO check that getSupportedOutputContainers is configured properly
		List <String> containers = GetXuggleSupport.getSupportedFormats();
		// Empty container
		containers.add(0, "");
		return containers;
    }

	public static void form(Long id) {
		if (id != null) {
			Transcodification transco = Transcodification.findById(id);
			List<String> profileNames = new ArrayList<String>();
			if (transco.profile != null)
				profileNames.add(transco.profile.profileName);
			render(transco, profileNames);
		} else {
			List<String> profileNames = getProfiles();
			List <String> containers = getContainers();
			render(profileNames, containers);
		}

	}

	public static void listCodecs(String container) {
		container = container.trim();
		List<String> video = GetXuggleSupport.getVideoSupportedCodecs(container);
		List<String> audio = GetXuggleSupport.getAudioSupportedCodecs(container);
		renderJSON(new CodecList(audio, video));
	}
	
	public static void save(Long id, String srcUrl, String dstUrl, 
			String description, String profileName, String container, 
			String videocodec, String audiocodec) {
		
		Profile profile;
		Transcodification transco;
		
		if(id == null) {
			User author = User.find("byEmail", Security.connected()).first();
			
			if (profileName == null || profileName.length() == 0) {
				profile = new Profile(container + "+" + videocodec + "+" + audiocodec, 
						container, videocodec, 1, 1, 1, audiocodec, 1, 1, 1).save();
			} else {
				profile = Profile.find("byProfileName", profileName).first();
			}
			
			transco = new Transcodification(author,srcUrl,dstUrl,description,profile);
			
		} else {
			transco = Transcodification.findById(id);
			transco.srcUrl = srcUrl;
			transco.dstUrl = dstUrl;
			transco.description = description;
		}
		
		// Validate the form
		validation.valid(transco);
		
		if(validation.hasErrors()) {
			for(Error error : validation.errors()) {

				System.err.println("key " + error.getKey() + ":" +error.message());
			}
			List<String> profileNames = getProfiles();
			List <String> containers = getContainers();
			
			render("@form", transco, profileNames, containers);
		}
		transco.save();
		index();
	}
    
}
