package br.com.lopes.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.lopes.dao.CidadeDAO;
import br.com.lopes.model.Cidade;

@FacesConverter(forClass=Cidade.class,value="cidadeConverter")
public class CidadeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String id) {
		
		if (id == null || id.isEmpty() || id.startsWith("Selecione")){
			return null;
		}
		
		return new CidadeDAO().getById(Long.parseLong(id));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		
		if (object == null || !(object instanceof Cidade)){
			return null;
		}
			
		
		Cidade cidade = (Cidade) object;
		
		if (cidade == null || cidade.getId()== 0){
			return null;
		}
		
		return String.valueOf(cidade.getId());
	}

}
