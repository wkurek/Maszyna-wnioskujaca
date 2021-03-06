package model;

public class Variable implements Unifable {
    private static int idCounter = 0;

    private int id;
    private String name;

    public Variable() {
        this.id = idCounter++;
    }

    public Variable(String name) {
        this.name = name;
        this.id = idCounter++;
    }
    public Variable(Variable v)
    {
        this.name = v.name;
        this.id=v.id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getIdCounter() {
        return idCounter;
    }


    public static void setIdCounter(int idCounter) {
        Variable.idCounter = idCounter;
    }

    @Override
    public SubstitutionSet unify(Unifable expression, SubstitutionSet substitutionSet) {
        if(expression == this) {
            return new SubstitutionSet(substitutionSet);
        }

        if(substitutionSet.isBound(this)) {
            Unifable binding = substitutionSet.getBinding(this);
            return expression.unify(binding, substitutionSet);
        }

        SubstitutionSet newSubstitutionSet = new SubstitutionSet(substitutionSet);
        newSubstitutionSet.addSubstitution(this, expression);
        return newSubstitutionSet;
    }

    @Override
    public Expression replaceVariables(SubstitutionSet substitutionSet) {
        Expression substituted;
        if(substitutionSet.isBound(this)) {
            substituted = substitutionSet.getBinding(this);
            while (substituted instanceof Variable && substitutionSet.isBound((Variable) substituted))
                substituted = substitutionSet.getBinding((Variable)substituted);
            return substituted;
        }
        else return this;
    }

    @Override
    public String toString() {
        String string = "";
        if(name != null) string = string.concat(name);

        return string.concat("_" + id);
    }
}
