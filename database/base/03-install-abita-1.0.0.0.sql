------------------------------------------------
-- Configuration technique spécifique à ABITA --
------------------------------------------------

-- USER_EXTENSION (EXTENSION DES UTILISATEURS)
-- Note : Tous les champs de cette tables hormis la clé primaire
--		  doivent avoir une valeur par défaut
CREATE TABLE USER_EXTENSION ( 	usr_id NUMBER(19,0) not null, 
								uex_is_manager VARCHAR2(1 CHAR) DEFAULT 'N' not null, 
 								CONSTRAINT fk_use_2_fwu PRIMARY KEY (usr_id) USING INDEX TABLESPACE TBS_ABITA_IDX,
 								CONSTRAINT chk_is_manager CHECK (uex_is_manager IN ('Y', 'N')));
 								
-- TRIGGER : USER_EXTENSION_TRIGGER
-- Lors de l'ajout d'un nouvel utilisateur, complète les informations additionnelles de l'utilisateur
-- dans la table USER_EXTENSION
CREATE OR REPLACE TRIGGER user_extension_trigger
AFTER INSERT ON FWK_USER
FOR EACH ROW
BEGIN
  INSERT INTO USER_EXTENSION (USR_ID)
  VALUES (:NEW.USR_ID);
END;
/

-- Ajout des données initiales de la table USER_EXTENSION
-- Ajoute les données des utilisateurs déjà présent dans FWK_USR
Insert into User_extension (USR_ID)
Select USR_ID from fwk_user;

---------------------------------------------
-- Configuration métier spécifique à ABITA --
---------------------------------------------

-- THIRD_PARTY_TERMINATION (RESILIATION TIERS)
CREATE TABLE THIRD_PARTY_TERMINATION ( 	tpt_id NUMBER(19,0) not null, 
								tpt_label VARCHAR2(50 CHAR) not null, 
 								CONSTRAINT pk_third_party_termination PRIMARY KEY (tpt_id) USING INDEX TABLESPACE TBS_ABITA_IDX);

-- SEQUENCE pour THIRD_PARTY_TERMINATION
CREATE SEQUENCE SEQ_THIRD_PARTY_TERMINATION
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- COMMENT pour THIRD_PARTY_TERMINATION
COMMENT ON TABLE THIRD_PARTY_TERMINATION IS 'RESILISATION TIERS';


-- TERMINATION (RESILIATION CONTRAT) 
CREATE TABLE TERMINATION (	ter_id NUMBER(19,0) not null, 
						tpt_label VARCHAR2(50 CHAR) not null, 
 						CONSTRAINT pk_termination PRIMARY KEY (ter_id) USING INDEX TABLESPACE TBS_ABITA_IDX);

-- SEQUENCE pour TERMINATION
CREATE SEQUENCE SEQ_TERMINATION
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- COMMENT pour TERMINATION
COMMENT ON TABLE TERMINATION IS 'RESILISATION';


-- FIELD_OF_ACTIVITY (DOMAINE ACTIVITE)
CREATE TABLE FIELD_OF_ACTIVITY (	foa_id NUMBER(19,0) not null, 
 							foa_label VARCHAR2(50 CHAR) not null, 
 							CONSTRAINT pk_field_of_activity PRIMARY KEY (foa_id) USING INDEX TABLESPACE TBS_ABITA_IDX);

-- SEQUENCE pour FIELD_OF_ACTIVITY
CREATE SEQUENCE SEQ_FIELD_OF_ACTIVITY
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- COMMENT pour FIELD_OF_ACTIVITY
COMMENT ON TABLE FIELD_OF_ACTIVITY IS 'DOMAINE D''ACTIVITÉ';


-- BENEFITS_LEVEL (Barème des avantages en nature)
CREATE TABLE BENEFITS_LEVEL (	ble_id NUMBER(19,0) not null, 
 							ble_minimum_threshold NUMBER(7,3) not null, 
 							ble_benefits_one_room NUMBER(7,3) not null, 
 							ble_benefits_many_rooms NUMBER(7,3) not null, 
 							CONSTRAINT pk_benefits_level PRIMARY KEY (ble_id) USING INDEX TABLESPACE TBS_ABITA_IDX);

