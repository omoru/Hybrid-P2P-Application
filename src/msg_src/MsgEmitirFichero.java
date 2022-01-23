package msg_src;

public class MsgEmitirFichero extends Mensaje {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ruta_filename;
	private String filename;
	
	public MsgEmitirFichero(String ip_origen,String ip_destino,String ruta_filename,String filename, String id_user) {
		super(ip_origen, ip_destino, "MENSAJE_EMITIR_FICHERO");
		this.ruta_filename=ruta_filename;
		this.id_usuario=id_user;
		this.filename=filename;
		// TODO Auto-generated constructor stub
	}

	public String getRutaFilename() {
		return this.ruta_filename;
	}
	
	public String getFilename() {
		return this.filename;
	}
	
}
