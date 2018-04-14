package cz.cvut.student;

import cz.cvut.fel.aic.zui.gobblet.Gobblet;
import cz.cvut.fel.aic.zui.gobblet.algorithm.Algorithm;
import cz.cvut.fel.aic.zui.gobblet.environment.Board;
import cz.cvut.fel.aic.zui.gobblet.environment.Move;

import java.util.ArrayList;
import java.util.Random;

public class AlphaBeta extends Algorithm {
    private int winningPlayer = Integer.MIN_VALUE;

    //alpha - best known value for MAX phase
    //beta - best known value for MIN phase
    @Override
    protected int runImplementation(Board game, int depth, int player, int alpha, int beta) {
        ArrayList<Move> successors = game.generatePossibleMoves(player);
        if (successors.isEmpty() || depth == 0) return game.evaluateBoard();

        if (winningPlayer == Integer.MIN_VALUE) {
            winningPlayer = player;
        }

        if (winningPlayer == player) {
            int score = Integer.MIN_VALUE;
            for (Move m : successors) {
                Board b = new Board(game);
                b.makeMove(m);
                score = Math.max(score, run(b, depth - 1, Gobblet.switchPlayer(player), alpha, beta));
                alpha = Math.max(alpha, score);
                if (alpha >= beta) break;
            }
            return score;
        } else {
            int score = Integer.MAX_VALUE;

            for (Move m : successors) {
                Board b = new Board(game);
                b.makeMove(m);
                score = Math.min(score, run(b, depth - 1, Gobblet.switchPlayer(player), alpha, beta));
                beta = Math.min(beta, score);
                if (alpha >= beta) break;
            }
            return score;
        }
    }
}
