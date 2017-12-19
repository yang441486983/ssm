package cn.edu.neu.model;

import org.springframework.web.multipart.MultipartFile;

public class Color {
	private int colorId;
	private String colorName;
	public Color() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Color(int colorId, String colorName) {
		super();
		this.colorId = colorId;
		this.colorName = colorName;
	}
	@Override
	public String toString() {
		return "Color [colorId=" + colorId + ", colorName=" + colorName + "]";
	}
	public int getColorId() {
		return colorId;
	}
	public void setColorId(int colorId) {
		this.colorId = colorId;
	}
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	
		
	
	
	
}
