package msg_src;

public class MsgPedirFichero extends Mensaje {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String filename;
	
	public MsgPedirFichero(String id_usuario,String origen, String dest, String filename) {
		super(origen, dest, "MENSAJE_PEDIR_FICHERO");
		this.filename=filename;
		this.id_usuario=id_usuario;
	}
	public String getFilename() {
		return filename;
	}
	
		

}
