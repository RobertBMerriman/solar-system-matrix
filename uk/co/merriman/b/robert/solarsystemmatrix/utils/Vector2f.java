package uk.co.merriman.b.robert.solarsystemmatrix.utils;

public class Vector2f
{

	public float x, y, w;

	// Default constructor
	public Vector2f()
	{
		this.x = 0.0f;
		this.y = 0.0f;
		this.w = 1.0f; // !?!
	}

	// Clone a Vector2f
	public Vector2f(Vector2f v)
	{
		this.x = v.x;
		this.y = v.y;
		this.w = v.w;
	}

	// Construct with given x and y values (defaulting w)
	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
		this.w = 1.0f; // !?!
	}

	// Construct with given x, y and w values
	public Vector2f(float x, float y, float w)
	{
		this.x = x;
		this.y = y;
		this.w = w;
	}

	// Translate
	public void translate(float tx, float ty)
	{
		x += tx;
		y += ty;
	}

	// Scale
	public void scale(float sx, float sy)
	{
		x *= sx;
		y *= sy;
	}

	// Rotate (using radians)
	public void rotate(float rad)
	{	// Using a temporary X so that the rotations are applied to the original values before they are reassigned
		float tmpX = (float) (x * Math.cos(rad) - y * Math.sin(rad));
		y = (float) (x * Math.sin(rad) - y * Math.cos(rad));
		x = tmpX;
	}
	
	// Shear
	public void shear(float sx, float sy)
	{	// Using a temporary X so that the rotations are applied to the original values before they are reassigned
		float tmpX = x + sx * y;
		y = y + sy * x;
		x = tmpX;
	}

}
