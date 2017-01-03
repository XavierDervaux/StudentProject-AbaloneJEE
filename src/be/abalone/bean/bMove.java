package be.abalone.bean;

public class bMove {
	private int type = -1; //Le type  de déplacement
	
	private int ori_x1 = -1;
	private int ori_y1 = -1;
	private int ori_x2 = -1;
	private int ori_y2 = -1;
	private int ori_x3 = -1;
	private int ori_y3 = -1;
	
	private int des_x1 = -1;
	private int des_y1 = -1;
	private int des_x2 = -1;
	private int des_y2 = -1;
	private int des_x3 = -1;
	private int des_y3 = -1;
	

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
	public int ox1() {
		return ori_x1;
	}
	public void setOri_x1(int ori_x1) {
		this.ori_x1 = ori_x1;
	}
	public int oy1() {
		return ori_y1;
	}
	public void setOri_y1(int ori_y1) {
		this.ori_y1 = ori_y1;
	}
	public int ox2() {
		return ori_x2;
	}
	public void setOri_x2(int ori_x2) {
		this.ori_x2 = ori_x2;
	}
	public int oy2() {
		return ori_y2;
	}
	public void setOri_y2(int ori_y2) {
		this.ori_y2 = ori_y2;
	}
	public int ox3() {
		return ori_x3;
	}
	public void setOri_x3(int ori_x3) {
		this.ori_x3 = ori_x3;
	}
	public int oy3() {
		return ori_y3;
	}
	public void setOri_y3(int ori_y3) {
		this.ori_y3 = ori_y3;
	}
	
	
	public int dx1() {
		return des_x1;
	}
	public void setDes_x1(int des_x1) {
		this.des_x1 = des_x1;
	}
	public int dy1() {
		return des_y1;
	}
	public void setDes_y1(int des_y1) {
		this.des_y1 = des_y1;
	}
	public int dx2() {
		return des_x2;
	}
	public void setDes_x2(int des_x2) {
		this.des_x2 = des_x2;
	}
	public int dy2() {
		return des_y2;
	}
	public void setDes_y2(int des_y2) {
		this.des_y2 = des_y2;
	}
	public int dx3() {
		return des_x3;
	}
	public void setDes_x3(int des_x3) {
		this.des_x3 = des_x3;
	}
	public int dy3() {
		return des_y3;
	}
	public void setDes_y3(int des_y3) {
		this.des_y3 = des_y3;
	}
}
