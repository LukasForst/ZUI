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
        if (alpha == Integer.MIN_VALUE) {
            alpha += 10;
        }

        if (beta == Integer.MAX_VALUE){
            beta =-10;
        }

        if (winningPlayer == Integer.MIN_VALUE) {
            winningPlayer = player;
        }

        Collection<Board> succ = evaluateSuccessors(game, player);
        if (succ.isEmpty() || depth == 0) {
            if (player != WHITE_PLAYER) {
                return -game.evaluateBoard();
            }
            return game.evaluateBoard();
        }

        int b = beta;
        boolean firstChild = true;
        for (Board g : succ) {
            int v = -run(g, depth - 1, Gobblet.switchPlayer(player), -b, -alpha);

            if (firstChild) {
                firstChild = false;
            } else {
                if (alpha < v && v < beta) {
                    v = -run(g, depth - 1, Gobblet.switchPlayer(player), -beta, -v);
                }
            }
            alpha = Math.max(alpha, v);

            if (beta <= alpha) return alpha;
            b = alpha + 1;
        }
        return alpha;
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
