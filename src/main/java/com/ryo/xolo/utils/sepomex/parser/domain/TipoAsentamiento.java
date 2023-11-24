package com.ryo.xolo.utils.sepomex.parser.domain;

import java.util.UUID;

import com.ryo.xolo.utils.sepomex.parser.domain.generics.SqlInsert;

public class TipoAsentamiento implements SqlInsert {

	private final static String SQL_INSERT = "INSERT INTO public.tc_tipo_asentamiento (nid, ennum, descripcion) VALUES(%1$d, '%2$s', '%3$s');";

	private int nid;
	private String ennum;
	private String descripcion;

	public TipoAsentamiento(int nid, String descripcion) {
		super();
		this.nid = nid;
		this.ennum = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
		this.descripcion = descripcion;
	}

	@Override
	public String toSqlInsert() {
		return String.format(SQL_INSERT, nid, ennum, descripcion);
	}

	public int getNid() {
		return nid;
	}

	public void setNid(int nid) {
		this.nid = nid;
	}

	public String getEnnum() {
		return ennum;
	}

	public void setEnnum(String ennum) {
		this.ennum = ennum;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
