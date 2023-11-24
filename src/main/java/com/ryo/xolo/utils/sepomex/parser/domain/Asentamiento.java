package com.ryo.xolo.utils.sepomex.parser.domain;

import java.util.UUID;

import com.ryo.xolo.utils.sepomex.parser.domain.generics.SqlInsert;

public class Asentamiento implements SqlInsert {

	private final static String SQL_INSERT = "INSERT INTO public.tc_asentamiento " + 
			"(nid, descripcion, codigo_postal, nid_tipo_asentamiento, nid_subdivision) " + 
			"VALUES(%1$d, '%2$s', '%3$s', %4$d, %5$d);";

	private int nid;
	private String cp;
	private String descripcion;
	private int idTipoAsentamiento;
	private int idSubdivision;

	public Asentamiento(int nid, String descripcion, String cp, int idTipoAsentamiento, int idSubdivision) {
		super();
		this.nid = nid;
		this.descripcion = descripcion;
		this.cp = cp;
		this.idTipoAsentamiento = idTipoAsentamiento;
		this.idSubdivision = idSubdivision;
	}

	@Override
	public String toSqlInsert() {
		return String.format(SQL_INSERT, nid, descripcion, cp, idTipoAsentamiento, idSubdivision);
	}

	public int getNid() {
		return nid;
	}

	public void setNid(int nid) {
		this.nid = nid;
	}
	
}
