package msg_src;

import java.io.Serializable;

public abstract class Mensaje implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String ip_origen;
	protected String id_usuario;
	protected String ip_destino;
	protected String msg;
	
	public Mensaje(String ip_origen,String ip_dest, String msg) {

		this.ip_origen = ip_origen;
		this.ip_destino=ip_dest;
		this.msg = msg;
	}
	
	public String getIPOrigen() {
		return this.ip_origen;		
	}
	
	public String getIPDestino() {
		return this.ip_destino;
	}
	public String getMensaje() {
		return this.msg;
	}
	
	public String getIdUsuario(){
		return this.id_usuario;
	}
	
}
