package be.pppt.v1;

public class Size {
	int width = 0, height = 0;

	
	public Size(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}
	
	public Size(Size copySize) {
		super();
		this.width = copySize.getWidth();
		this.height = copySize.getHeight();
	}
	
	public boolean isBigger(Size otherSize) {
		if (this.width < otherSize.getWidth() || this.height < otherSize.getHeight()) {
			return true;
		}
		return false;
	}
	
	public double radius()
	{
		double radius2 = Math.pow(width / 2, 2) + Math.pow(height / 2, 2);
		double radius = Math.sqrt(radius2);
		
		return radius;
	}
	
	public void maximizeSize(Size otherSize) {
		if (this.width < otherSize.getWidth()) {
			this.width = otherSize.getWidth();
		}
		if (this.height < otherSize.getHeight()) {
			this.height = otherSize.getHeight();
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
