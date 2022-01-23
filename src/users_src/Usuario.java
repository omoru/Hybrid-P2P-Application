package users_src;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id_usuario;
	private String ip_usuario;
	private ArrayList<File> files;
	
	public Usuario(String id, String ip) {
		this.id_usuario = id;
		this.ip_usuario = ip;
		this.files =new ArrayList<File>();
	}
	
	
	public Usuario(Usuario u) {
		
		 this.id_usuario = u.id_usuario;
	     this.ip_usuario= u.ip_usuario;
	     this.files = new ArrayList<File>(u.files);
	    
	     
	}

	public String getIdUsuario() {
		return id_usuario;
	}
	
	public String getIpUsuario() {
		return ip_usuario;
	}
	
	public ArrayList<File> getFiles() {
		return this.files;
		
	}

	
	public void addFile(String filename,String ruta_filename) {
		this.files.add(new File(filename,ruta_filename));
	}
}
