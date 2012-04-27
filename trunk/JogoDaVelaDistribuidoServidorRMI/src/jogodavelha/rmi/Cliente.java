package jogodavelha.rmi;

import java.rmi.Naming;
import java.util.Scanner;

public class Cliente {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Jogo jogo = null;
		int idSessao = -1;
		int idJogador = -1;
		Scanner scan = new Scanner(System.in);
		try{
			jogo = (Jogo)Naming.lookup("rmi://127.0.0.1/jogo"); //busca um objeto Jogo no servidor RMI
			//in�cio de partida
			System.out.println("Iniciar partida (1) ou entrar em uma partida (outro n�mero)? ");
			int opcao = scan.nextInt();
			if(opcao != 1){
				System.out.println("Informe o id da sess�o: ");
				idSessao = scan.nextInt();
				idJogador = jogo.entrarNaPartida(idSessao);
				printJogo(jogo.getTabuleiro(idSessao));
			}
			else{
				int[] ids = jogo.criarPartida();
				idSessao = ids[0];
				idJogador = ids[1];
				System.out.println("Sess�o criada com o id " + idSessao);
				System.out.println("Aguarde entrada do outro jogador");
				jogo.aguardarResposta(idSessao, idJogador);
			}
			//turnos
			int vencedor = 0;
			do{
				System.out.println("Aguarde");
				jogo.aguardarResposta(idSessao, idJogador);
				vencedor = jogo.getJogadorVencedor(idSessao);
				printJogo(jogo.getTabuleiro(idSessao));
				if(vencedor == 0){ //partida n�o acabou
					boolean jogadaValida = false;
					do{
						System.out.println("Sua jogada (linha coluna): ");
						int i = scan.nextInt();
						int j = scan.nextInt();
						jogadaValida = jogo.marcarPosicao(i, j, idSessao, idJogador);
						if(!jogadaValida){
							System.out.println("Jogada inv�lida!!");
						}
					}while(!jogadaValida);
					printJogo(jogo.getTabuleiro(idSessao));
					vencedor = jogo.getJogadorVencedor(idSessao);
				}
			}while(vencedor == 0);
			if(vencedor == idJogador){
				System.out.println("Voc� venceu!!!");
			}
			else if(vencedor == -1){
				System.out.println("Empatou!");
			}
			else{
				System.out.println("Voc� perdeu :(");
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private static void printJogo(int[][] tabuleiro){
		for(int i = 0; i < tabuleiro.length; i++){
			for(int j = 0; j < tabuleiro.length; j++){
				System.out.print(tabuleiro[i][j] + " ");
			}
			System.out.println("");
		}
	}
}
