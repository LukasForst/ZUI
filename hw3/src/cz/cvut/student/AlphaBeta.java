package cz.cvut.student;

import com.sun.org.apache.xml.internal.security.algorithms.implementations.IntegrityHmac;
import cz.cvut.fel.aic.zui.gobblet.Gobblet;
import cz.cvut.fel.aic.zui.gobblet.algorithm.Algorithm;
import cz.cvut.fel.aic.zui.gobblet.environment.Board;
import cz.cvut.fel.aic.zui.gobblet.environment.Move;

import java.util.*;

public class AlphaBeta extends Algorithm {
    private static final int WHITE_PLAYER = 0;
    private static final int BLACK = 4;

    private int winningPlayer = Integer.MIN_VALUE;

    //alpha - best known value for MAX phase
    //beta - best known value for MIN phase
    @Override
    protected int runImplementation(Board game, int depth, int player, int alpha, int beta) {
        if(alpha == Integer.MIN_VALUE){
            alpha -= 10;
        }

        if(winningPlayer == Integer.MIN_VALUE){
            winningPlayer = player;
        }

        Queue<Board> queue = evaluateSuccessors(game, player);
        if (queue.isEmpty() || depth == 0) return game.evaluateBoard();

        int b = beta;
        int score = Integer.MIN_VALUE;
        boolean firstChild = true;
        while (!queue.isEmpty()){
            Board g = queue.poll();
            score = Math.max(score, -run(g, depth - 1, Gobblet.switchPlayer(player), -b, -alpha));

            if(alpha < score){
                score = Math.max(score, -run(g, depth - 1, Gobblet.switchPlayer(player), -beta, -alpha));
            }

            alpha = Math.max(alpha, score);

            if (beta <= alpha) break;
            b = alpha + 1;
        }
        return score;
    }

    private Queue<Board> evaluateSuccessors(Board game, int player){
        Queue<Board> queue;

        if(WHITE_PLAYER == winningPlayer){
            queue = new PriorityQueue<>((o1, o2) -> o2.evaluateBoard() - o1.evaluateBoard());
        } else {
            queue = new PriorityQueue<>((Comparator.comparingInt(Board::evaluateBoard)));
        }

        ArrayList<Move> successors = game.generatePossibleMoves(player);
        for(Move m : successors){
            Board g = new Board(game);
            g.makeMove(m);
            queue.add(g);
        }
        return queue;
    }
}
