import java.util.Scanner;
public class MagneticCave {
    private static final int BOARD_SIZE = 8;
    private static final char PLAYER1_BRICK = '■';
    private static final char PLAYER2_BRICK = '□';
    private char[][] board;
    private boolean player1Turn;
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public MagneticCave() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        player1Turn = true;

        // Initialize the board with empty cells
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = ' ';
            }
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public void printBoard() {
        System.out.println("    A    B    C    D    E    F    G    H ");
        System.out.println("  ┌────┬────┬────┬────┬────┬────┬────┬────┐");
        for (int i = BOARD_SIZE - 1; i >= 0; i--) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print("│   " + board[i][j]);
            }
            System.out.println("│   " + (i + 1));
            if (i > 0) {
                System.out.println("  ├────┼────┼────┼────┼────┼────┼────┼────┤");
            }
        }
        System.out.println("  └────┴────┴────┴────┴────┴────┴────┴────┘");
        System.out.println("     A    B    C    D    E    F    G    H ");
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean isMoveValid(int row, int col) {
        if (board[row][col] != ' ') {
            return false;
        }

        // Check if the move is stacked directly on the left or right wall
        if (col == 0 || col == BOARD_SIZE - 1) {
            return true;
        }

        // Check if the move is stacked to the left or right of another brick
        if (col > 0 && board[row][col - 1] != ' ') {
            return true;
        }
        if (col < BOARD_SIZE - 1 && board[row][col + 1] != ' ') {
            return true;
        }

        return false;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean isBoardFull() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean hasPlayerWon(int row, int col, char player) {
        int count;

        // Check row
        count = 1;
        int j = col - 1;
        while (j >= 0 && board[row][j] == player) {
            count++;
            j--;
        }
        j = col + 1;
        while (j < BOARD_SIZE && board[row][j] == player) {
            count++;
            j++;
        }
        if (count >= 5) {
            return true;
        }

        // Check column
        count = 1;
        int i = row - 1;
        while (i >= 0 && board[i][col] == player) {
            count++;
            i--;
        }
        i = row + 1;
        while (i < BOARD_SIZE && board[i][col] == player) {
            count++;
            i++;
        }
        if (count >= 5) {
            return true;
        }

        // Check diagonal (top-left to bottom-right)
        count = 1;
        i = row - 1;
        j = col - 1;
        while (i >= 0 && j >= 0 && board[i][j] == player) {
            count++;
            i--;
            j--;
        }
        i = row + 1;
        j = col + 1;
        while (i < BOARD_SIZE && j < BOARD_SIZE && board[i][j] == player) {
            count++;
            i++;
            j++;
        }
        if (count >= 5) {
            return true;
        }

        // Check diagonal (top-right to bottom-left)
        count = 1;
        i = row - 1;
        j = col + 1;
        while (i >= 0 && j < BOARD_SIZE && board[i][j] == player) {
            count++;
            i--;
            j++;
        }
        i = row + 1;
        j = col - 1;
        while (i < BOARD_SIZE && j >= 0 && board[i][j] == player) {
            count++;
            i++;
            j--;
        }
        if (count >= 5) {
            return true;
        }

        return false;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    private void makeMoveManual(int row, int col, char player) {
        board[row][col] = player;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    private void playManual() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printBoard();
            char currentPlayer = player1Turn ? PLAYER1_BRICK : PLAYER2_BRICK;
            System.out.println("Player " + (player1Turn ? "1" : "2") + ", it's your turn.");

           /* System.out.print("Enter the row (1-8): ");
            int row = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            System.out.print("Enter the column (A-H): ");
            String colStr = scanner.nextLine().toUpperCase();
            int col = colStr.charAt(0) - 'A';*/

            System.out.println("Enter the move: ");
            String moveStr = scanner.nextLine();
            int row = Character.getNumericValue(moveStr.charAt(0)) ;
            int col = moveStr.charAt(1) - 'A';



            if (row < 1 || row > BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
                System.out.println("Invalid move! Please try again.");
                continue;
            }

            if (!isMoveValid(row - 1, col)) {
                System.out.println("Invalid move! Please try again.");
                continue;
            }

            makeMoveManual(row - 1, col, currentPlayer);

            if (hasPlayerWon(row - 1, col, currentPlayer)) {
                printBoard();
                System.out.println("Player " + (player1Turn ? "1" : "2") + " wins!");
                break;
            }

            if (isBoardFull()) {
                printBoard();
                System.out.println("It's a draw!");
                break;
            }

            player1Turn = !player1Turn;
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean makeMove(int row, int col) {
        if (isMoveValid(row, col)) {
            char player = player1Turn ? PLAYER1_BRICK : PLAYER2_BRICK;
            board[row][col] = player;
            return true;
        }
        return false;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean makeMove2(int row, int col) {
        if (isMoveValid(row, col)) {
            char player = player1Turn ? PLAYER2_BRICK : PLAYER1_BRICK;
            board[row][col] = player;
            return true;
        }
        return false;
    }

    private void undoMove(int row, int col) {
        board[row][col] = ' ';
    }

    private int evaluateBoard() {
        int player1Score = 0;
        int player2Score = 0;

        // Check rows for consecutive bricks
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j <= BOARD_SIZE - 5; j++) {
                int player1Count = 0;
                int player2Count = 0;
                for (int k = 0; k < 5; k++) {
                    if (board[i][j + k] == PLAYER1_BRICK) {
                        player1Count++;
                    } else if (board[i][j + k] == PLAYER2_BRICK) {
                        player2Count++;
                    }
                }
                if (player1Count == 5) {
                    player1Score += 100; // Player 1 wins
                } else if (player2Count == 5) {
                    player2Score += 100; // Player 2 wins
                } else {
                    player1Score += player1Count * player1Count;
                    player2Score += player2Count * player2Count;
                }
            }
        }

        // Check columns for consecutive bricks
        for (int j = 0; j < BOARD_SIZE; j++) {
            for (int i = 0; i <= BOARD_SIZE - 5; i++) {
                int player1Count = 0;
                int player2Count = 0;
                for (int k = 0; k < 5; k++) {
                    if (board[i + k][j] == PLAYER1_BRICK) {
                        player1Count++;
                    } else if (board[i + k][j] == PLAYER2_BRICK) {
                        player2Count++;
                    }
                }
                if (player1Count == 5) {
                    player1Score += 100; // Player 1 wins
                } else if (player2Count == 5) {
                    player2Score += 100; // Player 2 wins
                } else {
                    player1Score += player1Count * player1Count;
                    player2Score += player2Count * player2Count;
                }
            }
        }

        // Check diagonals (top-left to bottom-right) for consecutive bricks
        for (int i = 0; i <= BOARD_SIZE - 5; i++) {
            for (int j = 0; j <= BOARD_SIZE - 5; j++) {
                int player1Count = 0;
                int player2Count = 0;
                for (int k = 0; k < 5; k++) {
                    if (board[i + k][j + k] == PLAYER1_BRICK) {
                        player1Count++;
                    } else if (board[i + k][j + k] == PLAYER2_BRICK) {
                        player2Count++;
                    }
                }
                if (player1Count == 5) {
                    player1Score += 100; // Player 1 wins
                } else if (player2Count == 5) {
                    player2Score += 100; // Player 2 wins
                } else {
                    player1Score += player1Count * player1Count;
                    player2Score += player2Count * player2Count;
                }
            }
        }

        // Check diagonals (top-right to bottom-left) for consecutive bricks
        for (int i = 0; i <= BOARD_SIZE - 5; i++) {
            for (int j = 4; j < BOARD_SIZE; j++) {
                int player1Count = 0;
                int player2Count = 0;
                for (int k = 0; k < 5; k++) {
                    if (board[i + k][j - k] == PLAYER1_BRICK) {
                        player1Count++;
                    } else if (board[i + k][j - k] == PLAYER2_BRICK) {
                        player2Count++;
                    }
                }
                if (player1Count == 5) {
                    player1Score += 100; // Player 1 wins
                } else if (player2Count == 5) {
                    player2Score += 100; // Player 2 wins
                } else {
                    player1Score += player1Count * player1Count;
                    player2Score += player2Count * player2Count;
                }
            }
        }

        if (player1Score > player2Score) {
            return player1Score;
        } else if (player2Score > player1Score) {
            return -player2Score;
        } else {
            return 0; // Tie
        }
    }

    private int minimax(int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || isBoardFull()) {
            return evaluateBoard();
        }

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (makeMove2(i, j)) {
                        int eval = minimax(depth - 1, alpha, beta, false);
                        undoMove(i, j);
                        maxEval = Math.max(maxEval, eval);
                        alpha = Math.max(alpha, eval);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (makeMove2(i, j)) {
                        int eval = minimax(depth - 1, alpha, beta, true);
                        undoMove(i, j);
                        minEval = Math.min(minEval, eval);
                        beta = Math.min(beta, eval);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return minEval;
        }
    }

    private Move getBestMove() {
        int bestScore = Integer.MIN_VALUE;
        Move bestMove = null;
        char player = player1Turn ? PLAYER1_BRICK : PLAYER2_BRICK;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (makeMove2(i, j)) {
                    int score = minimax(5, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                    undoMove(i, j);

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new Move(i, j, player);
                    }
                }
            }
        }

        return bestMove;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public void playGame1() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Magnetic Cave Game");
        System.out.println("Player 1: " + PLAYER1_BRICK);
        System.out.println("Player 2: " + PLAYER2_BRICK);
        System.out.println("Enter moves in the format: row column (e.g., 1A)");

        while (true) {
            printBoard();

            if (player1Turn) {
                System.out.print("Player 1's move: ");
                if (scanner.hasNext()) {
                    String moveStr = scanner.next();
                    if (moveStr.equalsIgnoreCase("quit")) {
                        System.out.println("Game ended by player 1.");
                        break;
                    }
                    if (moveStr.length() == 2) {
                        int row = Character.getNumericValue(moveStr.charAt(0)) - 1;
                        int col = moveStr.charAt(1) - 'A';
                        if (makeMove(row, col)) {
                            if (hasPlayerWon(row, col, PLAYER1_BRICK)) {
                                printBoard();
                                System.out.println("Player 1 wins!");
                                break;
                            }
                            player1Turn = false;
                        } else {
                            System.out.println("Invalid move! Try again.");
                        }
                    } else {
                        System.out.println("Invalid move! Try again.");
                    }
                }
            } else {
                System.out.println("Player 2's move (automatic): ");
                Move bestMove = getBestMove();
                if (bestMove != null) {
                    System.out.println("Player 2 places " + bestMove.getBrick() + " at " + (bestMove.getRow() + 1) + ((char) (bestMove.getCol() + 'A')));
                    makeMove(bestMove.getRow(), bestMove.getCol());
                    if (hasPlayerWon(bestMove.getRow(), bestMove.getCol(), PLAYER2_BRICK)) {
                        printBoard();
                        System.out.println("Player 2 wins!");
                        break;
                    }
                    player1Turn = true;
                }
            }
        }

        scanner.close();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public void playGame2() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Magnetic Cave Game");
        System.out.println("Player 1: " + PLAYER2_BRICK);
        System.out.println("Player 2: " + PLAYER1_BRICK);
        System.out.println("Enter moves in the format: row column (e.g., 1A)");

        while (true) {
            printBoard();

            if (player1Turn) {
                System.out.print("Player 1's move: ");
                if (scanner.hasNext()) {
                    String moveStr = scanner.next();
                    if (moveStr.equalsIgnoreCase("quit")) {
                        System.out.println("Game ended by player 1.");
                        break;
                    }
                    if (moveStr.length() == 2) {
                        int row = Character.getNumericValue(moveStr.charAt(0)) - 1;
                        int col = moveStr.charAt(1) - 'A';
                        if (makeMove2(row, col)) {
                            if (hasPlayerWon(row, col, PLAYER2_BRICK)) {
                                printBoard();
                                System.out.println("Player 1 wins!");
                                break;
                            }
                            player1Turn = false;
                        } else {
                            System.out.println("Invalid move! Try again.");
                        }
                    } else {
                        System.out.println("Invalid move! Try again.");
                    }
                }
            } else {
                System.out.println("Player 2's move (automatic): ");
                Move bestMove = getBestMove();
                if (bestMove != null) {
                    System.out.println("Player 2 places " + bestMove.getBrick() + " at " + (bestMove.getRow() + 1) + ((char) (bestMove.getCol() + 'A')));
                    makeMove2(bestMove.getRow(), bestMove.getCol());
                    if (hasPlayerWon(bestMove.getRow(), bestMove.getCol(), PLAYER1_BRICK)) {
                        printBoard();
                        System.out.println("Player 2 wins!");
                        break;
                    }
                    player1Turn = true;
                }
            }
        }

        scanner.close();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        MagneticCave game = new MagneticCave();
        Scanner scanner = new Scanner(System.in);

        ////////////////////
        System.out.println();
        System.out.println();
        System.out.println("Welcome to Magnetic Cave game!");
        System.out.println("The rules of the game are simple:");
        System.out.println("- Initially, the cave (the board) is empty.");
        System.out.println("- Player ■ and player □ move in an alternate fashion, starting with ■. So ■ starts, followed by □, then ■ again, then □ again, ...");
        System.out.println("- Because there are two big magnets on each side of the cave, a player can only place a brick on an empty cell of the cave \n  provided that the brick is stacked directly on the left or right wall, or is stacked to the left or the right of another brick (of any color).");
        System.out.println("- As soon as one player is able to align 5 consecutive bricks in a row, in a column or in a diagonal, then this player wins the game.");
        System.out.println("- If no player is able to achieve a winning configuration and the board is full, then the game stops and there is a tie.");
        ////////////////////
        System.out.println();
        System.out.println("♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣");
        System.out.println("♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣ START THE GAME ♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣");
        System.out.println("♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣");
        System.out.println();
        System.out.println();

        System.out.println("Select a play mode:");
        System.out.println("1. Manual entry for both ■'s moves and □'s moves");
        System.out.println("2. Manual entry for ■'s moves & automatic moves for □");
        System.out.println("3. Manual entry for □'s moves & automatic moves for ■");
        int mode = scanner.nextInt();

        switch (mode) {
            case 1:
                game.playManual();
                break;
            case 2:
                game.playGame1();
                break;
            case 3:
                game.playGame2();
                break;
            default:
                System.out.println("Invalid mode. Exiting the game.");
                break;
        }

        scanner.close();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    private static class Move {
        private int row;
        private int col;
        private char brick;

        public Move(int row, int col, char brick) {
            this.row = row;
            this.col = col;
            this.brick = brick;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public char getBrick() {
            return brick;
        }
    }
}