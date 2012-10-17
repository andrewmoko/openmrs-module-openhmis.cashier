/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.openhmis.cashier.web.controller;

import java.util.Set;

import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.cashier.api.IBillService;
import org.openmrs.module.openhmis.cashier.api.model.Bill;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/module/openhmis/cashier/bill.form")
public class BillAddEditController {
	
	@RequestMapping(method = RequestMethod.GET)
	public void bill(ModelMap model,
			@RequestParam(value = "billUuid", required = false) String billUuid,
			@RequestParam(value = "patientUuid", required = false) String patientUuid) {
		Patient patient = null;
		if (patientUuid != null) {
			String patientIdentifier = null;
			PatientService service = Context.getPatientService();
			patient = service.getPatientByUuid(patientUuid);
			Set<PatientIdentifier> identifiers = patient.getIdentifiers();
			for (PatientIdentifier id : identifiers)
				if (id.getPreferred()) patientIdentifier = id.getIdentifier();
			model.addAttribute("patient", patient);
			model.addAttribute("patientIdentifier", patientIdentifier);
		}
		else if (billUuid != null) {
			Bill bill = null;
			IBillService service = Context.getService(IBillService.class);
			bill = service.getByUuid(billUuid);
			patient = bill.getPatient();
			model.addAttribute("bill", bill);
			model.addAttribute("billAdjusted", bill.getBillAdjusted());
			model.addAttribute("adjustedBy", bill.getAdjustedBy());
			model.addAttribute("patient", patient);
		}
	}
}