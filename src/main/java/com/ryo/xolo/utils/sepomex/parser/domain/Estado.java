package com.ryo.xolo.utils.sepomex.parser.domain;

import java.util.UUID;

import com.ryo.xolo.utils.sepomex.parser.domain.generics.SqlInsert;

public class Estado implements SqlInsert {

	private final static String SQL_INSERT = "INSERT INTO public.tc_division " + 
			"(nid, alpha2, alpha3, descripcion, is_capital, nid_pais, nid_tipo_division) " + 
			"VALUES(0, 'XX'::character varying, 'XXX'::character varying, '', false, 0, 0); ";

	private int nid;
	private String ennum;
	private String descripcion;
	private int nidDivision;

	public Estado(int nid, String descripcion, int nidDivision) {
		super();
		this.nid = nid;
		this.ennum = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
		this.descripcion = descripcion;
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
