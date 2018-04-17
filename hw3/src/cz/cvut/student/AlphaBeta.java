package cz.cvut.student;

import cz.cvut.fel.aic.zui.gobblet.Gobblet;
import cz.cvut.fel.aic.zui.gobblet.algorithm.Algorithm;
import cz.cvut.fel.aic.zui.gobblet.environment.Board;
import cz.cvut.fel.aic.zui.gobblet.environment.Move;

import java.util.*;

public class AlphaBeta extends Algorithm {
    private int winningPlayer = Integer.MIN_VALUE;

    private void setPlayer(int player){
        if (winningPlayer == Integer.MIN_VALUE) {
            winningPlayer = player;
        }
    }

    private int normalizeValue(int a ){
        switch (a){
            case Integer.MAX_VALUE:
                return --a;
            case Integer.MIN_VALUE:
                return ++a;
            default:
                return a;
        }
    }

    private int evaluateBoard(int player, Board game){
        if (player == Board.WHITE_PLAYER) {
            return game.evaluateBoard();
        } else {
            return -game.evaluateBoard();
        }
    }

    @Override
    protected int runImplementation(Board game, int depth, int player, int alpha, int beta) {
        setPlayer(player);
        alpha = normalizeValue(alpha);
        beta = normalizeValue(beta);

        ArrayList<Board> succ = evaluateSuccessors(game, player);
        if (depth == 0 || game.isTerminate(player) != Board.DUMMY || succ.isEmpty()) {
            return evaluateBoard(player, game);
        } else if(depth == 1){
            return evaluateBoard(player, succ.get(0));
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

    private ArrayList<Board> evaluateSuccessors(Board game, int player) {
        ArrayList<Move> successors = game.generatePossibleMoves(player);
        ArrayList<Board> result = new ArrayList<>(successors.size());
        for (Move m : successors) {
            Board b = new Board(game);
            b.makeMove(m);
            result.add(b);
        }
        if (Board.WHITE_PLAYER == player) {
            result.sort(((o1, o2) -> o2.evaluateBoard() - o1.evaluateBoard()));
        } else {
            result.sort((Comparator.comparingInt(Board::evaluateBoard)));
        }
        return result;
    }
}
