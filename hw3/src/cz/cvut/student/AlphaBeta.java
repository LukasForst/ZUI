package cz.cvut.student;

import cz.cvut.fel.aic.zui.gobblet.Gobblet;
import cz.cvut.fel.aic.zui.gobblet.algorithm.Algorithm;
import cz.cvut.fel.aic.zui.gobblet.environment.Board;
import cz.cvut.fel.aic.zui.gobblet.environment.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class AlphaBeta extends Algorithm {
    private static final int WHITE_PLAYER = 0;
    private static final int BLACK = 4;

    private int winningPlayer = Integer.MIN_VALUE;

    //alpha - best known value for MAX phase
    //beta - best known value for MIN phase
    @Override
    protected int runImplementation(Board game, int depth, int player, int alpha, int beta) {
        if (winningPlayer == Integer.MIN_VALUE) {
            winningPlayer = player;
        }

        Collection<Board> succ = evaluateSuccessors(game, player);
        if (depth == 0 || game.isTerminate(player) != Board.DUMMY || succ.isEmpty()) {
            return game.evaluateBoard();
        }

        boolean firstChild = true;
        int score;
        if (winningPlayer == player) {
            int b = beta;
            for (Board g : succ) {
                score = run(g, depth - 1, Gobblet.switchPlayer(player), alpha, b);
                if(firstChild){
                    firstChild = false;
                } else{
                    if(alpha < score){
                        score = run(g, depth - 1, Gobblet.switchPlayer(player), alpha, beta);
                    }
                }
                alpha = Math.max(alpha, score);
                if (beta <= alpha) break;
                b = alpha + 1;
            }
            return alpha;
        } else {
            int b = alpha;
            for (Board g : succ) {
                score = run(g, depth - 1, Gobblet.switchPlayer(player), b, beta);
                if(firstChild){
                    firstChild = false;
                } else{
                    if(beta < score){
                        score = run(g, depth - 1, Gobblet.switchPlayer(player), alpha, beta);
                    }
                }
                beta = Math.min(beta, score);
                if (beta <= alpha) break;
                b = beta + 1;
            }
            return beta;
        }
    }

    private Collection<Board> evaluateSuccessors(Board game, int player) {
        ArrayList<Move> successors = game.generatePossibleMoves(player);
        ArrayList<Board> result = new ArrayList<>(successors.size());
        for (Move m : successors) {
            Board b = new Board(game);
            b.makeMove(m);
            result.add(b);
        }
        if (WHITE_PLAYER == player) {
            result.sort(((o1, o2) -> o2.evaluateBoard() - o1.evaluateBoard()));
        } else {
            result.sort((Comparator.comparingInt(Board::evaluateBoard)));
        }
        return result;
    }
}
