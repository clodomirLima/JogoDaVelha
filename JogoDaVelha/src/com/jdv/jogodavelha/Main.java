package com.jdv.jogodavelha;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

// classe principal do projeto
public class Main extends Activity {

	// definicao  da constante de cada botao
	
	// ultilizamos essa constantes para recuperar o botao atrave do metodo getQuadrado
	// cria uma constante de quadrado
	private final String QUADRADO = "quadrado";

	// constante que sera impressa  no text do menu
	private final String bola = "O";
	private final String xis = "X";

	// guarda o ultimo valor jogado
	private  String lastPlay = "X";

	// chama o pacote View e guarda numa variavel
	// guarda a instancia da nossa view 
	private View view;

	// matrix que guarda os valores e determina se o jogo acabou
	int[][] estadoFinal = new int[][]{
			// verifica as linhas
			{1,2,3},//1
			{4,5,6},//2
			{7,8,9},//3

			// verifica as colunas
			{1,4,7},//4
			{2,5,8},//5
			{3,6,9},//6

			// verifica as diagonais
			{1,5,9},//7
			{3,5,7},//8
	};

	// metodo principal do projeto onde vai chamar as views ou arquivo main.xml
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// guarda a instancia do arquivo main.xml dentro da variavel view
		// infla o arquivo main.xml e guarda a instancia dele dentro da View
		setView(getLayoutInflater().inflate(R.layout.main, null));

		// aqui estamos passando a nossa instancia da nossa View para a nossa Activity
		setContentView(getView());

