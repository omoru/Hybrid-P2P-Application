package msg_src;

public class MsgPreparadoCS extends Mensaje {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int puerto_propio;
	private String my_ip;
	private String filename;

	
	public MsgPreparadoCS(String id_user_destination,String ip_server, String my_ip, int puerto_propio,String filename) {
		super(my_ip, ip_server, "MENSAJE_PREPARADO_CLIENTESERVIDOR");
		this.id_usuario=id_user_destination;
		this.my_ip = my_ip;
		this.puerto_propio= puerto_propio;
		this.filename=filename;
			
		// TODO Auto-generated constructor stub
	}

	public int getPuertoPropio() {
		return this.puerto_propio;
	}
	
	public String getMyIP() {
		return this.my_ip;
	}
	
	public String getFilename() {
		return this.filename;
	}
	
	

	
}
