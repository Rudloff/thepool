package com.ad.thepool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;

import com.ad.thepool.wrapper.StreamWrapper;

public class Map implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8625816434500585798L;
	private char matrix[][] = new char[Scene.DIMY][Scene.DIMX];
	private char index[][] = new char[Scene.DIMY][Scene.DIMX];

	public Map(String filename, String tagfilename) {
		int i, j;

		for (i = 0; i < matrix.length; i++) {
			for (j = 0; j < (matrix[0]).length; j++) {
				matrix[i][j] = ' ';
			}
		}
		for (i = 0; i < index.length; i++) {
			for (j = 0; j < (index[0]).length; j++) {
				index[i][j] = ' ';
			}
		}
		loadLevel(filename);
		if (tagfilename != null) {
			loadIndex(tagfilename);
		}
	}

	public boolean loadLevel(String filename) {
		String line;
		int i = 0, j = 0;

		BufferedReader reader = null;

		try {

			reader = StreamWrapper.getReader(filename);

			if (reader != null) {
				while ((line = reader.readLine()) != null && i < matrix.length) {
					for (j = 0; j < (matrix[0]).length; j++) {
						matrix[i][j] = line.charAt(j);
					}
					i++;
				}
			}
		} catch (IOException e) {
			return false;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
		return true;
	}

	public boolean loadIndex(String filename) {
		String line;
		int i = 0, j = 0;

		BufferedReader reader = null;

		try {
			reader = StreamWrapper.getReader(filename);
			if (reader != null) {
				while ((line = reader.readLine()) != null && i < index.length) {
					for (j = 0; j < (index[0]).length; j++) {
						index[i][j] = line.charAt(j);
					}
					i++;
				}
			}
		} catch (IOException e) {
			return false;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
		return true;
	}

	public char[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(char[][] matrix) {
		this.matrix = matrix;
	}

	public char[][] getIndex() {
		return index;
	}

	public void setIndex(char[][] tags) {
		this.index = tags;
	}

	@Override
	public String toString() {
		int i, j;
		String ret = new String();

		for (i = 0; i < matrix.length; i++) {
			for (j = 0; j < (matrix[0]).length; j++) {
				ret = ret + matrix[i][j];
			}
			ret += "\n";
		}
		return ret;
	}
}
