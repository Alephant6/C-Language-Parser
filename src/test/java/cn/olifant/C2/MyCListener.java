package cn.olifant.C2;


public class MyCListener extends CBaseListener{
    // 定义最基础的类型 baseType
    private String baseType;
    // 判断是否为几层函数声明，默认不是函数。不用enterFunctionDefinition和enterFunctionSpecifier来监听
    // 是因为在functionDefinition的判断在declaration更上层的externalDeclaration，declaration中无functionDefinition规则；
    // 并且在externalDeclaration下会优先执行declaration，更无法识别出functionDefinition
    // 同理functionSpecifier也被其他的规则顶替
    private int numFunctionDeclaration =0;
    // 中括号-[]层数，默认没有
    private int numBracket =0;
    // 普通指针个数，默认没有
    private int numPointer =0;
    // 抽象指针个数，默认没有
    private int numAbstractPointer =0;
    // 函数参数个数，默认没有
    private int numParameters =0;
    // 函数参数索引数
    private int parameterIndex =0;



    // 进入declaration时
    @Override
    public void enterDeclaration(CParser.DeclarationContext ctx) {
        //如果只有declaration两个子节点，说明是最基本的变量声明，这时候它的第一个子节点不会分离类型和变量名，所以需要进一步获取子节点
        if (ctx.getChildCount()==2){
            baseType = ctx.getChild(0).getChild(0).getText();
        }else {
            // 否则declaration第一个子节点就是基础类型
            baseType = ctx.getChild(0).getText();
        }
    }

    // 离开declaration时
    @Override
    public void exitDeclaration(CParser.DeclarationContext ctx) {
        // 打印基础规则
        // 如果不含函数，则最后打印
        if (numFunctionDeclaration ==0){
            System.out.print(baseType);
        }

    }

    // 进入directDeclarator
    @Override
    public void enterDirectDeclarator(CParser.DirectDeclaratorContext ctx) {
            // 如果directDeclarator中以]结尾，说明含有[]
            if (ctx.getText().endsWith("]")) {
                // 判定存在[]，且其层数加1，这是一个数组
                numBracket++;
            }
//        }
    }

    // 退出directDeclarator
    @Override
    public void exitDirectDeclarator(CParser.DirectDeclaratorContext ctx) {
        if (numBracket !=0 && ctx.getChildCount() > 2) {
            String size = ctx.getChild(ctx.getChildCount()-2).getText();
            System.out.print("size-" + size + " array of " );
            // []，且其层数减1
            numBracket--;
        }
    }

    // 进入declarator
    @Override
    public void enterDeclarator(CParser.DeclaratorContext ctx) {
        // 如果不为空，获取declartor中指针个数
        if (ctx.pointer()!=null) numPointer = ctx.pointer().getChildCount();
    }

    // 离开declarator
    @Override
    public void exitDeclarator(CParser.DeclaratorContext ctx) {
        // 有多少个指针就打印多少次
        while (numPointer!=0){
            System.out.print("pointer to ");
            numPointer--;
        }

    }

    // 进入abstractDeclarator
    @Override
    public void enterAbstractDeclarator(CParser.AbstractDeclaratorContext ctx) {
        // 如果不为空，获取abstractDeclartor中抽象指针个数
        if (ctx.pointer()!=null) numAbstractPointer = ctx.pointer().getChildCount();
    }

    // 退出abstractDeclarator
    @Override
    public void exitAbstractDeclarator(CParser.AbstractDeclaratorContext ctx) {
        // 有多少个抽象指针就打印多少次
        while (numAbstractPointer != 0) {
            System.out.print("pointer to ");
            numAbstractPointer--;
        }
    }

    // 进入parameterList
    @Override
    public void enterParameterList(CParser.ParameterListContext ctx) {
        // 函数声明数加1
        numFunctionDeclaration++;
        // 获得函数参数数量
        numParameters = ctx.parameterDeclaration().size();
        // 如果函数只有一个参数
        if (numParameters == 1){
            System.out.print("function that accepts ");
        } else {
            System.out.print("function that accepts (");
        }

    }

    // 进入参数-parameterDeclaration
    @Override
    public void enterParameterDeclaration(CParser.ParameterDeclarationContext ctx) {
        // 如果是最后一个，且不只有一个参数
        if(parameterIndex==numParameters-1 && numParameters !=1){
            System.out.print("and ");
        }
    }

    // 退出参数-parameterDeclaration
    @Override
    public void exitParameterDeclaration(CParser.ParameterDeclarationContext ctx) {
        String typeName = ctx.getChild(0).getText();
        // 如果是第一个就直接打印
        if (parameterIndex==0){
            System.out.print(typeName+" ");
        // 如果是最后一个，且不只有一个参数
        }else if (parameterIndex==numParameters-1 && numParameters !=1) {
            System.out.print(typeName+") ");
        // 去头去尾后剩下的是中间参数，用", "隔开
        }else {
            System.out.print(", "+typeName);
        }
        // 参数索引数加1
        parameterIndex++;
    }

    // 离开函数-parameterList
    @Override
    public void exitParameterList(CParser.ParameterListContext ctx) {
        System.out.print("returning "+ baseType );


    }



}