-- SEQUENCE pour BENEFITS_LEVEL
CREATE SEQUENCE SEQ_BENEFITS_LEVEL
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- COMMENT pour BENEFITS_LEVEL
COMMENT ON TABLE BENEFITS_LEVEL IS 'Barème des avantages en nature';


-- RENT_TAX (TAUX REVISION)
CREATE TABLE RENT_TAX (	rta_id NUMBER(19,0) not null, 
 					rta_code VARCHAR2(10 CHAR) not null, 
 					rta_value NUMBER(7, 3) not null, 
 					CONSTRAINT pk_rent_tax PRIMARY KEY (rta_id) USING INDEX TABLESPACE TBS_ABITA_IDX);

-- SEQUENCE pour RENT_TAX
CREATE SEQUENCE SEQ_RENT_TAX
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- COMMENT pour RENT_TAX
COMMENT ON TABLE RENT_TAX IS 'TVA LOCATION';
COMMENT ON COLUMN RENT_TAX.rta_code IS 'Code de TVA de location';


-- CHARGE_TAX (TVA CHARGE)
CREATE TABLE CHARGE_TAX (cta_id NUMBER(19,0) not null, 
					cta_code VARCHAR2(10 CHAR) not null, 
 					cta_value NUMBER(7, 3) not null, 
 					CONSTRAINT pk_charge_tax PRIMARY KEY (cta_id) USING INDEX TABLESPACE TBS_ABITA_IDX);

-- SEQUENCE CHARGE_TAX
CREATE SEQUENCE SEQ_CHARGE_TAX
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- COMMENT CHARGE_TAX
COMMENT ON TABLE CHARGE_TAX IS 'TVA CHARGE';
COMMENT ON COLUMN CHARGE_TAX.cta_code IS 'Code de TVA de charge';


-- ROOM_CATEGORY (CATEGORIE LOCAL)
CREATE TABLE ROOM_CATEGORY (	rca_id NUMBER(19,0) not null, 
 						rca_label VARCHAR2(50 CHAR) not null, 
 						CONSTRAINT pk_room_category PRIMARY KEY (rca_id) USING INDEX TABLESPACE TBS_ABITA_IDX);

-- SEQUENCE ROOM_CATEGORY
CREATE SEQUENCE SEQ_ROOM_CATEGORY
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- COMMENT ROOM_CATEGORY
COMMENT ON TABLE ROOM_CATEGORY IS 'CATEGORIE LOCAL';


-- HOUSING_NATURE (NATURE)
CREATE TABLE HOUSING_NATURE (	hna_id NUMBER(19,0) not null, 
 						hna_label VARCHAR2(50 CHAR) not null, 
 						CONSTRAINT pk_housing_nature PRIMARY KEY (hna_id) USING INDEX TABLESPACE TBS_ABITA_IDX);

-- SEQUENCE HOUSING_NATURE
CREATE SEQUENCE SEQ_HOUSING_NATURE
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- COMMENT HOUSING_NATURE
COMMENT ON TABLE HOUSING_NATURE IS 'NATURE LOGEMENT';


-- ADJUSTMENT_RATE (TAUX REVISION)
CREATE TABLE ADJUSTMENT_RATE (ara_id NUMBER(19,0) not null, 
 						ara_value NUMBER(7, 3) not null, 
 						CONSTRAINT pk_adjustment_rate PRIMARY KEY (ara_id) USING INDEX TABLESPACE TBS_ABITA_IDX);

-- SEQUENCE ADJUSTMENT_RATE
CREATE SEQUENCE SEQ_ADJUSTMENT_RATE
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- COMMENT ADJUSTMENT_RATE
COMMENT ON TABLE ADJUSTMENT_RATE IS 'TAUX REVISION; Taux de révision du Loyer Surface Corrigée (LCS)';


-- PAYMENT_METHOD (PAIEMENT)
CREATE TABLE PAYMENT_METHOD (	pme_id NUMBER(19,0) not null, 
 						pme_label VARCHAR2(50 CHAR) not null, 
 						CONSTRAINT pk_payment_method PRIMARY KEY (pme_id) USING INDEX TABLESPACE TBS_ABITA_IDX);

