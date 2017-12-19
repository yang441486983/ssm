package cn.edu.neu.model;

import org.springframework.web.multipart.MultipartFile;

public class Size {
	
	private int sizeId;
	private String sizeName;
	public Size() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Size(int sizeId, String sizeName) {
		super();
		this.sizeId = sizeId;
		this.sizeName = sizeName;
	}
	@Override
	public String toString() {
		return "Size [sizeId=" + sizeId + ", sizeName=" + sizeName + "]";
	}
	public int getSizeId() {
		return sizeId;
	}
	public void setSizeId(int sizeId) {
		this.sizeId = sizeId;
	}
	public String getSizeName() {
		return sizeName;
	}
	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}
	
	
	
}
