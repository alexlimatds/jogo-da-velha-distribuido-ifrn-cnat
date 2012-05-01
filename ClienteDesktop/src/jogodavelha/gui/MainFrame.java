package jogodavelha.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import jogodavelha.rmi.IdInvalidoExcpetion;
import jogodavelha.rmi.Jogo;

public class MainFrame extends JFrame implements ActionListener{
	
	private JButton btnNovoJogo, btnSair;
	private JButton[][] btnCasa = new JButton[3][3];
	private Jogo jogo;
	private JLabel lblJogadoDaVez = new JLabel();
	private Integer idSessao, idJogador;
	
	public MainFrame() {
		super("Jogo da Velha Distribuído");
		
		JPanel pnlTabuleiro = new JPanel(new GridLayout(3, 3));
		for(int i = 0; i < btnCasa.length; i++){
			for(int j = 0; j < btnCasa[i].length; j++){
				btnCasa[i][j] = new JButton(" ");
				btnCasa[i][j].addActionListener(this);
				pnlTabuleiro.add(btnCasa[i][j]);
			}
		}
		
		JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnNovoJogo = new JButton("Novo Jogo");
		btnNovoJogo.addActionListener(this);
		pnlBotoes.add(btnNovoJogo);
		
		btnSair = new JButton("Sair");
		btnSair.addActionListener(this);
		pnlBotoes.add(btnSair);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(pnlTabuleiro, BorderLayout.CENTER);
		getContentPane().add(pnlBotoes, BorderLayout.SOUTH);
		getContentPane().add(lblJogadoDaVez, BorderLayout.NORTH);
		
		iniciarInterface();
		habilitarComandos(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setResizable(false);
	}
	
	private void iniciarInterface(){
		for(int i = 0; i < btnCasa.length; i++){
			for(int j = 0; j < btnCasa[i].length; j++){
				btnCasa[i][j].setText( " " );
			}
		}
		lblJogadoDaVez.setText("Desconectado");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			Object fonte = e.getSource();
			if(fonte == btnNovoJogo){
				novoJogo();
			}
			else if(fonte == btnSair){
				int opcao = JOptionPane.showConfirmDialog(this, "Sair do jogo?");
				if(opcao == JOptionPane.OK_OPTION){
					System.exit(0);
				}
				return;
			}
			else{
				if(jogo != null){
					processarJogada(fonte);
					/*int vencedor = jogo.getJogadorVencedor(idSessao);
					if(vencedor != 0){ //partida acabou
						if(vencedor == -1){
							JOptionPane.showMessageDialog(this, "A partida empatou!");
						}
						else if(vencedor == idJogador){
							JOptionPane.showMessageDialog(this, "Você venceu!");
						}
						else if(vencedor == idJogador){
							JOptionPane.showMessageDialog(this, "Você perdeu!");
						}
						lblJogadoDaVez.setText("Fim de jogo");
					}*/
				}
			}
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(this, "Ocorreu um erro");
			ex.printStackTrace();
		}
		/*catch(IdInvalidoExcpetion ex){
			JOptionPane.showMessageDialog(this, "Sessão expirada");
		}
		catch(RemoteException ex){
			JOptionPane.showMessageDialog(this, "Falha na conexão com o servidor.");
		}*/
	}
	
	private boolean processarFimDeJogo() throws RemoteException, IdInvalidoExcpetion{
		int vencedor = jogo.getJogadorVencedor(idSessao);
		if(vencedor != 0){ //partida acabou
			habilitarComandos(false);
			if(vencedor == -1){
				JOptionPane.showMessageDialog(this, "A partida empatou!");
			}
			else if(vencedor == idJogador){
				JOptionPane.showMessageDialog(this, "Você venceu!");
			}
			else{
				JOptionPane.showMessageDialog(this, "Você perdeu!");
			}
			lblJogadoDaVez.setText("Fim de jogo");
			return true;
		}
		return false;
	}
	