-- SEQUENCE PAYMENT_METHOD
CREATE SEQUENCE SEQ_PAYMENT_METHOD
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- COMMENT PAYMENT_METHOD
COMMENT ON TABLE PAYMENT_METHOD IS 'PAIEMENT (Mode de)';


-- COST_CENTER (CENTRE COUT)
CREATE TABLE COST_CENTER (	cce_id NUMBER(19,0) not null, 
 						cce_label VARCHAR2(50 CHAR) not null, 
 						CONSTRAINT pk_cost_center PRIMARY KEY (cce_id) USING INDEX TABLESPACE TBS_ABITA_IDX);

-- SEQUENCE COST_CENTER
CREATE SEQUENCE SEQ_COST_CENTER
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- COMMENT COST_CENTER
COMMENT ON TABLE COST_CENTER IS 'CENTRE COÛT';


-- FIELD (Domaine)
CREATE TABLE FIELD (fie_id NUMBER(19,0) not null, 
 				fie_label VARCHAR2(50 CHAR) not null, 
				CONSTRAINT pk_field PRIMARY KEY (fie_id) USING INDEX TABLESPACE TBS_ABITA_IDX);

-- SEQUENCE FIELD
CREATE SEQUENCE SEQ_FIELD
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- COMMENT FIELD
COMMENT ON TABLE FIELD IS 'DOMAINE';


-- RENT_TYPOLOGY (TYPOLOGIE LOYER)
CREATE TABLE RENT_TYPOLOGY (	rtp_id NUMBER(19,0) not null, 
 						rtp_label VARCHAR2(20 CHAR) not null, 
 						CONSTRAINT pk_rent_typology PRIMARY KEY (rtp_id) USING INDEX TABLESPACE TBS_ABITA_IDX);

-- SEQUENCE RENT_TYPOLOGY
CREATE SEQUENCE SEQ_RENT_TYPOLOGY
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- COMMENT RENT_TYPOLOGY
COMMENT ON TABLE RENT_TYPOLOGY IS 'TYPOLOGIE LOYER';


-- PAYMENT_CYCLE (PERIODICITE PAIEMENT)
CREATE TABLE PAYMENT_CYCLE (	PCY_ID NUMBER(19,0) NOT NULL, 
 						PCY_LABEL VARCHAR2(50 CHAR) NOT NULL, 
 						CONSTRAINT PK_PAYMENT_CYCLE PRIMARY KEY (PCY_ID) USING INDEX TABLESPACE TBS_ABITA_IDX);

-- SEQUENCE PAYMENT_CYCLE
CREATE SEQUENCE SEQ_PAYMENT_CYCLE
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- COMMENT PAYMENT_CYCLE
COMMENT ON TABLE PAYMENT_CYCLE IS 'PERIODICITE PAIEMENT';


-- TYPE_TENANT (TYPE OCCUPANT)
CREATE TABLE TYPE_TENANT (	TTE_ID NUMBER(19,0) NOT NULL, 
 						TTE_LABEL VARCHAR2(20 CHAR) NOT NULL, 
 						CONSTRAINT PK_TYPE_TENANT PRIMARY KEY (TTE_ID) USING INDEX TABLESPACE TBS_ABITA_IDX);

-- SEQUENCE TYPE_TENANT
CREATE SEQUENCE SEQ_TYPE_TENANT
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- COMMENT TYPE_TENANT
COMMENT ON TABLE TYPE_TENANT IS 'TYPE OCCUPANT';


-- THIRD_PARTY (TIERS)
CREATE TABLE THIRD_PARTY (	thp_id NUMBER(19,0) not null, 
 						thp_gcp_ref VARCHAR2(50 CHAR) not null, 
						thp_name VARCHAR2(50 CHAR) not null, 
						thp_address VARCHAR2(250 CHAR) not null, 
						thp_postal_code VARCHAR2(10 CHAR) not null, 
						thp_city VARCHAR2(50 CHAR) not null, 
						thp_phone VARCHAR2(20 CHAR), 
						thp_mail_address VARCHAR2(255 CHAR), 
						thp_beneficiary_name VARCHAR2(50 CHAR), 
						thp_beneficiary_address VARCHAR2(250 CHAR), 
						-- thp_comment VARCHAR2(4000 CHAR), 
						CONSTRAINT pk_third_party PRIMARY KEY (thp_id) USING INDEX TABLESPACE TBS_ABITA_IDX);

