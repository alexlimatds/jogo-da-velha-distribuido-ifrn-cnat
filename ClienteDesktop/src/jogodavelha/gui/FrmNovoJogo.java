package jogodavelha.gui;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class FrmNovoJogo extends JDialog implements ActionListener {
	
	public static final int NOVA_SESSAO = 1, SESSAO_EXISTENTE = 2, CANCELAR = 0;
	
	private JRadioButton rdbtNovoJogo, rdbtSessaoExistente;
	private JTextField txtIdSessao;
	private JButton btnOk, btnCancelar;
	private int acao, idSessao;
	
	public FrmNovoJogo(Frame parent) {
		super(parent, "Novo Jogo", true);
		
		ButtonGroup btnGroup = new ButtonGroup();
		
		rdbtNovoJogo = new JRadioButton("Novo jogo");
		btnGroup.add(rdbtNovoJogo);
		
		rdbtSessaoExistente = new JRadioButton("Sessão aberta");
		btnGroup.add(rdbtSessaoExistente);
		
		txtIdSessao = new JTextField(10);
		
		btnOk = new JButton("OK");
		btnOk.addActionListener(this);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		
		getContentPane().setLayout(new GridLayout(3, 2));
		add(rdbtNovoJogo);
		add(new JLabel("          "));
		add(rdbtSessaoExistente);
		add(txtIdSessao);
		add(btnOk);
		add(btnCancelar);
		
		pack();
	}
	
	public int getAcao(){
		return acao;
	}
	
	public int getIdSessao(){
		return idSessao;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == btnOk){
			if(rdbtNovoJogo.isSelected()){
				acao = NOVA_SESSAO;
				idSessao = -1;
			}
			else if(rdbtSessaoExistente.isSelected()){
				idSessao = Integer.parseInt(txtIdSessao.getText());
				acao = SESSAO_EXISTENTE;
			}
			dispose();
		}
		else if(source == btnCancelar){
			acao = CANCELAR;
			idSessao = -1;
			dispose();
		}
	}

}
