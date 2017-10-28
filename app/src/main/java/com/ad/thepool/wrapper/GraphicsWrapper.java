package com.ad.thepool.wrapper;

import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;

import com.ad.thepool.Game;
import com.ad.thepool.Pool;
import com.ad.thepool.PoolThread;
import com.ad.thepool.PoolView;
import com.ad.thepool.Scene;

public class GraphicsWrapper {
	Canvas g;

	static Paint paintGlow = new Paint();
	static Paint paintLine = new Paint();
	static Paint paintCircleOut = new Paint();
	static Paint paintCircleIn = new Paint();
	static Paint paintChar = new Paint();
	static Paint paintCharBlack = new Paint();
	static Paint paintRect = new Paint();
	static Rect bounds = new Rect();

	static ImageWrapper charImage = new ImageWrapper(Scene.DIMX * Game.raster, Scene.DIMY * Game.raster, false);

	public static final int NO_COLOR = -1;
	public static final int COLOR_RED = 0;
	public static final int COLOR_WHITE = 1;
	public static final int COLOR_BLACK = 2;
	public static final int COLOR_GREEN = 3;
	public static final int COLOR_ORANGE = 4;
	public static final int COLOR_GREY = 5;
	public static final int COLOR_BLUE = 6;

	public static final float TEXT_SCALE = 0.5F;

	static {
		paintGlow.setAntiAlias(true);
		paintGlow.setStyle(Paint.Style.FILL);
		paintLine.setAntiAlias(true);
		paintLine.setStyle(Paint.Style.FILL);
		Typeface font = Typeface.createFromAsset(PoolThread.mContext.getAssets(), "fonts/PressStart2P.ttf");
		paintChar.setTypeface(font);
		paintChar.setAntiAlias(true);
		paintChar.setShadowLayer(0.2F, 1, 1, Color.BLACK);
		paintChar.setTextSize((float) (Game.raster * TEXT_SCALE));
		paintCharBlack.setTypeface(font);
		paintCharBlack.setAntiAlias(true);
		paintCharBlack.setShadowLayer(0.2F, 1, 1, Color.DKGRAY);
		paintCharBlack.setTextSize((float) (Game.raster * TEXT_SCALE));
	}

	public GraphicsWrapper(Canvas g) {
		this.g = g;
	}

	public void drawImage(ImageWrapper img, int x, int y, int r) {
		BitmapDrawable image = img.getImage(r);
		g.save();
		image.setBounds(x, y, x + img.getWidth(), y + img.getHeight());
		image.draw(g);
		g.restore();
	}

	public void drawImage(ImageWrapper img, int x, int y, int width, int height, int r) {
		BitmapDrawable image = img.getImage(r);

		g.save();
		image.setBounds(x, y, x + width, y + height);
		image.draw(g);
		g.restore();
	}

	public void drawImage(ImageWrapper img, int x, int y, int width, int height, int rgb, float darkest, float scale, int r, boolean qual) {

		BitmapDrawable image = img.getImage(r);
		if (qual == true) {
			int red = Color.red(rgb);
			int green = Color.green(rgb);
			int blue = Color.blue(rgb);
			// int alpha = Color.alpha(rgb);

			float rred = darkest + (red * scale / 255);
			float rgreen = darkest + (green * scale / 255);
			float rblue = darkest + (blue * scale / 255);
			// float ralpha = alpha * 1 / 255;

			red = (int) (rred * 255 / 2);
			green = (int) (rgreen * 255 / 2);
			blue = (int) (rblue * 255 / 2);

			if (red > 255)
				red = 255;
			if (green > 255)
				green = 255;
			if (blue > 255)
				blue = 255;

			LightingColorFilter lcf = new LightingColorFilter(Color.rgb(red, green, blue), 0);
			image.setColorFilter(lcf);
		}
		else
		{
			image.setColorFilter(null);
		}
		g.save();
		image.setBounds(x, y, x + width, y + height);
		image.draw(g);
		g.restore();
	}

	public void drawLine(int fromx, int fromy, int tox, int toy, int color, int colorGlow, int width) {

		if (colorGlow != NO_COLOR) {
			paintGlow.setMaskFilter(new BlurMaskFilter(width + 2, Blur.NORMAL));
			paintGlow.setShader(new LinearGradient(8f, 80f, 30f, 20f, setColor(color), setColor(colorGlow), Shader.TileMode.MIRROR));
			paintGlow.setStrokeWidth(width + 2);
			g.drawLine(fromx, fromy, tox, toy, paintGlow);
		}
		paintLine.setColor(setColor(color));
		paintLine.setStrokeWidth(width);

		g.drawLine(fromx, fromy, tox, toy, paintLine);

	}

