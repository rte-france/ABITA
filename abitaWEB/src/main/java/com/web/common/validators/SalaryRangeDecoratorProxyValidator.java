package com.web.common.validators;

import com.abita.util.decorator.SalaryRangeDecorator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Classe de validation chargée de valider la saisie utilisateur lors de la modification d'un barème pour avantage en nature
 * @author
 */
public class SalaryRangeDecoratorProxyValidator implements Validator, Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -1303131250104110145L;

  /** Le nom de l'attribut indiquant le libellé du champ */
  private static final String ATTRIBUTE_SUMMARY_MESSAGE = "summaryMessage";

  /** Le nom de l'attribut indiquant le libellé du champ */
  private static final String DEFAULT_VALIDATION_MESSAGE = "Echec de la validation d'un barème";

  /*
   * (non-Javadoc)
   *
   * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
   */
  @Override
  public void validate(FacesContext context, UIComponent component, Object value) {

    // Obtain the component and submitted value of the confirm password component.
    BigDecimal newValue = (BigDecimal) value;
    SalaryRangeDecorator salaryRange = (SalaryRangeDecorator) component.getAttributes().get("salaryRange");

    if (newValue.compareTo(salaryRange.getMinimumThreshold()) != 0 && !isValid(salaryRange, newValue)) {
      FacesMessage facesMessage = new FacesMessage();
      facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);

      String resumedMessage = getSummaryMessage(component);
      facesMessage.setSummary(resumedMessage);

      throw new ValidatorException(facesMessage);
    }
  }

  /**
   * Methode déléguée chargée de valider la saisie utilisateur en fonction de la position du barème modifié
   * @param object Le barème modifié
   * @param testedValue La nouvelle valeur de seuil inférieur
   * @return Retourne <code>true</code> si la nouvelle valeur est valide
   */
  private boolean isValid(SalaryRangeDecorator object, BigDecimal testedValue) {
    boolean result = true;

    int listSize = object.getSalaryRangeLinkedList().size();
    int position = object.getSalaryRangeLinkedList().indexOf(object);
    if (position < 0) {
      return false;
    }
    if (position > 0) {
      // On ne teste la limite basse uniquement si il peut exister un élément précédent
      BigDecimal lowerValue = object.getSalaryRangeLinkedList().get(position - 1).getMinimumThreshold();
      result = result && testedValue.compareTo(lowerValue) > 0;
    }
    if (position < listSize - 1) {
      // On ne teste la limite basse uniquement si il peut exister un élément suivant
      BigDecimal upperValue = object.getSalaryRangeLinkedList().get(position + 1).getMinimumThreshold();
      result = result && testedValue.compareTo(upperValue) < 0;
    }

    return result;
  }

  /**
   * Renvoie le libellé à afficher dans le message d'erreur
   *
   * La méthode renvoie la valeur de l'attribut "label" si elle est définie et non nulle ou la valeur de l'identifiant client
   *
   * @param component le composant dont on veut le libellé
   *
   * @return le libellé à afficher
   */
  private String getSummaryMessage(final UIComponent component) {
    String msg = null;
    Object msgValue = component.getAttributes().get(ATTRIBUTE_SUMMARY_MESSAGE);
    if (msgValue != null) {
      msg = String.valueOf(msgValue);
    } else {
      msg = DEFAULT_VALIDATION_MESSAGE;
    }
    return msg;
  }
}