-- SEQUENCE THIRD_PARTY
CREATE SEQUENCE SEQ_THIRD_PARTY
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- COMMENT THIRD_PARTY
COMMENT ON TABLE THIRD_PARTY IS 'TIERS';
COMMENT ON COLUMN THIRD_PARTY.thp_gcp_ref IS 'Référence fournisseur GCP';
COMMENT ON COLUMN THIRD_PARTY.thp_name IS 'Nom du tiers';
COMMENT ON COLUMN THIRD_PARTY.thp_address IS 'Adresse postale du tiers';
COMMENT ON COLUMN THIRD_PARTY.thp_postal_code IS 'Code postal du tiers';
COMMENT ON COLUMN THIRD_PARTY.thp_city IS 'Ville du tiers';
COMMENT ON COLUMN THIRD_PARTY.thp_phone IS 'Téléphone du tiers';
COMMENT ON COLUMN THIRD_PARTY.thp_mail_address IS 'Adresse de courriel du tiers';


-- HOUSING (LOGEMENT)
CREATE TABLE HOUSING (	hou_id NUMBER(19,0) not null, 
 					hou_address VARCHAR2(250 CHAR) not null, 
					hou_postal_code VARCHAR2(10 CHAR) not null, 
					hou_city VARCHAR2(50 CHAR) not null, 
					hou_register_date DATE not null, 
					hou_unregister_date DATE, 
					hou_real_surface_area NUMBER(6,2) not null, 
					hou_revised_surface_area NUMBER(6,2) not null, 
					hou_rte_property VARCHAR2(1 CHAR) not null, 
					hou_garden_available VARCHAR2(1 CHAR) not null, 
					hou_garage_available VARCHAR2(1 CHAR) not null, 
					hou_housing_status VARCHAR2(25 CHAR) not null, 
					hou_room_count NUMBER(3,0) not null, 
					hou_core VARCHAR2(1 CHAR) not null, 
					hou_year_of_construction VARCHAR2(4 CHAR), 
					hna_id NUMBER(19,0), 
					usr_id NUMBER(19,0) not null, 
					rca_id NUMBER(19,0) not null, 
					CONSTRAINT pk_housing PRIMARY KEY (hou_id) USING INDEX TABLESPACE TBS_ABITA_IDX, 
					CONSTRAINT fk_hou_2_hna FOREIGN KEY (hna_id) REFERENCES HOUSING_NATURE (hna_id), 
					CONSTRAINT fk_hou_2_usr FOREIGN KEY (usr_id) REFERENCES USER_EXTENSION (usr_id), 
					CONSTRAINT fk_hou_2_rca FOREIGN KEY (rca_id) REFERENCES ROOM_CATEGORY (rca_id), 
					CONSTRAINT CHK_USR_ID_NOT_NULL CHECK (USR_ID IS NOT NULL) DEFERRABLE INITIALLY DEFERRED, 
					CONSTRAINT CHK_RCA_ID_NOT_NULL CHECK (RCA_ID IS NOT NULL) DEFERRABLE INITIALLY DEFERRED);

-- SEQUENCE HOUSING
CREATE SEQUENCE SEQ_HOUSING
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- COMMENT HOUSING
COMMENT ON TABLE HOUSING IS 'LOGEMENT';
COMMENT ON COLUMN HOUSING.hou_register_date IS 'Date d''entrée du bien dans le parc RTE';
COMMENT ON COLUMN HOUSING.hou_unregister_date IS 'Date de sortie du bien du le parc RTE';
COMMENT ON COLUMN HOUSING.hou_real_surface_area IS 'Surface réelle';
COMMENT ON COLUMN HOUSING.hou_revised_surface_area IS 'Surface corrigée';
COMMENT ON COLUMN HOUSING.hou_garage_available IS 'Disponibilité d''un garage';
COMMENT ON COLUMN HOUSING.hou_room_count IS 'Nombre de pièces';
COMMENT ON COLUMN HOUSING.hou_year_of_construction IS 'Année de construction';


