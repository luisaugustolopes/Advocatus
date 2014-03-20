package br.com.lopes.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.lopes.model.Pessoa;
import br.com.lopes.util.JpaUtils;

//@FacesConverter(forClass = Pessoa.class)
public class PessoaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String id) {
		if (id == null || id.isEmpty()) {
			return null;
		}
		return JpaUtils.getEntityManager().find(Pessoa.class,Long.valueOf(id));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
		Pessoa pessoa = (Pessoa) object;
		if (pessoa == null || pessoa.getId() == 0) {
			return null;
		}
		return String.valueOf(pessoa.getId());
	}

}