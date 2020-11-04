package visao;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import excecao.RequisicaoNaoEncontradoException;
import modelo.Cliente;
import modelo.Requisicao;
import servico.ClienteService;
import servico.RequisicaoService;


public class DialogRequisicao extends Dialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private static RequisicaoService requisicaoService;
	
	private static ClienteService clienteService; 
	
	static
    {
    	@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

    	requisicaoService = (RequisicaoService)fabrica.getBean ("requisicaoService");
    	clienteService = (ClienteService)fabrica.getBean("clienteService");
    }
	
	private JButton novoButton;
	private JButton cadastrarButton;
	private JButton editarButton;
	private JButton alterarButton;
	private JButton removerButton;
	private JButton cancelarButton;
	private JButton buscarButton;
	
	private JTextField nomeDoProdutoTextField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel nomeDoProdutoMensagem2;
	private JLabel dataMensagem2;
	
	private JPanel panel;
	
	private Requisicao umaRequisicao;
	private JTextField dataTextField;
	private JTextField clienteTextField;
	
	private Cliente cliente;
	
	public void designaRequisicaoAFrame(Requisicao umaRequisicao){
		this.umaRequisicao = umaRequisicao;
		
		nomeDoProdutoTextField.setText(umaRequisicao.getNomeDoProduto());
		dataTextField.setText(umaRequisicao.getData());
		
		nomeDoProdutoMensagem2.setText("");
		dataMensagem2.setText("");
	}
	
	public void designaClienteAFrame(Cliente cliente) {

		this.cliente = cliente;
		clienteTextField.setText(cliente.getNome());
	}
	
	public DialogRequisicao(JFrame frame){
super(frame);
		
		setBounds(105, 147, 618, 330);
		setTitle("Cadastro de Requisicoes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 0, 602, 292);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel cadastrarLabel = new JLabel("Cadastro de Requisicoes");
		cadastrarLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cadastrarLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		cadastrarLabel.setBounds(189, 21, 190, 26);
		panel.add(cadastrarLabel);
		
		JLabel nomeLabel = new JLabel("Nome");
		nomeLabel.setBounds(62, 78, 46, 14);
		panel.add(nomeLabel);
		
		nomeDoProdutoTextField = new JTextField();
		nomeLabel.setLabelFor(nomeDoProdutoTextField);
		nomeDoProdutoTextField.setBounds(138, 75, 278, 20);
		panel.add(nomeDoProdutoTextField);
		nomeDoProdutoTextField.setColumns(50);
		nomeDoProdutoTextField.setBackground(SystemColor.window);
		nomeLabel.setBounds(62, 78, 46, 14);
		
		novoButton = new JButton("Novo");
		novoButton.setBounds(452, 50, 96, 23);
		panel.add(novoButton);
		novoButton.addActionListener(this);

		cadastrarButton = new JButton("Cadastrar");
		cadastrarButton.setBounds(452, 77, 96, 23);
		panel.add(cadastrarButton);
		cadastrarButton.addActionListener(this);
		
		editarButton = new JButton("Editar");
		editarButton.setBounds(452, 104, 96, 23);
		panel.add(editarButton);
		editarButton.addActionListener(this);
		
		alterarButton = new JButton("Alterar");
		alterarButton.setBounds(452, 131, 96, 23);
		panel.add(alterarButton);
		alterarButton.addActionListener(this);

		removerButton = new JButton("Remover");
		removerButton.setBounds(452, 158, 96, 23);
		panel.add(removerButton);
		removerButton.addActionListener(this);

		cancelarButton = new JButton("Cancelar");
		cancelarButton.setBounds(452, 185, 96, 23);
		panel.add(cancelarButton);
		cancelarButton.addActionListener(this);

		buscarButton = new JButton("Buscar");
		buscarButton.setBounds(452, 212, 96, 23);
		panel.add(buscarButton);
		
		dataTextField = new JTextField();
		dataTextField.setBounds(136, 132, 280, 20);
		panel.add(dataTextField);
		dataTextField.setColumns(10);
		
		JLabel dataLabel = new JLabel("Data");
		dataLabel.setBounds(62, 135, 46, 14);
		panel.add(dataLabel);
		
		clienteTextField = new JTextField();
		clienteTextField.setBounds(138, 213, 278, 20);
		panel.add(clienteTextField);
		clienteTextField.setColumns(10);
		
		JLabel clienteLabel = new JLabel("Cliente");
		clienteLabel.setBounds(62, 216, 46, 14);
		panel.add(clienteLabel);
		
		nomeDoProdutoMensagem2 = new JLabel("New label");
		nomeDoProdutoMensagem2.setBounds(138, 104, 278, 14);
		panel.add(nomeDoProdutoMensagem2);
		nomeDoProdutoMensagem2.setVisible(false);

		dataMensagem2 = new JLabel("");
		dataMensagem2.setHorizontalAlignment(SwingConstants.LEFT);
		dataMensagem2.setBounds(138, 162, 278, 14);
		panel.add(dataMensagem2);
		dataMensagem2.setVisible(false);	
		buscarButton.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object obj = e.getSource();
		if (obj == novoButton)
		{	novo();
		}
		else if (obj == cadastrarButton)
		{
			boolean deuErro = validaRequisicao();
			
			if(!deuErro)
			{	
				umaRequisicao = new Requisicao();
				umaRequisicao.setNomeDoProduto(nomeDoProdutoTextField.getText());
				umaRequisicao.setData(dataTextField.getText());
				umaRequisicao.setCliente(cliente);
				requisicaoService.inclui(umaRequisicao);
				
				salvo();
				
				JOptionPane.showMessageDialog(this, "Requisicao cadastrada com sucesso", "", 
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	
		else if(obj == editarButton)
		{
			editavel();
		}
		else if(obj == alterarButton)
		{
			boolean deuErro = validaRequisicao();
			
			if(!deuErro)
			{	
				umaRequisicao = new Requisicao();
				umaRequisicao.setNomeDoProduto(nomeDoProdutoTextField.getText());
				umaRequisicao.setData(dataTextField.getText());
				
				try{
					requisicaoService.altera(umaRequisicao);
					salvo();
					JOptionPane.showMessageDialog(this, "Requisicao cadastrada com sucesso", "", 
							JOptionPane.INFORMATION_MESSAGE);
				} catch(RequisicaoNaoEncontradoException e1){
					
					novo();
					
					JOptionPane.showMessageDialog(this, "Requisicao cadastrada com sucesso", "", 
							JOptionPane.ERROR_MESSAGE);
					
					
				}
					
			}
		}
		else if(obj == removerButton)
		{
			try 
			{
				requisicaoService.exclui(umaRequisicao);
	
				removido();
				
				JOptionPane.showMessageDialog(this, "Requisicao removido com sucesso", "", 
						JOptionPane.INFORMATION_MESSAGE);
			} 
			catch (RequisicaoNaoEncontradoException e1) 
			{
				novo();
				
				JOptionPane.showMessageDialog(this, "Requisicao não encontrado", "", 
					JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(obj == cancelarButton)
		{
			try 
			{
				umaRequisicao = requisicaoService.recuperaUmaRequisicao(umaRequisicao.getNumero());
	
				nomeDoProdutoTextField.setText(umaRequisicao.getNomeDoProduto());
				
				dataTextField.setText(umaRequisicao.getData());
				
				cancelado();
			} 
			catch (RequisicaoNaoEncontradoException e1) 
			{
				novo();
				
				JOptionPane.showMessageDialog(this, "Requisicao não encontrado", "", 
					JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(obj == buscarButton){
			
			DialogTabelaCliente dialog = new DialogTabelaCliente(this);
			dialog.setVisible(true);
			
		}
	}
	
	private boolean validaRequisicao()
	{
		boolean deuErro = false;
		if (nomeDoProdutoTextField.getText().trim().length() == 0)
		{	deuErro = true;
			nomeDoProdutoMensagem2.setText("Campo de preenchimento obrigatório");
		}
		else
		{	nomeDoProdutoMensagem2.setText("");
		}
		
		if (dataTextField.getText().trim().length() == 0)
		{	deuErro = true;
			dataMensagem2.setText("Campo de preenchimento obrigatório");
		}
		else
		{	dataMensagem2.setText("");
		}
		return deuErro;
	}
	
	public void novo()
	{
		nomeDoProdutoTextField.setEnabled(true);
		dataTextField.setEditable(true);
		
		nomeDoProdutoTextField.setText("");
		dataTextField.setText("");
		buttonGroup.clearSelection();
		

		novoButton.setEnabled(false);
		cadastrarButton.setEnabled(true);
		editarButton.setEnabled(false);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(false);
		cancelarButton.setEnabled(false);
		
	}
	
	public void salvo()
	{
		nomeDoProdutoTextField.setEnabled(false);
		dataTextField.setEnabled(false);

		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(true);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(true);
		cancelarButton.setEnabled(false);
		
	}
	
	public void editavel()
	{
		nomeDoProdutoTextField.setEnabled(true);
		dataTextField.setEnabled(true);
		
		novoButton.setEnabled(false);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(false);
		alterarButton.setEnabled(true);
		removerButton.setEnabled(false);
		cancelarButton.setEnabled(true);
		
	}
	
	public void removido()
	{
		nomeDoProdutoTextField.setEnabled(false);
		dataTextField.setEnabled(false);
		
		
		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(false);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(false);
		cancelarButton.setEnabled(false);
		
	}
	
	public void cancelado()
	{
		nomeDoProdutoTextField.setEnabled(false);
		dataTextField.setEnabled(true);
		
		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(true);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(true);
		cancelarButton.setEnabled(false);
		
	}
}