	public void drawCircle(int fromx, int fromy, int radius, int color, int colorOut, int alpha) {
		paintCircleOut.setColor(setColor(colorOut));
		paintCircleOut.setAlpha(alpha);
		g.drawCircle(fromx, fromy, radius / 2, paintCircleOut);

		paintCircleIn.setColor(setColor(color));
		paintCircleIn.setAlpha(alpha);
		g.drawCircle(fromx, fromy, radius / 4, paintCircleIn);

	}

	public static int getRGB(int r, int g, int b, int a) {
		return Color.argb(a, r, g, b);
	}

	public static int calcColor(int c1v, int c2v, double ratio) {

		if (ratio > 1) {
			return c2v;
		}
		if (ratio < 0) {
			return c1v;
		}

		int red = (int) (Color.red(c1v) * (1 - ratio) + Color.red(c2v) * ratio);
		int green = (int) (Color.green(c1v) * (1 - ratio) + Color.red(c2v) * ratio);
		int blue = (int) (Color.blue(c1v) * (1 - ratio) + Color.blue(c2v) * ratio);
		int alpha = (int) (Color.alpha(c1v) * (1 - ratio) + Color.alpha(c2v) * ratio);
		return Color.argb(alpha, red, green, blue);
	}

	public void setClip(int x, int y, int width, int height) {
		g.clipRect(x, y, width, height);
	}

	public void drawChars(String text, int start, int end, int xpos, int ypos, int color, int framex, int framey, int frameWidth, int frameHeight, int yoffset) {
		text = text.substring(start, end);

		// paintRect.setColor(0xff0000ff);
		// g.drawRect(framex, framey, framex + frameWidth, framey + frameHeight,
		// paintRect);
		// g.drawText("TEST", xpos, ypos + yoffset, paintChar);
		Paint p;

		g.save();
		g.clipRect(framex, framey, framex + frameWidth, framey + frameHeight);
		if (color != COLOR_BLACK) {
			p = paintChar;
		}
		else
		{
			p = paintCharBlack;
		}
		p.setColor(setColor(color));
		g.drawText(text, xpos, ypos + yoffset, p);
		g.restore();
	}

	public void drawChars(String text, int start, int end, int xpos, int ypos, int color, int yoffset) {
		text = text.substring(start, end);
		
		Paint p;

		if (color != COLOR_BLACK) {
			p = paintChar;
		}
		else
		{
			p = paintCharBlack;
		}

		p.setColor(setColor(color));

		g.drawText(text, xpos, ypos + yoffset, p);

	}

	private int setColor(int col) {
		int ret;

		switch (col) {
		case COLOR_RED:
			ret = Color.RED;
			break;
		case COLOR_WHITE:
			ret = Color.WHITE;
			break;
		case COLOR_BLACK:
			ret = Color.BLACK;
			break;
		case COLOR_GREEN:
			ret = Color.GREEN;
			break;
		case COLOR_ORANGE:
			ret = 0xFF9C33;
			break;
		case COLOR_GREY:
			ret = Color.GRAY;
			break;
		case COLOR_BLUE:
			ret = Color.BLUE;
			break;
		default:
			ret = Color.WHITE;
			break;
		}
		return ret;
	}

	public void fillRoundRect(int x, int y, int width, int height, int round, int round2, int color, int alpha) {

		paintRect.setColor(setColor(color));
		paintRect.setAlpha(alpha);

		g.drawRoundRect(new RectF(x, y, x + width, y + height), (float) round, (float) round2, paintRect);
	}

	public void fillRect(int x, int y, int width, int height, int color, int alpha) {
		paintRect.setColor(setColor(color));
		paintRect.setAlpha(alpha);

		g.drawRect(new RectF(x, y, x + width, y + height), paintRect);
	}

	public void fillRectRGB(int x, int y, int width, int height, int rgb) {
		paintRect.setColor(rgb);

		g.drawRect(new RectF(x, y, x + width, y + height), paintRect);
	}

	public void drawRect(int x, int y, int width, int height, int color, int alpha) {
		paintRect.setColor(setColor(color));
		paintRect.setAlpha(alpha);
		g.drawLine(x, y, x + width, y, paintRect);
		g.drawLine(x, y, x, y + height, paintRect);
		g.drawLine(x + width, y, x + width, y + height, paintRect);
		g.drawLine(x, y + height, x + width, y + height, paintRect);

		// g.drawRect(new RectF(x, y, x + width, y + height), paintRect);
	}

	public int getFontHeight() {
		return (int) (Game.raster * TEXT_SCALE);
	}

	public int getFontWidth(String text) {

		paintChar.getTextBounds(text, 0, text.length(), bounds);

		return bounds.width();
	}

	public Canvas getG() {
		return g;
	}

	public void setG(Canvas g) {
		this.g = g;
	}

}
