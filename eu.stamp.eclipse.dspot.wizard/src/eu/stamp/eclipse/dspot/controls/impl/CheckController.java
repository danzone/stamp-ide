package eu.stamp.eclipse.dspot.controls.impl;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import eu.stamp.eclipse.plugin.dspot.controls.CheckProxy;
import eu.stamp.eclipse.plugin.dspot.controls.Controller;
import eu.stamp.eclipse.plugin.dspot.processing.DSpotMapping;
import eu.stamp.eclipse.plugin.dspot.properties.DSpotProperties;

public class CheckController extends Controller {

	private Button button;
	
	protected CheckProxy proxy;
	
	public CheckController(String key, String labelText, int place, String tooltip,String activationDirection,String condition) {
		super(key,labelText,false,place,tooltip);
		this.activationDirection = activationDirection;
		this.condition = condition;
	}
	
	public void setProxy(CheckProxy proxy) { this.proxy = proxy; }

	@Override
	public void notifyListener() { 
		if(button != null) DSpotMapping.getInstance()
		.setValue(key,String.valueOf(button.getSelection()));
	}
	
	@Override
	public void createControl(Composite parent) {
		button = new Button(parent,SWT.CHECK);
		GridDataFactory.swtDefaults().indent(DSpotProperties.INDENT).applyTo(button);
		button.setTextDirection(SWT.LEFT);
		button.setText(labelText);
		button.setToolTipText(tooltip);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
                activations();
				if(proxy == null) DSpotMapping.getInstance()
					.setValue(key,String.valueOf(button.getSelection()));
				else proxy.setTemporalData(button.getSelection());
			}
		});
		updateController(DSpotMapping.getInstance().getValue(key));
	    setEnabled(isEnabled);
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.isEnabled = enabled;
		if(button != null && !button.isDisposed()) button.setEnabled(enabled);
	}

	@Override
	public void loadConfiguration(ILaunchConfiguration configuration) {
		try {
			String selection = configuration.getAttribute(key,"false");
			boolean boo = selection.contains("rue");
			if(button != null && !button.isDisposed())button.setSelection(boo);
		    if(proxy != null) {
		    	proxy.setTemporalData(boo);
		    	proxy.save();
		    }
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadProject() {}

	@Override
	public void updateController(String data) {
		if(data == null || button == null) return;
		button.setSelection(data.contains("rue"));
	}

	@Override
	public int checkActivation(String condition) {
		if(button.getSelection() == condition.contains("rue")) {
			if(condition.contains("nti")) return ANTI_ACTIVATION;
			return ACTIVATION;
		}
		else if(button.getSelection() == condition.contains("alse")){
			if(condition.contains("nti")) return ACTIVATION;
			return ANTI_ACTIVATION;
		}
		return 0;
	}
}
