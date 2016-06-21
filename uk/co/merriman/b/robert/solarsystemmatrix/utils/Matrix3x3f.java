package uk.co.merriman.b.robert.solarsystemmatrix.utils;

public class Matrix3x3f
{

	
	private float[][] m = new float[3][3];
	
	public Matrix3x3f(float[][] m)
	{
		setMatrix(m);
	}
	
	public void setMatrix(float[][] m)
	{
		this.m = m;
	}
	
	/**
	 * Adds two matrices together by adding their individual values one by one and returning the new matrix
	 * 
	 * @param m1 Matrix to add
	 * @return The resultant matrix
	 */
	public Matrix3x3f add(Matrix3x3f m1)
	{
		return new Matrix3x3f(new float[][]
		{
			{ // M[0]
				this.m[0][0] + m1.m[0][0], // M[0][0]
				this.m[0][1] + m1.m[0][1], // M[0][1]
				this.m[0][2] + m1.m[0][2]  // M[0][2]
			},
			{ // M[1]
				this.m[1][0] + m1.m[1][0], // M[1][0]
				this.m[1][1] + m1.m[1][1], // M[1][1]
				this.m[1][2] + m1.m[1][2]  // M[1][2]
			},
			{ // M[2]
				this.m[2][0] + m1.m[2][0], // M[2][0]
				this.m[2][1] + m1.m[2][1], // M[2][1]
				this.m[2][2] + m1.m[2][2]  // M[2][2]
			}
		});
	}
	
	/**
	 * Subtracts one matrix from another by subtracting their individual values one by one and returning the new matrix
	 * 
	 * @param m1 Matrix to subtract
	 * @return The resultant matrix
	 */
	public Matrix3x3f sub(Matrix3x3f m1)
	{
		return new Matrix3x3f(new float[][]
		{
			{ // M[0]
				this.m[0][0] - m1.m[0][0], // M[0][0]
				this.m[0][1] - m1.m[0][1], // M[0][1]
				this.m[0][2] - m1.m[0][2]  // M[0][2]
			},
			{ // M[1]
				this.m[1][0] - m1.m[1][0], // M[1][0]
				this.m[1][1] - m1.m[1][1], // M[1][1]
				this.m[1][2] - m1.m[1][2]  // M[1][2]
			},
			{ // M[2]
				this.m[2][0] - m1.m[2][0], // M[2][0]
				this.m[2][1] - m1.m[2][1], // M[2][1]
				this.m[2][2] - m1.m[2][2]  // M[2][2]
			}
		});
	}
	
	/**
	 * Multiplies two matrices together.
	 * It does this by multiplying the each element from the top row of the first matrix by each element of each column of the second.
	 * Then the second row of the first by each of the columns in the second etc for each row and column
	 * 
	 * @param m1 Matrix to multiply
	 * @return The resultant matrix
	 */
	public Matrix3x3f mul(Matrix3x3f m1)
	{
		return new Matrix3x3f(new float[][]
		{ // Pattern: Outer numbers match the overall matrix number. Inner numbers are 0, 1, 2 (eg iterating through the matrix)
			{ // M[0]
				this.m[0][0] * m1.m[0][0] +
				this.m[0][1] * m1.m[1][0] +
				this.m[0][2] * m1.m[2][0], // M[0][0]
				
				this.m[0][0] * m1.m[0][1] +
				this.m[0][1] * m1.m[1][1] +
				this.m[0][2] * m1.m[2][1], // M[0][1]
				
				this.m[0][0] * m1.m[0][2] +
				this.m[0][1] * m1.m[1][2] +
				this.m[0][2] * m1.m[2][2]  // M[0][2]
			},
			{ // M[1]
				this.m[1][0] * m1.m[0][0] +
				this.m[1][1] * m1.m[1][0] +
				this.m[1][2] * m1.m[2][0], // M[1][0]
				
				this.m[1][0] * m1.m[0][1] +
				this.m[1][1] * m1.m[1][1] +
				this.m[1][2] * m1.m[2][1], // M[1][1]
				
				this.m[1][0] * m1.m[0][2] +
				this.m[1][1] * m1.m[1][2] +
				this.m[1][2] * m1.m[2][2]  // M[1][2]
			},
			{ // M[2]
				this.m[2][0] * m1.m[0][0] +
				this.m[2][1] * m1.m[1][0] +
				this.m[2][2] * m1.m[2][0], // M[2][0]
				
				this.m[2][0] * m1.m[0][1] +
				this.m[2][1] * m1.m[1][1] +
				this.m[2][2] * m1.m[2][1], // M[2][1]
				
				this.m[2][0] * m1.m[0][2] +
				this.m[2][1] * m1.m[1][2] +
				this.m[2][2] * m1.m[2][2]  // M[2][2]
			}
		});
	}
	
