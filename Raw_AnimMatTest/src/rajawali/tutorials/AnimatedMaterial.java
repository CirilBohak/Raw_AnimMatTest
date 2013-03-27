package rajawali.tutorials;

import rajawali.materials.AMaterial;
import android.opengl.GLES20;


public class AnimatedMaterial extends AMaterial {
	protected static final String mVShader = 
		"precision mediump float;\n" +
		"uniform mat4 uMVPMatrix;\n" +
		
		"#ifdef ANIMATED\n" +
		"uniform float uCurrentFrame;\n" +
		"uniform float uTileSize;\n" +
		"uniform float uNumTileRows;\n" +
		"#endif\n" +
		
		"attribute vec4 aPosition;\n" +		
		"attribute vec2 aTextureCoord;\n" +
		
		"varying vec2 vTextureCoord;\n" +

		"void main() {\n" +
		"	vec4 position = vec4(aPosition);\n" +
		"	gl_Position = uMVPMatrix * position;\n" +
		"	#ifdef ANIMATED\n" +
		"		vTextureCoord.s = ( mod(uCurrentFrame, uNumTileRows) + aTextureCoord.s ) * uTileSize;\n" +
		"		vTextureCoord.t = uTileSize * (floor(uCurrentFrame  / uNumTileRows) + aTextureCoord.t);\n" +
		"	#else\n" +
		"		vTextureCoord = aTextureCoord;\n" +
		"	#endif\n" +
		"}\n";
	
	protected static final String mFShader = 
		"precision mediump float;\n" +

		"varying vec2 vTextureCoord;\n" +
		"uniform sampler2D uDiffuseTexture;\n" +
		"uniform sampler2D uAlphaTexture;\n" +
		
		"#ifdef ANIMATED\n" +
		"uniform float uTileSize;\n" +
		"uniform float uNumTileRows;\n" +
		"#endif\n" +

		"void main() {\n" +
		"	vec4 color = vec4(1.0, 1.0, 0.0, 0.0);\n" +
		"	#ifdef ALPHA_MAP\n" +
		"		gl_FragColor = texture2D(uAlphaTexture, vTextureCoord);\n" +
		"	#else\n" +
		"		gl_FragColor = texture2D(uDiffuseTexture, vTextureCoord);\n" +
		"	#endif\n" +
		"}\n";
	
	
	protected int muCurrentFrameHandle;
	protected int muTileSizeHandle;
	protected int muNumTileRowsHandle;
	
	protected int mCurrentFrame;
	protected float mTileSize;
	protected float mNumTileRows;
	protected boolean mIsAnimated;
	
	public AnimatedMaterial() {
		this(false);
	}

	public AnimatedMaterial(boolean isAnimated) {
		this(mVShader, mFShader, isAnimated);
	}

	public AnimatedMaterial(String vertexShader, String fragmentShader, boolean isAnimated) {
		super(vertexShader, fragmentShader, NONE);
		mIsAnimated = isAnimated;
		if(mIsAnimated) {
			mUntouchedVertexShader = "\n#define ANIMATED\n" + mUntouchedVertexShader;
			mUntouchedFragmentShader = "\n#define ANIMATED\n" + mUntouchedFragmentShader;
		}
		setShaders(mUntouchedVertexShader, mUntouchedFragmentShader);
	}
	
	@Override
	public void useProgram() {
		super.useProgram();
	}
	
	@Override
	public void setShaders(String vertexShader, String fragmentShader)
	{
		super.setShaders(vertexShader, fragmentShader);
		muCurrentFrameHandle = getUniformLocation("uCurrentFrame");
		muTileSizeHandle = getUniformLocation("uTileSize");
		muNumTileRowsHandle = getUniformLocation("uNumTileRows");
	}
	
	public void setCurrentFrame(int currentFrame) {
		mCurrentFrame = currentFrame;
		GLES20.glUniform1f(muCurrentFrameHandle, mCurrentFrame);
	}
	
	public void setTileSize(float tileSize) {
		mTileSize = tileSize;
		GLES20.glUniform1f(muTileSizeHandle, mTileSize);
	}
	
	public void setNumTileRows(int numTileRows) {
		mNumTileRows = numTileRows;
		GLES20.glUniform1f(muNumTileRowsHandle, mNumTileRows);
	}
}
