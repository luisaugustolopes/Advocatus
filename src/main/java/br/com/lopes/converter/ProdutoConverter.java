package br.com.lopes.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.lopes.model.Produto;
import br.com.lopes.util.JpaUtils;

@FacesConverter(forClass = Produto.class)
public class ProdutoConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String string) {
		System.out.println("ProdutoConverter.getAsObject(): " + string);
		if (string == null || string.isEmpty()) {
			return null;
		}
		return JpaUtils.getEntityManager().find(Produto.class,
				Integer.valueOf(string));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object object) {
		Produto produto = (Produto) object;
		System.out.println("ProdutoConverter.getAsString(): " + produto);
		if (produto == null || produto.getId() == 0 ) {
			return null;
		}
		return String.valueOf(produto.getId());
	}
}