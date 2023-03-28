package cn.olifant.CDeclaration;


public class CDeclarationListener extends CBaseListener{
    // Define the most basic type baseType
    private String baseType;
    // Determine whether it is a multi-level function declaration, the default is not a function.
    private int numFunctionDeclaration =0;
    // The number of common pointers, none by default
    private int numPointer =0;
    // The number of abstract pointers, none by default
    private int numAbstractPointer =0;
    // Number of function parameters, none by default
    private int numParameters =0;
    // Function parameter index number
    private int parameterIndex =0;


    @Override
    public void enterDeclaration(CParser.DeclarationContext ctx) {
        // Get the basic type in the declaration
        if (ctx.declarationSpecifiers()!=null) {
            baseType = ctx.declarationSpecifiers().getText();
        }

    }


    @Override
    public void exitDeclaration(CParser.DeclarationContext ctx) {
        // print baseType
        // If there is no function, it will be printed at the end
        if (numFunctionDeclaration ==0){
            System.out.print(baseType);
        }

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
    public void enterDeclarator(CParser.DeclaratorContext ctx) {
        // If not empty, get the number of pointers in declartor
        if (ctx.pointer()!=null) numPointer = ctx.pointer().getChildCount();
    }

    // 离开declarator
    @Override
    public void exitDeclarator(CParser.DeclaratorContext ctx) {
        // Print as many times as there are pointers
        while (numPointer!=0){
            System.out.print("pointer to ");
            numPointer--;
        }

    }


    @Override
    public void enterAbstractDeclarator(CParser.AbstractDeclaratorContext ctx) {
        // If not empty, get the number of abstract pointers in abstractDeclartor
        if (ctx.pointer()!=null) numAbstractPointer = ctx.pointer().getChildCount();
    }


    @Override
    public void exitAbstractDeclarator(CParser.AbstractDeclaratorContext ctx) {
        // Print as many times as there are abstract pointers
        while (numAbstractPointer != 0) {
            System.out.print("pointer to ");
            numAbstractPointer--;
        }
    }


    @Override
    public void enterParameterList(CParser.ParameterListContext ctx) {
        // Increase the number of function declarations by 1
        numFunctionDeclaration++;
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
            System.out.print("and ");
        }
    }


    @Override
    public void exitParameterDeclaration(CParser.ParameterDeclarationContext ctx) {
        String typeName = ctx.getChild(0).getText();
        // If it is the first one, it will be printed directly
        if (parameterIndex==0){
            System.out.print(typeName+" ");
            // If it is the last one, and there is more than one parameter
        }else if (parameterIndex==numParameters-1 && numParameters !=1) {
            System.out.print(typeName+") ");
            // After removing the head and the tail, the rest is the intermediate parameters, separated by ", "
        }else {
            System.out.print(", "+typeName);
        }
        // Increment the parameter index number by 1
        parameterIndex++;
    }


    @Override
    public void exitParameterList(CParser.ParameterListContext ctx) {
        System.out.print("returning "+ baseType );

    }



}
