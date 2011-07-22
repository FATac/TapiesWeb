package models;

import java.util.List;

public class CodecList {
	boolean hasAudio = false;
	public List <String> audioCodecs;
	boolean hasVideo = false;
	public List <String> videoCodecs;

	public CodecList (List <String> audioCodecs, List <String> videoCodecs) {
		this.audioCodecs = audioCodecs;
		this.videoCodecs = videoCodecs;
		
		if (this.audioCodecs.size()>0)
			this.hasAudio = true;
		if (this.videoCodecs.size()>0)
			this.hasVideo = true;
		
	}

}

