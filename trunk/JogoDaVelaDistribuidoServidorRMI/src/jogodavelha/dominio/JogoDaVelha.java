package jogodavelha.dominio;

/**
 * Classe que representa um jogo da velha. Aqui est� contida 
 * toda a l�gica do jogo.
 */
public class JogoDaVelha {
	private int[][] tabuleiro; //tabuleiro (grade) do jogo
	//indica de quem � a vez de jogar. 
	//1 para primeiro jogador e 2 para segundo jogador
	private int jogadorAtual;
	//ver m�todo getJogadorVencedor()
	private int jogadorVencedor;
	//armazena a quantidade de jogadas realizadas
	private int	qtdJogadas;
	
	public JogoDaVelha(){
		jogadorAtual = 1;
		jogadorVencedor = 0;
		qtdJogadas = 0;
		tabuleiro = new int[3][3];
	}
	
	//Retorna true caso alguma linha, coluna ou diagonal tenha sido 
	//fechada por algum jogador
	private boolean fimDeJogo(){
		//verifica as linhas
		for(int i = 0; i < 3; i++){
			if(tabuleiro[i][0] != 0 &&
			   tabuleiro[i][0] == tabuleiro[i][1] && 
			   tabuleiro[i][0] == tabuleiro[i][2]){
				return true;
			}
		}
		//verifica as colunas
		for(int j = 0; j < 3; j++){
			if(tabuleiro[0][j] != 0 &&
			   tabuleiro[0][j] == tabuleiro[1][j] && 
			   tabuleiro[0][j] == tabuleiro[2][j]){
				return true;
			}
		}
		//verifica as diagonais
		if(tabuleiro[0][0] != 0 && 
		   tabuleiro[0][0] == tabuleiro[1][1] &&
		   tabuleiro[0][0] == tabuleiro[2][2]){
			return true;
		}
		if(tabuleiro[0][2] != 0 && 
		   tabuleiro[0][2] == tabuleiro[1][1] &&
		   tabuleiro[0][2] == tabuleiro[2][0]){
			return true;
		}
		return false;
	}
	
	/* Indica a marca��o em determinada posi��o da grade. Zero significa 
       posi��o livre, 1 significa que a posi��o foi marcada pelo primeiro 
       jogador e 2 significa que a posi��o foi marcada pelo segundo jogador.
       i - linha da posi��o que se quer a marca
       j - coluna da posi��o que se quer a marca */
	public int getMarcacao(int i, int j){
		return tabuleiro[i][j];
	}
 
	/* Realiza uma jogada (marca uma posi��o). Retorna true caso a jogada seja 
       v�lida e false em caso contr�rio. Caso a jogada seja v�lida, alterna o 
       jogador atual.
       i - linha da posi��o a marcar
       j - coluna da posi��o a marcar */
	public boolean jogar(int i, int j){
		if(i < 3 && i >= 0 && j < 3 && j >= 0 && tabuleiro[i][j] == 0){ //jogada v�lida
			tabuleiro[i][j] = jogadorAtual; //marca a grade com o n�mero do jogador
			qtdJogadas++;
			if(fimDeJogo()){ //o jogador fechou uma linha, coluna ou diagonal
				jogadorVencedor = jogadorAtual;
			}
			else if(qtdJogadas == 9){ //deu empate
				jogadorVencedor = -1;
			}
			else{ //a partida n�o acabou
				//troca jogador atual
				if(jogadorAtual == 1){
					jogadorAtual = 2;
				}
				else{
					jogadorAtual = 1;
				}
			}
			return true; //indica que a jogada � v�lida
		}
		else{ //jogada inv�lida
			return false;
		}
	}
 
	/* Retorna o n�mero do jogador atual, ou seja, que jogador tem a vez. 
	   1 indica primeiro jogador  e 2 indica segundo jogador. */
	public int getJogadorAtual(){
		return jogadorAtual;
	}
 
	/* Retorna o n�mero do jogador que venceu partida. 1 indica primeiro jogador, 
       2 indica segundo jogador, zero indica que a partida ainda n�o acabou e 
       -1 indica que a partida terminou empatada */
	public int getJogadorVencedor(){
		return jogadorVencedor;
	}
	
	public String toString(){
		String str = "";
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				str += getMarcacao(i, j) + " ";
			}
			str += "\n";
		}
		return str;
	}
}
