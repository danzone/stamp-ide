/*******************************************************************************
 * Copyright (c) 2018 Atos
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 	Ricardo Jose Tejada Garcia (Atos) - main developer
 * 	Jesús Gorroñogoitia (Atos) - architect
 * Initially developed in the context of STAMP EU project https://www.stamp-project.eu
 *******************************************************************************/
package eu.stamp.wp4.descartes.wizard.configuration;

import eu.stamp.wp4.descartes.wizard.DescartesWizard;

public interface IDescartesWizardPart {
	/**
	 * The wizard calls this method over all the parts implementing this interface
	 * when a change in the configuration requires an update in the wizard parts
	 * for example another project is selected
	 * @param wConf : teh new wizard configuration
	 */
	public void updateDescartesWizardPart(DescartesWizardConfiguration wConf);
	/**
	 * @param wizard : the updated reference too the Descartes wizard
	 */
	public void updateWizardReference(DescartesWizard wizard);

}
