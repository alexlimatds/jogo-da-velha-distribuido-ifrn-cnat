package jogodavelha.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Jogo extends Remote{
	/**
	 * Cria uma nova partida e fica aguardando a entrada de 
	 * outro jogador.
	 * @return um array com dois elementos, no qual o primeiro elemento  
	 * � o id da sess�o do jogo e o segundo elemento � o id do jogador 
	 * para a partida.
	 */
	int[] criarPartida() throws RemoteException;
	
	/**
	 * Permite que um segundo jogador ingresse em uma partida j� 
	 * aberta pelo primeiro jogador.
	 * @param idSessao id da sess�o na qual o jogador deseja ingressar.
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
	 * @return 0 caso a partida ainda n�o tenha acabado, -1 caso tenha 
	 * terminada empatada ou o id do jogador vencedor.
	 * @throws RemoteException
	 */
	int getJogadorVencedor(int idSessao) throws RemoteException, IdInvalidoExcpetion;
	
	/**
	 * Marca uma posi��o como sendo do jogador. Retorna true caso a jogada seja 
	 * v�lida e false em caso contr�rio.
	 * @param i
	 * @param j
	 * @return
	 * @throws RemoteException
	 */
	boolean marcarPosicao(int i, int j, int idSessao, int idJogador) throws RemoteException, IdInvalidoExcpetion;
	
	int[][] getTabuleiro(int idSessao) throws RemoteException, IdInvalidoExcpetion;
	
}
