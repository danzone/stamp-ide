package eu.stamp.eclipse.plugin.dspot.view.tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import eu.stamp.eclipse.plugin.dspot.json.DSpotTimeJSON;

public class CloverCoverageReportsTree extends DSpotReportsTree{

	private File cloverTextFile;
	
	public CloverCoverageReportsTree(File cloverTextFile) {
		super(null,"Clover Coverage Selector");
		this.cloverTextFile = cloverTextFile;
	}

	@Override
	public void createTree(Tree tree, DSpotTimeJSON time) {
		
		TreeItem rootItem = new TreeItem(tree,SWT.NONE);
		rootItem.setText(0,"Test Class name : ");
		String name = cloverTextFile.getName();
		name = name.substring(name.lastIndexOf(time.projectName + ".") + time.projectName.length() + 1,
				name.indexOf("_clover"));
		rootItem.setText(1,name);

		TreeItem item = new TreeItem(rootItem,SWT.NONE);
		item.setText(0, "Amplification time : ");
		item.setText(1, String.valueOf(time.getClassTime(name)) + " ms");
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(cloverTextFile));
			String line;
			while((line = br.readLine()) != null) {
				if(line.contains(":")) {
					String[] data = line.split(":");
					item = new TreeItem(rootItem,SWT.NONE);
					item.setText(data);
				}
			}
			br.close();
		} catch (IOException e) { e.printStackTrace();}
		
	}

}
