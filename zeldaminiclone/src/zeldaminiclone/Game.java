package zeldaminiclone;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener{

	//Tamanho da janela
	public static int WIDTH = 640, HEIGHT = 480;
	
	public static int SCALE = 3;
	
	//Chamando as classes
	public static Player player;
	public static World world;
	
	public List<Inimigo> inimigos = new ArrayList<Inimigo>();
	
	Color Salmao = Color.decode("#fcd8a8");
	/*NOTA 1: Apersar de eu colocar para o player nascer no meio da janela ele tá meio 
	 * torto*/
	
	//Insere os elementos no jogo
	public Game() {
		
		//Define o uso do keyboard
		this.addKeyListener(this);
		
		//Define a janela com o tamanho já definido
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		//Define onde a entidade player aparece
		new Spritesheet();
		player = new Player(240,240);
		world = new World ();
		inimigos.add(new Inimigo(32,32));
	}
	
	//Metodos de outras entidades
	public void tick() {
		player.tick();
		
		for(int i = 0; i<inimigos.size(); i++) {
			inimigos.get(i).tick();
		}
	}
	
	//Rederiza tudo
	public void render() {
		
		//Renderiza a janela constantimente 
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		//Renderiza o fundo
		g.setColor(Salmao);
		g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
		
		//Renderiza o player
		player.render(g);
		for(int i = 0; i<inimigos.size(); i++) {
			inimigos.get(i).render(g);
		}
		world.render(g);
		
		bs.show();
	}
	
	public static void main(String[] args ) {
		Game game = new Game();
		JFrame frame = new JFrame();
		
		//Titulo que fica na janela
		frame.add(game);
		frame.setTitle("Mini Zelda");

		frame.pack();
		
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		new Thread(game).start();
	}
	
	@Override
	public void run() {
		while(true) {
			tick();
			render();
			//60 fps
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	
	/*NOTA 2: Por algum motivo que eu desconheça no momento as teclas de up e down estão
	 * trocadas, então pra deixar o jogo funcionando certinho eu inverti no codigo tbm*/
	
	//Reconhece quando uma tecla e precionada 
	@Override
	public void keyPressed(KeyEvent e) {
		
		//Reconhece que a setinha foi precionada e define como true
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.up = true;
		} else if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.down = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_Z) {
			player.shoot = true;
		} 
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		//Reconhece que a setinha foi solta e define como false
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.up = false;
		} else if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.down = false;
		}
		
	}
	
}
