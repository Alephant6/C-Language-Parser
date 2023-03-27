package cn.olifant.C2;


public class MyCListener extends CBaseListener{
    // Define the most basic type baseType
    private String baseType;
    // Determine whether it is a multi-level function declaration, the default is not a function.
    private int numFunctionDeclaration =0;
    // Brackets-[] layer number, no by default
    private int numBracket =0;
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
        //If there are only two child nodes of declaration, it means that it is the most basic variable declaration. At this time, its first child node will not separate the type and variable name, so further child nodes need to be obtained
        if (ctx.getChildCount()==2){
            baseType = ctx.getChild(0).getChild(0).getText();
        }else {
            // Otherwise the first child node of the declaration is the underlying type
            baseType = ctx.getChild(0).getText();
        }
    }


    @Override
    public void exitDeclaration(CParser.DeclarationContext ctx) {
        // print ground rules
        // If there is no function, it will be printed at the end
        if (numFunctionDeclaration ==0){
            System.out.print(baseType);
        }

    }


    @Override
    public void enterDirectDeclarator(CParser.DirectDeclaratorContext ctx) {
            // If the directDeclarator ends with ], it means it contains []
            if (ctx.getText().endsWith("]")) {
                // It is determined that [] exists, and its layer number is increased by 1, which is an array
                numBracket++;
            }
//        }
    }


    @Override
    public void exitDirectDeclarator(CParser.DirectDeclaratorContext ctx) {
        if (numBracket !=0 && ctx.getChildCount() > 2) {
            String size = ctx.getChild(ctx.getChildCount()-2).getText();
            System.out.print("size-" + size + " array of " );
            // [], and its layer number minus 1
            numBracket--;
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