	private void processarJogada(Object fonte){
		for(int i = 0; i < btnCasa.length; i++){
			for(int j = 0; j < btnCasa[i].length; j++){
				if(btnCasa[i][j] == fonte){
					final int l = i;
					final int c = j;
					habilitarComandos(false);
					lblJogadoDaVez.setText("Aguarde...");
					SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
						@Override
						public Void doInBackground() throws Exception {
							boolean jogadaOk = jogo.marcarPosicao(l, c, idSessao, idJogador);
							if(!jogadaOk){
								JOptionPane.showMessageDialog(null, "Jogada inválida");
								lblJogadoDaVez.setText("Sua vez...");
								habilitarComandos(true);
							}
							else{
								int[][] tabuleiro = jogo.getTabuleiro(idSessao);
								lblJogadoDaVez.setText("Aguarde sua vez.");
								atualizarInterface(tabuleiro); //atualização da minha jogada
								if(!processarFimDeJogo()){ //verifica se o jogo acabou após a minha jogada
									//jogo não acabou, então espero a jogada do oponente
									jogo.aguardarResposta(idSessao, idJogador);
									tabuleiro = jogo.getTabuleiro(idSessao);
									atualizarInterface(tabuleiro); //atualização da jogada do oponente
									if(!processarFimDeJogo()){
										habilitarComandos(true);
										lblJogadoDaVez.setText("Sua vez.");
									}
								}
							}
							return null;
						}
					};
					worker.execute();
				}
			}
		}
	}
	
	private void atualizarInterface(int[][] tabuleiro){
		for(int i = 0; i < btnCasa.length; i++){
			for(int j = 0; j < btnCasa[i].length; j++){
				int v = tabuleiro[i][j];
				btnCasa[i][j].setText( getMarca(v) );
			}
		}
	}
	
	private void novoJogo(){
		FrmNovoJogo frm = new FrmNovoJogo(this);
		frm.setLocationRelativeTo(this);
		frm.setVisible(true);
		int acao = frm.getAcao();
		try{
			if(acao != FrmNovoJogo.CANCELAR){
				habilitarComandos(false);
				jogo = (Jogo)Naming.lookup("rmi://127.0.0.1/jogo"); //busca um objeto Jogo no servidor RMI
				//cria nova sessão (partida)
				if(acao == FrmNovoJogo.NOVA_SESSAO){
					int[] ids = jogo.criarPartida();
					idSessao = ids[0];
					idJogador = ids[1];
					System.out.println("Sessão criada com o id " + idSessao);
					System.out.println("Aguarde entrada do outro jogador");
					lblJogadoDaVez.setText("Aguarde entrada do outro jogador");
					SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
						@Override
						public Void doInBackground() throws Exception {
							jogo.aguardarResposta(idSessao, idJogador);
							System.out.println("Partida iniciada.");
							lblJogadoDaVez.setText("Partida iniciada.");
							System.out.println("Aguarde sua vez...");
							lblJogadoDaVez.setText("Aguarde sua vez...");
							jogo.aguardarResposta(idSessao, idJogador);
							int[][] tabuleiro = jogo.getTabuleiro(idSessao);
							atualizarInterface(tabuleiro);
							return null;
						}
						@Override
						public void done() {
							System.out.println("Sua vez");
							lblJogadoDaVez.setText("Sua vez.");
							habilitarComandos(true);
						}
					};
					worker.execute();
				}
				//entra em uma sessão
				else if(acao == FrmNovoJogo.SESSAO_EXISTENTE){
					idSessao = frm.getIdSessao();
					System.out.println("Entrando na sessão " + idSessao);
					SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
						@Override
						public Void doInBackground() throws Exception {
							System.out.println("Estabelecendo conexão...");
							lblJogadoDaVez.setText("Estabelecendo conexão...");
							idJogador = jogo.entrarNaPartida(idSessao);
							System.out.println("Partida iniciada.");
							lblJogadoDaVez.setText("Partida iniciada.");
							System.out.println("Aguarde sua vez...");
							lblJogadoDaVez.setText("Aguarde sua vez...");
							jogo.aguardarResposta(idSessao, idJogador);
							int[][] tabuleiro = jogo.getTabuleiro(idSessao);
							atualizarInterface(tabuleiro);
							return null;
						}
						@Override
						public void done() {
							System.out.println("Sua vez");
							lblJogadoDaVez.setText("Sua vez.");
							habilitarComandos(true);
						}
					};
					worker.execute();
				}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Falha na conexão com o servidor.");
		}
	}
	
	private void habilitarComandos(boolean b){
		for(int i = 0; i < btnCasa.length; i++){
			for(int j = 0; j < btnCasa[i].length; j++){
				btnCasa[i][j].setEnabled(b);
			}
		}
	}
	
	private String getMarca(int x){
		if(x == 1)
			return "X";
		else if(x == 2)
			return "O";
		else if(x == 0)
			return " ";
		else
			throw new IllegalArgumentException("x inválido");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}

}
