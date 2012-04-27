package jogodavelha.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Jogo extends Remote{
	/**
	 * Cria uma nova partida e fica aguardando a entrada de 
	 * outro jogador.
	 * @return um array com dois elementos, no qual o primeiro elemento  
	 * é o id da sessão do jogo e o segundo elemento é o id do jogador 
	 * para a partida.
	 */
	int[] criarPartida() throws RemoteException;
	
	/**
	 * Permite que um segundo jogador ingresse em uma partida já 
	 * aberta pelo primeiro jogador.
	 * @param idSessao id da sessão na qual o jogador deseja ingressar.
	 * @return o id do jogador para a partida.
	 */
	int entrarNaPartida(int idSessao) throws RemoteException, IdInvalidoExcpetion;
	
	/**
	 * Faz com que o jogador aguarde a sua vez de jogar.
	 * @throws RemoteException
	 */
	void aguardarResposta(int idSessao, int idJogador) throws RemoteException, IdInvalidoExcpetion;
	
	/**
	 * Indica se algum jogador ganhou a partida.
	 * @return 0 caso a partida ainda não tenha acabado, -1 caso tenha 
	 * terminada empatada ou o id do jogador vencedor.
	 * @throws RemoteException
	 */
	int getJogadorVencedor(int idSessao) throws RemoteException, IdInvalidoExcpetion;
	
	/**
	 * Marca uma posição como sendo do jogador. Retorna true caso a jogada seja 
	 * válida e false em caso contrário.
	 * @param i
	 * @param j
	 * @return
	 * @throws RemoteException
	 */
	boolean marcarPosicao(int i, int j, int idSessao, int idJogador) throws RemoteException, IdInvalidoExcpetion;
	
	int[][] getTabuleiro(int idSessao) throws RemoteException, IdInvalidoExcpetion;
	
}
