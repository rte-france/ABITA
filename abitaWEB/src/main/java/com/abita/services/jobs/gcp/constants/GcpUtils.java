package com.abita.services.jobs.gcp.constants;

/**
 * Classe utilitaire pour les pièces GCP
 */
public final class GcpUtils {

  /**
   * Constructeur privé
   */
  private GcpUtils() {
  }

  /**
   * Permet de savoir si le type de la pièce est YL
   * @param gcpType type de la pièce
   * @return vrai si c’est une pièce YL
   */
  public static Boolean isYlType(GcpConstants.GcpType gcpType) {
    Boolean ylType = false;

    if (gcpType.equals(GcpConstants.GcpType.YL)) {
      ylType = true;
    } else if (gcpType.equals(GcpConstants.GcpType.YL_FP)) {
      ylType = true;
    } else if (gcpType.equals(GcpConstants.GcpType.YL_REGUL)) {
      ylType = true;
    } else if (gcpType.equals(GcpConstants.GcpType.YL_REVI)) {
      ylType = true;
    }

    return ylType;
  }

  /**
   * Permet de savoir si le type de la pièce est NC
   * @param gcpType type de la pièce
   * @return vrai si c’est une pièce NC
   */
  public static Boolean isNcType(GcpConstants.GcpType gcpType) {
    Boolean ncType = false;

    if (gcpType.equals(GcpConstants.GcpType.NC)) {
      ncType = true;
    } else if (gcpType.equals(GcpConstants.GcpType.NC_REGUL)) {
      ncType = true;
    }

    return ncType;
  }

  /**
   * Permet de savoir si la pièce concerne les contrats tiers
   * @param gcpType type de la pièce
   * @return vrai si c’est une pièce pour les contrats tiers
   */
  public static Boolean isThirdPartyContractType(GcpConstants.GcpType gcpType) {
    Boolean thirdPartyContractType = false;

    Boolean ylType = isYlType(gcpType);

    if (ylType) {
      thirdPartyContractType = true;
    } else if (gcpType.equals(GcpConstants.GcpType.ZN)) {
      thirdPartyContractType = true;
    }

    return thirdPartyContractType;
  }

  /**
   * Permet de savoir si la pièce concerne les contrats occupants
   * @param gcpType type de la pièce
   * @return vrai si c’est une pièce pour les contrats occupants
   */
  public static Boolean isTenantContractType(GcpConstants.GcpType gcpType) {
    return isNcType(gcpType);
  }
}
