package cn.edu.neu.model;

import org.springframework.web.multipart.MultipartFile;

public class Stock {
	private int stockId;
	private int goodsId;
	private int sizeId;
	private String sizeName;
	private int colorId;
	private String colorName;
	private int stockNum;
	private int salesNum;
	public Stock() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Stock(int stockId, int goodsId, int sizeId, String sizeName, int colorId, String colorName, int stockNum,
			int salesNum) {
		super();
		this.stockId = stockId;
		this.goodsId = goodsId;
		this.sizeId = sizeId;
		this.sizeName = sizeName;
		this.colorId = colorId;
		this.colorName = colorName;
		this.stockNum = stockNum;
		this.salesNum = salesNum;
	}
	@Override
	public String toString() {
		return "Stock [stockId=" + stockId + ", goodsId=" + goodsId + ", sizeId=" + sizeId + ", sizeName=" + sizeName
				+ ", colorId=" + colorId + ", colorName=" + colorName + ", stockNum=" + stockNum + ", salesNum="
				+ salesNum + "]";
	}
	public int getStockId() {
		return stockId;
	}
	public void setStockId(int stockId) {
		this.stockId = stockId;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
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
	public int getStockNum() {
		return stockNum;
	}
	public void setStockNum(int stockNum) {
		this.stockNum = stockNum;
	}
	public int getSalesNum() {
		return salesNum;
	}
	public void setSalesNum(int salesNum) {
		this.salesNum = salesNum;
	}
	
	
}
