package uk.co.merriman.b.robert.solarsystemmatrix;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

import uk.co.merriman.b.robert.solarsystemmatrix.utils.*;


public class SolarSystem extends JFrame implements Runnable
{
	private static final long serialVersionUID = 3543428091734294515L;

	private static final int SCREEN_W = 1280;
	private static final int SCREEN_H = 960;

	private FrameRate frameRate;
	private BufferStrategy bufferStrat;
	private volatile boolean running;
	private Thread gameThread;
	private KeyboardInput keyboard;

	private ArrayList<CosmicBody> cosmicBodies;

	private boolean showStars;
	private int[] stars;
	private Random rand = new Random();

	private boolean showOrbits;

	// Comment

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

		cosmicBodies = new ArrayList<CosmicBody>();

		CosmicBody sun = new CosmicBody(null, 0.0f, 0.0f, SCREEN_W / 2, SCREEN_H / 2, 100, Color.YELLOW);
		cosmicBodies.add(sun);
		CosmicBody mercury = new CosmicBody(sun, 0.0f, (float) Math.toRadians(1.0), SCREEN_W / 8, 0, 12, Color.DARK_GRAY);
		cosmicBodies.add(mercury);
		CosmicBody venus = new CosmicBody(sun, 0.0f, (float) Math.toRadians(3.0), SCREEN_W / 6, 0, 18, Color.ORANGE);
		cosmicBodies.add(venus);
		CosmicBody earth = new CosmicBody(sun, 0.0f, (float) Math.toRadians(0.5), SCREEN_W / 4, 0, 20, Color.BLUE);
		cosmicBodies.add(earth);
		CosmicBody moon = new CosmicBody(earth, 0.0f, (float) Math.toRadians(2.5), 30, 0, 10, Color.LIGHT_GRAY);
		cosmicBodies.add(moon);
		CosmicBody mars = new CosmicBody(sun, 0.0f, (float) Math.toRadians(2.0), SCREEN_W / 3, 0, 16, Color.RED);
		cosmicBodies.add(mars);

		showStars = true;
		stars = new int[1000]; // 500 stars
		for (int i = 0; i < stars.length - 1; i += 2)
		{
			stars[i] = rand.nextInt(SCREEN_W); // Generate an x position
			stars[i + 1] = rand.nextInt(SCREEN_H); // Generate a y position
		}

		showOrbits = false;
	}

	private void processInput()
	{
		keyboard.poll();

		if (keyboard.keyDownOnce(KeyEvent.VK_S))
			showStars = !showStars;

		if (keyboard.keyDownOnce(KeyEvent.VK_O))
			showOrbits = !showOrbits;

	}

	// Use the BufferStrategy's graphics to draw to the screen
	private void render(Graphics g)
	{
		g.setFont(new Font("Courier New", Font.PLAIN, 12));
		g.setColor(Color.GREEN);
		frameRate.calculate();
		g.drawString(frameRate.getFrameRate(), 20, 20);
		g.drawString("Press [S] to toggle stars", 20, 50);
		g.drawString("Press [O] to toggle orbits", 20, 80);


		// Draw Stars
		if (showStars)
		{
			g.setColor(Color.WHITE);
			for (int i = 0; i < stars.length - 1; i += 2)
				g.fillRect(stars[i], stars[i + 1], 1, 1);
		}


		for (CosmicBody cosmicBody : cosmicBodies)
		{
			cosmicBody.move();
			if (showOrbits)
				cosmicBody.drawOrbit(g);
			cosmicBody.draw(g);
		}



		// End draw
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
