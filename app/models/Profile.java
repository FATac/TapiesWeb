package models;

import javax.persistence.Entity;

import play.data.validation.Required;
import play.db.jpa.Model;


@Entity
public class Profile extends Model {
	
	@Required
	public String profileName;
	
	@Required
	public String container;
	
	@Required
	public String videoCodec;
	@Required
	public int videoBitrate;
	@Required
	public int videoHeight;
	@Required
	public int videoWidth;
	
	@Required
	public String audioCodec;
	@Required
	public int audioChannels;
	@Required
	public int audioBitrate;
	@Required
	public int audioSampleRate;
	
	public Profile(String profileName, String container, String videoCodec, 
			int videoBitrate, int videoHeight, int videoWidth, 
			String audioCodec, int audioChannels, 
			int audioBitrate, int audioSampleRate) {
		
		this.profileName = profileName;
		this.container = container;
		this.videoCodec = videoCodec;
		this.videoBitrate = videoBitrate;
		this.videoHeight = videoHeight;
		this.videoWidth = videoWidth;
		
		this.audioCodec = audioCodec;
		this.audioChannels = audioChannels;
		this.audioBitrate = audioBitrate;
		this.audioSampleRate = audioSampleRate;
		
	}
	
	public String toString() {
		return profileName;
	}
	

}
