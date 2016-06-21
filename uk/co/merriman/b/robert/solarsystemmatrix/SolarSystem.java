package uk.co.merriman.b.robert.solarsystemmatrix;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.Random;
import javax.swing.*;

import uk.co.merriman.b.robert.solarsystemmatrix.utils.*;


public class SolarSystem extends JFrame implements Runnable
{
	
	private static final int SCREEN_W = 1280;
	private static final int SCREEN_H = 960;
	
	private FrameRate frameRate;
	private BufferStrategy bufferStrat;
	private volatile boolean running;
	private Thread gameThread;
	private KeyboardInput keyboard;
	
	private float earthRot, earthDelta;
	private float moonRot, moonDelta;
	
	private boolean showStars;
	private int[] stars;
	private Random rand = new Random();
	
	protected void createAndShowGUI()
	{
		// Set up canvas
		Canvas canvas = new Canvas();
		canvas.setSize(SCREEN_W, SCREEN_H);
		canvas.setBackground(Color.BLACK);
		canvas.setIgnoreRepaint(true);
		getContentPane().add(canvas);
		setTitle(this.getClass().getSimpleName());
		setIgnoreRepaint(true);
		pack();
		
		// Add key listeners
		keyboard = new KeyboardInput();
		canvas.addKeyListener(keyboard);
		
		// Set up canvas to draw to
		setVisible(true);
		canvas.createBufferStrategy(2);
		bufferStrat = canvas.getBufferStrategy();
		canvas.requestFocus();
		
		// Start game
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	@Override
	public void run()
	{
		running = true;
		initialise();
		while(running)
		{
			gameLoop();
		}
	}
	
	// Game loop with very simple logic and sleep mechanism
	private void gameLoop()
	{
		processInput();
		
		renderFrame();
		sleep(10L);
	}
	
	// Draws a frame to the screen using the BufferStrategy
	private void renderFrame()
	{
		do
		{
			do
			{
				Graphics g = null;
				try // If able to draw
				{
					g = bufferStrat.getDrawGraphics(); // Get the drawing surface
					g.clearRect(0, 0, getWidth(), getHeight()); // Clear the screen
					render(g); // Draw to it
				}
				finally // If not able to draw
				{
					if (g != null)
					{
						g.dispose(); // dispose of graphics to allow for next frame to be drawn
					}
				}
			} while (bufferStrat.contentsRestored()); // Draw again if the contents has recently been restored from an Alt-Tab or similar
			
			bufferStrat.show(); // Show drawn surface
			
		} while (bufferStrat.contentsLost()); // Keep trying to draw while the contents are lost
	}
	
	// Sleep for a specified number of milliseconds
	private void sleep(long sleep)
	{
		try
		{
			Thread.sleep(sleep);
		}
		catch (InterruptedException intrpEx)
		{
			intrpEx.printStackTrace();
		}
	}
	
	private void initialise()
	{
		frameRate = new FrameRate();
		frameRate.initialise();
		
		earthDelta = (float) Math.toRadians(0.5); // Move 0.5 degrees per frame
		moonDelta = (float) Math.toRadians(2.5); // Move 2.5 degrees per frame
		
		showStars = true;
		stars = new int[1000]; // 500 stars
		for (int i = 0; i < stars.length - 1; i += 2)
		{
			stars[i] = rand.nextInt(SCREEN_W); // Generate an x position
			stars[i + 1] = rand.nextInt(SCREEN_H); // Generate a y position
		}
	}
	
	private void processInput()
	{
		keyboard.poll();
		
		if (keyboard.keyDownOnce(KeyEvent.VK_SPACE))
			showStars = !showStars;
	}
	
	// Use the BufferStrategy's graphics to draw to the screen
	private void render(Graphics g)
	{
		g.setFont(new Font("Courier New", Font.PLAIN, 12));
		g.setColor(Color.GREEN);
		frameRate.calculate();
		g.drawString(frameRate.getFrameRate(), 20, 20);
		g.drawString("Press [SPACE] to toggle stars", 20, 50);
		
		// Draw Stars
		if (showStars)
		{
			g.setColor(Color.WHITE);
			for (int i = 0; i < stars.length - 1; i += 2)
				g.fillRect(stars[i], stars[i + 1], 1, 1);
		}
		
		// Draw the Sun
		Matrix3x3f sunMat = Matrix3x3f.identity();
		sunMat = sunMat.mul(Matrix3x3f.translate(SCREEN_W/2, SCREEN_H/2));
		Vector2f sun = sunMat.mul(new Vector2f());
		
		g.setColor(Color.YELLOW);
		g.fillOval((int) sun.x - 50, (int) sun.y - 50, 100, 100);
		
		
		// Draw Earth's orbit
		g.setColor(Color.GRAY);
		g.drawOval((int) sun.x - SCREEN_W / 4, (int) sun.y - SCREEN_W / 4, SCREEN_W / 2, SCREEN_W / 2);
		
		// Draw Earth
		Matrix3x3f earthMat = Matrix3x3f.translate(SCREEN_W / 4, 0);
		earthMat = earthMat.mul(Matrix3x3f.rotate(earthRot));
		earthMat = earthMat.mul(sunMat);
		earthRot += earthDelta;
		
		Vector2f earth = earthMat.mul(new Vector2f());
		g.setColor(Color.BLUE);
		g.fillOval((int) earth.x - 10, (int) earth.y - 10, 20, 20);
		
		
		// Draw the Moon
		Matrix3x3f moonMat = Matrix3x3f.translate(30, 0);
		moonMat = moonMat.mul(Matrix3x3f.rotate(moonRot));
		moonMat = moonMat.mul(earthMat);
		moonRot += moonDelta;
		
		Vector2f moon = moonMat.mul(new Vector2f());
		g.setColor(Color.LIGHT_GRAY);
		g.fillOval((int) moon.x - 5, (int) moon.y - 5, 10, 10);
		
	}
	
	// Catch a window closing event
	protected void onWindowClosing()
	{
		try
		{
			running = false; // Stop running the game
			gameThread.join(); // Wait for the thread to stop exectuting
		}
		catch (InterruptedException intrpEx)
		{
			intrpEx.printStackTrace();
		}
		
		System.exit(0); // Exit the program
	}
	
	public static void main(String[] args)
	{
		final SolarSystem app = new SolarSystem();
		app.addWindowListener(new WindowAdapter()
				{
					public void windowClosing(WindowEvent winEv)
					{
						app.onWindowClosing();
					}
				});
		
		
		SwingUtilities.invokeLater(new Runnable()
		{
				@Override
				public void run()
				{
					app.createAndShowGUI();
				}
				
			});
	}

}