package jogodavelha.rmi;

import java.util.Random;

import jogodavelha.dominio.JogoDaVelha;

/**
 * Gerencia e guarda os dados de uma sessão (partida).
 * @author Alexandre
 *
 */
public class Sessao {
	private Integer idJogador1, idJogador2, idSessao;
	private JogoDaVelha jogo;
	private Random rand = new Random();
	private boolean encerrada = false;

	public Sessao(Integer idSessao) {
		this.idSessao = idSessao;
		Random rand = new Random();
		idJogador1 = rand.nextInt(1000);
		jogo = new JogoDaVelha();
	}
	
	public Integer conectarJogador2(){
		do{
			idJogador2 = rand.nextInt(1000);
		}while(idJogador1 == idJogador2);
		return idJogador2;
	}
	
	public JogoDaVelha getJogo(){
		return jogo;
	}

	public Integer getIdJogador1() {
		return idJogador1;
	}

	public Integer getIdJogador2() {
		return idJogador2;
	}

	public Integer getIdSessao() {
		return idSessao;
	}
	
	public boolean isEncerrada() {
		return encerrada;
	}

	public void encerrar() {
		this.encerrada = true;
	}
}
