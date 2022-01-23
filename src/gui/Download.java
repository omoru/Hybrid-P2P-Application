package gui;

public class Download {
	
	private String filename;
	private long kbytes;
	
	public Download(String filename, long kbytes) {
		this.filename = filename;
		this.kbytes = kbytes;
	}
	public String getFilename() {
		return filename;
	}
	public long getKbytes() {
		return kbytes;
	}
	
	public void changeValue(long kbytes) {
		this.kbytes=kbytes;
	}
	
	

}
