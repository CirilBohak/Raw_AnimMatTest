package rajawali.tutorials;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.lights.DirectionalLight;
import rajawali.materials.AnimatedMaterial;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class AsteroidsRenderer extends RajawaliRenderer {
	private DirectionalLight mLight;

	private final int MAX_FRAMES = 64;
	private int mFrameCount;
	private Plane sprite;
	private Plane sprite2;
	private AnimatedMaterial am;
	private AnimatedMaterial am2;
	

	public AsteroidsRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
	}

	protected void initScene() {
		mLight = new DirectionalLight(1.0f, 2.0f, 1.0f);
		mLight.setColor(1.0f, 1.0f, 1.0f);
		mLight.setPower(3);
		
		mCamera.setZ(-4.0f);

		Bitmap particleBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.explosion1spritesheet128);
		
		Bitmap particleBitmap2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.explosion_3_40_128);
		
		sprite = new Plane(1, 1, 1, 1);
		am = new AnimatedMaterial(true);
		sprite.setY(-1.0f);
		sprite.setMaterial(am);
		sprite.addTexture(mTextureManager.addTexture(particleBitmap));
		sprite.setTransparent(true);
		this.addChild(sprite);
		
		sprite2 = new Plane(1, 1, 1, 1);
		am2 = new AnimatedMaterial(true);
		sprite2.setY(1.0f);
		sprite2.setMaterial(am2);
		sprite2.addTexture(mTextureManager.addTexture(particleBitmap2));
		sprite2.setTransparent(true);
		this.addChild(sprite2);
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);

		am.setCurrentFrame(mFrameCount);
		am.setTileSize(1 / 8f);
		am.setNumTileRows(8);
		
		am2.setCurrentFrame(mFrameCount);
		am2.setTileSize(1 / 8f);
		am2.setNumTileRows(8);
		
		sprite.setZ(-0.4f * ((float)mFrameCount / MAX_FRAMES));
		
		if (mFrameCount++ >= MAX_FRAMES) {
			sprite.setZ(0f);
			mFrameCount = 0;
		}
	}
}
