package eu.stamp.eclipse.dspot.controls.impl;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.swt.widgets.Composite;

import eu.stamp.eclipse.plugin.dspot.controls.IControllerProxy;
import eu.stamp.eclipse.plugin.dspot.controls.SimpleController;
import eu.stamp.eclipse.plugin.dspot.processing.DSpotMapping;

public class SimpleControllerProxy extends SimpleController implements IControllerProxy {

	protected final SimpleController innerController;
	
	protected String data;
	
	protected String temporalData;
	
	public SimpleControllerProxy(SimpleController innerController) {
		super(null,null,false,false,0,null);
		this.innerController = innerController;
        this.place = innerController.place;
        this.activationDirection = innerController.activationDirection;		
	    innerController.setProxy(this);
	}
	
	@Override
	public void createControl(Composite parent) {
		innerController.createControl(parent);
	}
	
	@Override
	public void loadConfiguration(ILaunchConfiguration configuration) {
		innerController.loadConfiguration(configuration);
	}

	@Override
	public void setText(String text) {
		innerController.setText(text);
	}

	@Override
	public void notifyListener() {
		innerController.notifyListener();
	}

	@Override
	public void setEnabled(boolean enabled) {
		innerController.setEnabled(enabled);
	}

	@Override
	public void loadProject() {
		innerController.loadProject();
	}

	@Override
	public void updateController(String data) {
		innerController.updateController(data);
	}

	@Override
	public int checkActivation(String condition) {
		return innerController.checkActivation(condition);
	}

	@Override
	public void save() {
		data = temporalData;
		DSpotMapping.getInstance().setValue(innerController.getKey(),data);
	}
	
	public void setTemporalData(String temporalData) {
		this.temporalData = temporalData;
	}

}
