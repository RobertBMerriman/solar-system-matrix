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
	private int translation, halfTranslation;
	
	private int size, halfSize;
	
	private Color color;
	
	
	private boolean hasOrbit;
	
	
	
	
	public CosmicBody(CosmicBody parent, float rotation, float rotationDelta, int translation, int size, Color color)
	{
		this.parent = parent;
		
		this.rotation = rotation;
		this.rotationDelta = rotationDelta;
		
		this.translation = translation;
		this.halfTranslation = (int) (translation * 0.5f);
		
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
			bodyMatrix = bodyMatrix.mul(Matrix3x3f.translate(translation, translation));
		}
		else
		{
			bodyMatrix = Matrix3x3f.translate(translation, 0);
			bodyMatrix = bodyMatrix.mul(Matrix3x3f.rotate(rotation));
			bodyMatrix = bodyMatrix.mul(parent.getMatrix());
			rotation += rotationDelta;
		}
		
		bodyVector = bodyMatrix.mul(new Vector2f());
	}
	
	public void draw(Graphics g)
	{
		g.setColor(color);
		g.fillOval((int) (bodyVector.x - halfSize), (int) (bodyVector.y - halfSize), size, size);
	}
	
	public void drawOrbit(Graphics g)
	{
		if (hasOrbit)
		{
			g.setColor(Color.GRAY);
			g.drawOval((int) (parent.getVector().x - translation), (int) (parent.getVector().y - translation), halfTranslation, halfTranslation);
		}
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
