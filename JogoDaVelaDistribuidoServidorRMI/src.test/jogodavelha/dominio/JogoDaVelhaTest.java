package jogodavelha.dominio;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Caso de teste da classe jogodavelha.dominio.JogoDaVelha.
 */
public class JogoDaVelhaTest {

	private JogoDaVelha jogo;

	@Before
	public void setUp() throws Exception {
		jogo = new JogoDaVelha(); // cria um novo jogo antes de cada método de teste
	}

	/**
	 * Jogador 1 ganha fechando a primeira linha.
	 */
	@Test
	public void testJogar01() {
		// jogador 1
		assertEquals(1, jogo.getJogadorAtual()); // verifica se a vez é do jogador 1
		jogo.jogar(0, 0); // jogada válida, então deve alternar jogador

		// jogador 2
		assertEquals(2, jogo.getJogadorAtual()); // verifica se a vez é do jogador 2
		jogo.jogar(1, 0);

		// jogador 1
		assertEquals(1, jogo.getJogadorAtual());
		jogo.jogar(0, 1);

		// jogador 2
		assertEquals(2, jogo.getJogadorAtual());
		jogo.jogar(1, 1);

		// jogador 1
		assertEquals(1, jogo.getJogadorAtual());
		jogo.jogar(0, 2);

		// jogador 1 fechou uma linha, então verifica se ele é o vencedor
		assertEquals(jogo.getJogadorVencedor(), 1);
	}

	/**
	 * Jogador 2 ganha fechando a segunda coluna.
	 */
	@Test
	public void testJogar02() {
		assertEquals(1, jogo.getJogadorAtual()); // jogador um
		jogo.jogar(0, 0);
		assertEquals(2, jogo.getJogadorAtual()); // jogador dois
		jogo.jogar(0, 1);
		assertEquals(1, jogo.getJogadorAtual()); // jogador um
		jogo.jogar(2, 2);
		assertEquals(2, jogo.getJogadorAtual()); // jogador dois
		jogo.jogar(1, 1);
		assertEquals(1, jogo.getJogadorAtual()); // jogador um
		jogo.jogar(1, 0);
		assertEquals(2, jogo.getJogadorAtual()); // jogador dois
		jogo.jogar(2, 1);
		// jogador 2 fechou uma coluna, então verifica se ele é o vencedor
		assertEquals(jogo.getJogadorVencedor(), 2);
	}
	
	/**
	 * Jogador 1 ganha fechando uma diagonal.
	 */
	@Test
	public void testJogar03() {
		assertEquals(1, jogo.getJogadorAtual()); // jogador um
		jogo.jogar(0, 0);
		assertEquals(2, jogo.getJogadorAtual()); // jogador dois
		jogo.jogar(0, 1);
		assertEquals(1, jogo.getJogadorAtual()); // jogador um
		jogo.jogar(1, 1);
		assertEquals(2, jogo.getJogadorAtual()); // jogador dois
		jogo.jogar(0, 2);
		assertEquals(1, jogo.getJogadorAtual()); // jogador um
		jogo.jogar(2, 2);
		// jogador 1 fechou uma diagonal, então verifica se ele é o vencedor
		assertEquals(jogo.getJogadorVencedor(), 1);
	}
	
	/**
	 * Empate.
	 */
	@Test
	public void testEmpate() {
		assertEquals(1, jogo.getJogadorAtual()); // jogador um
		jogo.jogar(0, 0);
		assertEquals(2, jogo.getJogadorAtual()); // jogador dois
		jogo.jogar(0, 1);
		assertEquals(1, jogo.getJogadorAtual()); // jogador um
		jogo.jogar(0, 2);
		assertEquals(2, jogo.getJogadorAtual()); // jogador dois
		jogo.jogar(1, 0);
		assertEquals(1, jogo.getJogadorAtual()); // jogador um
		jogo.jogar(1, 1);
		assertEquals(2, jogo.getJogadorAtual()); // jogador dois
		jogo.jogar(2, 0);
		assertEquals(1, jogo.getJogadorAtual()); // jogador um
		jogo.jogar(2, 1);
		assertEquals(2, jogo.getJogadorAtual()); // jogador dois
		jogo.jogar(2, 2);
		assertEquals(1, jogo.getJogadorAtual()); // jogador um
		jogo.jogar(1, 2);
		// verifica empate
		assertEquals(jogo.getJogadorVencedor(), -1);
	}
	
	@Test
	public void testJogadaInvalida01(){
		boolean ok = jogo.jogar(-1, -1);
		assertFalse(ok);
	}
	
	@Test
	public void testJogadaInvalida02(){
		boolean ok = jogo.jogar(-1, 2);
		assertFalse(ok);
	}
	
	@Test
	public void testJogadaInvalida03(){
		boolean ok = jogo.jogar(3, 3);
		assertFalse(ok);
	}
	
	@Test
	public void testJogadaInvalida04(){
		boolean ok = jogo.jogar(5, 2);
		assertFalse(ok);
	}
}
