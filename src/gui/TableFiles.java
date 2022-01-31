package gui;


public class TableFiles {
	
	private String filename;
	private String id_user;
	
	public TableFiles(String filename,String id_user) {
		this.filename=filename;
		this.id_user= id_user;
	}

	public String getFilename() {
		return filename;
	}

	public String getId_user() {
		return id_user;
	}

	
}
