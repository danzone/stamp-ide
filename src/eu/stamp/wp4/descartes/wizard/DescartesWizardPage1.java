package eu.stamp.wp4.descartes.wizard;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.ui.wizards.TypedElementSelectionValidator;
import org.eclipse.jdt.internal.ui.wizards.TypedViewerFilter;
import org.eclipse.jdt.ui.JavaElementComparator;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jdt.ui.StandardJavaElementContentProvider;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

import eu.stamp.wp4.descartes.wizard.configuration.DescartesWizardConfiguration;
import eu.stamp.wp4.descartes.wizard.configuration.IDescartesWizardPart;
import eu.stamp.wp4.descartes.wizard.utils.DescartesWizardConstants;

@SuppressWarnings("restriction")
public class DescartesWizardPage1 extends WizardPage implements IDescartesWizardPart{
	
	private DescartesWizard wizard;
	private String[] mutatorsTexts;
	private ArrayList<TreeItem> items = new ArrayList<TreeItem>(1);
	private String[] initialNames;
	private  Tree mutatorsTree;

	public DescartesWizardPage1(DescartesWizard wizard) {
		super("First page");
		this.wizard = wizard;
		setTitle("First page");
	}
	
	// widgets
	Text projectText;
	
    private String projectPath;
	
	@Override
	public void createControl(Composite parent) {
		
		// create the composite
		Composite composite = new Composite(parent,SWT.NONE);
		GridLayout layout = new GridLayout();    // the layout of composite
		layout.numColumns = 3;
		composite.setLayout(layout);
		/*
		 *   ROW 1
		 */
		Label projectLabel = new Label(composite,SWT.NONE);
		projectLabel.setText("path of the project : ");
		GridDataFactory.swtDefaults().grab(false, false).applyTo(projectLabel);
		
		projectText = new Text(composite,SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(projectLabel);
		projectText.setText(projectPath);
		
		Button projectButton = new Button(composite,SWT.PUSH);
		projectButton.setText("Select a Project");
		GridDataFactory.swtDefaults().applyTo(projectButton);
		/*
		 *   ROW 2
		 */
		Label mutatorsLabel = new Label(composite,SWT.NONE);
		mutatorsLabel.setText("Mutators");
		GridDataFactory.fillDefaults().span(3, 1).applyTo(mutatorsLabel);
		/*
		 *   ROW 3
		 */
		mutatorsTree = new Tree(composite,SWT.V_SCROLL | SWT.CHECK);
        GridData gd = new GridData(SWT.FILL,SWT.FILL,true,true);
        gd.horizontalSpan = 2;
        gd.verticalSpan = 6;
        gd.minimumWidth = 250;
        mutatorsTree.setLayoutData(gd);
        mutatorsTree.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
        GridLayout Layforgr1 = new GridLayout();
        mutatorsTree.setLayout(Layforgr1);
        
        for(int i = 0; i < mutatorsTexts.length; i++) {
         TreeItem item = new TreeItem(mutatorsTree,SWT.NONE);	
         item.setText(mutatorsTexts[i]);
         items.add(item);
        }

         initialNames = new String[items.size()];
         for(int i = 0; i < items.size(); i++) initialNames[i] = items.get(i).getText();

        Button removeMutatorButton = new Button(composite,SWT.PUSH);
        removeMutatorButton.setText("Remove selected mutators");
        GridDataFactory.fillDefaults().applyTo(removeMutatorButton);
        
        Button addMutatorButton = new Button(composite,SWT.PUSH);
        addMutatorButton.setText("Add mutator");
        GridDataFactory.fillDefaults().applyTo(addMutatorButton);
        
        Button removeAllButton = new Button(composite,SWT.PUSH);
        removeAllButton.setText("Remove all");
        GridDataFactory.fillDefaults().applyTo(removeAllButton);
        
        Button initialListButton = new Button(composite,SWT.PUSH);
        initialListButton.setText("Set initial mutators");
        GridDataFactory.fillDefaults().applyTo(initialListButton);
        
        Button defaultMutatorsButton = new Button(composite,SWT.PUSH);
        defaultMutatorsButton.setText("Set default mutators");
        GridDataFactory.fillDefaults().applyTo(defaultMutatorsButton);
        
        // listeners
        projectButton.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		IJavaProject jProject = showProjectDialog();
        		wizard.setWizardConfiguration(new DescartesWizardConfiguration(jProject));
        	}
        });
        removeMutatorButton.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
            for(int i = 0; i < items.size(); i++) {
            	if(!items.get(i).isDisposed()) if(items.get(i).getChecked()) {
            		items.get(i).dispose();
            		items.remove(i); i--;
            	}
            }}
        });
        addMutatorButton.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        	  String sr = showInputDialog();
        	  if(sr != null) { 
        		  items.add(new TreeItem(mutatorsTree,SWT.NONE));
        		  items.get(items.size()-1).setText(sr);
        	  }
        	}
        });
        removeAllButton.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		for(int i = items.size()-1; i >= 0; i--) items.remove(i);
        		mutatorsTree.removeAll();
        	}
        });
        initialListButton.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		for(int i = items.size()-1; i >= 0; i--) items.remove(i);
        		mutatorsTree.removeAll();
        		for(int i = 0; i < initialNames.length; i++) {
        		TreeItem it = new TreeItem(mutatorsTree,SWT.NONE);
        		it.setText(initialNames[i]);
        		items.add(it);}
        	}
        });
        String[] defaultMutators = {"void","null","true","false","empty","0","1",
    			"(byte)0","(byte)1","(short)1","(short)2","0L","1L","0.0","1.0","0.0f","1.0f",
    			"'\\40'","'A'","\"\"","\"A\""};
        defaultMutatorsButton.addSelectionListener(new SelectionAdapter(){
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		for(int i = items.size()-1; i >= 0; i--) items.remove(i);
        		mutatorsTree.removeAll();
        		for(int i = 0; i < defaultMutators.length; i++) {
        		TreeItem it = new TreeItem(mutatorsTree,SWT.NONE);
        		it.setText(defaultMutators[i]);
        		items.add(it);}
        	}
        });
		// required to avoid an error in the System
		setControl(composite);
		setPageComplete(true);	
	    
		}

	@Override
	public void updateDescartesWizardPart(DescartesWizardConfiguration wConf) {
		projectPath = wConf.getProjectPath();
		if(projectText != null) { if(!projectText.isDisposed()) projectText.setText(projectPath);}
		
		mutatorsTexts = wConf.getMutatorsTexts();
		
		if(mutatorsTree != null) if(!mutatorsTree.isDisposed()) {
		for(int i = items.size()-1; i >= 0; i--) items.remove(i);
		mutatorsTree.removeAll();
		
        for(int i = 0; i < mutatorsTexts.length; i++) {
            TreeItem item = new TreeItem(mutatorsTree,SWT.NONE);
            item.setText(mutatorsTexts[i]);
            items.add(item);
           }

            initialNames = new String[items.size()];
            for(int i = 0; i < items.size(); i++) initialNames[i] = items.get(i).getText();}

	}
	@Override
	public void updateWizardReference(DescartesWizard wizard) {
		this.wizard = wizard;
	}
	
	public String[] getMutatorsSelection() {
		String[] texts = new String[items.size()];
		for(int i = 0; i < items.size(); i++) texts[i] = items.get(i).getText();
		return texts;
	}
	
	private String showInputDialog () {
		InputDialog dialog = new InputDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Add mutator",
				"enter the new mutator",null,null);
		if(dialog.open() == Window.OK) return dialog.getValue();
		return null;
	}
	
	private IJavaProject showProjectDialog() {
		
		Class<?>[] acceptedClasses = new Class[] {IJavaProject.class,IProject.class};
		TypedElementSelectionValidator validator = new TypedElementSelectionValidator(acceptedClasses,true);
		ViewerFilter filter= new TypedViewerFilter(acceptedClasses) {
			@Override
			public boolean select(Viewer viewer,Object parentElement, Object element) {
				if(element instanceof IProject) {
					try {
						return ((IProject)element).hasNature(DescartesWizardConstants.MAVEN_NATURE_ID);
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
				if(element instanceof IJavaProject) {
					try {
						return ((IJavaProject)element).getProject().hasNature(DescartesWizardConstants.MAVEN_NATURE_ID);
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
				return false;
			}
		};	
		
		  IWorkspaceRoot fWorkspaceRoot= ResourcesPlugin.getWorkspace().getRoot();
	        
	        StandardJavaElementContentProvider provider= new StandardJavaElementContentProvider();
	        ILabelProvider labelProvider= new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_DEFAULT);
	        Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	        ElementTreeSelectionDialog dialog= new ElementTreeSelectionDialog(shell, labelProvider, provider);
	        dialog.setValidator(validator);
	        dialog.setComparator(new JavaElementComparator());
	        dialog.setTitle(" Select a project ");
	        dialog.setMessage(" Select a project ");
	        dialog.setInput(JavaCore.create(fWorkspaceRoot));
	        dialog.addFilter(filter);
	        dialog.setHelpAvailable(false);
	        
	      

	        if(dialog.open() == Window.OK) {
	            Object[] results = dialog.getResult();
	            for(Object ob : results) {
	            	if(ob instanceof IJavaProject) { 
	            		IJavaProject jProject = (IJavaProject)ob;
	            		return jProject;
	             }
	            }
	        }
	        return null;
	}

}