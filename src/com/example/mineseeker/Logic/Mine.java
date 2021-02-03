package com.example.mineseeker.Logic;

/**
 * 
 * @author Leon
 * 
 *         <pre>
 * mine
 * really not much to say
 * you are either a mine
 * or 
 * you are not
 * </pre>
 * 
 */
public class Mine {

	private boolean isMine;
	private boolean found;

	public Mine(boolean isMine) {
		this.isMine = isMine;
		if (isMine) {
			found = false;
		} else {
			found = true;
		}
	}

	public boolean isFound() {
		return found;
	}

	public boolean isMine() {
		return isMine;
	}

	public void destoryMine() {
		isMine = false;
	}

	public void setFound() {
		found = true;
	}
}
