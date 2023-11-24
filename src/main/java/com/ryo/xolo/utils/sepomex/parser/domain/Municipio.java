package com.ryo.xolo.utils.sepomex.parser.domain;

import java.util.UUID;

import com.ryo.xolo.utils.sepomex.parser.domain.generics.SqlInsert;

public class Municipio implements SqlInsert {

	private final static String SQL_INSERT = "INSERT INTO public.tc_subdivision " + 
			"(nid, ennum, descripcion, is_capital, nid_tipo_subdivision, nid_division) " + 
			"VALUES(%1$d, '%2$s', '%3$s', false, %4$d, %5$d);";

	private int nid;
	private String ennum;
	private String descripcion;
	private int nidDivision;

	public Municipio(int nid, String descripcion, int nidDivision) {
		super();
		this.nid = nid;
		this.ennum = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
		this.descripcion = descripcion;
		if(nidDivision == 7) // CHIAPAS
			this.nidDivision = 5;
		else if (nidDivision == 8) // CHIHUAHUA
			this.nidDivision = 6;
		else if (nidDivision == 9) // CIUDAD DE MÉXICO
			this.nidDivision = 7;
		else if (nidDivision == 5) // COAHUILA DE ZARAGOZA
			this.nidDivision = 8;
		else if (nidDivision == 6) // COLIMA
			this.nidDivision = 9;
		else	
			this.nidDivision = nidDivision;
	}

	@Override
	public String toSqlInsert() {
		return String.format(SQL_INSERT, nid, ennum, descripcion, 1, nidDivision);
	}

	public int getNid() {
		return nid;
	}

	public void setNid(int nid) {
		this.nid = nid;
	}
	
}
