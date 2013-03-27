package rajawali.tutorials;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import rajawali.BaseObject3D;
import rajawali.materials.AnimatedMaterial;
import rajawali.materials.TextureInfo;
import rajawali.materials.TextureManager;
import rajawali.primitives.Plane;

public class Explosion extends BaseObject3D{
	private final int MAX_FRAMES = 64;
	private int mFrameCount;
	private Plane sprite;
	private Plane sprite2;
	private AnimatedMaterial am;
	private AnimatedMaterial am2;
	
	public Explosion(Resources r, TextureManager tm) {
		Bitmap particleBitmap = BitmapFactory.decodeResource(r, R.drawable.explosion1spritesheet128);
		TextureInfo particleTexture = tm.addTexture(particleBitmap);
		
		Bitmap particleBitmap2 = BitmapFactory.decodeResource(r, R.drawable.explosion_3_40_128);
		TextureInfo particleTexture2 = tm.addTexture(particleBitmap2);
		
		sprite = new Plane(1, 1, 1, 1);
		am = new AnimatedMaterial(true);
		sprite.setMaterial(am);
		sprite.addTexture(tm.addTexture(particleBitmap));
		sprite.setTransparent(true);
		this.addChild(sprite);
		
		System.out.println("_____");
		System.out.println("_____");
		System.out.println(am);
		System.out.println("textureID: " + particleTexture.getTextureId());
		System.out.println("textureName: " + particleTexture.getTextureName());
		System.out.println("_____");
		
		sprite2 = new Plane(1, 1, 1, 1);
		am2 = new AnimatedMaterial(true);
		sprite2.setX(1.0f);
		sprite2.setMaterial(am2);
		sprite2.addTexture(tm.addTexture(particleBitmap2));
		sprite2.setTransparent(true);
		this.addChild(sprite2);

		System.out.println(am2);
		System.out.println("textureID: " + particleTexture2.getTextureId());
		System.out.println("textureName: " + particleTexture2.getTextureName());
	}
	
	public void update() {
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
	
	public void reStart() {
		sprite.setVisible(false);
		mFrameCount = 0;
	}
}
