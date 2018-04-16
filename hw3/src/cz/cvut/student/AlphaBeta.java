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

    private int winningPlayer = Integer.MIN_VALUE;

    //alpha - best known value for MAX phase
    //beta - best known value for MIN phase
    @Override
    protected int runImplementation(Board game, int depth, int player, int alpha, int beta) {
        if (winningPlayer == Integer.MIN_VALUE) {
            winningPlayer = player;
        }

        if (alpha == Integer.MIN_VALUE) {
            alpha += 1;
        }

        if (beta == Integer.MAX_VALUE) {
            beta -= 1;
        }

        Collection<Board> succ = evaluateSuccessors(game, player);
        if (depth == 0 || game.isTerminate(player) != Board.DUMMY || succ.isEmpty()) {
            if (player == WHITE_PLAYER) {
                return game.evaluateBoard();
            } else {
                return -game.evaluateBoard();
            }
        }

        boolean firstChild = true;
        int score;
        for (Board g : succ) {
            if (firstChild) {
                firstChild = false;
                score = -run(g, depth - 1, Gobblet.switchPlayer(player), -beta, -alpha);
            } else {
                score = -run(g, depth - 1, Gobblet.switchPlayer(player), -alpha - 1, -alpha);
                if (alpha < score && score < beta) {
                    score = -run(g, depth - 1, Gobblet.switchPlayer(player), -beta, -score);
                }
            }
            alpha = Math.max(alpha, score);
            if (alpha >= beta) break;
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
