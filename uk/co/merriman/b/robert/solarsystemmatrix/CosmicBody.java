package uk.co.merriman.b.robert.solarsystemmatrix;

import java.awt.Color;
import java.awt.Graphics;

import uk.co.merriman.b.robert.solarsystemmatrix.utils.Matrix3x3f;
import uk.co.merriman.b.robert.solarsystemmatrix.utils.Vector2f;

public class CosmicBody
{
	
	private CosmicBody parent;
	private Matrix3x3f bodyMatrix;
	private Vector2f bodyVector;
	
	private float rotation, rotationDelta;
	private int translationX, doubleTranslationX, translationY;
	
	private int size, halfSize;
	
	private Color color;
	
	
	private boolean hasOrbit;
	
	
	
	
	public CosmicBody(CosmicBody parent, float rotation, float rotationDelta, int translationX, int translationY, int size, Color color)
	{
		this.parent = parent;
		
		this.rotation = rotation;
		this.rotationDelta = rotationDelta;
		
		this.translationX = translationX;
		this.doubleTranslationX = translationX * 2;
		this.translationY = translationY;
		
		this.size = size;
		this.halfSize = (int) (size * 0.5f);
		
		this.color = color;
		
		hasOrbit = true;
	}
	
	public void move()
	{
		
		if (parent == null) // No parent = no rotation
		{
			hasOrbit = false;
			bodyMatrix = Matrix3x3f.identity();
			bodyMatrix = bodyMatrix.mul(Matrix3x3f.translate(translationX, translationY));
		}
		else
		{
			bodyMatrix = Matrix3x3f.translate(translationX, translationY);
			bodyMatrix = bodyMatrix.mul(Matrix3x3f.rotate(rotation));
			bodyMatrix = bodyMatrix.mul(parent.getMatrix());
			rotation += rotationDelta;
		}
		
		bodyVector = bodyMatrix.mul(new Vector2f());
	}
	
	public void drawOrbit(Graphics g)
	{
		if (hasOrbit)
		{
			g.setColor(Color.GRAY);
			g.drawOval((int) (parent.getVector().x - translationX), (int) (parent.getVector().y - translationX), doubleTranslationX, doubleTranslationX);
		}
	}
	
	public void draw(Graphics g)
	{
		g.setColor(color);
		g.fillOval((int) (bodyVector.x - halfSize), (int) (bodyVector.y - halfSize), size, size);
	}
	
	
	public Matrix3x3f getMatrix()
	{
		return bodyMatrix;
	}
	
	public Vector2f getVector()
	{
		return bodyVector;
	}
	
	
}
