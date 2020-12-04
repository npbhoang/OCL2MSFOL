/**************************************************************************
Copyright 2020 ngpbh
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

@author: ngpbh
***************************************************************************/

package org.vgu.se.smt.ocl;

import org.vgu.dm2schema.dm.DataModel;

import com.vgu.se.jocl.expressions.AssociationClassCallExp;
import com.vgu.se.jocl.expressions.BooleanLiteralExp;
import com.vgu.se.jocl.expressions.Expression;
import com.vgu.se.jocl.expressions.IntegerLiteralExp;
import com.vgu.se.jocl.expressions.IteratorExp;
import com.vgu.se.jocl.expressions.LiteralExp;
import com.vgu.se.jocl.expressions.OperationCallExp;
import com.vgu.se.jocl.expressions.PropertyCallExp;
import com.vgu.se.jocl.expressions.RealLiteralExp;
import com.vgu.se.jocl.expressions.StringLiteralExp;
import com.vgu.se.jocl.expressions.VariableExp;
import com.vgu.se.jocl.expressions.sql.functions.SqlFnCurdate;
import com.vgu.se.jocl.expressions.sql.functions.SqlFnTimestampdiff;

public class O2F_InvalidVisitor extends OCL2MSFOLVisitor {

    public O2F_InvalidVisitor(DataModel dm) {
        this.setDataModel(dm);
    }

    @Override
    public void visit(Expression exp) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(IteratorExp iteratorExp) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(OperationCallExp operationCallExp) {
        switch (operationCallExp.getReferredOperation().getName()) {
        case "allInstances":
            break;
        case "oclIsUndefined":
            String template = Template.Invalid.oclIsUndefined;
            this.setFOLFormulae(template);
            break;
        case "oclIsInvalid":
            template = Template.Invalid.oclIsInvalid;
            this.setFOLFormulae(template);
            break;
        case "oclIsKindOf":
            template = Template.Invalid.oclIsKindOf;
            this.setFOLFormulae(template);
            break;
        case "oclIsTypeOf":
            template = Template.Invalid.oclIsTypeOf;
            this.setFOLFormulae(template);
            break;
        case "oclAsType":
            break;
        case "=":
            template = Template.Invalid.equality;

            Expression exp = operationCallExp.getSource();
            Expression argExp = operationCallExp.getArguments().get(0);
            nullVisitor = new O2F_NullVisitor(dm);
            exp.accept(nullVisitor);
            String firstArgument = nullVisitor.getFOLFormulae();
            argExp.accept(nullVisitor);
            String secondArgument = nullVisitor.getFOLFormulae();
            invalVisitor = new O2F_InvalidVisitor(dm);
            exp.accept(invalVisitor);
            String thirdArgument = invalVisitor.getFOLFormulae();
            argExp.accept(invalVisitor);
            String forthArgument = invalVisitor.getFOLFormulae();

            this.setFOLFormulae(String.format(template, firstArgument,
                secondArgument, thirdArgument, forthArgument));
            break;
        case "<>":
            template = Template.Invalid.inequality;

            exp = operationCallExp.getSource();
            argExp = operationCallExp.getArguments().get(0);
            nullVisitor = new O2F_NullVisitor(dm);
            exp.accept(nullVisitor);
            firstArgument = nullVisitor.getFOLFormulae();
            argExp.accept(nullVisitor);
            secondArgument = nullVisitor.getFOLFormulae();
            invalVisitor = new O2F_InvalidVisitor(dm);
            exp.accept(invalVisitor);
            thirdArgument = invalVisitor.getFOLFormulae();
            argExp.accept(invalVisitor);
            forthArgument = invalVisitor.getFOLFormulae();

            this.setFOLFormulae(String.format(template, firstArgument,
                secondArgument, thirdArgument, forthArgument));
            break;
        case "<=":
        case ">=":
        case ">":
        case "<":
            break;
        case "and":
            template = Template.Invalid.and;

            exp = operationCallExp.getSource();
            argExp = operationCallExp.getArguments().get(0);
            invalVisitor = new O2F_InvalidVisitor(dm);
            exp.accept(invalVisitor);
            firstArgument = invalVisitor.getFOLFormulae();
            argExp.accept(invalVisitor);
            secondArgument = invalVisitor.getFOLFormulae();
            trueVisitor = new O2F_TrueVisitor(dm);
            exp.accept(trueVisitor);
            String fifthArgument = trueVisitor.getFOLFormulae();
            argExp.accept(trueVisitor);
            thirdArgument = trueVisitor.getFOLFormulae();
            nullVisitor = new O2F_NullVisitor(dm);
            exp.accept(nullVisitor);
            String sixthArgument = nullVisitor.getFOLFormulae();
            argExp.accept(invalVisitor);
            forthArgument = nullVisitor.getFOLFormulae();

            this.setFOLFormulae(String.format(template, firstArgument,
                secondArgument, thirdArgument, forthArgument, fifthArgument,
                sixthArgument));
            break;
        case "or":
            template = Template.Invalid.or;

            exp = operationCallExp.getSource();
            argExp = operationCallExp.getArguments().get(0);
            invalVisitor = new O2F_InvalidVisitor(dm);
            exp.accept(invalVisitor);
            firstArgument = invalVisitor.getFOLFormulae();
            argExp.accept(invalVisitor);
            secondArgument = invalVisitor.getFOLFormulae();
            falseVisitor = new O2F_FalseVisitor(dm);
            exp.accept(falseVisitor);
            fifthArgument = falseVisitor.getFOLFormulae();
            argExp.accept(falseVisitor);
            thirdArgument = falseVisitor.getFOLFormulae();
            nullVisitor = new O2F_NullVisitor(dm);
            exp.accept(nullVisitor);
            sixthArgument = nullVisitor.getFOLFormulae();
            argExp.accept(invalVisitor);
            forthArgument = nullVisitor.getFOLFormulae();

            this.setFOLFormulae(String.format(template, firstArgument,
                secondArgument, thirdArgument, forthArgument, fifthArgument,
                sixthArgument));
            break;
        case "not":
            template = Template.Invalid.not;
            exp = operationCallExp.getArguments().get(0);
            invalVisitor = new O2F_InvalidVisitor(dm);
            exp.accept(invalVisitor);
            this.setFOLFormulae(
                String.format(template, invalVisitor.getFOLFormulae()));
            break;
        case "implies":
            template = Template.Invalid.implies;

            exp = operationCallExp.getSource();
            argExp = operationCallExp.getArguments().get(0);

            invalVisitor = new O2F_InvalidVisitor(dm);
            exp.accept(invalVisitor);
            firstArgument = invalVisitor.getFOLFormulae();
            argExp.accept(invalVisitor);
            secondArgument = invalVisitor.getFOLFormulae();

            this.setFOLFormulae(
                String.format(template, firstArgument, secondArgument));
            break;
        case "size":
            break;
        case "isEmpty":
            break;
        case "notEmpty":
            break;
        case "isUnique":
            break;
        case "flatten":
            break;
        default:
            break;
        }
    }

    @Override
    public void visit(LiteralExp literalExp) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(StringLiteralExp stringLiteralExp) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(BooleanLiteralExp booleanLiteralExp) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(IntegerLiteralExp integerLiteralExp) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(RealLiteralExp realLiteralExp) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(PropertyCallExp propertyCallExp) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(AssociationClassCallExp associationClassCallExp) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(VariableExp variableExp) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(SqlFnCurdate variableExp) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(SqlFnTimestampdiff variableExp) {
        // TODO Auto-generated method stub

    }

}