package com.company;

import java.util.Scanner;

public class Ameoba {

    char[][] board = new char[3][3];

    public void printStart() {
        System.out.println("üdv az amöbában!");
        System.out.println("Az egyes számú játékos a kör, a kettes számú játékos a kereszt!");
        System.out.println("A mező az alábbi szerint néz ki, a játék során az ennek megfelelő számkódot adjátok meg!");
        System.out.println("00  01  02\n10  11  12\n20  21  22");
    }

    public Ameoba() {
        init();
    }

    private void init() {
        //feltölti a táblát '_' karakterekkel.
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = '_';
            }
        }
    }

    private void drawBoard() {

//kirajzolja a táblát
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void play() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            //két eset van, az egyes vagy kettes játékos lép, ez ismétlődik amíg valamiért nem kell megállnia
            //ilyen ha megtelik a tábla(fullBoard(), vagy rossz számkód vagy foglalt poziciót ad meg a játékos (insertSymbol())
            //ha insertSymbol() eredménye true, tovább megy és megvizsgálja, hogy valamilyen kombinációban megvan-e az adott szimbolum háromszor.

            boolean wasInsertSuccessful = false;
            while (wasInsertSuccessful == false) {
                drawBoard();
                if (!fullBoard()) {
                    System.out.println("Kérem az egyes játékos adja meg a lépését:");
                    String playerOne = scan.next();
                    wasInsertSuccessful = insertSymbol(playerOne, 'O');
                    if (wasInsertSuccessful) {
                        if (isCharacterWinner('O')) {
                            System.out.println("Nyert a kör!");
                            drawBoard();
                            return;
                        }
                    }
                } else {
                    System.out.println("Megtelt a tábla! Az eredmény döntetlen.");
                    return;
                }
            }
            wasInsertSuccessful = false;
            while (wasInsertSuccessful == false) {
                drawBoard();
                if (!fullBoard()) {
                    System.out.println("Kérem a kettes játékos adja meg a lépését:");
                    String playerTwo = scan.next();
                    wasInsertSuccessful = insertSymbol(playerTwo, 'X');
                    if (wasInsertSuccessful) {
                        if (isCharacterWinner('X')) {
                            System.out.println("Nyert a kereszt!");
                            drawBoard();
                            return;
                        }
                    }
                } else {
                    System.out.println("Megtelt a tábla!Az eredmény döntetlen.");
                    return;
                }
            }

        }

    }


    private boolean insertSymbol(String index, char symbol) {
        //a játékos által beírt szimbolum elhelyezés a táblán
        //megvizsgálja, hogy a megfelelő számkódot írta be (0-2) között, majd a Stringből-karakterből-számmá alakítjuk,
        //hogy a tábla megadott i, j helyére kerüljön az adott szimbolum, közben vizsgáljuk, hogy
        //foglalt e a pozíció
        if (index.matches("[0-2][0-2]")) {

            char[] indexes = index.toCharArray();
            int i = Integer.parseInt(indexes[0] + "");
            int j = Integer.parseInt(indexes[1] + "");
            if (board[i][j] == '_') {
                board[i][j] = symbol;
                return true;
            } else {
                System.out.println("Már foglalt a pozíció!");
            }
        } else {
            System.out.println("Nem megfelelő számkód");
        }
        return false;
    }

    private boolean fullBoard() {

        //megvizsgáljuk hogy van-e még '_' karakter, ha nincs, akkor megtelt a tábla
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '_') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isCharacterWinner(char symbol) {

        return checkColumn(symbol) || checkRow(symbol) || checkDiagonalLeftToRight(symbol) || checkDiagonalRightToLeft(symbol);
    }


    private boolean checkRow(char symbol) {
        //megvizsgálja hogy, sorban összegyűlt három szimbolum.

        for (int i = 0; i < board.length; i++) {
            boolean win = true;
            for (int j = 0; j < board[i].length; j++) {

                if (board[i][j] != symbol) {
                    win = false;
                }

            }
            if (win == true) {
                return true;
            }
        }
        return false;
    }


    private boolean checkColumn(char symbol) {
        //megvizsgálja, hogy az oszlopba összegyűlt három azonos szimbolum
        for (int i = 0; i < board.length; i++) {
            boolean win = true;
            for (int j = 0; j < board[i].length; j++) {
//oszloponként nézi ha j és i -t felcseréljük.
                if (board[j][i] != symbol) {
                    win = false;
                }

            }
            if (win == true) {
                return true;
            }

        }

        return false;
    }

    private boolean checkDiagonalLeftToRight(char symbol) {
        //megvizsgálja hogy átlóba ósszegyűlt három azonos szimbolum
        boolean win = true;
        for (int i = 0; i < board.length; i++) {
            if (board[i][i] != symbol)
            //átlóba lép ha mindkettő i.
            {
                win = false;
            }

        }

        return win;
    }

    private boolean checkDiagonalRightToLeft(char symbol) {
        //megvizsgálja hogy átlóba ósszegyűlt három azonos szimbolum
        boolean win = true;
        for (int i = 0, j = board.length - 1; i < board.length; i++, j--)
        //egyszerre lépteti i és j-t, átlóba lép
        {
            if (board[i][j] != symbol) {
                win = false;
            }
        }

        return win;
    }


}
