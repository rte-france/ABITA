package com.abita.web.audit;

import com.dto.UserDTO;
import com.web.audit.data.IOptionalParametersResolver;
import com.web.audit.data.OptionalParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Résolver de paramètres optionnels pour les indicateurs
 * @author
 *
 */
public class OptionalParametersResolver implements IOptionalParametersResolver {

  @Override
  public List<OptionalParameter> getOptionalParameters(UserDTO arg0) {
    return new ArrayList<OptionalParameter>();
  }

}
