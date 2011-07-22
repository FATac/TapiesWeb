package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;

@Entity
public class Transcodification extends Model {

	@Required
	public String srcUrl;
	
	@Required
	public String dstUrl;
	
	public Date postedAt;

	@Lob
	public String description;

	@ManyToOne
	public User author;
	
	@ManyToOne
	public Profile profile;

	public Transcodification(User author, String srcUrl, String dstUrl, 
			String description, Profile profile) {
		this.author = author;
		this.srcUrl = srcUrl;
		this.dstUrl = dstUrl;
		this.profile = profile;
		this.description = description;
		this.postedAt = new Date();
	}


	public Transcodification previous() {
		return Transcodification.find("postedAt < ? order by postedAt desc", postedAt).first();
	}

	public Transcodification next() {
		return Transcodification.find("postedAt > ? order by postedAt asc", postedAt).first();
	}
	
	public String toString() {
		return srcUrl;
	}
}
