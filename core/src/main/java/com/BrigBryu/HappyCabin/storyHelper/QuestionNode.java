package com.BrigBryu.HappyCabin.storyHelper;

import java.util.LinkedList;
import java.util.List;

public class QuestionNode {
    public List<QuestionNode> children;
    public QuestionNode parent;
    public String message;
    public String option;

    public QuestionNode(String option, String message) {
        this.option = option;
        this.message = message;
        this.children = new LinkedList<>();
    }

    public void setParent(QuestionNode parent) {
        this.parent = parent;
    }

    public void addChild(QuestionNode child) {
        children.add(child);
    }

    public LinkedList<String> getOptions(){
        LinkedList<String> list = new LinkedList<>();
        for(QuestionNode child: children) {
            list.add(child.option);
        }
        if(list.isEmpty()){
            list.add(Constants.CONTINUE);
        }
        return list;
    }

    /**
     * Returns corresponding child node that has the same option or parent if no child has that option
     */
    public QuestionNode getOption(String optionWanted){
        for(QuestionNode child:children){
            if(optionWanted.equals(child.option)){
                return child;
            }
        }
        return parent;
    }
}