		// precisa guarda a instancia da view que se ta criando poi isso nao
		// pode chamar a viem main.xml pelo "setContentView(R.layout.main);" 
	}

	// cria o metodo clickQuadrado 
	public void clickQuadrado(View v){
		
		if(!isFim()){// verifica se o jogo acabou
		if(((Button)v).getText().equals("")){// verifica se o texto do botao que esta vindo pela variavel v
			// e diferente de vazio

			// começa a atribuir o valor X ou O se estiver vazio
			if(getLastPlay().equals(xis)){// verifica se o ultimo valor jogado e ingual a xis
				((Button)v).setText(bola);// se for e jogado bola 
				setLastPlay(bola);// seta lastPlay() como bola
				// para que da proxima vez ele nao entre nessa condiçao
			}else{
				((Button)v).setText(xis);// seta o texto do botao como X
				// atribui o valor xis a tag
				setLastPlay(xis);// seta o valor de lastPlay() como X 
			}
		}else{
			// exibe a mensagem de que ja foi jogado
			Toast.makeText(getView().getContext(),"Ja foi Jogado!", Toast.LENGTH_SHORT).show();
		}
		
		// chama o metodo isFim() da classe la em baixo
		isFim();// verifica se e o fim do jogo
		// exibe a mensagem de que quadrado esta sendo clicado
		//Toast.makeText(getView().getContext(),v.getTag().toString(), Toast.LENGTH_SHORT).show();
		}
	}


	// recupera todos os botoes do arquivo main.xml por um laço
	public Button getQuadrado(int tagNum){// retorna o respectivo quadrado requerido pela variavel tagNum 
		// localiza a constante pela tag no arquivo main.xml

		// findViewWithTag() busca a View atraves da tag 
		return (Button)getView().findViewWithTag(QUADRADO+tagNum);
	}

	// looping para percorrer os nove quadrados do jogo e habilitar os quadrados
	// e invocado quando o botao do main.xml e pressionado
	public void newGame(View v){

		// habilita os quadrados
		setEnableQuadrado(true);// ativa os quadrados
		setColorBlack();// pinta o texto dos quadrados de preto
		limpaCampos();// invoca o metodo limpaCampos limpando os campos
		
		// guarda os valores do radio button de X e O
		// retorna a instansia do nossso radio button rX e rO
		// e armazena a instancia deles em rX ou rO.
		RadioButton rX = (RadioButton)getView().findViewById(R.id.rbX); 
		RadioButton rO = (RadioButton)getView().findViewById(R.id.rbO); 
		
		// verifica qual radioButton esta selecionado
		if(rX.isChecked()){//verifica se rX esta checkado
			setLastPlay(bola);
			// altera o lastPlay() para o inverso do que o usuario esta querendo jogar
			// fazemos isso porque sempre jogamos o oposto que estiver contido em lastPlay() 
		}else{
			if(rO.isChecked()){
				setLastPlay(xis);
				// indicamos que o ultimo a jogar foi xis porque queremos começar jogar com  bola
			}
		}
	}
	// metodo para limpa os campos
	public void limpaCampos(){
		// loopin para percorrer os quadrados
				for(int i=0; i<=9; ++i){// percorrer todos os botoes
					if(getQuadrado(i) != null){// verifica se o botao ta recuperando ele e diferente de null
						getQuadrado(i).setText("");// seta o texto como vazio e limpa o campo
					}
				}
	}

	// funcao para habilitar todos os quadrados
	public void setEnableQuadrado(boolean b){

		// o laco comeca com um porque o primeiro quadrado e 1
		for(int i=1; i<=9; ++i){// percorre todos os campos
			if(getQuadrado(i) != null){// verifica se quadrado e diferente de null
				getQuadrado(i).setEnabled(b);// se for passa o valor de para b para o respectivo quadrado
			}
		}
	}

	// pinta os quadrados que ganharam o jogo
	public void setColorQuadrado(int btn, int colorX){
		
		// recebe o numero do botao pela variavel pelo btn
		// passa a instancia da nossa cor pela variavel colorX
		getQuadrado(btn).setTextColor(getResources().getColor(colorX));
		// recuperamos o botao e setamos o textColor com a cor passada pela variavel colorX
	}
	
	// pinta os quadrados para preto
	public void setColorBlack(){
		for(int i=0; i<=9; ++i){// percorre todos os quadrados 
			if(getQuadrado(i)!=null){// verifica se o quadrado e diferente de null
				setColorQuadrado(i, R.color.black);// envia a cor preta para o metodo setColor()
			}
		}
	}
	
	// verifica se o jogo acabou
	public boolean isFim(){

		//Toast.makeText(getView().getContext(),"bla", Toast.LENGTH_SHORT).show();

		// guarda os valores dos botoes que foram pressionado e depois compara eles
		// vamos percorrer todas as nossas condicoes definidas na nossa matriz
		for(int i=0; i<=7; ++i){
			
			// guarda os valores dos botoes
			String s1 = getQuadrado(estadoFinal[i][0]).getText().toString();
			String s2 = getQuadrado(estadoFinal[i][1]).getText().toString();
			String s3 = getQuadrado(estadoFinal[i][2]).getText().toString();
			
			// verificamos se s1, s2 e s3 sao diferentes de vazio
			// temos que verificar porque se nao o jogo acaba na primeira jogada
			// porque o texto dos botoes vao conter o mesmo texto 
			// no caso serao vazio
			// verifica se os quadrados nao estao vazios
			if ((!s1.equals("")) && (!s2.equals("")) && (!s3.equals(""))){

				// compara todos os valores dos botoes e verifica se o jogo acabou
				if((s1.equals(s2)) && (s2.equals(s3))){
					// caso os 3 botoes sejam iguais e pintado-os de vermelho
					setColorQuadrado( estadoFinal[i][0],R.color.red);
					setColorQuadrado( estadoFinal[i][1],R.color.red);
					setColorQuadrado( estadoFinal[i][2],R.color.red);
					
					// exibe a mensagem para o usuario que o jogo acabou
					Toast.makeText(getView().getContext(),"Fim do jogo!", Toast.LENGTH_LONG).show();
					return true;// retorna verdadeiro
				}
			}
		}
		return false;// retorna falso
	}

	// Ctrl + 3 chama o get e o set
	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	// cria o get e o set do lastPlay
	public String getLastPlay() {
		return lastPlay;
	}

	public void setLastPlay(String lastPlay) {
		this.lastPlay = lastPlay;
	}

}
