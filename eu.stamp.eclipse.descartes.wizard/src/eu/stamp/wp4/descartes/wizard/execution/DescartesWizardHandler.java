/*******************************************************************************
 * Copyright (c) 2018 Atos
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 	Ricardo José Tejada García (Atos) - main developer
 * 	Jesús Gorroñogoitia (Atos) - architect
 * Initially developed in the context of STAMP EU project https://www.stamp-project.eu
 *******************************************************************************/
package eu.stamp.wp4.descartes.wizard.execution;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;

import eu.stamp.wp4.descartes.wizard.DescartesWizard;
import eu.stamp.wp4.descartes.wizard.configuration.DescartesWizardConfiguration;
import eu.stamp.wp4.descartes.wizard.utils.DescartesWizardConstants;

public class DescartesWizardHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		DescartesWizardConfiguration wConf = new DescartesWizardConfiguration();
		if(wConf.getProject() != null) { 
			try {
				if(wConf.getProject().getProject().hasNature(DescartesWizardConstants.MAVEN_NATURE_ID)) {
WizardDialog wizDiag = new WizardDialog(HandlerUtil.getActiveShell(event),new DescartesWizard(wConf));
wizDiag.open();} else {
	MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
			"Project has not maven nature", "The selected project must be maven");
}
			} catch (CoreException e) {
				e.printStackTrace();
			} 
		}else {
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"No project selected", "Please, select a maven project");		}
		return null;
	}

}
