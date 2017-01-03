package be.abalone.bean;

public class bMoveResp {
	private bMove m = new bMove();
	private int ori_x4 = -1;
	private int ori_y4 = -1;
	private int ori_x5 = -1;
	private int ori_y5 = -1;
	private int des_x4 = -1;
	private int des_y4 = -1;
	private int des_x5 = -1;
	private int des_y5 = -1;
	
	public bMove getM() {
		return m;
	}
	public void setM(bMove m) {
		this.m = m;
	}
	public int ox4() {
		return ori_x4;
	}
	public void setOri_x4(int ori_x4) {
		this.ori_x4 = ori_x4;
	}
	public int oy4() {
		return ori_y4;
	}
	public void setOri_y4(int ori_y4) {
		this.ori_y4 = ori_y4;
	}
	public int ox5() {
		return ori_x5;
	}
	public void setOri_x5(int ori_x5) {
		this.ori_x5 = ori_x5;
	}
	public int oy5() {
		return ori_y5;
	}
	public void setOri_y5(int ori_y5) {
		this.ori_y5 = ori_y5;
	}
	public int dx4() {
		return des_x4;
	}
	public void setDes_x4(int des_x4) {
		this.des_x4 = des_x4;
	}
	public int dy4() {
		return des_y4;
	}
	public void setDes_y4(int des_y4) {
		this.des_y4 = des_y4;
	}
	public int dx5() {
		return des_x5;
	}
	public void setDes_x5(int des_x5) {
		this.des_x5 = des_x5;
	}
	public int dy5() {
		return des_y5;
	}
	public void setDes_y5(int des_y5) {
		this.des_y5 = des_y5;
	}
}
