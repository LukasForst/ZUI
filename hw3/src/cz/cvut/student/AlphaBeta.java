package cz.cvut.student;

import cz.cvut.fel.aic.zui.gobblet.Gobblet;
import cz.cvut.fel.aic.zui.gobblet.algorithm.Algorithm;
import cz.cvut.fel.aic.zui.gobblet.environment.Board;
import cz.cvut.fel.aic.zui.gobblet.environment.Move;

import java.util.ArrayList;
import java.util.Random;

public class AlphaBeta extends Algorithm {

    //alpha - best known value for MAX phase
    //beta - best known value for MIN phase
    @Override
    protected int runImplementation(Board game, int depth, int player, int alpha, int beta) {

        ArrayList<Move> successors = game.generatePossibleMoves(player);

        //negascout
        if (successors.isEmpty() || depth == 0) {
            return game.evaluateBoard();
        }

        int b = beta;
        for (int i = 0; i < successors.size(); i++) {
            Move succ = successors.get(i);
            Board nextGame = new Board(game);
            nextGame.makeMove(succ);

            int v = -run(nextGame, depth - 1, Gobblet.switchPlayer(player), -b, -alpha);
            if (alpha < v && v < beta && i != 0) {
                v = -run(nextGame, depth - 1, Gobblet.switchPlayer(player), -beta, -v);
            }
            alpha = Math.max(alpha, v);
            if (alpha >= beta) {
                return alpha;
            }
            b = alpha + 1;
        }

        return alpha;
    }
}