-- TENANT (OCCUPANT)
CREATE TABLE TENANT (	ten_id NUMBER(19,0) not null, 
 					ten_reference VARCHAR2(15 CHAR) not null, 
 					ten_firstname VARCHAR2(50 CHAR) not null, 
					ten_lastname VARCHAR2(50 CHAR) not null, 
					ten_managerial_emp VARCHAR2(1 CHAR) not null, 
					ten_managerial_emp_last_year VARCHAR2(1 CHAR) not null, 
					ten_household_size NUMBER(3,0) not null, 
					ten_household_size_last_year NUMBER(3,0) not null, 
					ten_actual_salary NUMBER(10,3), 
					ten_reference_gross_salary NUMBER(10,3), 
					tte_id NUMBER(19,0) not null, 
					CONSTRAINT pk_tenant PRIMARY KEY (ten_id) USING INDEX TABLESPACE TBS_ABITA_IDX, 
					CONSTRAINT fk_ten_2_tte FOREIGN KEY (tte_id) REFERENCES TYPE_TENANT (tte_id), 
					CONSTRAINT CHK_TTE_ID_NOT_NULL CHECK (TTE_ID IS NOT NULL) DEFERRABLE INITIALLY DEFERRED);

-- SEQUENCE TENANT
CREATE SEQUENCE SEQ_TENANT
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- COMMENT TENANT
COMMENT ON TABLE TENANT IS 'OCCUPANT';
COMMENT ON COLUMN TENANT.ten_reference IS 'Référence occupant';
COMMENT ON COLUMN TENANT.ten_firstname IS 'Prénom de l''occupant';
COMMENT ON COLUMN TENANT.ten_lastname IS 'Nom de famille de l''occupant';
COMMENT ON COLUMN TENANT.ten_managerial_emp IS 'L''occupant est-il cadre';
COMMENT ON COLUMN TENANT.ten_managerial_emp_last_year IS 'L''occupant est-il cadre à octobre N-1';
COMMENT ON COLUMN TENANT.ten_household_size IS 'Nombre de personnes constituant le foyer';
COMMENT ON COLUMN TENANT.ten_actual_salary IS 'Salaire actuel';
COMMENT ON COLUMN TENANT.ten_reference_gross_salary IS 'Salaire brut de référence temps plein à octobre de l''année N-1';


-- THIRD_PARTY_CONTRACT (CONTRAT TIERS)
CREATE TABLE THIRD_PARTY_CONTRACT (tpc_id NUMBER(19,0) not null, 
							tpc_contract_period NUMBER(5) not null, 
							tpc_notice_period NUMBER(5), 
							tpc_start_validity_date DATE not null, 
							tpc_start_supplier_payment_dat DATE not null, 
							tpc_rent_amount NUMBER(10,3) not null, 
							tpc_expected_charge_cost NUMBER(10,3) not null, 
							tpc_guaranteed_deposit_amount NUMBER(10,3), 
							tpc_real_estate_agency_fee NUMBER(10,3), 
							tpc_sporadically_invoicing NUMBER(10,3), 
							tpc_cancellation_date DATE, 
							tpc_guaranteed_deposit_refund VARCHAR2(1 CHAR), 
							thp_id NUMBER(19,0) not null, 
							hou_id NUMBER(19,0) not null, 
							foa_id NUMBER(19,0) not null, 
							cce_id NUMBER(19,0) not null, 
							pcy_id NUMBER(19,0) not null, 
							tpt_id NUMBER(19,0), 
							CONSTRAINT pk_third_party_contract PRIMARY KEY (tpc_id) USING INDEX TABLESPACE TBS_ABITA_IDX, 
							CONSTRAINT fk_tpc_2_thp FOREIGN KEY (thp_id) REFERENCES THIRD_PARTY (thp_id), 
							CONSTRAINT fk_tpc_2_dwe FOREIGN KEY (hou_id) REFERENCES HOUSING (hou_id), 
							CONSTRAINT fk_tpc_2_foa FOREIGN KEY (foa_id) REFERENCES FIELD_OF_ACTIVITY (foa_id),
							CONSTRAINT fk_tpc_2_cce FOREIGN KEY (cce_id) REFERENCES COST_CENTER (cce_id),
							CONSTRAINT fk_tpc_2_pcy FOREIGN KEY (pcy_id) REFERENCES PAYMENT_CYCLE (pcy_id),
							CONSTRAINT fk_tpc_2_tpt FOREIGN KEY (tpt_id) REFERENCES THIRD_PARTY_TERMINATION (tpt_id), 
							CONSTRAINT CHK_THP_ID_NOT_NULL CHECK (THP_ID IS NOT NULL) DEFERRABLE INITIALLY DEFERRED, 
							CONSTRAINT CHK_HOU_ID_NOT_NULL CHECK (HOU_ID IS NOT NULL) DEFERRABLE INITIALLY DEFERRED, 
							CONSTRAINT CHK_FOA_ID_NOT_NULL CHECK (FOA_ID IS NOT NULL) DEFERRABLE INITIALLY DEFERRED, 
							CONSTRAINT CHK_CCE_ID_NOT_NULL CHECK (CCE_ID IS NOT NULL) DEFERRABLE INITIALLY DEFERRED,
							CONSTRAINT CHK_PCY_ID_NOT_NULL CHECK (PCY_ID IS NOT NULL) DEFERRABLE INITIALLY DEFERRED);

