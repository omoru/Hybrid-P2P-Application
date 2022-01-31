package users_src;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FlujosUsuario implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id_usuario;
	private ObjectOutputStream fout;
	private ObjectInputStream fin;
	
	public FlujosUsuario(String id, ObjectOutputStream fout, ObjectInputStream fin) {
		super();
		this.id_usuario = id;
		this.fout = fout;
		this.fin = fin;
	}

	public String getIdUsuario() {
		return id_usuario;
	}

	public ObjectOutputStream getFout() {
		return fout;
	}

	public ObjectInputStream getFin() {
		return fin;
	}
}
