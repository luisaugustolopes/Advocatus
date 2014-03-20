package br.com.lopes.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.lopes.model.Pessoa;
import br.com.lopes.model.PessoaFisica;
import br.com.lopes.model.PessoaJuridica;
import br.com.lopes.util.JpaUtils;

//@FacesConverter(forClass=PessoaJuridica.class,value="pessoaJuridicaConverter")
public class PessoaJuridicaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String id) {
		if (id == null || id.isEmpty()) {
			return null;
		}
		return JpaUtils.getEntityManager().find(PessoaJuridica.class,Long.valueOf(id));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
		PessoaJuridica juridica = (PessoaJuridica) object;
		if (juridica == null || juridica.getId() == 0) {
			return null;
		}
		return String.valueOf(juridica.getId());
	}

}