-- SEQUENCE THIRD_PARTY_CONTRACT
CREATE SEQUENCE SEQ_THIRD_PARTY_CONTRACT
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- COMMENT THIRD_PARTY_CONTRACT
COMMENT ON TABLE THIRD_PARTY_CONTRACT IS 'CONTRAT_TIERS';
COMMENT ON COLUMN THIRD_PARTY_CONTRACT.tpc_contract_period IS 'Durée du contrat, en nombre de mois';
COMMENT ON COLUMN THIRD_PARTY_CONTRACT.tpc_notice_period IS 'Durée de préavis, en nombre de mois';
COMMENT ON COLUMN THIRD_PARTY_CONTRACT.tpc_start_validity_date IS 'Date de début de validité';
COMMENT ON COLUMN THIRD_PARTY_CONTRACT.tpc_start_supplier_payment_dat IS 'Date de début du paiement fournisseur';
COMMENT ON COLUMN THIRD_PARTY_CONTRACT.tpc_rent_amount IS 'Montant du loyer mensuel';
COMMENT ON COLUMN THIRD_PARTY_CONTRACT.tpc_expected_charge_cost IS 'Montant des charges prévisionnelles mensuelles';
COMMENT ON COLUMN THIRD_PARTY_CONTRACT.tpc_guaranteed_deposit_amount IS 'Montant du dépôt de garantie';
COMMENT ON COLUMN THIRD_PARTY_CONTRACT.tpc_real_estate_agency_fee IS 'Montant des frais d''agence';
COMMENT ON COLUMN THIRD_PARTY_CONTRACT.tpc_sporadically_invoicing IS 'Facturation ponctuelle';
COMMENT ON COLUMN THIRD_PARTY_CONTRACT.tpc_cancellation_date IS 'Date résiliation contrat';
COMMENT ON COLUMN THIRD_PARTY_CONTRACT.tpc_guaranteed_deposit_refund IS 'Remboursement du dépôt de garantie';


