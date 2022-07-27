/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.common;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Classe pour propager les messages vers la redirection.
 * @author.
 */
public class MultiPageMessagesSupport implements PhaseListener {

  /** serialVersionUID. */
  private static final long serialVersionUID = 1250469273857785274L;

  /** Nom de la variable de session. */
  private static final String SESSIONTOKEN = "MULTI_PAGE_MESSAGES_SUPPORT";

  /**
   * {@inheritDoc}
   * @see javax.faces.event.PhaseListener#getPhaseId()
   */
  @Override
  public PhaseId getPhaseId() {
    return PhaseId.ANY_PHASE;
  }

  /**
   * Check to see if we are "naturally" in the RENDER_RESPONSE phase. If we
   * have arrived here and the response is already complete, then the page is
   * not going to show up: don't display messages yet.
   *
   * @param event the event
   * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
   */
  @Override
  public void beforePhase(final PhaseEvent event) {
    FacesContext facesContext = event.getFacesContext();
    saveMessages(facesContext);

    if (PhaseId.RENDER_RESPONSE.equals(event.getPhaseId()) && !facesContext.getResponseComplete()) {
      restoreMessages(facesContext);
    }
  }

  /**
   * Save messages into the session after every phase.
   *
   * @param event the event
   * @see javax.faces.event.PhaseListener#afterPhase(javax.faces.event.PhaseEvent)
   */
  @Override
  public void afterPhase(final PhaseEvent event) {
    if (!PhaseId.RENDER_RESPONSE.equals(event.getPhaseId())) {
      FacesContext facesContext = event.getFacesContext();
      saveMessages(facesContext);
    }
  }

  /**
   * Sauvegarde des messages.
   *
   * @param facesContext le context
   * @return le nombre de message sauvegardé
   */
  @SuppressWarnings("unchecked")
  private int saveMessages(final FacesContext facesContext) {
    Set<FacesMessage> messages = new HashSet<FacesMessage>();
    for (Iterator<FacesMessage> iter = facesContext.getMessages(null); iter.hasNext();) {
      messages.add(iter.next());
      iter.remove();
    }

    if (messages.isEmpty()) {
      return 0;
    }

    Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
    Set<FacesMessage> existingMessages = (Set<FacesMessage>) sessionMap.get(SESSIONTOKEN);
    if (existingMessages != null) {
      existingMessages.addAll(messages);
    } else {
      sessionMap.put(SESSIONTOKEN, messages);
    }
    return messages.size();
  }

  /**
   * Restaure les messages sauvegardés.
   * @param facesContext le contexte
   * @return le nombre de message restauré
   */
  @SuppressWarnings("unchecked")
  private int restoreMessages(final FacesContext facesContext) {
    Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
    Set<FacesMessage> messages = (Set<FacesMessage>) sessionMap.remove(SESSIONTOKEN);

    if (messages == null) {
      return 0;
    }

    int restoredCount = messages.size();
    for (Object element : messages) {
      facesContext.addMessage(null, (FacesMessage) element);
    }
    return restoredCount;
  }
}
