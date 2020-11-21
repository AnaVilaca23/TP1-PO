package tp.poquest2;

import gurobi.*;

/**
 *
 * @author Ana Vilaca
 */
public class TPPO2 {

//PROBLEMA DOS PRODUTOS P1 E P2
    public static void main(String[] args) {
        //o algoritmo é definido dentro de um bloco try-catch
        try {

            // cria o ambiente do gurobi e seta as opcoes e inicia o ambiente
            GRBEnv env = new GRBEnv(true);//cria
            env.set("logFile", "mip1.log");//seta
            env.start();//inicia

            // cria um modelo vazio
            GRBModel model = new GRBModel(env);

            // cria as variáveis do problema
            GRBVar x = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "x");
            GRBVar y = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "y");

            // seta a funcao objetivo: maximizar 100x+150y
            //adiciona os termos da funcao
            GRBLinExpr expr = new GRBLinExpr();
            expr.addTerm(100.0, x);
            expr.addTerm(150.0, y);
            model.setObjective(expr, GRB.MAXIMIZE);

            //adiciona as resticoes uma a uma, nao e necessario adicionar a restricao de nao negatividade
            // adiciona as restricoes: x <= 40
            expr = new GRBLinExpr();
            expr.addTerm(1.0, x);
            expr.addTerm(0.0, y);
            model.addConstr(expr, GRB.LESS_EQUAL, 40.0, "c0");

            // adiciona restricao: y <= 30
            expr = new GRBLinExpr();
            expr.addTerm(0.0, x);
            expr.addTerm(1.0, y);
            model.addConstr(expr, GRB.LESS_EQUAL, 30.0, "c1");

            // adiciona restricao: 2x +3y <= 120
            expr = new GRBLinExpr();
            expr.addTerm(2.0, x);
            expr.addTerm(3.0, y);
            model.addConstr(expr, GRB.LESS_EQUAL, 120.0, "c1");

            // modelo de otimizacao
            model.optimize();

            System.out.println(x.get(GRB.StringAttr.VarName)
                    + " " + x.get(GRB.DoubleAttr.X));
            System.out.println(y.get(GRB.StringAttr.VarName)
                    + " " + y.get(GRB.DoubleAttr.X));

            System.out.println("Obj: " + model.get(GRB.DoubleAttr.ObjVal));

            // deleta o modelo e o ambiente
            model.dispose();
            env.dispose();

        } catch (GRBException e) {
            System.out.println("Error code: " + e.getErrorCode() + ". "
                    + e.getMessage());
        }
    }

}
