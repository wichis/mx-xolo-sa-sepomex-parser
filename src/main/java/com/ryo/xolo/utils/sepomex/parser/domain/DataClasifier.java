package com.ryo.xolo.utils.sepomex.parser.domain;

import java.util.ArrayList;
import java.util.List;

public class DataClasifier {
	public List<String> header;
	public List<String[]> data;
		
	public DataClasifier() {
		super();
		this.data = new ArrayList<String[]>();
	}
	public List<String> getHeader() {
		return header;
	}
	public void setHeader(List<String> header) {
		this.header = header;
	}
	public List<String[]> getData() {
		return data;
	}
	public void setData(List<String[]> data) {
		this.data = data;
	}

}
