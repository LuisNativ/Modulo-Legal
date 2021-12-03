package com.abaco.persistencia.entidad;

import java.util.ArrayList;
import java.util.Collection;

public class EListaParametroSql extends ArrayList<EParametroSql> {

	private static final long serialVersionUID = 1L;

	public EListaParametroSql() {
		super();
	}

	@Override
	public boolean add(EParametroSql e) {
		return add(e, true);
	}

	public boolean add(EParametroSql e, boolean blnSetPosicion) {
		if (blnSetPosicion) {
			e.setPosicion(this.size() + 1);
		}
		return super.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends EParametroSql> c) {
		return super.addAll(c);
	}

	public boolean addAll(Collection<? extends EParametroSql> c, boolean blnSetPosicion) {
		if (blnSetPosicion) {
			int tamanioActual = this.size();
			for(EParametroSql a : c) {
				a.setPosicion(++tamanioActual);
			}
		}
		return super.addAll(c);
	}
}
