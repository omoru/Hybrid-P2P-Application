package launcher;

import client_src.Client;

public class MainClient {

	public static void main(String[] args) {
			start(args);			
	}
	
	private static void start(String[] args) {
		if(args.length!=4) {
			System.out.println("Usage: args[0]->ip server, args[1]->puerto server, args[2]-> ip cliente,  args[3]-> GUI o BATCH");
			System.out.println("Ejemplo: 192.168.0.21 555 192.168.0.21 GUI");
			return;
		}
		String mode = args[3];
		String ip_host = args[0];//ip server
		int PUERTO=Integer.parseInt(args[1]);
		String ip_client = args[2];//ip cliente
		if(mode.equalsIgnoreCase("GUI") ||mode.equalsIgnoreCase("BATCH")) {
			Client client = new Client(PUERTO,ip_client,ip_host,mode);
			client.start();	
			try {
				client.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Introduce un modo valido por argumentos(GUI o BATCH)");
			return;
		}
		
		
	}
	

	
	



}
