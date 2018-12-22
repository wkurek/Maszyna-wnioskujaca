package model.operator;

import model.*;
import model.graph.AndNode;
import model.graph.Node;
import model.graph.PredicateNode;

import java.util.ArrayList;
import java.util.Arrays;

public class AndOperator extends Operator {
    AndOperator(ArrayList<Goal> operands) {
        super(operands);
    }

    public AndOperator(Goal... operands) {
        super(Arrays.asList(operands));
    }

    @Override
    public Operator getTailOperator() {
        ArrayList<Goal> tailOpernads = new ArrayList<>(operands);
        tailOpernads.remove(0); //remove first operand

        return new AndOperator(tailOpernads);
    }

    @Override
    public String toString() {
        String string = "";

        for(int i = 0; i < getOperandsCount(); ++i) {
            string = string.concat(getOpernad(i).toString());

            if(i != (getOperandsCount() - 1)) string = string.concat(" ^ ");
        }

        return string;
    }


    @Override
    public Expression replaceVariables(SubstitutionSet substitutionSet) {
        ArrayList<Goal> newOperands = new ArrayList<>();

        for(int i = 0; i < getOperandsCount(); ++i) {
            newOperands.add((Goal) getOpernad(i).replaceVariables(substitutionSet));
        }

        return new AndOperator(newOperands);
    }

    @Override
    public Node getNode(SubstitutionSet substitutionSet, ClausureSet clausureSet) {
        if(!hasTailOperands() && (getFirstOperand() instanceof Conclusion)) {
            return new PredicateNode(clausureSet, substitutionSet, getFirstOperand());
        }

        return new AndNode(clausureSet, substitutionSet, this);
    }
}
