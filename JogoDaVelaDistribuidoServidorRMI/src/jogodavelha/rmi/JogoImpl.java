package jogodavelha.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import jogodavelha.dominio.JogoDaVelha;

public class JogoImpl implements Jogo {
	
	//mapeia id da sessão com a respectiva sessão
	private Map<Integer, Sessao> mapSessao = new HashMap<Integer, Sessao>();
	private Random rand = new Random();
	
	public JogoImpl() throws RemoteException{
		UnicastRemoteObject.exportObject(this);
	}
	
	@Override
	public int[] criarPartida() throws RemoteException {
		//cria id da sessão
		Integer idSessao = null;
		do{
			Integer x = rand.nextInt(10000);
			if(!mapSessao.containsKey(x)){ //evita id de sessão duplicado
				idSessao = x;
			}
		}while(idSessao == null);
		//cria sessão
		Sessao sessao = new Sessao(idSessao);
		mapSessao.put(idSessao, sessao);
		
		return new int[]{idSessao, sessao.getIdJogador1()};
	}

	@Override
	public int entrarNaPartida(int idSessao) throws RemoteException, IdInvalidoExcpetion {
		Sessao sessao = getSessao(idSessao);
		return sessao.conectarJogador2();
	}

	@Override
	public void aguardarResposta(int idSessao, int idJogador) throws RemoteException, IdInvalidoExcpetion {
		Sessao sessao = getSessao(idSessao);
		
		//aguarda jogador2 entrar na partida
		while(sessao.getIdJogador2() == null){
			try {
				Thread.sleep(2000);//espera 2 segundos
			} catch (InterruptedException e) {
				throw new RemoteException(e.getMessage(), e);
			}
		}
		
		//os dois jogadores estão conectados
		JogoDaVelha jogo = sessao.getJogo();
		long startTime = System.currentTimeMillis();
		validarIdJogador(idSessao, idJogador);
		while(true){
			int jogadorDaVez = (jogo.getJogadorAtual() == 1 ? sessao.getIdJogador1() : sessao.getIdJogador2());
			try {
				Thread.sleep(2000);//espera 2 segundos
			} catch (InterruptedException e) {
				throw new RemoteException(e.getMessage(), e);
			}
			if(idJogador == jogadorDaVez || jogo.getJogadorVencedor() != 0){
				return;
			}
			long endTime = System.currentTimeMillis();
			if((endTime - startTime) / 1000.0 >= 120){//timeout!
				throw new RemoteException("Servidor demorou muito a responder!");
			}
		}
		
	}

	@Override
	public int getJogadorVencedor(int idSessao) throws RemoteException, IdInvalidoExcpetion {
		Sessao sessao = getSessao(idSessao);
		int vencedor = sessao.getJogo().getJogadorVencedor();
		if(vencedor == -1 || vencedor == 0){ //ainda não acabou ou acabou em empate
			return vencedor;
		}
		//um dos dois jogadores venceu
		return (vencedor == 1 ? sessao.getIdJogador1() : sessao.getIdJogador2());
	}

	@Override
	public boolean marcarPosicao(int i, int j, int idSessao, int idJogador) throws RemoteException, IdInvalidoExcpetion{
		Sessao sessao = getSessao(idSessao);
		validarIdJogador(idSessao, idJogador);
		JogoDaVelha jogo = sessao.getJogo();
		int jogadorDaVez = (jogo.getJogadorAtual() == 1 ? sessao.getIdJogador1() : sessao.getIdJogador2());
		if(jogadorDaVez != idJogador){//erro na interação do cliente com o servidor. cliente não segue o fluxo correto da aplicação
			throw new IdInvalidoExcpetion(idJogador + " tentou jogar mas a vez é do outro jogador");
		}
		return jogo.jogar(i, j);
	}

	@Override
	public int[][] getTabuleiro(int idSessao) throws RemoteException, IdInvalidoExcpetion{
		Sessao sessao = getSessao(idSessao);
		int[][] tabuleiro = new int[3][3];
		for(int i = 0; i < tabuleiro.length; i++){
			for(int j = 0; j < tabuleiro.length; j++){
				tabuleiro[i][j] = sessao.getJogo().getMarcacao(i, j);
			}
		}
		return tabuleiro;
	}
	
	private Sessao getSessao(int idSessao) throws IdInvalidoExcpetion{
		if(!mapSessao.containsKey(idSessao)){
			throw new IdInvalidoExcpetion("Id de sessão inválido");
		}
		return mapSessao.get(idSessao);
	}
	
	private void validarIdJogador(int idSessao, int idJogador) throws IdInvalidoExcpetion{
		Sessao sessao = getSessao(idSessao);
		if(!(sessao.getIdJogador1() == idJogador || sessao.getIdJogador2() == idJogador)){
			throw new IdInvalidoExcpetion("Id de jogador inválido");
		}
	}
}
