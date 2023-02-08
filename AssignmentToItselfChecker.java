import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpression;

public class AssignmentToItselfChecker extends ASTVisitor {
    public AssignmentToItselfChecker() {
        shouldVisitExpressions = true;
    }

    @Override
    public int visit(IASTExpression expression) {
        if (isAssignmentToItself(expression)) {
            System.out.println("  (num = num) : WARNING .." + expression.getFileLocation());
        }
        return PROCESS_CONTINUE;
    }

    private boolean isAssignmentToItself(IASTExpression expr) {
        if (expr instanceof IASTBinaryExpression) {
            IASTBinaryExpression binExpr = (IASTBinaryExpression) expr;
            if (binExpr.getOperator() == IASTBinaryExpression.op_assign) {
                IASTExpression operand1 = binExpr.getOperand1();
                IASTExpression operand2 = binExpr.getOperand2();
                if (operand1 != null && operand2 != null) {
                    String op1 = operand1.getRawSignature();
                    String op2 = operand2.getRawSignature();
                    String exprImage = binExpr.getRawSignature();
                    return op1.equals(op2)
                            // When macro is used, RawSignature returns macro name, see bug 321933
                            && !op1.equals(exprImage);
                }
            }
        }
        return false;
    }
}
