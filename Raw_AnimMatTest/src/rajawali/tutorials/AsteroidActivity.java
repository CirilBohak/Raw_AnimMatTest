package rajawali.tutorials;

import rajawali.RajawaliActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class AsteroidActivity extends RajawaliActivity {
	private AsteroidsRenderer mRenderer;
	
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		mRenderer = new AsteroidsRenderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(mRenderer);
	}
}
