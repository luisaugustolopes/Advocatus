package br.com.lopes.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.lopes.model.Pessoa;
import br.com.lopes.model.PessoaFisica;
import br.com.lopes.util.JpaUtils;

//@FacesConverter(forClass=PessoaFisica.class,value="pessoaFisicaConverter")
public class PessoaFisicaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String id) {
		if (id == null || id.isEmpty()) {
			return null;
		}
		return JpaUtils.getEntityManager().find(PessoaFisica.class,Long.valueOf(id));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
		PessoaFisica fisica = (PessoaFisica) object;
		if (fisica == null || fisica.getId() == 0) {
			return null;
		}
		return String.valueOf(fisica.getId());
	}

}
