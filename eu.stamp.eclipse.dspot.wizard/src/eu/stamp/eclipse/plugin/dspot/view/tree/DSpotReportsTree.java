package eu.stamp.eclipse.plugin.dspot.view.tree;

import org.eclipse.swt.widgets.Tree;

import eu.stamp.eclipse.plugin.dspot.json.DSpotTestClassJSON;
import eu.stamp.eclipse.plugin.dspot.json.DSpotTimeJSON;

public abstract class DSpotReportsTree {

protected DSpotTestClassJSON info; // TODO put java-doc

public final String name;

public DSpotReportsTree(DSpotTestClassJSON info,String name) {
this.info = info;
this.name = name; 
}

/**
 *  this method must be called in the form Display.getDefafult().syncExec(new Runnable(){
 *  public void run() { DSpotReportsTree.createTree(tree,info,time); });
 */
public abstract void createTree(Tree tree,DSpotTimeJSON time);
}
