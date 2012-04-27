package jogodavelha.dominio;

/**
 * Classe que representa um jogo da velha. Aqui está contida 
 * toda a lógica do jogo.
 */
public class JogoDaVelha {
	private int[][] tabuleiro; //tabuleiro (grade) do jogo
	//indica de quem é a vez de jogar. 
	//1 para primeiro jogador e 2 para segundo jogador
	private int jogadorAtual;
	//ver método getJogadorVencedor()
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
	
	/* Indica a marcação em determinada posição da grade. Zero significa 
       posição livre, 1 significa que a posição foi marcada pelo primeiro 
       jogador e 2 significa que a posição foi marcada pelo segundo jogador.
       i - linha da posição que se quer a marca
       j - coluna da posição que se quer a marca */
	public int getMarcacao(int i, int j){
		return tabuleiro[i][j];
	}
 
	/* Realiza uma jogada (marca uma posição). Retorna true caso a jogada seja 
       válida e false em caso contrário. Caso a jogada seja válida, alterna o 
       jogador atual.
       i - linha da posição a marcar
       j - coluna da posição a marcar */
	public boolean jogar(int i, int j){
		if(i < 3 && i >= 0 && j < 3 && j >= 0 && tabuleiro[i][j] == 0){ //jogada válida
			tabuleiro[i][j] = jogadorAtual; //marca a grade com o número do jogador
			qtdJogadas++;
			if(fimDeJogo()){ //o jogador fechou uma linha, coluna ou diagonal
				jogadorVencedor = jogadorAtual;
			}
			else if(qtdJogadas == 9){ //deu empate
				jogadorVencedor = -1;
			}
			else{ //a partida não acabou
				//troca jogador atual
				if(jogadorAtual == 1){
					jogadorAtual = 2;
				}
				else{
					jogadorAtual = 1;
				}
			}
			return true; //indica que a jogada é válida
		}
		else{ //jogada inválida
			return false;
		}
	}
 
	/* Retorna o número do jogador atual, ou seja, que jogador tem a vez. 
	   1 indica primeiro jogador  e 2 indica segundo jogador. */
	public int getJogadorAtual(){
		return jogadorAtual;
	}
 
	/* Retorna o número do jogador que venceu partida. 1 indica primeiro jogador, 
       2 indica segundo jogador, zero indica que a partida ainda não acabou e 
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
