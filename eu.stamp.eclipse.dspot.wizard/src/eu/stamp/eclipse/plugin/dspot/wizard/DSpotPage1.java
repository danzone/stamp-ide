
package eu.stamp.eclipse.plugin.dspot.wizard;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import eu.stamp.eclipse.plugin.dspot.context.ConfigurationManager;
import eu.stamp.eclipse.plugin.dspot.context.ProjectManager;
import eu.stamp.eclipse.plugin.dspot.controls.Controller;
import eu.stamp.eclipse.plugin.dspot.processing.DSpotMapping;
/**
 * 
 */
public class DSpotPage1 extends DSpotPage {

	protected DSpotPage1(String pageName) {
		super(pageName,"page1");
	}
	
	@Override
	public void createControl(Composite parent) {
		
		// create the composite
		Composite composite = new Composite(parent,SWT.NONE);
		GridLayout layout = new GridLayout();    // the layout of composite
		layout.numColumns = 3;
		composite.setLayout(layout);

		ProjectManager projectManager = new ProjectManager();
		ConfigurationManager configurationManager = new ConfigurationManager(); // TODO
		configurationManager.setProjectManager(projectManager);
        configurationManager.createConfigurationControl(composite,this);
        projectManager.createProjectControl(composite);
        
		List<Controller> controllers = DSpotMapping.getInstance()
				.getControllersList(ID);
		for(Controller controller : controllers) {
			if(controller instanceof IPageUserElement)((IPageUserElement)controller).setPage(this);
			controller.createControl(composite);
			controller.loadProject();
		}
		
		// required to avoid an error in the System
		setControl(composite);
		setPageComplete(true);
	}
}