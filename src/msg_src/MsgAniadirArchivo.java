package msg_src;

public class MsgAniadirArchivo extends Mensaje {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String filename;
	private String ruta_filename;
	
	
	public MsgAniadirArchivo(String ip_origen, String ip_dest, String filename, String ruta_filename,String id_usuario) {
		super(ip_origen, ip_dest, "MENSAJE_AÑADIR_ARCHIVO");
		this.filename=filename;
		this.id_usuario=id_usuario;
		this.ruta_filename=ruta_filename;
	}

	public String getFilename() {
		return this.filename;
	}


	public String getRuta_filename() {
		return ruta_filename;
	}
}
