package com.web.common.converters;

import com.web.common.data.StringSelectItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.util.Collection;

/**
 * Convertisseur des objets StringSelectItem.
 *
 * @author
 */
public class StringSelectItemConverter implements Converter {

	/**
	 * Le nom de l'attribut à définir au niveau du composant qui va utiliser
	 * ce converter. L'attribut doit pointer vers une
	 * {@link Collection}&lt;? extends {@link StringSelectItem}&gt.
	 */
	public static final String VALUES_ATTRIBUTE = "converter-values";

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		final Collection<? extends StringSelectItem> values = (Collection<? extends StringSelectItem>) component
				.getAttributes().get(VALUES_ATTRIBUTE);

		if (values != null) {
			if (!StringUtils.isBlank(value)) {

				final String label = String.valueOf(value);
				for (StringSelectItem stringSelectItem : values) {
					if (StringUtils.equals(label, stringSelectItem.getLabel())) {
						return stringSelectItem;
					}
				}

				Logger.getLogger(StringSelectItemConverter.class)
					.warn("StringSelectItem not found '" + value + "'");
			}
		} else {
			Logger.getLogger(StringSelectItemConverter.class).warn("no values where search '" + value + "'");
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value instanceof StringSelectItem) {
			final StringSelectItem stringSelectItem = (StringSelectItem) value;
			if (stringSelectItem.getLabel() != null) {
				return stringSelectItem.getLabel();
			}
		}
		return null;
	}

}
