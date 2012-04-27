package jogodavelha.rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Servidor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			LocateRegistry.createRegistry(1099); //dispensa rodar rmiregistry pelo console
			JogoImpl jogo = new JogoImpl();
			Naming.rebind("jogo", jogo);
			System.out.println("Servidor de jogo online!");
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