-- CONTRACT (CONTRAT OCCUPANT)
CREATE TABLE CONTRACT (	con_id NUMBER(19,0) not null, 
					con_signature_date DATE not null, 
					con_notice_period NUMBER(2) not null, 
					con_start_validity_date DATE not null, 
					con_end_validity_date DATE not null, 
					con_revised_surface_area_rent NUMBER(9,2) not null, 
					con_market_rent_price NUMBER(9,2) not null, 
					con_rent_price_limit NUMBER(9,2) not null, 
					con_garage_rent NUMBER(9,2) not null, 
					con_garden_rent NUMBER(9,2) not null, 
					con_extra_rent NUMBER(9,2), 
					con_withdrawn_rent NUMBER(9,2), 
					con_benefits NUMBER(9,2), 
					con_crl_amount NUMBER(9,2), 
					con_real_estate_rental_value NUMBER(9,2), 
					con_comment VARCHAR2(4000 CHAR), 
					hou_id NUMBER(19,0) not null, 
					ten_id NUMBER(19,0) not null, 
					rtp_id NUMBER(19,0) not null, 
					ter_id NUMBER(19,0), 
					fie_id NUMBER(19,0) not null, 
					cce_id NUMBER(19,0) not null, 
					pme_id NUMBER(19,0) not null, 
					ara_id NUMBER(19,0) not null, 
					CONSTRAINT pk_contract PRIMARY KEY (con_id) USING INDEX TABLESPACE TBS_ABITA_IDX, 
					CONSTRAINT fk_con_2_dwe FOREIGN KEY (hou_id) REFERENCES HOUSING (hou_id), 
					CONSTRAINT fk_con_2_occ FOREIGN KEY (ten_id) REFERENCES TENANT (ten_id), 
					CONSTRAINT fk_con_2_rtp FOREIGN KEY (rtp_id) REFERENCES RENT_TYPOLOGY (rtp_id), 
					CONSTRAINT fk_con_2_ter FOREIGN KEY (ter_id) REFERENCES TERMINATION (ter_id), 
					CONSTRAINT fk_con_2_fie FOREIGN KEY (fie_id) REFERENCES FIELD (fie_id), 
					CONSTRAINT fk_con_2_cce FOREIGN KEY (cce_id) REFERENCES COST_CENTER (cce_id), 
					CONSTRAINT fk_con_2_pme FOREIGN KEY (pme_id) REFERENCES PAYMENT_METHOD (pme_id), 
					CONSTRAINT fk_con_2_ara FOREIGN KEY (ara_id) REFERENCES ADJUSTMENT_RATE (ara_id), 
					CONSTRAINT CHK_CON_HOU_ID_NOT_NULL CHECK (HOU_ID IS NOT NULL) DEFERRABLE INITIALLY DEFERRED, 
					CONSTRAINT CHK_CON_TEN_ID_NOT_NULL CHECK (TEN_ID IS NOT NULL) DEFERRABLE INITIALLY DEFERRED, 
					CONSTRAINT CHK_CON_RTP_ID_NOT_NULL CHECK (RTP_ID IS NOT NULL) DEFERRABLE INITIALLY DEFERRED, 
					CONSTRAINT CHK_CON_FIE_ID_NOT_NULL CHECK (FIE_ID IS NOT NULL) DEFERRABLE INITIALLY DEFERRED, 
					CONSTRAINT CHK_CON_CCE_ID_NOT_NULL CHECK (CCE_ID IS NOT NULL) DEFERRABLE INITIALLY DEFERRED, 
					CONSTRAINT CHK_CON_PME_ID_NOT_NULL CHECK (PME_ID IS NOT NULL) DEFERRABLE INITIALLY DEFERRED, 
					CONSTRAINT CHK_CON_ARA_ID_NOT_NULL CHECK (ARA_ID IS NOT NULL) DEFERRABLE INITIALLY DEFERRED);

-- SEQUENCE CONTRACT
CREATE SEQUENCE SEQ_CONTRACT
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- COMMENT CONTRACT
COMMENT ON TABLE CONTRACT IS 'CONTRAT';
COMMENT ON COLUMN CONTRACT.con_signature_date IS 'Date de signature du contrat';
COMMENT ON COLUMN CONTRACT.con_notice_period IS 'Délai de préavis';
COMMENT ON COLUMN CONTRACT.con_start_validity_date IS 'Date de début de validité du contrat';
COMMENT ON COLUMN CONTRACT.con_end_validity_date IS 'Date de fin de validité du contrat';
COMMENT ON COLUMN CONTRACT.con_revised_surface_area_rent IS 'Loyer surface corrigée';
COMMENT ON COLUMN CONTRACT.con_market_rent_price IS 'Prix du loyer sur le marché';
COMMENT ON COLUMN CONTRACT.con_rent_price_limit IS 'Loyer plafond';
COMMENT ON COLUMN CONTRACT.con_garage_rent IS 'Loyer garage';
COMMENT ON COLUMN CONTRACT.con_garden_rent IS 'Loyer jardin';
COMMENT ON COLUMN CONTRACT.con_extra_rent IS 'Surloyer';
COMMENT ON COLUMN CONTRACT.con_withdrawn_rent IS 'Loyer prélevé';
COMMENT ON COLUMN CONTRACT.con_benefits IS 'Avantages en nature';
COMMENT ON COLUMN CONTRACT.con_crl_amount IS 'Montant de la CRL';
COMMENT ON COLUMN CONTRACT.con_real_estate_rental_value IS 'Montant de la Valeur Locative Foncière';

