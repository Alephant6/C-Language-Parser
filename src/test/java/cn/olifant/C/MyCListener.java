package cn.olifant.C;

public class MyCListener extends CBaseListener {
    private String type;
    private String name;
    private String size;

//    @Override
//    public void exitDeclarator(CParser.DeclaratorContext ctx) {
//        if (ctx.pointer() != null) {
//            type = "pointer to " + type;
//        }
//        if (ctx.directDeclarator().directDeclarator() != null) {
//            size = ctx.directDeclarator().getText();
//            type = "array of " + size + " " + type;
//        }
//        if (ctx.directDeclarator().Identifier() != null) {
//            name = ctx.directDeclarator().Identifier().getText();
//        }
//
//    }

    @Override
    public void exitTypeSpecifier(CParser.TypeSpecifierContext ctx) {
//        type = ctx.getRuleContext().getText();
        name = ctx.typedefName().getText();
    }

    @Override
    public void exitDeclarationSpecifier(CParser.DeclarationSpecifierContext ctx) {
        type = ctx.getText();
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }
}
