package msg_src;

public class MsgPreparadoSC extends Mensaje{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ip_conexion;
	private int puerto_conexion;
	private String filename;
	
	public MsgPreparadoSC(String ip_conexion,int puerto_conexion,String filename) {
		super(null, null, "MENSAJE_PREPARADO_SERVIDORCLIENTE");
		this.ip_conexion=ip_conexion;
		this.puerto_conexion=puerto_conexion;
		this.filename=filename;
		
	}
	public int getPuertoPropio() {
		return this.puerto_conexion;
	}
	
	public String getMyIP() {
		return this.ip_conexion;
	}
	
	public String getFilename() {
		return this.filename;
	}
	


}
