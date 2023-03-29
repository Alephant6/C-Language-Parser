package cn.olifant.CDeclaration;


public class CDeclarationListener extends CBaseListener{
    // Define the most basic type baseType
    private String baseType;
    // Number of function parameters, none by default
    private int numParameters =0;
    // Function parameter index number
    private int parameterIndex =0;


    @Override
    public void exitDeclaration(CParser.DeclarationContext ctx) {
        // Get the basic type in the declaration
        if (ctx.getChildCount()==2 && ctx.declarationSpecifiers()!=null) {
            baseType = ctx.declarationSpecifiers().getChild(0).getText();
        } else if (ctx.declarationSpecifiers()!=null){
            baseType = ctx.declarationSpecifiers().getText();
        }
        // print baseType
        System.out.print(baseType);

    }


    @Override
    public void exitDirectDeclarator(CParser.DirectDeclaratorContext ctx) {
        // Determine whether it is an array, and the size is not empty
        if (ctx.LeftBracket()!=null && ctx.assignmentExpression()!=null){
            String size = ctx.assignmentExpression().getText();
            System.out.print("size-" + size + " array of " );
        }
    }


    @Override
    public void exitDeclarator(CParser.DeclaratorContext ctx) {
        // The number of common pointers, none by default
        int numPointer =0;
        // If not empty, get the number of pointers in declartor
        if (ctx.pointer()!=null) numPointer = ctx.pointer().getChildCount();
        // Print as many times as there are pointers
        while (numPointer!=0){
            System.out.print("pointer to ");
            numPointer--;
        }

    }


    @Override
    public void exitAbstractDeclarator(CParser.AbstractDeclaratorContext ctx) {
        // The number of abstract pointers, none by default
        int numAbstractPointer =0;
        // If not empty, get the number of abstract pointers in abstractDeclartor
        if (ctx.pointer()!=null) numAbstractPointer = ctx.pointer().getChildCount();
        // Print as many times as there are abstract pointers
        while (numAbstractPointer != 0) {
            System.out.print("pointer to ");
            numAbstractPointer--;
        }
    }


    @Override
    public void enterParameterList(CParser.ParameterListContext ctx) {
        // Get the number of function parameters
        numParameters = ctx.parameterDeclaration().size();
        // If the function has only one parameter
        if (numParameters == 1){
            System.out.print("function that accepts ");
        } else {
            System.out.print("function that accepts (");
        }

    }


    @Override
    public void enterParameterDeclaration(CParser.ParameterDeclarationContext ctx) {
        // If it is the last one, and there is more than one parameter
        if(parameterIndex==numParameters-1 && numParameters !=1){
            System.out.print(" and ");
        }
        // if between the first and the second-to-last
        if(parameterIndex>0 && parameterIndex<numParameters-1){
            System.out.print(", ");

        }
    }


    @Override
    public void exitParameterDeclaration(CParser.ParameterDeclarationContext ctx) {
        // Get the base name of parameter
        String parameterBaseName = ctx.getChild(0).getText();
        // If there are multiple parameters and it is the last
        if (parameterIndex==numParameters-1 && numParameters !=1) {
            System.out.print(parameterBaseName +")");
        }else {
            System.out.print(parameterBaseName);
        }
        // Increment the parameter index number by 1
        parameterIndex++;
    }


    @Override
    public void exitParameterList(CParser.ParameterListContext ctx) {
        System.out.print(" returning " );
    }



}
