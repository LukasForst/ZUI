package cz.cvut.student;

import cz.cvut.fel.aic.zui.gobblet.Gobblet;
import cz.cvut.fel.aic.zui.gobblet.algorithm.Algorithm;
import cz.cvut.fel.aic.zui.gobblet.environment.Board;
import cz.cvut.fel.aic.zui.gobblet.environment.Move;

import java.util.ArrayList;
import java.util.Random;

public class AlphaBeta extends Algorithm {
    @Override
    protected int runImplementation(Board game, int depth, int player, int alpha, int beta) {

        ArrayList<Move> successors = game.generatePossibleMoves(player);

        Random rnd = new Random();
        Move randomMove = successors.get(rnd.nextInt(successors.size()));

        Board subGame = new Board(game);
        if (!subGame.makeMove(randomMove))
            System.err.println("Something is terribly wrong in AB!");

        int subGameHeuristicValue = subGame.evaluateBoard();

        if (depth > 0) {
            int subGameValue = run(subGame, depth - 1, Gobblet.switchPlayer(player), alpha, beta);
        }

        return -1;
    }
}