	public static Matrix3x3f zero()
	{
		return new Matrix3x3f(new float[][]
		{
			{0.0f, 0.0f, 0.0f},
			{0.0f, 0.0f, 0.0f},
			{0.0f, 0.0f, 0.0f}
		});
	}
	
	public static Matrix3x3f identity()
	{
		return new Matrix3x3f( new float[][]
		{
			{1.0f, 0.0f, 0.0f},
			{0.0f, 1.0f, 0.0f},
			{0.0f, 0.0f, 1.0f}
		});
	}
	
	public static Matrix3x3f translate(Vector2f v)
	{
		return translate(v.x, v.y);
	}

	public static Matrix3x3f translate(float x, float y)
	{
		return new Matrix3x3f( new float[][]
		{
			{1.0f, 0.0f, 0.0f},
			{0.0f, 1.0f, 0.0f},
			{   x,    y, 1.0f}
		});
	}
	
	public static Matrix3x3f scale(Vector2f v)
	{
		return scale(v.x, v.y);
	}

	public static Matrix3x3f scale(float x, float y)
	{
		return new Matrix3x3f( new float[][]
		{
			{   x, 0.0f, 0.0f},
			{0.0f,    y, 0.0f},
			{0.0f, 0.0f, 1.0f}
		});
	}
	
	public static Matrix3x3f shear(Vector2f v)
	{
		return shear(v.x, v.y);
	}

	public static Matrix3x3f shear(float x, float y)
	{
		return new Matrix3x3f( new float[][]
		{
			{1.0f,    y, 0.0f},
			{   x, 1.0f, 0.0f},
			{0.0f, 0.0f, 1.0f}
		});
	}
	
	public static Matrix3x3f rotate(float rad)
	{
		return new Matrix3x3f( new float[][]
		{
			{(float)  Math.cos(rad), (float) Math.sin(rad), 0.0f},
			{(float) -Math.sin(rad), (float) Math.cos(rad), 0.0f},
			{                  0.0f,                  0.0f, 1.0f}
		});
	}
	
	public Vector2f mul(Vector2f vec)
	{
		return new Vector2f
		(
			vec.x * this.m[0][0] +
			vec.y * this.m[1][0] +
			vec.w * this.m[2][0], // v.x
			
			vec.x * this.m[0][1] +
			vec.y * this.m[1][1] +
			vec.w * this.m[2][1], // v.y
			
			vec.x * this.m[0][2] +
			vec.y * this.m[1][2] +
			vec.w * this.m[2][2]  // v.w
		);
	}

	@Override
	public String toString()
	{
		//return "Matrix3x3f [m=" + Arrays.toString(m) + "]";
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < 3; ++i)
		{
			buf.append("[");
			buf.append(m[i][0]);
			buf.append(",\t");
			buf.append(m[i][1]);
			buf.append(",\t");
			buf.append(m[i][2]);
			buf.append("]\n");
		}
		return buf.toString();
	}
	
	
}
