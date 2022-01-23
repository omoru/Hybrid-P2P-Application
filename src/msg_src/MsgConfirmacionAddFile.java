package msg_src;

public class MsgConfirmacionAddFile extends Mensaje {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MsgConfirmacionAddFile(String ip_origen, String ip_dest) {
		super(ip_origen, ip_dest, "MENSAJE_CONFIRMACION_AÑADIR_ARCHIVO");
	}
	



}
